package Test;

import Matador.Models.RaffleCup.Die;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DieTest {
    Die die1, die2;
    private double[] probs = {
            0.0, //0
            0.0, //1
            0.0278,
            0.0556,
            0.0833,
            0.1111,
            0.1389,
            0.1667,
            0.1389,
            0.1111,
            0.0833,
            0.0556,
            0.0278
    };

    @Before
    public void setUp() {
        die1 = new Die();
        die2 = new Die();
    }

    @Test
    public void rollDie() {
        int amountTest = 100001;
        int rollValue = 0;
        int[] amountCount = new int[13];
        for(int i = 0; i < amountTest; i++){
            die1.rollDie();
            die2.rollDie();
            rollValue = die1.getDie() + die2.getDie();
            assertEquals(7, rollValue,5);
            amountCount[rollValue]++;
        }
        for (int i = 2; i >= 12; i++) {
            //assertEquals(probs, (double)(amountCount[i]/amountTest), 0.01);
        }
    }

    @Test
    public void getDie() {
    }
}