package io.github.clzander.maumaudroid.game.model.board.deck;

/**
 * Thrown when the deck is empty but a card is tried to be drawn
 */
public class EmptyDeckException extends Exception{
    public EmptyDeckException() {
        super();
    }
}
