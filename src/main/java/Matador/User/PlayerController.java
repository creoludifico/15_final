package Matador.User;

import GUI.InterfaceGUI;

public class PlayerController {
    private Player[] players;

    public PlayerController(){
        int playerCount = InterfaceGUI.awaitUserIntegerInput("Indtast antal spillere mellem 3-6", 3, 6);
        Player[] players = new Player[playerCount];
        for(int i = 1;i<=playerCount;i++){
            String playerName = InterfaceGUI.awaitUserStringInput("Indtast spiller nr. " + i);
            if(playerName.length() < 3 || playerName.length() > 15){
                InterfaceGUI.showMessage("Navnet skal være 3-15 karakterer langt");
                i--;
                continue;
            }

            Account account = new Account(3000);
            Player player = new Player(playerName, account);
            players[i-1] = player;

            InterfaceGUI.addGUIPlayer(player.getName(), player.getAccount().getBalance());
        }
        this.players = players;
    }

    public Player getPlayerFromIndex(int index){
        try{
            return players[index];
        }
        catch(Exception e){
            System.out.println("Fejl! Spilleren eksisterer ikke!");
        }
        return null;
    }
}
