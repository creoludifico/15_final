package Test;

import Matador.GUI.InterfaceGUI;
import Matador.Models.User.Account;
import Matador.Models.User.Player;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {
    Player player;
    Account account;

    @BeforeClass
    public static void initGui(){
        InterfaceGUI.initGUI();
        InterfaceGUI.setGuiPlayersCount(1);
        InterfaceGUI.addGUIPlayer("John Doe", 0);
    }

    @Before
    public void initialize(){
        account = new Account(0);
        player = new Player("John Doe", account);
    }

    @Test
    public void getName() {
        assertEquals(player.getName(), "John Doe");
    }

    @Test
    public void getAccount() {
        assertEquals(account, player.getAccount());
    }

    @Test
    public void setInJail() {
        player.setInJail(true);
    }

    @Test
    public void isInJail() {
        player.setInJail(true);
        assertEquals(player.isInJail(), true);
        player.setInJail(false);
        assertEquals(player.isInJail(), false);
    }

    @Test
    public void setHasEscapeJailCard() {
        player.setHasEscapeJailCard(true);
    }

    @Test
    public void hasEscapeJailCard() {
        player.setHasEscapeJailCard(true);
        assertEquals(player.hasEscapeJailCard(),true);
        player.setHasEscapeJailCard(false);
        assertEquals(player.hasEscapeJailCard(),false);
    }

    @Test
    public void setFieldIndexx() {
        player.setFieldIndexx(10);
    }

    @Test
    public void getFieldIndex() {
        for(int i = 0; i < 40; i++){
            player.setFieldIndexx(i);
            assertEquals(player.getFieldIndex(),i);
        }
    }

    @Test
    public void isBonusOnNextRaffle() {
        player.setBonusOnNextRaffle(true);
        assertEquals(player.isBonusOnNextRaffle(), true);
        player.setBonusOnNextRaffle(false);
        assertEquals(player.isBonusOnNextRaffle(),false);
    }

    @Test
    public void setBonusOnNextRaffle() {
        player.setBonusOnNextRaffle(true);
    }

    @Test
    public void getJailForRounds() {
        for(int i = 0; i < 10; i++) {
            player.setJailForRounds(i);
            assertEquals(player.getJailForRounds(), i);
        }
    }

    @Test
    public void setJailForRounds() {
        player.setJailForRounds(1);
    }
}