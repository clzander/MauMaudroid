package io.github.clzander.maumaudroid.game.model.player;

import java.util.ArrayList;
import java.util.List;

import io.github.clzander.maumaudroid.app.view.GameModelListener;
import io.github.clzander.maumaudroid.game.model.board.Board;
import io.github.clzander.maumaudroid.game.model.board.cards.Card;

public class GameModel implements GameModelInterface{

    private GameModelListener listener;

    private Board board;
    private List<Card> hand = new ArrayList<>();

    private int currentHandIndex = 0;
    private int currentPlayerIndex = 0;
    private final int playerIndex;
    private Card lastPlayedCard;

    public GameModel(int playerIndex) {
        this.playerIndex = playerIndex;
    }

    @Override
    public void registerModelListener(GameModelListener listener) {
        this.listener = listener;
    }

    @Override
    public void setBoard(Board commonBoard) {
        this.board = commonBoard;
    }

    @Override
    public void setHand(List<Card> localHand) {
        this.hand = localHand;
    }

    @Override
    public void selectNextCard() {
        if(hand.size() > currentHandIndex + 1) {
            this.currentHandIndex++;
        }
        this.listener.newHandCard(hand.get(currentHandIndex).getRessourceId());
    }

    @Override
    public void selectPreviousCard() {
        if(0 <= currentHandIndex - 1) {
            this.currentHandIndex--;
        }
        this.listener.newHandCard(hand.get(currentHandIndex).getRessourceId());
    }

    @Override
    public void getStartView() {
        this.listener.newHandCard(this.hand.get(currentHandIndex).getRessourceId());
        this.listener.newDiscardPileCard(this.board.getLastPlayedCard().getRessourceId());
    }

    @Override
    public boolean playCurrentCard() {
        Card card = this.hand.get(currentHandIndex);
        if(currentPlayerIndex == playerIndex) {
            if(card.getColor() == this.board.getLastPlayedCard().getColor() ||
                    card.getType() == this.board.getLastPlayedCard().getType()) {
                this.lastPlayedCard = card;
                this.hand.remove(currentHandIndex);
                this.board.playCard(card);
                this.nextPlayer();
                if(hand.size() > 0) {
                    this.listener.newHandCard(this.hand.get(0).getRessourceId());
                }
                this.listener.newDiscardPileCard(this.board.getLastPlayedCard().getRessourceId());
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public Card getLastPlayedCard() {
        return this.lastPlayedCard;
    }

    @Override
    public void newDiscardPileCard(Card card) {
        this.board.playCard(card);
        this.listener.newDiscardPileCard(this.board.getLastPlayedCard().getRessourceId());
    }

    @Override
    public void nextPlayer() {
        currentPlayerIndex = currentPlayerIndex == 1 ? 0 : 1;
    }
}
