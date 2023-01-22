package io.github.clzander.maumaudroid.game.controller;

import java.util.List;

import io.github.clzander.maumaudroid.game.model.board.MauMauBoard;
import io.github.clzander.maumaudroid.game.model.board.cards.Card;

public interface BluetoothGameListener {
    void setGameData(MauMauBoard commonBoard, List<Card> localHand);

    void synchronizeDiscardPile(Card card);

    void notifyLoose();

    void incrementCurrentPlayerIndex();

    void simulateCardDraw(int number);
}
