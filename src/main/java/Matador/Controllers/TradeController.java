package Matador.Controllers;

import Matador.GUI.InterfaceGUI;
import Matador.Models.Field.Field;
import Matador.Models.Field.OwnableField;
import Matador.Models.Field.StreetField;
import Matador.Models.User.Player;

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
                    playerController.modifyBalance(-biddingPrice, biddingPlayer);
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
        StreetField[] buildingsStreetFields = fieldController.getOwnerOfStreetBuildingsArray(player);
        if (buildingsStreetFields.length == 0) {
            InterfaceGUI.showMessage(player.getName() + ": Du har ingen byggegrunde med bygninger på");
            return;
        }
        String[] buildingsStreetNames = fieldController.transformToStringArray(buildingsStreetFields);
        String selection = InterfaceGUI.awaitDropDownSelected("Vælg det felt du vil sælge et hus eller et hotel fra", player.getName(), buildingsStreetNames);
        StreetField selectedField = (StreetField)fieldForName(selection);
        StreetField[] groupArray = fieldController.getStreetGroupArray(selectedField.getGroupName());
        if (ownsEntireGroup(groupArray, player) && selectedField.getBuildings() == maxHousesGroup(groupArray, player) && selectedField.getBuildings() > 0) {
            InterfaceGUI.showMessage(player.getName() + ": Du kan godt sælge et " + (selectedField.getBuildings() == 5 ? "hotel" : "hus") + " på feltet " + selectedField.getTitle());
            playerController.modifyBalance(selectedField.getBuildingPrice(), player);
            selectedField.setBuildings(selectedField.getBuildings() - 1);
            updateBuildings(selectedField);
        } else {
            InterfaceGUI.showMessage(player.getName() + ": Du kan ikke sælge et hus fra feltet");
        }
    }

    private OwnableField fieldForName(String name) {
        for (Field field: fieldController.getFields()) {
            if (name.equals(field.getTitle()))
                return (OwnableField)field;
        }
        return null;
    }

    private boolean ownsEntireGroup (StreetField[] group, Player player) {
        boolean result = true;
        for (StreetField street: group) {
            if (street.getOwner() != player)
                result = false;
        }
        return result;
    }

    private int maxHousesGroup (StreetField[] group, Player player) {
        int result = 0;
        for (StreetField street : group) {
            result = Math.max(result, street.getBuildings());
        }
        return result;
    }

    private int minHousesGroup (StreetField[] group, Player player) {
        int result = 5;
        for (StreetField street : group) {
            result = Math.min(result, street.getBuildings());
        }
        return result;
    }

    private void updateBuildings (StreetField streetField) {
        int index = fieldController.getFieldIndex(streetField);
        if(streetField.getBuildings() == 5) {
            InterfaceGUI.setFieldHouses(index, 0);
            InterfaceGUI.setFieldHotel(index,true);
        } else {
            InterfaceGUI.setFieldHotel(index,false);
            InterfaceGUI.setFieldHouses(index, streetField.getBuildings());
        }
    }

    public void buyHouse(Player player) {
        StreetField[] ownedStreetFields = fieldController.getOwnerOfStreetFieldsArray(player);
        if (ownedStreetFields.length == 0) {
            InterfaceGUI.showMessage(player.getName() + ": Du har ingen byggegrunde");
            return;
        }
        String[] ownedStreetNames = fieldController.transformToStringArray(ownedStreetFields);
        String selection = InterfaceGUI.awaitDropDownSelected(player.getName() + ": Vælg det felt du vil bygge et huse eller hotel på.", player.getName(), ownedStreetNames);
        StreetField selectedField = (StreetField)fieldForName(selection);
        StreetField[] groupArray = fieldController.getStreetGroupArray(selectedField.getGroupName());
        if (ownsEntireGroup(groupArray, player) && selectedField.getBuildings() == minHousesGroup(groupArray, player) && selectedField.getBuildings() < 5) {
            InterfaceGUI.showMessage(player.getName() + ": Du kan godt bygge et hus på feltet " + selectedField.getTitle());
            playerController.modifyBalance(-selectedField.getBuildingPrice(), player);
            selectedField.setBuildings(selectedField.getBuildings() + 1);
            updateBuildings(selectedField);
        } else {
            InterfaceGUI.showMessage(player.getName() + ": Du kan ikke bygge på feltet");
        }
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
                                playerController.modifyBalance(price, player1);
                                playerController.modifyBalance(-price, player2);
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
