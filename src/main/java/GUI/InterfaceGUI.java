package GUI;

import gui_main.GUI;

public class InterfaceGUI {
    private static GUI gui;

    public static void initGUI(){
        gui = new GUI();
    }

    public static void showMessage (String msg){
        gui.showMessage(msg);
    }
}
