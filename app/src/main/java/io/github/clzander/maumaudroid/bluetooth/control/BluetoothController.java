package io.github.clzander.maumaudroid.bluetooth.control;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import io.github.clzander.maumaudroid.app.control.BluetoothControllerListener;
import io.github.clzander.maumaudroid.app.view.MainActivity;
import io.github.clzander.maumaudroid.bluetooth.model.BluetoothConnectionState;
import io.github.clzander.maumaudroid.bluetooth.model.BluetoothModel;
import io.github.clzander.maumaudroid.bluetooth.model.BluetoothModelInterface;
import io.github.clzander.maumaudroid.game.controller.BluetoothGameListener;
import io.github.clzander.maumaudroid.game.controller.CouldNotSerializeException;
import io.github.clzander.maumaudroid.game.model.board.MauMauBoard;
import io.github.clzander.maumaudroid.game.model.board.ProvokedEmptyDeckException;
import io.github.clzander.maumaudroid.game.model.board.cards.Card;
import io.github.clzander.maumaudroid.game.model.board.cards.CardColor;
import io.github.clzander.maumaudroid.game.model.board.cards.CardType;
import io.github.clzander.maumaudroid.game.model.board.cards.MauMauCard;
import io.github.clzander.maumaudroid.game.model.board.deck.MauMauDeck;
import io.github.clzander.maumaudroid.game.model.player.TurnType;

public class BluetoothController implements BluetoothControllerInterface, ConnectionEstablishedListener {

    private final BluetoothModelInterface model;
    private final MainActivity appView;

    private final ActivityResultLauncher<Intent> bluetoothEnableRequestLauncher;
    private final ActivityResultLauncher<Intent> discoverableRequestLauncher;
    private final BroadcastReceiver receiver;
    private final ActivityResultLauncher<String> bluetoothConnectPermissionRequestLauncher;

    private BluetoothControllerListener listener;

    private ServerAcceptThread serverAcceptThread;
    private ClientConnectingThread clientConnectingThread;
    private ConnectedThread connectedThread;
    private DataInputStream dis;
    private DataOutputStream dos;
    private BluetoothGameListener gameListener;


    public BluetoothController(MainActivity mainActivity) {
        this.model = new BluetoothModel(mainActivity.getSystemService(BluetoothManager.class).getAdapter());
        this.model.addBluetoothUpdateListener(mainActivity);
        this.appView = mainActivity;

        this.bluetoothConnectPermissionRequestLauncher =
                mainActivity.registerForActivityResult(new ActivityResultContracts.RequestPermission(),
                        result -> {
                            if (result) {
                                requestBluetoothEnable();
                            } else {
                                onEnterMenuRequestResult(false);
                            }
                        });

        this.bluetoothEnableRequestLauncher =
                mainActivity.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                        result -> onEnterMenuRequestResult(result.getResultCode() == Activity.RESULT_OK));

