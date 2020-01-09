package GUI;

import Matador.Field.Field;
import Matador.Field.StreetField;
import gui_codebehind.GUI_Center;
import gui_fields.*;
import gui_main.GUI;

import java.awt.*;

public class InterfaceGUI {
    private static GUI gui;
    private static GUI_Field[] guiFields;
    private static GUI_Player[] guiPlayers;

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

    public static void initGUI(){
        gui = new GUI();
        guiFields = gui.getFields();
    }

    public static void initGUIFields(Field[] fields) {
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

    public static void setGuiPlayerBalance(String name, int balance){
        getGuiPlayer(name).setBalance(balance);
    }

    public static void setGUIFieldOwner(String name, int fieldIndex){
        GUI_Field guiField = guiFields[fieldIndex];
        ((GUI_Ownable) guiField).setOwnerName(name);
    }

    public static void showMessage (String msg){
        gui.showMessage(msg);
    }
    public static void showMessage (String msg, String name){
        gui.showMessage("PERSON: " + name + " - " + msg);
    }

    public static void setGuiCard(String text) {
        gui.displayChanceCard(text);
    }

    public static void hideGuiCard() {
        gui.displayChanceCard("");
    }

    public static String awaitUserStringInput (String msg){
        return gui.getUserString(msg);
    }

    public static int awaitUserIntegerInput (String msg) {
        return gui.getUserInteger(msg);
    }
    public static int awaitUserIntegerInput (String msg, String name) {
        return gui.getUserInteger("PERSON: " + name + " - " + msg);
    }

    public static int awaitUserIntegerInput (String msg, int min, int max) {
        return gui.getUserInteger(msg, min, max); //Der er en fejl i GUIen at hvis man ikke indtaster noget og klikker ENTER kommer der en fejl. (Kan ikke gribes via try catch)
    }

    public static String awaitUserButtonsClicked (String msg, String... buttonsString){
        return gui.getUserButtonPressed(msg, buttonsString);
    }
    public static String awaitUserButtonsClicked (String msg, String name, String... buttonsString){
        return gui.getUserButtonPressed("PERSON: " + name + " - " + msg, buttonsString);
    }

    public static String awaitDropDownSelected(String msg, String name, String... selections){
        return gui.getUserSelection("PERSON: " + name + " - " + msg, selections);
    }

    public static void setDices(int dieValue1, int dieValue2){
        gui.setDice(dieValue1, 4, 3, dieValue2, 6, 3);
    }

    public static void setGuiPlayersCount(int playerCount){
        guiPlayers = new GUI_Player[playerCount];
    }
    public static void addGUIPlayer(String name, int balance){
        GUI_Car guiCar = new GUI_Car(colors[initPlayerIndex], colors[initPlayerIndex], types[initPlayerIndex], GUI_Car.Pattern.ZEBRA);
        GUI_Player guiPlayer = new GUI_Player(name, balance, guiCar);
        gui.addPlayer(guiPlayer);
        guiPlayers[initPlayerIndex] = guiPlayer;
        initPlayerIndex++;
    }

    public static void movePlayerToField(String name, int fieldIndex) {
        GUI_Player guiPlayer = getGuiPlayer(name);
        for(GUI_Field guiField : guiFields){
            guiField.setCar(guiPlayer, false);
        }
        guiFields[fieldIndex].setCar(guiPlayer, true);
    }
    public static GUI_Player getGuiPlayer(String name){
        GUI_Player guiPlayer = null;
        for(GUI_Player gp : guiPlayers) {
            if (gp.getName().equals(name)) {
                guiPlayer = gp;
            }
        }
        return guiPlayer;
    }

    public static void setFieldHouses(int fieldIndex, int houseCount) {
        GUI_Field field = guiFields[fieldIndex];
        if (field instanceof GUI_Street) {
            ((GUI_Street)field).setHouses(houseCount);
        }
    }

    public static void setFieldHotel(int fieldIndex, boolean hasHotel) {
        GUI_Field field = guiFields[fieldIndex];
        if (field instanceof GUI_Street) {
            ((GUI_Street)field).setHotel(hasHotel);
        }
    }


    public static void removeGuiPlayer(String name, int[] fieldIndexes){
        GUI_Player[] guiPlayersNewTemp = new GUI_Player[guiPlayers.length-1];
        int i = 0;
        for(GUI_Player guiPlayer : guiPlayers){
            if(!guiPlayer.getName().equals(name)){
                guiPlayersNewTemp[i] = guiPlayer;
                i++;
            }
        }
        guiPlayers = guiPlayersNewTemp;

        for(int fieldIndex : fieldIndexes){
            InterfaceGUI.setGUIFieldOwner(null, fieldIndex);
        }
    }

    public static void shutDown(){
        gui.close();
    }
}
