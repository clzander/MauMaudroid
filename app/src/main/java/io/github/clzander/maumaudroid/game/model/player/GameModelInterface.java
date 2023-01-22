package io.github.clzander.maumaudroid.game.model.player;

import java.util.List;

import io.github.clzander.maumaudroid.app.view.GameModelListener;
import io.github.clzander.maumaudroid.game.model.board.Board;
import io.github.clzander.maumaudroid.game.model.board.cards.Card;

public interface GameModelInterface {
    void registerModelListener(GameModelListener listener);

    void setBoard(Board commonBoard);

    void setHand(List<Card> localHand);

    void selectNextCard();

    void selectPreviousCard();

    void getStartView();

    boolean playCurrentCard();

    Card getLastPlayedCard();

    void newDiscardPileCard(Card card);

    void nextPlayer();
}
