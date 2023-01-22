package io.github.clzander.maumaudroid.bluetooth.model;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

import java.util.UUID;

import io.github.clzander.maumaudroid.app.view.BluetoothUpdateListener;

public interface BluetoothModelInterface {


    /**
     * Returns the current state of the Bluetooth connection process
     * @return the current state
     */
    BluetoothConnectionState getCurrentState();


    /**
     * Changes the current state of the Bluetooth connection
     * @param state the new state
     */
    void setState(BluetoothConnectionState state);

    /**
     * Returns the Bluetooth adapter which is used for the connection
     * @return the Bluetooth adapter
     */
    BluetoothAdapter getBluetoothAdapter();

    /**
     * Returns the name of the bluetooth connection
     * @return the connection name
     */
    String getName();

    /**
     * Returns the UUID of this application
     * @return the UUID
     */
    UUID getUUID();


    String getPreviousDeviceName();

    String getNextDeviceName();

    BluetoothDevice getCurrentDevice();

    void addToConnectableDevices(BluetoothDevice device);

    void addBluetoothUpdateListener(BluetoothUpdateListener listener);

    void setIsHost(boolean isHost);

    boolean isHost();

    void addDevice(BluetoothDevice device);

    String getFirstDevice();
}
