package Matador.User;

import GUI.InterfaceGUI;
import Matador.ChanceCard.ChanceCardController;
import Matador.Field.Field;
import Matador.Field.FieldController;

public class PlayerController {
    private Player[] players;
    private Player currentPlayer;
    private FieldController fieldController;
    private ChanceCardController chanceCardController;

    public void setFieldController(FieldController fieldController) {
        this.fieldController = fieldController;
    }
    public void setChanceCardController(ChanceCardController chanceCardController) {
        this.chanceCardController = chanceCardController;
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
            player.getAccount().modifyBalance(200, player.getName());
            player.setBonusOnNextRaffle(false);
        }
        if (fieldIndex == fieldController.getFields().length){
            player.setBonusOnNextRaffle(true);
        }

        player.setFieldIndexx(fieldIndex % fieldController.getFields().length);
    }
}
