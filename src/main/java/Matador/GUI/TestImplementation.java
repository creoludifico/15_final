package Matador.GUI;

import Matador.Models.Field.Field;
import gui_fields.*;

import java.util.Arrays;

public class TestImplementation extends Implementation {
    private int stringIndex = 0;
    private String[] stringReturns = new String[]{"Kat", "Hund", "Flåt", "Random"};

    private int integerIndex = 0;
    private int[] integerReturns = new int[]{3};

    public void initGUIFields(Field[] fields) { }
    public void addGUIPlayer(String name, int balance) { }

    public void showMessage (String msg){ }
    public void showMessage (String msg, String name){ }

    public TestImplementation(){
    }
    public TestImplementation(int[] integerReturns, String[] stringReturns){
        this.integerReturns = integerReturns;
        this.stringReturns = stringReturns;
    }

    public String awaitUserStringInput (String msg){
        if(stringIndex < stringReturns.length)
            return stringReturns[stringIndex++];
        return "";
    }

    public String awaitUserButtonsClicked (String msg, String... buttonsString){
        return buttonsString[(int)(Math.random()*buttonsString.length)];
    }
    public String awaitUserButtonsClicked (String msg, String name, String... buttonsString){
        return buttonsString[(int)(Math.random()*buttonsString.length)];
    }
    public String awaitDropDownSelected(String msg, String name, String... selections){
        return selections[(int)(Math.random()*selections.length)];
    }

    public int awaitUserIntegerInput (String msg) {
        if(integerIndex < integerReturns.length)
            return integerReturns[integerIndex++];
        return (int)(Math.random()*1000);
    }
    public int awaitUserIntegerInput (String msg, String name) {
            if(integerIndex < integerReturns.length)
                return integerReturns[integerIndex++];
            return (int)(Math.random()*1000);
    }
    public int awaitUserIntegerInput (String msg, int min, int max) {
        if(integerIndex < integerReturns.length)
            return integerReturns[integerIndex++];
        return (int)(min + Math.random()*(max-min));
    }

    public void setGuiPlayerBalance(String name, int balance){ }
    public void setGUIFieldOwner(String name, int fieldIndex){ }
    public void setGuiCard(String text) { }
    public void setDices(int dieValue1, int dieValue2){ }
    public void setGuiPlayersCount(int playerCount){ }
    public void hideGuiCard() { }
    public void movePlayerToField(String name, int fieldIndex) { }
    public void setFieldHouses(int fieldIndex, int houseCount) { }
    public void setFieldHotel(int fieldIndex, boolean hasHotel) { }
    public void setFieldPawned(int fieldIndex, boolean pawned){ }
    public void setGuiPlayerLost(String name) { }
    public void shutDown(){ }
}
