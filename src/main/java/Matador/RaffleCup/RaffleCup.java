package Matador.RaffleCup;

import GUI.InterfaceGUI;

public class RaffleCup {
    private Die d1, d2;

    public RaffleCup(){
        d1 = new Die();
        d2 = new Die();
    }

    public void awaitShakeTheRaffleCup(){
        String[] buttons = new String[]{"Kast terning",};
        InterfaceGUI.awaitUserButtonsClicked("", buttons);
        d1.rollDie();
        d2.rollDie();
    }

    public boolean isSameDie(){
        return d1.getDie() == d2.getDie();
    }

    public int getTotalValue(){
        InterfaceGUI.setDices(d1.getDie(), d2.getDie());
        return d1.getDie() + d2.getDie();
    }
}
