package io.github.clzander.maumaudroid.bluetooth.control;

import android.bluetooth.BluetoothSocket;

public interface ConnectionEstablishedListener {
    void connectionEstablished(BluetoothSocket socket);
}
