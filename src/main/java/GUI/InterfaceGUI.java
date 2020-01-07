package GUI;

import gui_fields.GUI_Car;
import gui_fields.GUI_Field;
import gui_fields.GUI_Player;
import gui_main.GUI;

import java.awt.*;

public class InterfaceGUI {
    private static GUI gui;

    private static GUI_Car.Type[] types = new GUI_Car.Type[]{
            GUI_Car.Type.RACECAR,
            GUI_Car.Type.TRACTOR,
            GUI_Car.Type.CAR,
            GUI_Car.Type.UFO,
            GUI_Car.Type.RACECAR,
            GUI_Car.Type.TRACTOR
    };
    private static int typeIndex = 0;
    private static Color[] colors = new Color[]{
            Color.black,
            Color.blue,
            Color.pink,
            Color.green,
            Color.orange,
            Color.white
    };
    private static int colorIndex = 0;

    public static void initGUI(){ gui = new GUI(); }
    public static void showMessage (String msg){
        gui.showMessage(msg);
    }
    public static String awaitUserStringInput (String msg){ return gui.getUserString(msg); }
    public static int awaitUserIntegerInput (String msg) { return gui.getUserInteger(msg); }
    public static int awaitUserIntegerInput (String msg, int min, int max) { return gui.getUserInteger(msg, min, max); }
    public static void setDices(int dieValue1, int dieValue2){ gui.setDice(dieValue1, dieValue2); }

    public static void addGUIPlayer(String name, int balance){
        GUI_Car guiCar = new GUI_Car(colors[colorIndex], colors[colorIndex], types[typeIndex], GUI_Car.Pattern.ZEBRA);
        GUI_Player guiPlayer = new GUI_Player(name, balance, guiCar);
        gui.addPlayer(guiPlayer);
        typeIndex++;
        colorIndex++;
    }
}