        this.discoverableRequestLauncher =
                mainActivity.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                        result -> {
                            System.out.println(result.getResultCode());
                    successfulHosting(result.getResultCode() == 120);
                        });

        this.receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if(BluetoothDevice.ACTION_FOUND.equals(action)) {
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    BluetoothController.this.newDeviceDiscovered(device);
                }
            }
        };
    }

    @Override
    public void registerListener(BluetoothControllerListener listener) {
        this.listener = listener;
    }

    @Override
    public void requestBluetoothPermission() {
        if (ActivityCompat.checkSelfPermission(this.appView, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                this.bluetoothConnectPermissionRequestLauncher.launch(Manifest.permission.BLUETOOTH_CONNECT);
            } else {
                this.requestBluetoothEnable();
            }
        } else {
            this.requestBluetoothEnable();
        }
    }

    @Override
    public void requestHosting() {
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        this.discoverableRequestLauncher.launch(discoverableIntent);
    }

    @Override
    public void registerGameListener(BluetoothGameListener listener) {
        this.gameListener = listener;
    }

    @Override
    public void transmitCard(Card lastPlayedCard) {
        this.serializePlayCard(lastPlayedCard, null);
    }

    @Override
    public String firstDevice() {
        return this.model.getFirstDevice();
    }


    private void requestBluetoothEnable() {
        try {
            if (!this.bluetoothEnabled()) {
                Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                this.bluetoothEnableRequestLauncher.launch(enableBluetooth);
            } else {
                this.onEnterMenuRequestResult(true);
            }
        } catch (BluetoothNotSupportedException | SecurityException e) {
            this.onEnterMenuRequestResult(false);
        }
    }

    private void onEnterMenuRequestResult(boolean ready) {
        this.listener.bluetoothGranted(ready);
    }

    private void successfulHosting(boolean result) {
        this.listener.hostingGranted(result);
    }

    @Override
    public boolean bluetoothEnabled() throws BluetoothNotSupportedException {
        if (model.getBluetoothAdapter() == null) {
            throw new BluetoothNotSupportedException();
        } else return this.model.getBluetoothAdapter().isEnabled();
    }


    @Override
    public void startHosting() throws IOException {
        this.model.setIsHost(true);
        this.serverAcceptThread = new ServerAcceptThread(this);
        new Thread(this.serverAcceptThread).start();
    }

    @Override
    public void startDiscovering() throws SecurityException {
        this.model.setIsHost(false);
        Set<BluetoothDevice> devices = this.model.getBluetoothAdapter().getBondedDevices();

        for(BluetoothDevice device : devices) {
            this.model.addDevice(device);
        }

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.appView.registerReceiver(this.receiver, filter);
    }

    @Override
    public void newDeviceDiscovered(BluetoothDevice device) {
        this.model.addToConnectableDevices(device);
    }

    @Override
    public String previousDevice() {
        return this.model.getPreviousDeviceName();
    }

    @Override
    public String nextDevice() {
        return this.model.getNextDeviceName();
    }

    @Override
    public void connectToCurrentDevice() {
        try {
            this.clientConnectingThread = new ClientConnectingThread(this.model.getCurrentDevice(), this);
            new Thread(this.clientConnectingThread).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void connectionEstablished(BluetoothSocket socket) {
        this.model.setState(BluetoothConnectionState.BOUND);
        TurnType turnType;
        if (model.isHost()) {
            turnType = TurnType.FIRST;
        } else {
            turnType = TurnType.SECOND;
        }
        this.listener.connectionEstablished(turnType);
        try {
            this.connectedThread = new ConnectedThread(socket);
            this.dis = new DataInputStream(socket.getInputStream());
            this.dos = new DataOutputStream(socket.getOutputStream());
            new Thread(connectedThread).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This thread listens for incoming connections.
     */
    private class ServerAcceptThread implements Runnable {
        // The local server socket
        private final BluetoothServerSocket localSocket;
        private ConnectionEstablishedListener listener;

        public ServerAcceptThread(ConnectionEstablishedListener listener) throws IOException, SecurityException {

            // Create a new listening server socket
            this.localSocket = model.getBluetoothAdapter().listenUsingRfcommWithServiceRecord(
                    model.getName(), model.getUUID());

            this.listener = listener;

            model.setState(BluetoothConnectionState.LISTENING);
        }

        @Override
        public void run() {
            // Listen to the server socket if we're not connected
            System.out.println(model.getCurrentState());
            if (model.getCurrentState() == BluetoothConnectionState.LISTENING) {
                try {
                    BluetoothSocket remoteSocket = localSocket.accept();
                    this.listener.connectionEstablished(remoteSocket);
                    localSocket.close();
                } catch (IOException | SecurityException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * This thread tries to connect to a another
     */
    private class ClientConnectingThread implements Runnable {

        private BluetoothSocket localSocket;
        private ConnectionEstablishedListener listener;


        public ClientConnectingThread(BluetoothDevice remoteDevice, ConnectionEstablishedListener listener) throws IOException, SecurityException {
            this.localSocket = remoteDevice.createRfcommSocketToServiceRecord(model.getUUID());
            this.listener = listener;
        }

        @Override
        public void run() throws SecurityException {
            model.getBluetoothAdapter().cancelDiscovery();

            try {
                this.localSocket.connect();
                this.listener.connectionEstablished(this.localSocket);
            } catch (IOException e) {
                //TODO connection failed
            }
        }

        public void cancel() throws IOException {
            this.localSocket.close();
        }

    }

    private class ConnectedThread implements Runnable {

        private final BluetoothSocket socket;
        private final DataInputStream dis;
        private final DataOutputStream dos;


        public ConnectedThread(BluetoothSocket socket) throws IOException {
            this.socket = socket;

            this.dis = new DataInputStream(socket.getInputStream());
            this.dos = new DataOutputStream(socket.getOutputStream());
        }


        @Override
        public void run() {
            try {
                //the first player sends his board to the other player
                if(model.isHost()) {
                    //create common board
                    MauMauBoard commonBoard = new MauMauBoard();
                    List<Card> localHand = new ArrayList<>();

                    Card remoteHandCard;

                    try {
                        //send remote player the number of hand cards
                        dos.writeInt(commonBoard.getStartCardNumber());

                        //send hand cards and draw own hand cards
                        for(int i = 0; i < commonBoard.getStartCardNumber(); i++) {
                            //draw card for local player
                            localHand.add(commonBoard.drawCard());

                            //send card to remote player
                            remoteHandCard = commonBoard.drawCard();
                            dos.writeInt(getIntForColor(remoteHandCard.getColor()));
                            dos.writeInt(getIntForType(remoteHandCard.getType()));
                        }

                        //send discard pile card to remote player
                        dos.writeInt(getIntForColor(commonBoard.getLastPlayedCard().getColor()));
                        dos.writeInt(getIntForType(commonBoard.getLastPlayedCard().getType()));

                    } catch (ProvokedEmptyDeckException ignored) {
                    }

                    //send the deck
                    final int numberOfCardsToSend = commonBoard.getDeck().getDeckAsList().size();
                    dos.writeInt(numberOfCardsToSend);

                    for(int i = 0; i < numberOfCardsToSend; i++) {
                        dos.writeInt(getIntForColor(commonBoard.getDeck().getDeckAsList().get(i).getColor()));
                        dos.writeInt(getIntForType(commonBoard.getDeck().getDeckAsList().get(i).getType()));
                    }

                    gameListener.setGameData(commonBoard, localHand);
                    listener.gameSetUp();
                    System.out.println("a host");

                } else {
                    //receive the hand cards
                    final int numberOfHandCards = dis.readInt();

                    List<Card> localHand = new ArrayList<>();

                    CardColor currentHandCardColor;
                    CardType currentHandCardType;

                    for(int i = 0; i < numberOfHandCards; i++) {
                        currentHandCardColor = getColorFromInt(dis.readInt());
                        currentHandCardType = getTypeFromInt(dis.readInt());
                        localHand.add(new MauMauCard(currentHandCardColor, currentHandCardType));
                    }

                    //receive the discard pile card
                    CardColor DPCcolor = getColorFromInt(dis.readInt());
                    CardType DPCType = getTypeFromInt(dis.readInt());
                    Card discardPileCard = new MauMauCard(DPCcolor, DPCType);

                    //receive the deck
                    final int numberOfCardsToRead = dis.readInt();
                    final List<Card> remoteDeck = new ArrayList<>();

                    CardColor currentCardColor;
                    CardType currentCardType;

                    for(int i = 0; i < numberOfCardsToRead; i++) {
                        currentCardColor = getColorFromInt(dis.readInt());
                        currentCardType = getTypeFromInt(dis.readInt());
                        remoteDeck.add(new MauMauCard(currentCardColor, currentCardType));
                    }


                    MauMauBoard board = new MauMauBoard();
                    board.setDeck(new MauMauDeck(remoteDeck));
                    board.setDiscardPileCard(discardPileCard);

                    gameListener.setGameData(board, localHand);
                    listener.gameSetUp();
                    System.out.println("not a host");
                }
            } catch (IOException | CouldNotSerializeException e) {
                e.printStackTrace();
            }
            try {
                boolean again = true;
                while(again) {
                    again = this.read();
                }
            } catch (IOException ignored) {

            }
        }

        private boolean read() throws IOException {
            final int method = this.dis.readInt();

            switch (method) {
                case 0 : deserializePlayCard(); return true;
                case 1 : deserializeDraw(); return true;
                case 2 : deserializeLocalWin(); return true;
                default: return false;
            }
        }
    }


    public void transmitLocalDrawCardCall(int number) {
        this.serializeDraw(number);
    }


    public void transmitLocalPlayCardCall(Card card, CardColor wishedColor) {
        this.serializePlayCard(card, wishedColor);
    }


    public void transmitLocalWin() {
        this.serializeLocalWin();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                                serialization                                                   //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private final static int METHOD_PLAY = 0;
    private final static int METHOD_DRAW = 1;
    private final static int LOCAL_WIN = 2;

    private final static int WISHED_COLOR_IS_NULL_INT = 4;

    private void serializePlayCard(Card card, CardColor wishedColor) {
        try {
            dos.writeInt(METHOD_PLAY);
            dos.writeInt(this.getIntForColor(card.getColor()));
            dos.writeInt(this.getIntForType(card.getType()));

            if(wishedColor != null) {
                dos.writeInt(this.getIntForColor(wishedColor));
            } else {
                dos.writeInt(WISHED_COLOR_IS_NULL_INT);
            }


        } catch (IOException | CouldNotSerializeException e) {
            e.printStackTrace();
        }
    }

    private void serializeDraw(int number) {
        try {
            dos.writeInt(METHOD_DRAW);
            dos.writeInt(number);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void serializeLocalWin() {
        try {
            dos.writeInt(LOCAL_WIN);
        } catch (IOException ignored) {
        }
    }


    private int getIntForColor(CardColor color) throws CouldNotSerializeException{
        int returnValue;
        switch (color) {
            case CLUBS : returnValue = 0; break;
            case SPADES : returnValue = 1; break;
            case HEART : returnValue = 2; break;
            case DIAMONDS : returnValue = 3; break;
            default : throw new CouldNotSerializeException();
        }
        return returnValue;
    }

    private int getIntForType(CardType type) throws CouldNotSerializeException{
        switch (type) {
            case TWO : return 2;
            case THREE : return 3;
            case FOUR : return 4;
            case FIVE : return 5;
            case SIX : return 6;
            case SEVEN : return 7;
            case EIGHT : return 8;
            case NINE : return 9;
            case TEN : return 10;
            case JACK : return 11;
            case QUEEN : return 12;
            case KING : return 13;
            case ACE : return 14;
            default: throw new CouldNotSerializeException();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                               deserialization                                                  //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void deserializePlayCard() throws IOException {
        final int cardColorInt = dis.readInt();
        final int cardTypeInt = dis.readInt();
        final int wishedColorInt = dis.readInt();

        final CardColor color = this.getColorFromInt(cardColorInt);
        final CardType type = this.getTypeFromInt(cardTypeInt);

        final Card card = new MauMauCard(color,type);

        this.gameListener.synchronizeDiscardPile(card);
        this.gameListener.incrementCurrentPlayerIndex();
    }

    private void deserializeDraw() throws IOException {
        int number = dis.readInt();

        this.gameListener.simulateCardDraw(number);
        if(number < 2) {
            //normal draw
            this.gameListener.incrementCurrentPlayerIndex();
        }
    }

    private void deserializeLocalWin() {
        this.gameListener.notifyLoose();
    }

    private CardColor getColorFromInt(int color) {
        switch (color) {
            case 0 : return CardColor.CLUBS;
            case 1 : return CardColor.SPADES;
            case 2 : return CardColor.HEART;
            case 3 : return CardColor.DIAMONDS;
            default : throw new IllegalStateException("Unexpected value: " + color);
        }
    }

    private CardType getTypeFromInt(int type) {
        switch (type) {
            case 2 : return CardType.TWO;
            case 3 : return CardType.THREE;
            case 4 : return CardType.FOUR;
            case 5 : return CardType.FIVE;
            case 6 : return CardType.SIX;
            case 7 : return CardType.SEVEN;
            case 8 : return CardType.EIGHT;
            case 9 : return CardType.NINE;
            case 10 : return CardType.TEN;
            case 11 : return CardType.JACK;
            case 12 : return CardType.QUEEN;
            case 13 : return CardType.KING;
            case 14 : return CardType.ACE;
            default : throw new IllegalStateException("Unexpected value: " + type);
        }
    }
}
