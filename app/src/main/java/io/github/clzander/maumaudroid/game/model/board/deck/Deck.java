package io.github.clzander.maumaudroid.game.model.board.deck;

import java.util.List;

import io.github.clzander.maumaudroid.game.model.board.cards.Card;

public interface Deck {
    /**
     * Get the top card from the deck.
     * @return the card
     * @throws EmptyDeckException when the deck is empty
     */
    Card drawCard() throws EmptyDeckException;

    /**
     * Add cards to a deck and shuffle them in.
     * @param cards cards to add to the deck
     */
    void addCardsToDeck(List<Card> cards);

    List<Card> getDeckAsList();
}