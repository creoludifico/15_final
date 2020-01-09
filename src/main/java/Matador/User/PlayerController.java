package Matador.User;

import GUI.InterfaceGUI;
import Matador.ChanceCard.ChanceCardController;
import Matador.Field.Field;
import Matador.Field.FieldController;
import Matador.Field.OwnableField;
import Matador.Field.StreetField;
import Matador.TradeController;

public class PlayerController {
    private Player[] players;
    private Player currentPlayer;
    private FieldController fieldController;
    private TradeController tradeController;

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

    public void setCurrentPlayer(int currentPlayerIndex) {
        currentPlayer = players[currentPlayerIndex];
    }
    public Player getCurrentPlayer() {
        return currentPlayer;
    }
    public Player getPlayer(int playerIndex) {
        return players[playerIndex];
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
            int totalAssets = this.collectAsssets(player);
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
                        tradeController.tradeWithPlayer(currentPlayer);
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
                OwnableField[] ownableFields = fieldController.getOwnerOfFieldsArray(player);
                int[] ownableFieldIndexes = new int[ownableFields.length];
                for(int i = 0;i<ownableFields.length;i++){
                    ownableFields[i].setOwner(null, fieldController.getFieldIndex(ownableFields[i]));
                    ownableFields[i].setPawned(false);
                    if(ownableFields[i] instanceof StreetField){
                        StreetField streetField = (StreetField) ownableFields[i];
                        streetField.setBuildings(0);
                    }
                    ownableFieldIndexes[i] = fieldController.getFieldIndex(ownableFields[i]);
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

    public int collectAsssets (Player player){
        Field[] fields = fieldController.getFields();
        int total = 0;
        for (int index = 0; index < fields.length; index++) {
            Field field = fields[index];
            if (field instanceof OwnableField) {
                OwnableField ownableField = (OwnableField) field;
                if (ownableField.getOwner() == player) {
                    total += ownableField.getPrice();
                    if (ownableField instanceof StreetField) {
                        StreetField streetField = (StreetField) ownableField;
                        total += streetField.getBuildingPrice() * streetField.getBuildings();
                    }
                }
            }
        }
        total += player.getAccount().getBalance();
        return total;
    }
}
