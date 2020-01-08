package Matador;

import GUI.InterfaceGUI;
import Matador.Field.FieldController;
import Matador.RaffleCup.RaffleCup;
import Matador.User.Account;
import Matador.User.Player;
import Matador.User.PlayerController;

public class GameBoard {
    private PlayerController playerController;
    private FieldController fieldController;

    private boolean gameOver = false;

    public GameBoard(){
        //Opsætter gui boardet
        InterfaceGUI.initGUI();

        //Fields oprettes
        fieldController = new FieldController();

        //Spillerne oprettes
        playerController = new PlayerController();
    }

    public void runGame(){
        RaffleCup raffleCup = new RaffleCup();
        int currentPlayerIndex = 0;
        while(!gameOver){
            playerController.setCurrentPlayer(currentPlayerIndex);

            raffleCup.awaitShakeTheRaffleCup();
            playerController.movePlayerOnField(playerController.getCurrentPlayer(), raffleCup.getTotalValue(), fieldController.getFieldCount());

            currentPlayerIndex++;
            if(currentPlayerIndex == playerController.getPlayerCount()){
                currentPlayerIndex = 0;
            }
        }
    }
}