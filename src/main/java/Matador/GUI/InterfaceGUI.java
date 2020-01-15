package Matador.GUI;

import Matador.Models.Field.Field;
import gui_fields.*;
import gui_main.GUI;

import java.awt.*;
import java.util.Arrays;

public class InterfaceGUI {
    private static GUI gui;
    private static GUI_Field[] guiFields;
    private static GUI_Player[] guiPlayers;

    /* For the fakeGui return values: */
    private static boolean fakeGui;

    private static int stringIndex = 0;
    private static String[] stringReturns = new String[]{"Kat", "Hund", "Flåt"};

    private static int integerIndex = 0;
    private static int[] integerReturns = new int[]{3};

    private static int initPlayerIndex = 0;
    private static GUI_Car.Type[] types = new GUI_Car.Type[]{
            GUI_Car.Type.RACECAR,
            GUI_Car.Type.TRACTOR,
            GUI_Car.Type.CAR,
            GUI_Car.Type.UFO,
            GUI_Car.Type.RACECAR,
            GUI_Car.Type.TRACTOR
    };
    private static Color[] colors = new Color[]{
            Color.black,
            Color.blue,
            Color.pink,
            Color.green,
            Color.orange,
            Color.white
    };
    private static int lostCounter = 0;

    public static void initGUI(){
        gui = new GUI();
        guiFields = gui.getFields();
    }
    public static void initFakeGUI(){
        fakeGui = true;
    }
    public static void initFakeGUI(int[] _integerReturns, String[] _stringReturns) {
        integerReturns = _integerReturns;
        integerIndex = 0;
        stringReturns = _stringReturns;
        stringIndex = 0;
        initFakeGUI();
    }
    public static void initGUIFields(Field[] fields) {
        if(fakeGui)
            return;

        for(int i = 0;i<fields.length;i++){
            guiFields[i].setTitle(fields[i].getTitle());
            guiFields[i].setSubText(fields[i].getSubTitle());
            if(guiFields[i] instanceof GUI_Street){
                GUI_Street guiStreet = (GUI_Street) guiFields[i];
                guiStreet.setRent("");
                guiStreet.setRentLabel("");
            } else if(guiFields[i] instanceof GUI_Shipping){
                GUI_Shipping guiShipping = (GUI_Shipping) guiFields[i];
                guiShipping.setRentLabel("");
            }else if(guiFields[i] instanceof  GUI_Brewery){
                GUI_Brewery guiBrewery = (GUI_Brewery) guiFields[i];
                guiBrewery.setRent("");
                guiBrewery.setRentLabel("");
            }
        }
    }
    public static void addGUIPlayer(String name, int balance){
        if(fakeGui)
            return;

        GUI_Car guiCar = new GUI_Car(colors[initPlayerIndex], colors[initPlayerIndex], types[initPlayerIndex], GUI_Car.Pattern.ZEBRA);
        GUI_Player guiPlayer = new GUI_Player(name, balance, guiCar);
        gui.addPlayer(guiPlayer);
        guiPlayers[initPlayerIndex] = guiPlayer;
        initPlayerIndex++;
    }
    private static GUI_Player getGuiPlayer(String name){
        if(fakeGui)
            return null;

        GUI_Player guiPlayer = null;
        for(GUI_Player gp : guiPlayers) {
            if (gp.getName().equals(name)) {
                guiPlayer = gp;
            }
        }
        return guiPlayer;
    }

    public static void showMessage (String msg){
        if(fakeGui)
            return;
        gui.showMessage(msg);
    }
    public static void showMessage (String msg, String name){
        if(fakeGui)
            return;
        gui.showMessage("PERSON: " + name + " - " + msg);
    }

