package io.github.clzander.maumaudroid.app.control;

import io.github.clzander.maumaudroid.game.model.player.TurnType;

public interface BluetoothControllerListener {

    void bluetoothGranted(boolean granted);

    void hostingGranted(boolean granted);

    void gameSetUp();

    void connectionEstablished(TurnType turnType);
}
