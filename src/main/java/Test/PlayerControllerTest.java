package Test;

import Matador.Controllers.PlayerController;
import Matador.Models.User.Account;
import Matador.Models.User.Player;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerControllerTest {
    private PlayerController playerController = new PlayerController();
    @Test
    public void getCurrentPlayer() {
            Player[] players = new Player[3];
            Account account = new Account(3000);


            for (int i = 0; i < 2; i++) {
                Player player = new Player("test" + i, account);
                players[i] = player;
            }
            playerController.setCurrentPlayer(0);
            Player player = playerController.getCurrentPlayer();
            assertEquals(player, "test0");
    }

    @Test
    public void setCurrentPlayer() {
    }

    @Test
    public void getPlayers() {
    }

    @Test
    public void testGetPlayers() {
    }

    @Test
    public void getPlayer() {
    }

    @Test
    public void getPlayerFromName() {
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