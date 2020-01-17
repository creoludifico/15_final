package Matador.GUI;

import Matador.Models.Field.Field;
import gui_fields.*;
import gui_main.GUI;

import java.awt.*;
import java.util.Arrays;

public class InterfaceGUI {
    static Implementation implementation;

    public static void initGUI(){
        implementation = new GuiImplementation();
    }
    public static void initFakeGUI(){
        implementation = new TestImplementation();
    }
    public static void initFakeGUI(int[] integerReturns, String[] stringReturns) {
        implementation = new TestImplementation(integerReturns, stringReturns);
    }

    public static void initGUIFields(Field[] fields) {
        implementation.initGUIFields(fields);
    }
    public static void addGUIPlayer(String name, int balance){
        implementation.addGUIPlayer(name, balance);
    }

    public static void showMessage (String msg){
        implementation.showMessage(msg);
    }
    public static void showMessage (String msg, String name){
        implementation.showMessage(msg,name);
    }

    public static String awaitUserStringInput (String msg){
        return implementation.awaitUserStringInput(msg);
    }
    public static String awaitUserButtonsClicked (String msg, String... buttonsString){
        return implementation.awaitUserButtonsClicked(msg, buttonsString);
    }
    public static String awaitUserButtonsClicked (String msg, String name, String... buttonsString){
        return implementation.awaitUserButtonsClicked(msg, name, buttonsString);
    }
    public static String awaitDropDownSelected(String msg, String name, String... selections){
        return implementation.awaitDropDownSelected(msg, name, selections);
    }
    public static int awaitUserIntegerInput (String msg) {
        return implementation.awaitUserIntegerInput(msg);
    }
    public static int awaitUserIntegerInput (String msg, String name) {
        return implementation.awaitUserIntegerInput(msg, name);
    }
    public static int awaitUserIntegerInput (String msg, int min, int max) {
        return implementation.awaitUserIntegerInput(msg, min, max);
    }

    public static void setGuiPlayerBalance(String name, int balance){
        implementation.setGuiPlayerBalance(name, balance);
    }
    public static void setGUIFieldOwner(String name, int fieldIndex){
        implementation.setGUIFieldOwner(name, fieldIndex);
    }
    public static void setGuiCard(String text) {
        implementation.setGuiCard(text);
    }
    public static void setDices(int dieValue1, int dieValue2){
        implementation.setDices(dieValue1, dieValue2);
    }
    public static void setGuiPlayersCount(int playerCount){
        implementation.setGuiPlayersCount(playerCount);
    }

    public static void hideGuiCard() {
        implementation.hideGuiCard();
    }

    public static void movePlayerToField(String name, int fieldIndex) {
        implementation.movePlayerToField(name, fieldIndex);
    }

    public static void setFieldHouses(int fieldIndex, int houseCount) {
        implementation.setFieldHouses(fieldIndex, houseCount);
    }
    public static void setFieldHotel(int fieldIndex, boolean hasHotel) {
        implementation.setFieldHotel(fieldIndex, hasHotel);
    }
    public static void setFieldPawned(int fieldIndex, boolean pawned){
        implementation.setFieldPawned(fieldIndex, pawned);
    }

    public static void setGuiPlayerLost(String name) {
        implementation.setGuiPlayerLost(name);
    }

    public static void shutDown(){
        implementation.shutDown();;
    }
}
