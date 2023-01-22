package io.github.clzander.maumaudroid.bluetooth.control;

import android.bluetooth.BluetoothDevice;

import java.io.IOException;

import io.github.clzander.maumaudroid.app.control.BluetoothControllerListener;
import io.github.clzander.maumaudroid.game.controller.BluetoothGameListener;
import io.github.clzander.maumaudroid.game.model.board.cards.Card;

public interface BluetoothControllerInterface {

    /**
     * Checks if bluetooth is enabled
     * @throws BluetoothNotSupportedException if the device doesn't support bluetooth
     */
    boolean bluetoothEnabled() throws BluetoothNotSupportedException;

    void startHosting() throws IOException;

    void startDiscovering();

    String previousDevice();

    String nextDevice();

    void connectToCurrentDevice();

    void newDeviceDiscovered(BluetoothDevice device);

    void registerListener(BluetoothControllerListener listener);

    void requestBluetoothPermission();

    void requestHosting();

    void registerGameListener(BluetoothGameListener listener);

    void transmitCard(Card lastPlayedCard);

    String firstDevice();
}
