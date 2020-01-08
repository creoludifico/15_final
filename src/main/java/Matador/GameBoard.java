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

            if(currentPlayer.isInJail()){
                InterfaceGUI.showMessage("Du er i fængsel", currentPlayer.getName());

                String escapeJailCardString = "Brug dit fængsel kort";
                String pay100String = "Betal 100";
                String raffleDicesString = "Kast med terning og sats på to ens";
                String[] buttons;
                if(currentPlayer.hasEscapeJailCard()){
                    buttons = new String[]{
                            escapeJailCardString,
                            pay100String,
                            raffleDicesString
                    };
                }else{
                    buttons = new String[]{
                            pay100String,
                            raffleDicesString
                    };
                }
                String action = InterfaceGUI.awaitUserButtonsClicked("Du har nu følgende muligheder for at komme ud af fængslet: ", currentPlayer.getName(), buttons);
                if(action.equals(escapeJailCardString)){
                    currentPlayer.setHasEscapeJailCard(false);
                    currentPlayer.setInJail(false);
                }
                else if(action.equals(pay100String)){
                    currentPlayer.getAccount().setBalance(currentPlayer.getAccount().getBalance() - 100, currentPlayer.getName());
                    currentPlayer.setInJail(false);
                }
                else if(action.equals(raffleDicesString)){
                    raffleCup.shakeTheRaffleCup();
                    if(raffleCup.isSameDie()){
                        currentPlayer.setInJail(false);
                    }else{
                        InterfaceGUI.showMessage("Desværre, turen går videre", currentPlayer.getName());
                    }
                }

                if(!currentPlayer.isInJail()){
                    InterfaceGUI.showMessage("Du er fri af fængslet og må nu slå normalt", currentPlayer.getName());
                }
            }

            if(!currentPlayer.isInJail()){
                raffleCup.awaitShakeTheRaffleCup(currentPlayer.getName());
                playerController.movePlayerOnField(currentPlayer, raffleCup.getTotalValue());

                int currentPlayerFieldIndex = playerController.getCurrentPlayer().getFieldIndex();
                fieldController.fieldAction(currentPlayer, currentPlayerFieldIndex);
            }

            currentPlayerIndex++;
            if(currentPlayerIndex == playerController.getPlayers().length){
                currentPlayerIndex = 0;
            }
        }
    }
}