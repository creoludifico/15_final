package Matador.Controllers;

import Matador.GUI.InterfaceGUI;
import Matador.Models.Field.*;
import Matador.Models.User.Player;

import java.util.Arrays;

public class TradeController {
    private FieldController fieldController;
    private PlayerController playerController;

    public void setFieldController(FieldController fieldController) {
        this.fieldController = fieldController;
    }
    public void setPlayerController(PlayerController playerController) {
        this.playerController = playerController;
    }

    public void auction(OwnableField ownableField, int fieldIndex) {
        String[] playerNames = playerController.getPlayerNames(playerController.getPlayers());

        String endAuction = "Afslut auktion";
        String[] buttons = InterfaceGUI.getStringsForAction(playerNames, endAuction);

        Player highestBidder = null;
        int highestBid = 0;
        while(true){
            String message;
            if(highestBidder != null){
                message = "AUKTION: Vælg en spiller som vil byde eller afslut. Højeste bud er lige nu " + highestBid + " af " + highestBidder.getName();
            }else{
                message = "AUKTION: Vælg en spiller som vil byde eller afslut.";
            }
            String action = InterfaceGUI.awaitUserButtonsClicked(message, buttons);
            if(action.equals(endAuction)){
                if(highestBidder == null){
                    InterfaceGUI.showMessage("Ingen bød og derfor er grunden forsat ukøbt");
                }
                else {
                    ownableField.setOwner(highestBidder, fieldIndex);
                    playerController.modifyBalance(-highestBid, highestBidder);
                    InterfaceGUI.showMessage(highestBidder.getName() + " ejer nu grunden efter at have betalt budet på kr. " + highestBid);
                }
                break;
            }
            else{
                Player player = playerController.getPlayerFromName(action);
                int bid = InterfaceGUI.awaitUserIntegerInput("Indtast dit bud", player.getName());
                if(bid <= highestBid){
                    InterfaceGUI.showMessage("Budet skal være højere end det forige!", player.getName());
                    continue;
                }
                else if(bid > player.getAccount().getBalance()){
                    InterfaceGUI.showMessage("Du har ikke penge nok til at byde dette!", player.getName());
                    continue;
                }
                else{
                    highestBid = bid;
                    highestBidder = player;
                }
            }
        }
    }
    public void pawnField(Player player){
        OwnableField[] pawnableFields = fieldController.getTradeableFields(fieldController.getOwnableFields(player, false));
        if(pawnableFields.length == 0){
            InterfaceGUI.showMessage("Du har ingen grunde der kan pantsættes", player.getName());
            return;
        }

        String[] titles = fieldController.getTitlesFromFields(pawnableFields);

        String endPawn = "Afslut pansætning";
        String[] buttons = InterfaceGUI.getStringsForAction(titles, endPawn);

        String action = InterfaceGUI.awaitDropDownSelected("Vælg en grund du vil pansætte", player.getName(), buttons);

        if(action.equals(endPawn)){
            return;
        }
        OwnableField ownableField = (OwnableField) fieldController.getFieldFromTitle(action);
        ownableField.setPawned(true, fieldController.getFieldIndex(ownableField));
        playerController.modifyBalance(ownableField.getMortgage(), player);
        InterfaceGUI.showMessage("Du pantsættede " + ownableField.getTitle() + " og fik kr. " + ownableField.getMortgage());

    }
    public void unpawnField(Player player) {
        OwnableField[] pawnedFields = fieldController.getPawnedFields(player);
        if(pawnedFields.length == 0){
            InterfaceGUI.showMessage("Du har ingen grunde der kan få ophævet pantsætningen", player.getName());
            return;
        }

        String[] titles = fieldController.getTitlesFromFields(pawnedFields);

        String endPawn = "Afslut ophæv pantsætning";
        String[] buttons = InterfaceGUI.getStringsForAction(titles, endPawn);

        String action = InterfaceGUI.awaitDropDownSelected("Vælg en grund du vil ophæve pansætningen på", player.getName(), buttons);

        if(action.equals(endPawn)){
            return;
        }
        OwnableField ownableField = (OwnableField) fieldController.getFieldFromTitle(action);
        if(ownableField.getMortgage() > player.getAccount().getBalance()){
            InterfaceGUI.showMessage("Du har ikke råd til at ophæve pantsætningen på denne grund", player.getName());
            return;
        }
        ownableField.setPawned(false, fieldController.getFieldIndex(ownableField));
        playerController.modifyBalance(-ownableField.getMortgage(), player);
        InterfaceGUI.showMessage("Du ophævede pantsætningen på " + ownableField.getTitle() + " og betalte kr. " + ownableField.getMortgage());

    }
    public void sellHouse(Player player) {
        StreetField[] streetFields = fieldController.getDemolitionableFields(fieldController.getStreetFields(player, true));
        if(streetFields.length == 0){
            InterfaceGUI.showMessage("Du har ingen felter du kan sælge huse på", player.getName());
            return;
        }

        String[] titles = fieldController.getTitlesFromFields(streetFields);

        String endSellHouse = "Afslut salg af huse";
        String[] buttons = InterfaceGUI.getStringsForAction(titles, endSellHouse);

        String action = InterfaceGUI.awaitDropDownSelected("Vælg en grund du vil sælge et hus fra", player.getName(), buttons);

        if(action.equals(endSellHouse)){
            return;
        }
        StreetField streetField = (StreetField) fieldController.getFieldFromTitle(action);
        streetField.setBuildings(streetField.getBuildings()-1, fieldController.getFieldIndex(streetField));
        playerController.modifyBalance(streetField.getBuildingPrice(), player);
        InterfaceGUI.showMessage("Du solgte et hus på grunden " + streetField.getTitle() + " for kr. " + streetField.getBuildingPrice());

    }
    public void buyHouse(Player player) {
        StreetField[] streetFields = fieldController.getBuildableFields(fieldController.getStreetFields(player));
        if(streetFields.length == 0){
            InterfaceGUI.showMessage("Du har ingen felter du kan købe huse på", player.getName());
            return;
        }

        String[] titles = fieldController.getTitlesFromFields(streetFields);

        String endBuyHouse = "Afslut køb af huse";
        String[] buttons = InterfaceGUI.getStringsForAction(titles, endBuyHouse);

        String action = InterfaceGUI.awaitDropDownSelected("Vælg en grund du vil købe et hus på", player.getName(), buttons);

        if(action.equals(endBuyHouse)){
            return;
        }
        StreetField streetField = (StreetField) fieldController.getFieldFromTitle(action);
        if(streetField.getBuildingPrice() > player.getAccount().getBalance()){
            InterfaceGUI.showMessage("Du har ikke råd til at købe et hus/hotel på denne grund", player.getName());
            return;
        }
        streetField.setBuildings(streetField.getBuildings()+1, fieldController.getFieldIndex(streetField));
        playerController.modifyBalance(-streetField.getBuildingPrice(), player);
        InterfaceGUI.showMessage("Du købte et hus på grunden " + streetField.getTitle() + " for kr. " + streetField.getBuildingPrice());

    }
    public void trade(Player player) {
        OwnableField[] tradeableFields = fieldController.getTradeableFields(fieldController.getOwnableFields(player, false));
        if(tradeableFields.length == 0){
            InterfaceGUI.showMessage("Du har ingen felter du kan sælge til andre spillere", player.getName());
            return;
        }

        String[] titles = fieldController.getTitlesFromFields(tradeableFields);

        String endTrading = "Afslut handel med grund";
        String[] buttons = InterfaceGUI.getStringsForAction(titles, endTrading);

        String action = InterfaceGUI.awaitDropDownSelected("Vælg en grund du vil sælge til en anden spiller", player.getName(), buttons);

        if(action.equals(endTrading)){
            return;
        }
        OwnableField ownableField = (OwnableField) fieldController.getFieldFromTitle(action);

        String[] playerNames = playerController.getPlayerNames(playerController.getPlayers(player));
        String[] buttonsPlayer = InterfaceGUI.getStringsForAction(playerNames, endTrading);
        String playerToTradeName = InterfaceGUI.awaitUserButtonsClicked("Vælg en spiller du vil sælge grunden til", player.getName(), buttonsPlayer);

        if(playerToTradeName.equals(endTrading)){
            return;
        }
        Player playerToTrade = playerController.getPlayerFromName(playerToTradeName);

        int price = InterfaceGUI.awaitUserIntegerInput("Indtast prisen for grunden");
        if(price > playerToTrade.getAccount().getBalance()){
            InterfaceGUI.showMessage("Spiller " + playerToTradeName + " har ikke råd til denne pris kr. " + price);
            return;
        }

        playerController.modifyBalance(-price, playerToTrade);
        playerController.modifyBalance(price, player);
        ownableField.setOwner(playerToTrade, fieldController.getFieldIndex(ownableField));
        InterfaceGUI.showMessage("Grunden " + ownableField.getTitle() + " er blevet solgt til " + playerToTradeName + " for beløbet kr. " + price);
    }
}