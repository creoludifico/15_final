package Matador.User;

public class PlayerController {
    private Player[] players;

    public PlayerController(Player[] players){
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
