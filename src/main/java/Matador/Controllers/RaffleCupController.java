package Matador.Controllers;

import Matador.GUI.InterfaceGUI;
import Matador.Models.RaffleCup.Die;

public class RaffleCupController {
    private Die d1, d2;

    private int demoCounter = 0;

    public RaffleCupController(){
        d1 = new Die();
        d2 = new Die();
    }

    public void awaitShakeTheRaffleCup(String name){
        String[] buttons = new String[]{"Kast terning",};
        InterfaceGUI.awaitUserButtonsClicked("Kast terning", name, buttons);

        switch (demoCounter) {
            case 0:
                d1.setDie(4);
                d2.setDie(3);
                break;
            case 1:
                d1.setDie(3);
                d2.setDie(3);
                break;
            case 2:
                d1.setDie(1);
                d2.setDie(1);
                break;
            case 3:
                d1.setDie(6);
                d2.setDie(6);
                break;
            case 4:
                d1.setDie(5);
                d2.setDie(4);
                break;
            case 5:
                d1.setDie(3);
                d2.setDie(2);
            case 6:
                d1.setDie(1);
                d2.setDie(1);
                break;
            default:
                this.shakeTheRaffleCup();
                break;
        }
        demoCounter++;
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