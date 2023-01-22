package io.github.clzander.maumaudroid.app.control;

import io.github.clzander.maumaudroid.app.view.GameModelListener;

/**
 * Controller between the Main Activity and its fragments and the AppModel
 */
public interface AppControllerInterface {
    /**
     * User requested to host a game
     */
    void hostingRequested();

    /**
     * User requested to join a game
     */
    void joiningRequested();

    void doBluetoothPreparation();

    void connectionRequested();

    String previousDevice();

    String nextDevice();

    void connectToCurrentDevice();

    void nextCardRequested();

    void playSelectedCard();

    void previousCardRequested();

    void registerGameModelListener(GameModelListener listener);

    void getStartView();

    String firstDevice();
}
