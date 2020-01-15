package Matador.Controllers;

import Matador.GUI.InterfaceGUI;
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
            int totalAssets = this.getAssets(player, true, true, true);

            if(totalAssets > 0){
                String pawnField = "Pantsæt grund";
                String sellHouse =  "Salg af huse";
                String trading =  "Forhandle med en anden spiller";
                String endTurn =  "Afslut & tab";

                String[] buttonsForEndActions;
                while (true)
                {
                    String msg = "Du er tæt på at tabe! Gør noget! Du har nu følgende muligheder: ";
                    if(player.getAccount().getBalance() >= 0){
                        endTurn = "Afslut";
                        msg = "Du overlever denne gang. Du har nu følgende muligheder: ";
                    }
                    buttonsForEndActions = new String[]{
                            pawnField,
                            sellHouse,
                            trading,
                            endTurn
                    };

                    String action = InterfaceGUI.awaitUserButtonsClicked(msg, player.getName(), buttonsForEndActions);
                    if(action.equals(pawnField)){
                        tradeController.pawnField(currentPlayer);
                    }
                    if(action.equals(sellHouse)){
                        tradeController.sellHouse(currentPlayer);
                    }
                    if(action.equals(trading)){
                        tradeController.trade(currentPlayer);
                    }
                    if(action.equals(endTurn)){
                        break;
                    }
                }
            }

            if(player.getAccount().getBalance() < 0){
                OwnableField[] ownableFields = fieldController.getOwnableFields(player);
                for(OwnableField ownableField : ownableFields){
                    int fieldIndex = fieldController.getFieldIndex(ownableField);
                    ownableField.setOwner(null, fieldIndex);
                    ownableField.setPawned(false, fieldIndex);
                    if(ownableField instanceof StreetField){
                        StreetField streetField =  (StreetField) ownableField;
                        streetField.setBuildings(0, fieldIndex);
                    }
                }

                Player[] tempPlayers = new Player[players.length-1];

                int i = 0;
                for(Player activePlayer : players){
                    if(activePlayer != player){
                        tempPlayers[i] = activePlayer;
                        i++;
                    }
                }
                players = tempPlayers;
                InterfaceGUI.setGuiPlayerLost(player.getName());

                if(players.length == 1){
                    InterfaceGUI.showMessage("DU HAR VUNDET!! ", players[0].getName());
                    InterfaceGUI.shutDown();
                }
            }

        }
    }

    public int getHouses(Player player){
        int houseCount = 0;
        for(StreetField streetField: fieldController.getStreetFields(player, true)) {
            if(streetField.getBuildings() > 4){
                continue;
            }
            houseCount+=streetField.getBuildings();
        }
        return houseCount;
    }

    public int getHotels(Player player){
        int hotelCount = 0;
        for(StreetField streetField: fieldController.getStreetFields(player, true)) {
            if(streetField.getBuildings() != 4){
                continue;
            }
            hotelCount+=1;
        }
        return hotelCount;
    }
    
    //Det er kun total assests hvis ,true,true,true. Så derfor er det kun getAssets. (Den skal også bruges til andre steder hvor man skal bruge assets)
    public int getAssets(Player player, boolean withAccount, boolean withFieldPrice, boolean withBuildings){
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
