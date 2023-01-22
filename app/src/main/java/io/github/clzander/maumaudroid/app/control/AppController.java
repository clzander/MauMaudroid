package io.github.clzander.maumaudroid.app.control;

import java.io.IOException;

import io.github.clzander.maumaudroid.app.model.AppModel;
import io.github.clzander.maumaudroid.app.model.AppModelInterface;
import io.github.clzander.maumaudroid.app.model.AppState;
import io.github.clzander.maumaudroid.app.view.AppViewInterface;
import io.github.clzander.maumaudroid.app.view.GameModelListener;
import io.github.clzander.maumaudroid.app.view.MainActivity;
import io.github.clzander.maumaudroid.bluetooth.control.BluetoothController;
import io.github.clzander.maumaudroid.bluetooth.control.BluetoothControllerInterface;
import io.github.clzander.maumaudroid.game.controller.GameController;
import io.github.clzander.maumaudroid.game.model.player.TurnType;

public class AppController implements AppControllerInterface, BluetoothControllerListener {

    private final AppModelInterface appModel;
    private final AppViewInterface appView;

    private final BluetoothControllerInterface bluetoothController;

    private GameController gameController;

    public AppController(MainActivity mainActivity) {
        this.appModel = new AppModel();
        this.appModel.registerObserver(mainActivity);
        this.appView = mainActivity;

        this.bluetoothController = new BluetoothController(mainActivity);
        this.bluetoothController.registerListener(this);
    }

    @Override
    public void doBluetoothPreparation() {
        this.bluetoothController.requestBluetoothPermission();
    }

    @Override
    public void hostingRequested() {
        this.bluetoothController.requestHosting();
    }

    @Override
    public void joiningRequested() {
        this.bluetoothController.startDiscovering();
        this.appModel.setState(AppState.JOINING);
    }

    @Override
    public void connectionRequested() {
        this.appModel.setState(AppState.IN_GAME);
    }

    @Override
    public String previousDevice() {
        return this.bluetoothController.previousDevice();
    }

    @Override
    public String nextDevice() {
        return this.bluetoothController.nextDevice();
    }

    @Override
    public void connectToCurrentDevice() {
        this.bluetoothController.connectToCurrentDevice();
    }

    @Override
    public void nextCardRequested() {
        this.gameController.selectNextCard();
    }

    @Override
    public void playSelectedCard() {
        this.gameController.playSelectedCard();
    }

    @Override
    public void previousCardRequested() {
        this.gameController.selectPreviousCard();
    }

    @Override
    public void registerGameModelListener(GameModelListener listener) {
        this.gameController.registerGameModelListener(listener);
    }

    @Override
    public void getStartView() {
        this.gameController.getStartView();
    }

    @Override
    public String firstDevice() {
        return this.bluetoothController.firstDevice();
    }


    @Override
    public void bluetoothGranted(boolean granted) {
        if (granted) {
            this.appModel.setState(AppState.MAIN_MENU);
        } else {
            this.appView.bluetoothRejected();
        }
    }

    @Override
    public void hostingGranted(boolean granted) {
        if (granted) {
            this.appModel.setState(AppState.HOSTING);
            try {
                this.bluetoothController.startHosting();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            this.appView.hostingRejected();
        }
    }

    @Override
    public void gameSetUp() {
        this.appModel.setState(AppState.IN_GAME);
    }

    @Override
    public void connectionEstablished(TurnType turnType) {
        this.gameController = new GameController(turnType, this.bluetoothController);
    }
}
