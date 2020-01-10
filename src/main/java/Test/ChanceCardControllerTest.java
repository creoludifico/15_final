//package Test;
//
//import Matador.Models.ChanceCard.ChanceCard;
//import Matador.Controllers.ChanceCardController;
//
//import java.util.Arrays;
//
//import static org.junit.Assert.*;
//
//public class ChanceCardControllerTest {
//
//    private static int indexOfChanceCardArray(ChanceCard[] array, ChanceCard key) {
//        int returnValue = -1;
//        for (int i = 0; i < array.length; ++i) {
//            if (key.equals(array[i])) {
//                returnValue = i;
//                break;
//            }
//        }
//        return returnValue;
//    }
//
//    private void initBooleanArray(Boolean[] array, Boolean trueFalse) {
//        for (int i = 0; i < array.length; i++) {
//            array[i] = trueFalse;
//        }
//    }
//
//    private Boolean allTrueFalseArray(Boolean[] array, Boolean trueFalse) {
//        for (int i = 0; i < array.length; i++) {
//            if(array[i] != trueFalse)
//                return false;
//        }
//        return true;
//    }
//
//    @org.junit.Test
//    public void shuffleCards() {
//        ChanceCardController chanceCards = new ChanceCardController();
//        ChanceCard[] copiedDeck = Arrays.copyOf(chanceCards.getDeck(), chanceCards.getDeck().length);
//        chanceCards.shuffleCards();
//        ChanceCard[] shuffledDeck = chanceCards.getDeck();
//        assertEquals(copiedDeck.length, shuffledDeck.length);
//        for (ChanceCard card : copiedDeck) {
//            assertTrue(indexOfChanceCardArray(shuffledDeck, card) != -1);
//        }
//    }
//
//    @org.junit.Test
//    public void pickCard() {
//        ChanceCardController chanceCards = new ChanceCardController();
//        ChanceCard[] copiedDeck = Arrays.copyOf(chanceCards.getDeck(), chanceCards.getDeck().length);
//        Boolean[] isTaken = new Boolean[copiedDeck.length];
//        initBooleanArray(isTaken, false);
//        for (int index = 0; index < copiedDeck.length; index++) {
//            ChanceCard card = chanceCards.pickCard();
//            isTaken[indexOfChanceCardArray(copiedDeck, card)] = true;
//        }
//        assertTrue(allTrueFalseArray(isTaken, true));
//    }
//}