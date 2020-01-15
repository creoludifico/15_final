package Test;

import Matador.Controllers.FieldController;
import Matador.Controllers.PlayerController;
import Matador.Controllers.TradeController;
import Matador.GUI.InterfaceGUI;
import Matador.Models.ChanceCard.ChanceCard;
import Matador.Controllers.ChanceCardController;
import Matador.Models.Field.Field;
import Matador.Models.User.Account;
import Matador.Models.User.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class ChanceCardControllerTest {
    private ChanceCardController chanceCardController;
    private PlayerController playerController;
    private int[] integerReturns = new int[]{3};
    private String[] stringReturns = new String[]{"John Doe", "Doe John", "Joe Dohn", "Random"};

    @Before
    public void initialize(){
        InterfaceGUI.initFakeGUI();
        chanceCardController = new ChanceCardController();
        FieldController fieldController = new FieldController();
        playerController = new PlayerController();
        TradeController tradeController = new TradeController();
        chanceCardController.setFieldController(fieldController);
        chanceCardController.setPlayerController(playerController);
        playerController.setFieldController(fieldController);
        playerController.setTradeController(tradeController);
        fieldController.setChanceCardController(chanceCardController);
        fieldController.setPlayerController(playerController);
        fieldController.setTradeController(tradeController);
        tradeController.setFieldController(fieldController);
        tradeController.setPlayerController(playerController);
    }

    @After
    public void tearDown(){
        InterfaceGUI.shutDown();
        chanceCardController = null;
    }

    public static int getIndexOfChanceCard(ChanceCard[] chanceCards, ChanceCard key) {
        for (int index = 0; index < chanceCards.length; index++) {
            if (key.equals(chanceCards[index]))
                return index;
        }
        return -1;
    }

    private boolean[] initBooleanArray(int length, boolean trueFalse) {
        boolean[] result = new boolean[length];
        for (int i = 0; i < length; i++) {
            result[i] = trueFalse;
        }
        return result;
    }

    private boolean isAllTrueFalseArray(boolean[] array, boolean trueFalse) {
        for (int i = 0; i < array.length; i++) {
            if(array[i] != trueFalse)
                return false;
        }
        return true;
    }

    @Test
    public void action() {
        for(int i = 0; i < 100000; i++) {
            chanceCardController.action(playerController.getCurrentPlayer());
        }
    }

    @org.junit.Test
    public void shuffleCards() {
        ChanceCard[] originalDeck = Arrays.copyOf(chanceCardController.getChanceCards(), chanceCardController.getChanceCards().length);
        chanceCardController.shuffleChanceCards();
        ChanceCard[] shuffledDeck = chanceCardController.getChanceCards();

        assertEquals(originalDeck.length, shuffledDeck.length);

        for (ChanceCard card : shuffledDeck) {
            assertTrue(getIndexOfChanceCard(originalDeck, card) != -1);
        }
    }

    @org.junit.Test
    public void pickCard() {
        ChanceCard[] originalDeck = Arrays.copyOf(chanceCardController.getChanceCards(), chanceCardController.getChanceCards().length);
        boolean[] isTaken = initBooleanArray(originalDeck.length, false);

        chanceCardController.shuffleChanceCards();

        for (int index = 0; index < originalDeck.length; index++) {
            ChanceCard card = chanceCardController.pickChanceCard();
            isTaken[getIndexOfChanceCard(originalDeck, card)] = true;
        }

        assertTrue(isAllTrueFalseArray(isTaken, true));
    }

}
