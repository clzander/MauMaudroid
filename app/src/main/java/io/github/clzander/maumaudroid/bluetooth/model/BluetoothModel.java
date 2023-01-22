package io.github.clzander.maumaudroid.bluetooth.model;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.github.clzander.maumaudroid.app.view.BluetoothUpdateListener;

public class BluetoothModel implements BluetoothModelInterface {

    private BluetoothAdapter adapter;
    private BluetoothConnectionState state;

    private final String connectionName;
    private final UUID applicationUUID;

    private final List<BluetoothDevice> devices;
    private int currentDeviceIndex;

    private final List<BluetoothUpdateListener> listeners;
    private boolean isHost;

    public BluetoothModel(BluetoothAdapter adapter) {
        this.state = BluetoothConnectionState.DEFAULT;
        this.adapter = adapter;

        this.connectionName = "BluetoothMauMaudroid";
        this.applicationUUID = UUID.fromString("7905cb2f-5315-490c-a754-821372d9f865");

        this.devices = new ArrayList<>();
        this.currentDeviceIndex = 0;

        this.listeners = new ArrayList<>();
    }

    public void addBluetoothUpdateListener(BluetoothUpdateListener listener) {
        this.listeners.add(listener);
    }

    @Override
    public void setIsHost(boolean isHost) {
        this.isHost = isHost;
    }

    @Override
    public boolean isHost() {
        return isHost;
    }

    @Override
    public void addDevice(BluetoothDevice device) {
        this.devices.add(device);
    }

    @Override
    public String getFirstDevice() throws SecurityException {
        if(this.devices.size() > 0) {
            return this.devices.get(0).getName();
        } else {
            return "";
        }
    }

    @Override
    public BluetoothConnectionState getCurrentState() {
        return this.state;
    }

    @Override
    public void setState(BluetoothConnectionState state) {
        this.state = state;

        //for (BluetoothUpdateListener listener : this.listeners) {
        //    listener.connectionStateChanged(this.state);
        //}
    }

    @Override
    public BluetoothAdapter getBluetoothAdapter() {
        return this.adapter;
    }

    @Override
    public String getName() {
        return this.connectionName;
    }

    @Override
    public UUID getUUID() {
        return this.applicationUUID;
    }

    @Override
    public String getPreviousDeviceName() throws SecurityException {
        String returnName = "";
        if (this.devices.size() > 0) {
            if (this.currentDeviceIndex > 0) {
                returnName = this.devices.get(--this.currentDeviceIndex).getName();

            } else {
                returnName = this.devices.get(this.currentDeviceIndex).getName();
            }
        }
        return returnName;
    }

    @Override
    public String getNextDeviceName() throws SecurityException {
        String returnName = "";
        if (this.devices.size() > this.currentDeviceIndex) {
            returnName = this.devices.get(this.currentDeviceIndex).getName();
        }
        if (this.devices.size() > this.currentDeviceIndex + 1) {
            returnName = this.devices.get(++this.currentDeviceIndex).getName();
        }
        return returnName;
    }

    @Override
    public BluetoothDevice getCurrentDevice() {
        return this.devices.get(this.currentDeviceIndex);
    }

    @Override
    public void addToConnectableDevices(BluetoothDevice device) {
        this.devices.add(device);

        this.listeners.forEach(BluetoothUpdateListener::newDeviceFound);
    }
}
