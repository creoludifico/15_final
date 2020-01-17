package Test;

import Matador.Controllers.ChanceCardController;
import Matador.Controllers.FieldController;
import Matador.Controllers.PlayerController;
import Matador.Controllers.TradeController;
import Matador.GUI.InterfaceGUI;
import Matador.Models.Field.OwnableField;
import Matador.Models.Field.StreetField;
import Matador.Models.User.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TradeControllerTest {
    TradeController tradeController;
    PlayerController playerController;
    private int[] integerReturns = new int[]{3};
    private String[] stringReturns = new String[]{"John Doe", "Doe John", "Joe Dohn", "Random"};

    @Before
    public void setUp() throws Exception {
        InterfaceGUI.initFakeGUI(integerReturns, stringReturns);

        tradeController = new TradeController();

        FieldController fieldController = new FieldController();
        tradeController.setFieldController(fieldController);

        playerController = new PlayerController();
        tradeController.setPlayerController(playerController);
    }

    @After
    public void tearDown() throws Exception {
        InterfaceGUI.shutDown();
        tradeController = null;
    }

    @Test
    public void auction() {
        OwnableField field = new StreetField("The Plaza", 100, 20, 14, new int[]{1,2,3,4,5}, "Green");
        tradeController.auction(field, 1);
    }

    @Test
    public void pawnField() {
        tradeController.pawnField(playerController.getCurrentPlayer());
    }

    @Test
    public void unpawnField() {
        tradeController.unpawnField(playerController.getCurrentPlayer());
    }

    @Test
    public void sellHouse() {
        tradeController.sellHouse(playerController.getCurrentPlayer());
    }

    @Test
    public void buyHouse() {
        tradeController.buyHouse(playerController.getCurrentPlayer());
    }

    @Test
    public void trade() {
        tradeController.trade(playerController.getCurrentPlayer());
    }
}