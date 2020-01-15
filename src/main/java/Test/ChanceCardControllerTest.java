package Test;

import Matador.Models.ChanceCard.ChanceCard;
import Matador.Controllers.ChanceCardController;

import java.util.Arrays;

import static org.junit.Assert.*;

public class ChanceCardControllerTest {
    private ChanceCardController chanceCards = new ChanceCardController();

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

    @org.junit.Test
    public void shuffleCards() {
        ChanceCard[] originalDeck = Arrays.copyOf(chanceCards.getChanceCards(), chanceCards.getChanceCards().length);
        chanceCards.shuffleChanceCards();
        ChanceCard[] shuffledDeck = chanceCards.getChanceCards();

        assertEquals(originalDeck.length, shuffledDeck.length);

        for (ChanceCard card : shuffledDeck) {
            assertTrue(getIndexOfChanceCard(originalDeck, card) != -1);
        }
    }

    @org.junit.Test
    public void pickCard() {
        ChanceCard[] originalDeck = Arrays.copyOf(chanceCards.getChanceCards(), chanceCards.getChanceCards().length);
        boolean[] isTaken = initBooleanArray(originalDeck.length, false);

        chanceCards.shuffleChanceCards();

        for (int index = 0; index < originalDeck.length; index++) {
            ChanceCard card = chanceCards.pickChanceCard();
            isTaken[getIndexOfChanceCard(originalDeck, card)] = true;
        }

        assertTrue(isAllTrueFalseArray(isTaken, true));
    }

}
