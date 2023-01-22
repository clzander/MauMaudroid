package io.github.clzander.maumaudroid.game.controller;

import io.github.clzander.maumaudroid.app.view.GameModelListener;

public interface GameControllerInterface {
    void playSelectedCard();

    void selectNextCard();

    void selectPreviousCard();

    void registerGameModelListener(GameModelListener listener);

    void getStartView();
}
