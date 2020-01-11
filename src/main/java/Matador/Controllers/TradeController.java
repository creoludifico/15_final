package Matador.Controllers;

import Matador.GUI.InterfaceGUI;
import Matador.Models.Field.*;
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
        StreetField[] ownedStreetFieldWithoutBuildings = fieldController.getOwnerOfStreetFieldsArray(player, false);

    }

    public void sellHouse(Player player) {
        StreetField[] fields = sellableFields(player);
        if (fields.length == 0) {
            InterfaceGUI.showMessage(player.getName() + ": Du kan ikke sælge bygninger fra nogen grunde");
            return;
        }
        String[] names = fieldController.transformToStringArray(fields);
        String selection = InterfaceGUI.awaitDropDownSelected(player.getName() + ": Vælg det felt du vil sælge et hus eller hotel fra.", player.getName(), names);
        StreetField selectedField = (StreetField)fieldForName(selection);

        if (selectedField.getBuildings() > 0) {
            InterfaceGUI.showMessage(player.getName() + ": Du sælger nu et hus fra feltet " + selectedField.getTitle() + " til en værdi af " + selectedField.getBuildingPrice());
            playerController.modifyBalance(selectedField.getBuildingPrice(), player);
            selectedField.setBuildings(selectedField.getBuildings() - 1);
            updateBuildings(selectedField);
        } else {
            InterfaceGUI.showMessage(player.getName() + ": Der er intet at sælge!");
        }
    }

    private OwnableField fieldForName(String name) {
        for (Field field: fieldController.getFields()) {
            if (name.equals(field.getTitle()))
                return (OwnableField)field;
        }
        return null;
    }

    private String[] streetGroupNames() {
        Field[] fields = fieldController.getFields();
        int count = 0;
        String lastGroupName = "";
        for (Field field: fields) {
            if (field instanceof StreetField && !((StreetField)field).getGroupName().equals(lastGroupName)) {
                lastGroupName = ((StreetField)field).getGroupName();
                count++;
            }
        }
        String[] result = new String[count];
        int index = 0;
        lastGroupName = "";
        for (Field field: fields) {
            if (field instanceof StreetField && !((StreetField)field).getGroupName().equals(lastGroupName)) {
                lastGroupName = ((StreetField)field).getGroupName();
                result[index++] = ((StreetField) field).getGroupName();
            }
        }
        return result;
    }

    private int getStreetGroupSize(String groupName) {
        int count = 0;
        for (Field field : fieldController.getFields()) {
            if (field instanceof StreetField && ((StreetField) field).getGroupName().equals(groupName))
                count++;
        }
        return count;
    }

    private StreetField[][] getStreetGroups() {
        String[] streetGroupNames = streetGroupNames();
        StreetField[][] result = new StreetField[streetGroupNames.length][];
        for(int i = 0; i < result.length; i++) {
            result[i] = new StreetField[getStreetGroupSize(streetGroupNames[i])];
            int count = 0;
            for (Field field: fieldController.getFields()) {
                if (field instanceof StreetField && ((StreetField)field).getGroupName().equals(streetGroupNames[i]))
                    result[i][count++] = (StreetField)field;
            }
        }
        System.out.println();
        return result;
    }

    private boolean ownsEntireGroup (StreetField[] group, Player player) {
        boolean result = true;
        for (StreetField street: group) {
            if (street.getOwner() != player)
                result = false;
        }
        return result;
    }

    private StreetField[] ownedPartofGroup(StreetField[] group, Player player) {
        int count = 0;
        for (StreetField street: group) {
            if (street.getOwner() == player)
                count++;
        }
        StreetField[] result = count == 0 ? null : new StreetField[count];
        int index = 0;
        for (StreetField street: group) {
            if (street.getOwner() == player)
                result[index++] = street;
        }
        return result;
    }

    private StreetField[][] allOwnedGroups(Player player) {
        int count = 0;
        StreetField[][] groups = getStreetGroups();
        for (StreetField[] group: groups) {
            if(ownedPartofGroup(group, player) != null)
                count++;
        }
        StreetField[][] result = new StreetField[count][];
        int index = 0;
        for (StreetField[] group: groups) {
            if(ownedPartofGroup(group, player) != null)
                result[index++] = ownedPartofGroup(group, player);
        }
        return result;
    }

    private StreetField[] buildableFields(Player player) {
        StreetField[][] groups = getStreetGroups();
        int count = 0;
        for (StreetField[] group: groups) {
            if(ownsEntireGroup(group, player)) {
                for (StreetField field : group) {
                    if (field.getBuildings() == minHousesGroup(group))
                        count++;
                }
            }
        }
        StreetField[] result = new StreetField[count];
        int index = 0;
        for (StreetField[] group: groups) {
            if(ownsEntireGroup(group, player)) {
                for (StreetField field: group) {
                    if (field.getBuildings() == minHousesGroup(group))
                        result[index++] = field;
                }
            }
        }
        return result;
    }

    private StreetField[] sellableFields(Player player) {
        StreetField[][] groups = getStreetGroups();
        int count = 0;
        for (StreetField[] group: groups) {
            if(ownsEntireGroup(group, player)) {
                for (StreetField field: group) {
                    if (field.getBuildings() == maxHousesGroup(group))
                        count++;
                }
            }
        }
        StreetField[] result = new StreetField[count];
        int index = 0;
        for (StreetField[] group: groups) {
            if(ownsEntireGroup(group, player)) {
                for (StreetField field: group) {
                    if (field.getBuildings() == maxHousesGroup(group))
                        result[index++] = field;
                }
            }
        }
        return result;
    }

    private int maxHousesGroup (StreetField[] group) {
        int result = 0;
        for (StreetField street : group) {
            result = Math.max(result, street.getBuildings());
        }
        return result;
    }

    private int minHousesGroup (StreetField[] group) {
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
        StreetField[] fields = buildableFields(player);
        if (fields.length == 0) {
            InterfaceGUI.showMessage(player.getName() + ": Du kan ikke bygge på nogen grunde");
            return;
        }
        String[] names = fieldController.transformToStringArray(fields);
        String selection = InterfaceGUI.awaitDropDownSelected(player.getName() + ": Vælg det felt du vil bygge et huse eller hotel på.", player.getName(), names);
        StreetField selectedField = (StreetField)fieldForName(selection);

        if (selectedField.getBuildings() < 5) {
            InterfaceGUI.showMessage(player.getName() + ": Du bygger nu et hus på feltet " + selectedField.getTitle() + " til en værdi af "+ selectedField.getBuildingPrice());
            playerController.modifyBalance(-selectedField.getBuildingPrice(), player);
            selectedField.setBuildings(selectedField.getBuildings() + 1);
            updateBuildings(selectedField);
        } else {
            InterfaceGUI.showMessage(player.getName() + ": Du har allerede det maksimale antal bygninger på feltet");
        }
    }

    public void tradeWithPlayer(Player player1) {
        String endMsg = "Afslut handel";
        String[] buttons = getPlayerButtons(endMsg, player1);

        String aktion = InterfaceGUI.awaitUserButtonsClicked("Vælg den spiller du vil sælge en grund til", player1.getName(), buttons);
        for(Player player2 : playerController.getPlayers()){
            if(aktion.equals(player2.getName())){
                OwnableField[] fields = tradeableFields(player1);
                String[] player1OwnFieldsString = fieldController.transformToStringArray(fields);
                if(fields.length == 0){
                    InterfaceGUI.showMessage("Du har ingen grunde at forhandle med", player1.getName());
                }else{
                    String dropDownAktion = InterfaceGUI.awaitDropDownSelected("Vælg det felt du vil forhandle med " + player2.getName(), player1.getName(), player1OwnFieldsString);

                    for(OwnableField player1OwnField : fields){
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

    private boolean tradeableGroup(StreetField[] group) {
        boolean result = true;
        for (StreetField street: group) {
            if(street.getBuildings() > 0)
                result = false;
        }
        return result;
    }

    private boolean otherOwnedHaveBuildings(StreetField meField, Player player) {
        for (Field field: fieldController.getFields()) {
            if(field instanceof StreetField){
                StreetField streetField = (StreetField)field;
                if (streetField.getGroupName().equals(meField.getGroupName()) && streetField.getOwner() == player && streetField.getBuildings() > 0 && streetField != meField) {
                    return true;
                }
            }
        }
        return false;
    }

//    private OwnableField[] tradeableFields(Player player) {
//        int count = 0;
//        for (Field field: fieldController.getFields()) {
//            if(field instanceof OwnableField && ((OwnableField)field).getOwner() == player) {
//                boolean tradeable = true;
//                if (otherOwnedHaveBuildings((StreetField) field, player)) {
//                    tradeable = false;
//                }
//                if (tradeable) {
//                    count++;
//                }
//            }
//        }
//
//        OwnableField[] result = new OwnableField[count];
//        int index = 0;
//        for (Field field: fieldController.getFields()) {
//            if (field instanceof OwnableField && ((OwnableField) field).getOwner() == player) {
//                boolean tradeable = true;
//                if (field instanceof StreetField) {
//                    if (otherOwnedHaveBuildings((StreetField) field, player)) {
//                        tradeable = false;
//                    }
//
//                }
//                if (tradeable) {
//                    result[index++] = (OwnableField) field;
//                }
//            }
//        }
//        return result;
//    }

    private OwnableField[] tradeableFields(Player player) {
        StreetField[][] groups = allOwnedGroups(player);
        int count = 0;
        for (StreetField[] group: groups) {
            if(tradeableGroup(group)) {
                count += getStreetGroupSize(group[0].getGroupName());
            }
        }
        //remember to count the other tradeable fields as well!
        for (Field field: fieldController.getFields()) {
            if((field instanceof BeerField || field instanceof FerryField) && ((OwnableField)field).getOwner() == player)
                count++;
        }
        OwnableField result[] = new OwnableField[count];
        int index = 0;
        for (StreetField[] group: groups) {
            if(tradeableGroup(group)) {
                for (StreetField street: group) {
                    result[index++] = street;
                }
            }
        }
        for (Field field: fieldController.getFields()) {
            if((field instanceof BeerField || field instanceof FerryField) && ((OwnableField)field).getOwner() == player)
                result[index++] = (OwnableField) field;
        }
        return result;
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
