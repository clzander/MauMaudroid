package io.github.clzander.maumaudroid.game.controller;

import java.util.List;

import io.github.clzander.maumaudroid.app.view.GameModelListener;
import io.github.clzander.maumaudroid.bluetooth.control.BluetoothControllerInterface;
import io.github.clzander.maumaudroid.game.model.board.MauMauBoard;
import io.github.clzander.maumaudroid.game.model.board.cards.Card;
import io.github.clzander.maumaudroid.game.model.player.GameModel;
import io.github.clzander.maumaudroid.game.model.player.GameModelInterface;
import io.github.clzander.maumaudroid.game.model.player.TurnType;

public class GameController implements GameControllerInterface, BluetoothGameListener {

    private final GameModelInterface gameModel;

    private final BluetoothControllerInterface bluetoothController;

    public GameController(TurnType turnType, BluetoothControllerInterface bluetoothController) {
        this.bluetoothController = bluetoothController;
        this.bluetoothController.registerGameListener(this);
        this.gameModel = new GameModel(turnType == TurnType.FIRST? 0 : 1);
    }

    @Override
    public void playSelectedCard() {
        if(this.gameModel.playCurrentCard()) {
            this.bluetoothController.transmitCard(this.gameModel.getLastPlayedCard());
        }
    }

    @Override
    public void selectNextCard() {
        this.gameModel.selectNextCard();
    }

    @Override
    public void selectPreviousCard() {
        this.gameModel.selectPreviousCard();
    }

    @Override
    public void registerGameModelListener(GameModelListener listener) {
        this.gameModel.registerModelListener(listener);
    }

    @Override
    public void getStartView() {
        this.gameModel.getStartView();
    }

    @Override
    public void setGameData(MauMauBoard commonBoard, List<Card> localHand) {
        this.gameModel.setBoard(commonBoard);
        this.gameModel.setHand(localHand);
    }

    @Override
    public void synchronizeDiscardPile(Card card) {
        this.gameModel.newDiscardPileCard(card);

    }

    @Override
    public void notifyLoose() {

    }

    @Override
    public void incrementCurrentPlayerIndex() {
        this.gameModel.nextPlayer();
    }

    @Override
    public void simulateCardDraw(int number) {

    }
}