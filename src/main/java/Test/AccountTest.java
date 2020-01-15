package Test;

import Matador.Controllers.PlayerController;
import Matador.GUI.InterfaceGUI;
import Matador.Models.User.Account;
import Matador.Models.User.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import static org.junit.Assert.*;

public class AccountTest {
//    PlayerController playerController = new PlayerController();
//
//     initialisere spillerne før de indsættes i test metoderne
    @BeforeClass
    public static void InitializePlayers(){
        InterfaceGUI.initGUI();
        InterfaceGUI.setGuiPlayersCount(1);
         InterfaceGUI.addGUIPlayer("John Doe", 0);
//        Player[] players = new Player[3];
//        for (int i = 1;i<=3;i++) {
//            Account account = new Account(3000);
//            Player player = new Player("test" + i, account);
//            players[i - 1] = player;
//        }
    }

    @Test
    public void modifyBalance() {
        Account account = new Account(0);
        int check = account.getBalance();
        assertEquals(check, account.getBalance());
        for(int i = 0; i < 10000; i++) {
            check += i;
            account.modifyBalance(i, "John Doe");
            assertEquals(account.getBalance(), check);
        }
    }

    @Test
    public void getBalance() {
//        int balance = playerController.getPlayer(0).getAccount().getBalance();
//        playerController.getPlayer(0).getAccount().modifyBalance(2, playerController.getPlayer(0).getName());
//        int newBalance = playerController.getPlayer(0).getAccount().getBalance();
//
        Account account = new Account(-1);
        assertEquals(-1, account.getBalance());

        //Same as modifyBalance()
    }

    @Test
    public void getBalanceAsString() {
        Account account = new Account(0);
        for(int i = 0; i < 100000; i++) {
            account.modifyBalance(1, "John Doe");
            assertEquals("" + account.getBalance(), account.getBalanceAsString());
        }
    }
}