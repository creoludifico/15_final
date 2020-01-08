package Matador;

import GUI.InterfaceGUI;
import Matador.ChanceCard.ChanceCardController;
import Matador.Field.FieldController;
import Matador.RaffleCup.RaffleCup;
import Matador.User.Account;
import Matador.User.Player;
import Matador.User.PlayerController;

public class GameBoard {
    private PlayerController playerController;
    private FieldController fieldController;
    private ChanceCardController chanceCardController;

    private boolean gameOver = false;

    public GameBoard(){
        //Opsætter gui boardet
        InterfaceGUI.initGUI();

        //Fields oprettes
        fieldController = new FieldController();

        //Spillerne oprettes
        playerController = new PlayerController();

        //Chancekortene oprettes
        chanceCardController = new ChanceCardController();

        //Opsætning af relationer.
        fieldController.setPlayerController(playerController);
        fieldController.setChanceCardController(chanceCardController);
        playerController.setFieldController(fieldController);
        playerController.setChanceCardController(chanceCardController);
        chanceCardController.setPlayerController(playerController);
        chanceCardController.setFieldController(fieldController);
    }

    public void runGame(){
        RaffleCup raffleCup = new RaffleCup();
        int currentPlayerIndex = 0;
        while(!gameOver){
            playerController.setCurrentPlayer(currentPlayerIndex);
            Player currentPlayer = playerController.getCurrentPlayer();

            raffleCup.awaitShakeTheRaffleCup();
            playerController.movePlayerOnField(currentPlayer, raffleCup.getTotalValue());

            int currentPlayerFieldIndex = playerController.getCurrentPlayer().getFieldIndex();
            fieldController.fieldAction(currentPlayer, currentPlayerFieldIndex);

            currentPlayerIndex++;
            if(currentPlayerIndex == playerController.getPlayers().length){
                currentPlayerIndex = 0;
            }
        }
    }
}