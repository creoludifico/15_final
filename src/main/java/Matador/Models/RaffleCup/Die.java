package Matador.Models.RaffleCup;

import java.util.Random;

public class Die {
    private int value;

    public void rollDie() {
        this.value = new Random().nextInt(6) + 1;
    }
    public int getDie() {
        return this.value;
    }
}
