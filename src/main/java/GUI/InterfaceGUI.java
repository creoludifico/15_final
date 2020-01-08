package GUI;

import Matador.Field.Field;
import gui_fields.GUI_Car;
import gui_fields.GUI_Field;
import gui_fields.GUI_Player;
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
        }
    }

    public static void showMessage (String msg){
        gui.showMessage(msg);
    }

    public static String awaitUserStringInput (String msg){
        return gui.getUserString(msg);
    }

    public static int awaitUserIntegerInput (String msg) {
        return gui.getUserInteger(msg);
    }

    public static int awaitUserIntegerInput (String msg, int min, int max) {
        return gui.getUserInteger(msg, min, max); //Der er en fejl i GUIen at hvis man ikke indtaster noget og klikker ENTER kommer der en fejl. (Kan ikke gribes via try catch)
    }

    public static String awaitUserButtonsClicked (String msg, String... buttonsString){
        return gui.getUserButtonPressed(msg, buttonsString);
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
        for(GUI_Player guiPlayer : guiPlayers){
            if(guiPlayer.getName().equals(name)){
                for(GUI_Field guiField : guiFields){
                    guiField.setCar(guiPlayer, false);
                }
                guiFields[fieldIndex].setCar(guiPlayer, true);
            }
        }
    }
}
