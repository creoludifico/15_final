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
                OwnableField[] player1OwnFields = fieldController.getOwnerOfFieldsArray(player1);
                String[] player1OwnFieldsString = fieldController.transformToStringArray(player1OwnFields);
                if(player1OwnFields.length == 0){
                    InterfaceGUI.showMessage("Du har ingen grunde at forhandle med", player1.getName());
                }else{
                    String dropDownAktion = InterfaceGUI.awaitDropDownSelected("Vælg det felt du vil forhandle med " + player2.getName(), player1.getName(), player1OwnFieldsString);

                    for(OwnableField player1OwnField : player1OwnFields){
                        if(dropDownAktion.equals(player1OwnField.getTitle())){
                            int price = InterfaceGUI.awaitUserIntegerInput("Indtast den pris som grunden " + player1OwnField.getTitle() + " skal koste for " + player2.getName(), player1.getName());
                            if(price > player2.getAccount().getBalance()){
                                InterfaceGUI.showMessage("Spilleren " + player2.getName() + " har ikke råd til at købe grunden for den pris... I skal aftale noget andet.");
                                break;
                            }

                            String yes = "Ja";
                            String no = "Nej";
                            String[] confirmButtons = new String[]{yes, no};

                            String confirmAktion = InterfaceGUI.awaitUserButtonsClicked("Bekræft at dette er sandt. \n Grunden " + player1OwnField.getTitle() + " bliver solgt til " + player2.getName() + " for beløbet " + price, player1.getName(), confirmButtons);
                            if(confirmAktion.equals(yes)){
                                player1OwnField.setOwner(player2, fieldController.getFieldIndex(player1OwnField));
                                player1.getAccount().modifyBalance(price, player1.getName());
                                player2.getAccount().modifyBalance(-price, player2.getName());
                            }
                        }
                    }
                }
                break;
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
