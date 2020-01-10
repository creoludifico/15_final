package Matador.Controllers;

import Matador.GUI.InterfaceGUI;
import Matador.Models.Field.Field;
import Matador.Models.Field.OwnableField;
import Matador.Models.Field.StreetField;
import Matador.Models.User.Account;
import Matador.Models.User.Player;

public class PlayerController {
    private Player[] players;
    private Player currentPlayer;
    private FieldController fieldController;
    private TradeController tradeController;

    public Player getCurrentPlayer() {
        return currentPlayer;
    }
    public void setCurrentPlayer(int currentPlayerIndex) {
        currentPlayer = players[currentPlayerIndex];
    }

    public void setFieldController(FieldController fieldController) {
        this.fieldController = fieldController;
    }
    public void setTradeController(TradeController tradeController) {
        this.tradeController = tradeController;
    }

    public PlayerController(){
        int playerCount = 0;
        while(playerCount < 3 || playerCount > 6){
            playerCount = InterfaceGUI.awaitUserIntegerInput("Indtast antal spillere mellem 3-6", 3, 6);
        }
        Player[] players = new Player[playerCount];
        InterfaceGUI.setGuiPlayersCount(playerCount);
        for(int i = 1;i<=playerCount;i++){
            String playerName = InterfaceGUI.awaitUserStringInput("Indtast spiller nr. " + i);

            if(playerName.length() < 3 || playerName.length() > 15){
                InterfaceGUI.showMessage("Navnet skal være 3-15 karakterer langt");
                i--;
                continue;
            }
            boolean playerExist = false;
            for(Player player : players){
                if(player != null && player.getName().equals(playerName)){
                    InterfaceGUI.showMessage("Navnet skal være unikt.");
                    i--;
                    playerExist = true;
                }
            }
            if(playerExist) {
                continue;
            }

            Account account = new Account(3000);
            Player player = new Player(playerName, account);
            players[i-1] = player;

            InterfaceGUI.addGUIPlayer(player.getName(), player.getAccount().getBalance());
        }
        this.players = players;

        for(Player player : players){
            player.setFieldIndexx(0);
        }
    }

    public Player[] getPlayers(){
        return players;
    }
    public Player[] getPlayers(Player exceptPlayer){
        Player[] players;
        players = new Player[this.players.length-1];
        int index = 0;
        for(Player player : this.players){
            if(player != exceptPlayer){
                players[index] = player;
                index++;
            }
        }
        return players;
    }
    public Player getPlayer(int playerIndex) {
        return players[playerIndex];
    }
    public Player getPlayerFromName(String name) {
        for(Player player : players){
            if(player.getName().equals(name)){
                return player;
            }
        }
        return null;
    }
    public String[] getPlayerNames(Player[] players){
        if(players.length == 0){
            return null;
        }
        String[] playerNames = new String[players.length];
        for(int i = 0;i<players.length;i++){
            playerNames[i] = players[i].getName();
        }
        return playerNames;
    }

    public void movePlayerForwardField(Player player, int diceValues){
        int fieldIndex = player.getFieldIndex() + diceValues;
        movePlayerToField(player, fieldIndex);
    }
    public void movePlayerToField(Player player, int fieldIndex){
        if(fieldIndex > fieldController.getFields().length || player.isBonusOnNextRaffle()){
            this.modifyBalance(200, player);
            player.setBonusOnNextRaffle(false);
        }
        if (fieldIndex == fieldController.getFields().length){
            player.setBonusOnNextRaffle(true);
        }

        player.setFieldIndexx(fieldIndex % fieldController.getFields().length);
    }
    public void modifyBalance(int appendedBalance, Player player){
        player.getAccount().modifyBalance(appendedBalance, player.getName());

        if(player.getAccount().getBalance() < 0){
            int totalAssets = this.getAssests(player, true, true, true);
            if(totalAssets > 0){
                String sellHouse =  "Salg af huse";
                String pawnField = "Pantsæt grund";
                String trading =  "Forhandle med en anden spiller";
                String endLife =  "Afslut og tab";
                String[] buttonsLastStand;
                buttonsLastStand = new String[]{sellHouse, pawnField, trading, endLife};
                while(true){
                    String action = InterfaceGUI.awaitUserButtonsClicked("Du er på renden til at tabe! Du har nu følgende muligheder: ", currentPlayer.getName(), buttonsLastStand);
                    if(action.equals(pawnField)){
                        tradeController.pawnField(currentPlayer);
                    }
                    if(action.equals(sellHouse)){
                        tradeController.sellHouse(currentPlayer);
                    }
                    if(action.equals(trading))
                    {
                        tradeController.trade(currentPlayer);
                    }
                    if(action.equals(endLife))
                    {
                        totalAssets = 0;
                        break;
                    }
                    if(player.getAccount().getBalance() > 0){
                        break;
                    }
                }
            }

            if(totalAssets <= 0){
                OwnableField[] ownableFields = fieldController.getOwnableFields(player);
                int[] ownableFieldIndexes = new int[ownableFields.length];
                for(int i = 0;i<ownableFields.length;i++){
                    int fieldIndex = fieldController.getFieldIndex(ownableFields[i]);
                    ownableFields[i].setOwner(null, fieldIndex);
                    ownableFields[i].setPawned(false, fieldIndex);
                    if(ownableFields[i] instanceof StreetField){
                        StreetField streetField = (StreetField) ownableFields[i];
                        streetField.setBuildings(0, fieldIndex);
                    }
                    ownableFieldIndexes[i] = fieldIndex;
                }

                Player[] tempNewPlayers = new Player[players.length-1];
                int i = 0;
                for(Player activePlayer : players){
                    if(activePlayer != player){
                        tempNewPlayers[i] = activePlayer;
                        i++;
                    }
                }
                players = tempNewPlayers;

                InterfaceGUI.removeGuiPlayer(player.getName(), ownableFieldIndexes);
                InterfaceGUI.showMessage("Du har tabt.", player.getName());
            }
            if(players.length == 1){
                InterfaceGUI.showMessage("DU HAR VUNDET!!! Klik ok for at afslutte spillet", players[0].getName());
                InterfaceGUI.shutDown();
            }
        }
    }
    public int getAssests(Player player, boolean withAccount, boolean withFieldPrice, boolean withBuildings){
        int totalAssets = 0;
        OwnableField[] ownableFields = fieldController.getOwnableFields(player);
        for(OwnableField ownableField : ownableFields){
            if(withFieldPrice){
                totalAssets += ownableField.getPrice();
            }
            if(withBuildings){
                if(ownableField instanceof StreetField){
                    StreetField streetField = (StreetField) ownableField;
                        totalAssets += streetField.getBuildings() * streetField.getBuildingPrice();
                }
            }
        }
        if(withAccount){
            totalAssets += player.getAccount().getBalance();
        }
        return totalAssets;
    }
}
