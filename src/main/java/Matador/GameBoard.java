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
    private TradeController tradeController;

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

        //Trade controlleren oprettes
        tradeController = new TradeController();

        //Opsætning af relationer.
        fieldController.setPlayerController(playerController);
        fieldController.setChanceCardController(chanceCardController);
        fieldController.setTradeController(tradeController);
        playerController.setFieldController(fieldController);
        playerController.setChanceCardController(chanceCardController);
        chanceCardController.setPlayerController(playerController);
        chanceCardController.setFieldController(fieldController);
    }

    public void runGame(){
        RaffleCup raffleCup = new RaffleCup();
        int currentPlayerIndex = 0;
        int isSameDieCounter = 0;
        boolean playerShakeTheRaffleCupFromJail = false;
        boolean dieTurnIsDone = false;
        while(!gameOver){
            playerController.setCurrentPlayer(currentPlayerIndex);
            Player currentPlayer = playerController.getCurrentPlayer();
            String[] buttons;
            if(currentPlayer.isInJail()){
                InterfaceGUI.showMessage("Du er i fængsel", currentPlayer.getName());

                String escapeJailCardString = "Brug dit fængsel kort";
                String pay100String = "Betal 100";
                String raffleDicesString = "Kast med terning og sats på to ens";

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
                    currentPlayer.getAccount().modifyBalance(-100, currentPlayer.getName());
                    currentPlayer.setInJail(false);
                }
                else if(action.equals(raffleDicesString)){
                    raffleCup.shakeTheRaffleCup();
                    if(raffleCup.isSameDie()){
                        currentPlayer.setInJail(false);
                    }else{
                        currentPlayer.setJailForRounds(currentPlayer.getJailForRounds() + 1);
                        InterfaceGUI.showMessage("Desværre, turen går videre", currentPlayer.getName());
                    }
                    playerShakeTheRaffleCupFromJail = true;
                }

                if(!currentPlayer.isInJail()){
                    InterfaceGUI.showMessage("Du er fri af fængslet", currentPlayer.getName());
                }
            }

            if(!currentPlayer.isInJail()){

                if(!playerShakeTheRaffleCupFromJail) {
                    raffleCup.awaitShakeTheRaffleCup(currentPlayer.getName());
                }
                playerController.movePlayerOnField(currentPlayer, raffleCup.getTotalValue());

                int currentPlayerFieldIndex = playerController.getCurrentPlayer().getFieldIndex();
                fieldController.fieldAction(currentPlayer, currentPlayerFieldIndex, raffleCup);
            }

            playerShakeTheRaffleCupFromJail = false;
            if (raffleCup.isSameDie()) {
                isSameDieCounter++;
                if(isSameDieCounter > 2){
                    currentPlayer.setFieldIndex(10);
                    currentPlayer.setInJail(true);
                    isSameDieCounter = 0;
                    dieTurnIsDone = true;
                }
            } else {
                dieTurnIsDone = true;
                isSameDieCounter = 0;
            }
            if(dieTurnIsDone)
            {
                String sellHouse =  "Salg af huse";
                String buyHouse =  "Køb af huse";
                String forhandle =  "Forhandle med en anden spiller";
                String afslutTur =  "Afslut din tur";

                buttons = new String[]{
                        sellHouse,
                        buyHouse,
                        forhandle,
                        afslutTur
                };
                while (dieTurnIsDone)
                {

                    String action = InterfaceGUI.awaitUserButtonsClicked("Du har nu følgende muligheder: ", currentPlayer.getName(), buttons);
                    if(action.equals(sellHouse)){
                        tradeController.sellHouse();
                    }
                    if(action.equals(buyHouse))
                    {
                        tradeController.buyHouse();
                    }
                    if(action.equals(forhandle))
                    {
                        tradeController.tradeWithPlayer();
                    }
                    if(action.equals(afslutTur))
                    {
                        dieTurnIsDone = false;
                    }
                }
                currentPlayerIndex++;
            }

            if(currentPlayerIndex == playerController.getPlayers().length){
                currentPlayerIndex = 0;
            }
        }
    }
}