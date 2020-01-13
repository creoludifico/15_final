package Test;

import Matador.Controllers.PlayerController;
import Matador.Models.User.Account;
import Matador.Models.User.Player;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccountTest {

    PlayerController playerController = new PlayerController();

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