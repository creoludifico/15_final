package Matador;

import Matador.GUI.InterfaceGUI;
import Matador.Controllers.ChanceCardController;
import Matador.Controllers.FieldController;
import Matador.Controllers.TradeController;
import Matador.Controllers.RaffleCupController;
import Matador.Models.Field.Field;
import Matador.Models.User.Player;
import Matador.Controllers.PlayerController;

public class GameBoard {
    private PlayerController playerController;
    private FieldController fieldController;
    private ChanceCardController chanceCardController;
    private TradeController tradeController;

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
        playerController.setTradeController(tradeController);
        chanceCardController.setPlayerController(playerController);
        chanceCardController.setFieldController(fieldController);
        tradeController.setPlayerController(playerController);
        tradeController.setFieldController(fieldController);
    }

    public void runGame(){
        RaffleCupController raffleCupController = new RaffleCupController();
        int currentPlayerIndex = 0;
        int isSameDieCounter = 0;
        boolean playerShakeTheRaffleCupFromJail = false;
        boolean dieTurnIsDone = false;
        while(true){
            InterfaceGUI.hideGuiCard();

            playerController.setCurrentPlayer(currentPlayerIndex);
            Player currentPlayer = playerController.getCurrentPlayer();
            if(currentPlayer.isInJail()){
                InterfaceGUI.showMessage("Du er i fængsel", currentPlayer.getName());

                String escapeJailCardString = "Brug dit fængsel kort";
                String pay100String = "Betal 100";
                String raffleDicesString = "Kast med terning og sats på to ens";

                String[] buttonsForJail;
                if(currentPlayer.hasEscapeJailCard() && currentPlayer.getAccount().getBalance() >= 100){
                    buttonsForJail = new String[]{
                            escapeJailCardString,
                            pay100String,
                            raffleDicesString
                    };
                }else if(currentPlayer.getAccount().getBalance() >= 100){
                    buttonsForJail = new String[]{
                            pay100String,
                            raffleDicesString
                    };
                }else{
                    buttonsForJail = new String[]{
                            raffleDicesString
                    };
                }
                String action = InterfaceGUI.awaitUserButtonsClicked("Du har nu følgende muligheder for at komme ud af fængslet: ", currentPlayer.getName(), buttonsForJail);
                if(action.equals(escapeJailCardString)){
                    currentPlayer.setHasEscapeJailCard(false);
                    currentPlayer.setInJail(false);
                }
                else if(action.equals(pay100String)){
                    playerController.modifyBalance(-100, currentPlayer);
                    currentPlayer.setInJail(false);
                }
                else if(action.equals(raffleDicesString)){
                    raffleCupController.shakeTheRaffleCup();
                    if(raffleCupController.isSameDie()){
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
                    raffleCupController.awaitShakeTheRaffleCup(currentPlayer.getName());
                }
                playerController.movePlayerForwardField(currentPlayer, raffleCupController.getTotalValue());

                int currentPlayerFieldIndex = playerController.getCurrentPlayer().getFieldIndex();

                fieldController.fieldAction(currentPlayer, currentPlayerFieldIndex, raffleCupController);

                //Hvis man har tabt
                if(playerController.getPlayerFromName(currentPlayer.getName()) == null){
                    currentPlayerIndex++;
                    if(currentPlayerIndex >= playerController.getPlayers().length){
                        currentPlayerIndex = 0;
                    }
                    continue;
                }
            }

            playerShakeTheRaffleCupFromJail = false;
            if (raffleCupController.isSameDie()) {
                isSameDieCounter++;
                if(isSameDieCounter > 2){
                    playerController.movePlayerToField(currentPlayer, 10);
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
                String unpawnField = "Fjern pansætning af grund";
                String pawnField = "Pantsæt grund";
                String sellHouse =  "Salg af huse";
                String buyHouse =  "Køb af huse";
                String trading =  "Forhandle med en anden spiller";
                String endTurn =  "Afslut din tur";

                String[] buttonsForEndActions;
                buttonsForEndActions = new String[]{
                        unpawnField,
                        pawnField,
                        sellHouse,
                        buyHouse,
                        trading,
                        endTurn
                };
                while (true)
                {
                    String action = InterfaceGUI.awaitUserButtonsClicked("Du har nu følgende muligheder: ", currentPlayer.getName(), buttonsForEndActions);
                    if(action.equals(unpawnField)){
                        tradeController.unpawnField(currentPlayer);
                    }
                    if(action.equals(pawnField)){
                        tradeController.pawnField(currentPlayer);
                    }
                    if(action.equals(sellHouse)){
                        tradeController.sellHouse(currentPlayer);
                    }
                    if(action.equals(buyHouse)){
                        tradeController.buyHouse(currentPlayer);
                    }
                    if(action.equals(trading)){
                        tradeController.trade(currentPlayer);
                    }
                    if(action.equals(endTurn)){
                        dieTurnIsDone = false;
                        break;
                    }
                }
                currentPlayerIndex++;
            }
            if(currentPlayerIndex >= playerController.getPlayers().length){
                currentPlayerIndex = 0;
            }
        }
    }
}