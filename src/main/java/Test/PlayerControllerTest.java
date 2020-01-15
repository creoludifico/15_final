package Test;

import Matador.Controllers.PlayerController;
import Matador.GUI.InterfaceGUI;
import Matador.Models.User.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerControllerTest {
    private PlayerController playerController;

    private static String[] stringReturns = new String[]{"Kat", "Hund", "Flåt"};
    private static int[] integerReturns = new int[]{3};

    @Before
    public void initialize() {
        InterfaceGUI.initGUI(integerReturns, stringReturns);
        playerController = new PlayerController();
    }

    @After
    public void tearDown() {
        playerController = null;
        InterfaceGUI.shutDown();
    }

    @Test
    public void getCurrentPlayer() {
        for (int i = 0; i < playerController.getPlayers().length; i++)
        {
            playerController.setCurrentPlayer(i);
            Player player = playerController.getCurrentPlayer();
            assertEquals(player.getName(), playerController.getPlayer(i).getName());
        }
    }

    @Test
    public void getPlayerFromName() {
        for (int i = 0; i < playerController.getPlayers().length; i++) {
            playerController.setCurrentPlayer(i);
            String name = playerController.getCurrentPlayer().getName();
            Player player = playerController.getPlayerFromName(name);
            assertEquals(playerController.getCurrentPlayer(),player);
        }
    }


    @Test
    public void modifyBalance() {
        playerController.setCurrentPlayer(0);
        int beforeBalance = playerController.getPlayer(0).getAccount().getBalance();

        playerController.getPlayer(0).getAccount().modifyBalance( 2, playerController.getCurrentPlayer().getName());
        assertEquals(beforeBalance + 2, playerController.getCurrentPlayer().getAccount().getBalance());
    }

//    @Test
//    public void getAssets() {
//        InterfaceGUI.initGUI();
//        PlayerController playerController = new PlayerController();
//        FieldController fieldController = new FieldController();
//        RaffleCupController raffleCupController = new RaffleCupController();
//
//
//        playerController.setCurrentPlayer(0);
//        Player player = playerController.getCurrentPlayer();
//
////        fieldController.fieldAction(player,1, raffleCupController);
//        OwnableField[] ownableFields = fieldController.getOwnableFields(player);
//        for(OwnableField ownableField : ownableFields) {
//            int property = ownableField.getPrice();
//
//
//            playerController.setCurrentPlayer(0);
//
//            int assets = playerController.getAssets(player, true, false, false);
//            int money = playerController.getCurrentPlayer().getAccount().getBalance();
//
//            int total = money + property;
//            assertEquals(assets, total);
//
//        }
//    }
}