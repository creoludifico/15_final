package Test;

import Matador.Controllers.PlayerController;
import Matador.GUI.InterfaceGUI;
import Matador.Models.User.Account;
import Matador.Models.User.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccountTest {

    PlayerController playerController = new PlayerController();

    // initialisere spillerne før de indsættes i test metoderne
    @Before
    public void InitializePlayers(){
        Player[] players = new Player[3];
        for (int i = 1;i<=3;i++) {
            Account account = new Account(3000);
            Player player = new Player("test" + i, account);
            players[i - 1] = player;
        }
    }

    @Test

    public void modifyBalance() {

    }

    @Test
    public void getBalance() {
        int balance = playerController.getPlayer(0).getAccount().getBalance();
        playerController.getPlayer(0).getAccount().modifyBalance(balance + 2, playerController.getPlayer(0).getName());
        int newBalance = playerController.getPlayer(0).getAccount().getBalance();

        assertEquals(balance+2, newBalance);

    }

    @Test
    public void getBalanceAsString() {
    }
}