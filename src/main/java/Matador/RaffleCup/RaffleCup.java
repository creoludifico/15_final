package Matador.RaffleCup;

public class RaffleCup {
    private Die d1, d2;

    public RaffleCup(){
        d1 = new Die();
        d2 = new Die();
    }

    public void shakeTheRaffleCup(){
        d1.rollDie();
        d2.rollDie();
    }

    public int getValue(){
        return d1.getDie() + d2.getDie();
    }
}
