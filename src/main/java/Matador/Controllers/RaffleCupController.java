package Matador.Controllers;

import Matador.GUI.InterfaceGUI;
import Matador.Models.RaffleCup.Die;

public class RaffleCupController {
    private Die d1, d2;

    public RaffleCupController(){
        d1 = new Die();
        d2 = new Die();
    }

    public void awaitShakeTheRaffleCup(String name){
        String[] buttons = new String[]{"Kast terning",};
        InterfaceGUI.awaitUserButtonsClicked("Kast terning", name, buttons);
        this.shakeTheRaffleCup();
    }

    public void shakeTheRaffleCup(){
        d1.rollDie();
        d2.rollDie();
        InterfaceGUI.setDices(d1.getDie(), d2.getDie());
    }

    public boolean isSameDie(){
        return d1.getDie() == d2.getDie();
    }

    public int getTotalValue(){
        return d1.getDie() + d2.getDie();
    }
}
