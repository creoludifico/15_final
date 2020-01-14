package Test;

import Matador.Controllers.PlayerController;
import Matador.GUI.InterfaceGUI;
import Matador.Models.User.Account;
import Matador.Models.User.Player;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerControllerTest {


    @Test
    public void getCurrentPlayer() {
        PlayerController playerController = new PlayerController();

        InterfaceGUI.initGUI();

        for (int i = 0; i < playerController.getPlayers().length; i++)
        {
            playerController.setCurrentPlayer(i);
            Player player = playerController.getCurrentPlayer();
            assertEquals(player.getName(), playerController.getPlayer(i).getName());
        }
    }

//    @Test
//    public void setCurrentPlayer() {
//    }
//
//    @Test
//    public void getPlayers() {
//
//    }
//
//
//    @Test
//    public void getPlayer() {
//    }

    @Test
    public void getPlayerFromName() {
        InterfaceGUI.initGUI();
        PlayerController playerController = new PlayerController();


//        Player player = playerController.getCurrentPlayer();
//
//        int i = 0;
//        for(Player pl : playerController.getPlayers()){
//            playerController.setCurrentPlayer(i);
//            assertEquals(pl.getName(), player.getName());
//            i++;
//        }


    }

    @Test
    public void getPlayerNames() {
    }

    @Test
    public void movePlayerForwardField() {
    }

    @Test
    public void movePlayerToField() {
    }

    @Test
    public void modifyBalance() {
    }

    @Test
    public void getAssets() {
    }
}