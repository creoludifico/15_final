package Matador.User;

import GUI.InterfaceGUI;
import Matador.Field.Field;
import Matador.Field.FieldController;

public class PlayerController {
    private Player[] players;
    private Player currentPlayer;
    private FieldController fieldController;

    public PlayerController(FieldController fieldController){
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
            player.setFieldIndex(0);
        }
        this.fieldController = fieldController;
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

    public void movePlayerOnField(Player player, int diceValues){
        int fieldIndex = player.getFieldIndex() + diceValues;
        player.setFieldIndex(fieldIndex % fieldController.getFields().length);
    }

}
