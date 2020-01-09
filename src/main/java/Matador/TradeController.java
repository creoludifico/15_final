package Matador;

import GUI.InterfaceGUI;
import Matador.Field.Field;
import Matador.Field.FieldController;
import Matador.Field.OwnableField;
import Matador.User.Player;
import Matador.User.PlayerController;

public class TradeController {
    private FieldController fieldController;
    private PlayerController playerController;

    //For auktion
    private Player biddingPlayer = null;
    private int biddingPrice = 0;


    public void setFieldController(FieldController fieldController) {
        this.fieldController = fieldController;
    }

    public void setPlayerController(PlayerController playerController) {
        this.playerController = playerController;
    }

    public void auction(OwnableField ownableField, int fieldIndex) {
        InterfaceGUI.showMessage("Der skal nu bydes på grunden: " + ownableField.getTitle());

        String endMsg = "Afslut auktion";
        String[] buttons = getPlayerButtons(endMsg, null);

        while(true){
            String currentMsg;
            if(biddingPlayer != null){
                currentMsg = "Vælg spiller som vil byde eller afslut auktionen. Nuværende højeste bud af " + biddingPlayer.getName() + " på " + biddingPrice;
            }else{
                currentMsg = "Vælg spiller som vil byde eller afslut auktionen.";
            }
            String aktion = InterfaceGUI.awaitUserButtonsClicked(currentMsg, buttons);
            for(Player player : playerController.getPlayers()){
                if(aktion.equals(player.getName())){
                    int bid = InterfaceGUI.awaitUserIntegerInput("Skriv dit bud", player.getName());
                    if(bid > player.getAccount().getBalance()){
                        InterfaceGUI.showMessage("Du har ikke penge nok til at byde denne pris!", player.getName());
                    }
                    else if(bid <= biddingPrice){
                        InterfaceGUI.showMessage("Du kan ikke byde lavere end hvad der er blevet budt!", player.getName());
                    }else{
                        biddingPrice = bid;
                        biddingPlayer = player;
                    }
                }
            }
            if(aktion.equals(endMsg)){
                if(biddingPlayer == null) {
                    InterfaceGUI.showMessage("Ingen bød så derfor slutter auktionen uden grunden er købt");
                }
                else{
                    biddingPlayer.getAccount().modifyBalance(-biddingPrice, biddingPlayer.getName());
                    ownableField.setOwner(biddingPlayer, fieldIndex);
                    InterfaceGUI.showMessage(biddingPlayer.getName() + " vandt auktionen med det højeste bud på " + biddingPrice);
                }
                break;
            }
        }
        biddingPrice = 0;
        biddingPlayer = null;
    }

    public void pawnField(Player player){

    }

    public void sellHouse(Player player) {
    }

    public void buyHouse(Player player) {
    }

    public void tradeWithPlayer(Player player1) {
        String endMsg = "Afslut handel";
        String[] buttons = getPlayerButtons(endMsg, player1);

        String aktion = InterfaceGUI.awaitUserButtonsClicked("Vælg den spiller du vil sælge en grund til", player1.getName(), buttons);
        for(Player player2 : playerController.getPlayers()){
            if(aktion.equals(player2.getName())){
                String[] player1OwnFields = fieldController.getOwnerOfFieldsArray(player1);
                if(player1OwnFields.length == 0){
                    InterfaceGUI.showMessage("Du har ingen grunde at forhandle med", player1.getName());
                }else{
                    InterfaceGUI.awaitDropDownSelected("Vælg det felt du vil forhandle med " + player2.getName(), player1.getName(), player1OwnFields);
                }
            }
        }
    }


    public String[] getPlayerButtons(String endMsg, Player withoutPlayer){
        Player[] players = playerController.getPlayers();

        int lengthOfArray = players.length + 1;
        if(withoutPlayer != null){
            lengthOfArray--;
        }
        String[] buttons = new String[lengthOfArray];

        int i = 0;
        for(Player player : players){
            if(withoutPlayer != null && withoutPlayer.getName().equals(player.getName())){
                continue;
            }
            buttons[i] = player.getName();
            i++;
        }
        buttons[lengthOfArray-1] = endMsg;
        return buttons;
    }
}