    public static String[] getStringsForAction(String[] strings, String lastAction){
        int newLength = strings.length + 1;
        String[] actions = Arrays.copyOf(strings, newLength);
        actions[newLength-1] = lastAction;
        return actions;
    }
    public static String awaitUserStringInput (String msg){
        if(fakeGui){
            return stringReturns[stringIndex++];
        }
        return gui.getUserString(msg);
    }
    public static String awaitUserButtonsClicked (String msg, String... buttonsString){
        if(fakeGui){
            return buttonsString[(int)(Math.random()*buttonsString.length)];
        }
        return gui.getUserButtonPressed(msg, buttonsString);
    }
    public static String awaitUserButtonsClicked (String msg, String name, String... buttonsString){
        if(fakeGui){
            return buttonsString[(int)(Math.random()*buttonsString.length)];
        }
        return gui.getUserButtonPressed("PERSON: " + name + " - " + msg, buttonsString);
    }
    public static String awaitDropDownSelected(String msg, String name, String... selections){
        if(fakeGui){
            return selections[(int)(Math.random()*selections.length)];
        }
        return gui.getUserSelection("PERSON: " + name + " - " + msg, selections);
    }
    public static int awaitUserIntegerInput (String msg) {
        if(fakeGui){
            if(integerIndex < integerReturns.length)
                return integerReturns[integerIndex++];
            else
                return (int)(Math.random()*1000);
        }
        return gui.getUserInteger(msg);
    }
    public static int awaitUserIntegerInput (String msg, String name) {
        if(fakeGui){
            if(integerIndex < integerReturns.length)
                return integerReturns[integerIndex++];
            else
                return (int)(Math.random()*1000);
        }
        return gui.getUserInteger("PERSON: " + name + " - " + msg);
    }
    public static int awaitUserIntegerInput (String msg, int min, int max) {
        if(fakeGui){
            if(integerIndex < integerReturns.length)
                return integerReturns[integerIndex++];
            else
                return (int)(Math.random()*1000);
        }
        return gui.getUserInteger(msg, min, max); //Der er en fejl i GUIen at hvis man ikke indtaster noget og klikker ENTER kommer der en fejl. (Kan ikke gribes via try catch)
    }

    public static void setGuiPlayerBalance(String name, int balance){
        if(fakeGui)
            return;
        getGuiPlayer(name).setBalance(balance);
    }
    public static void setGUIFieldOwner(String name, int fieldIndex){
        if(fakeGui)
            return;
        GUI_Field guiField = guiFields[fieldIndex];
        ((GUI_Ownable) guiField).setOwnerName(name);
        ((GUI_Ownable) guiField).setBorder(getGuiPlayer(name).getPrimaryColor());
    }
    public static void setGuiCard(String text) {
        if(fakeGui)
            return;
        gui.displayChanceCard(text);
    }
    public static void setDices(int dieValue1, int dieValue2){
        if(fakeGui)
            return;
        gui.setDice(dieValue1, 4, 3, dieValue2, 6, 3);
    }
    public static void setGuiPlayersCount(int playerCount){
        if(fakeGui)
            return;
        guiPlayers = new GUI_Player[playerCount];
    }

    public static void hideGuiCard() {
        if(fakeGui)
            return;
        gui.displayChanceCard("");
    }

    public static void movePlayerToField(String name, int fieldIndex) {
        if(fakeGui)
            return;
        GUI_Player guiPlayer = getGuiPlayer(name);
        for(GUI_Field guiField : guiFields){
            guiField.setCar(guiPlayer, false);
        }
        guiFields[fieldIndex].setCar(guiPlayer, true);
    }

    public static void setFieldHouses(int fieldIndex, int houseCount) {
        if(fakeGui)
            return;
        GUI_Field field = guiFields[fieldIndex];
        if (field instanceof GUI_Street) {
            ((GUI_Street)field).setHouses(houseCount);
        }
    }
    public static void setFieldHotel(int fieldIndex, boolean hasHotel) {
        if(fakeGui)
            return;
        GUI_Field field = guiFields[fieldIndex];
        if (field instanceof GUI_Street) {
            ((GUI_Street)field).setHotel(hasHotel);
        }
    }
    public static void setFieldPawned(int fieldIndex, boolean pawned){
        if(fakeGui)
            return;
        GUI_Field field = guiFields[fieldIndex];
        if(pawned) {
            field.setDescription(field.getTitle() + " - FELTET ER PANTSANT");
        } else{
            field.setDescription(field.getTitle());
        }
    }

    public static void setGuiPlayerLost(String name) {
        if(fakeGui)
            return;
        GUI_Player guiPlayer = getGuiPlayer(name);
        for(GUI_Field guiField : guiFields){
            guiField.setCar(guiPlayer, false);
        }
        lostCounter++;
        guiPlayer.setName("-BANKEROT- " + lostCounter);
    }

    public static void shutDown(){
        if(fakeGui)
            return;
        gui.close();
    }
}
