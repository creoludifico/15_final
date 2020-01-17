package Matador.GUI;

import Matador.Models.Field.Field;
import gui_fields.*;

import java.util.Arrays;

public abstract class Implementation {
    public abstract void initGUIFields(Field[] fields);
    public abstract void addGUIPlayer(String name, int balance);

    public abstract void showMessage (String msg);
    public abstract void showMessage (String msg, String name);

//    public abstract String[] getStringsForAction(String[] strings, String lastAction);
    public abstract String awaitUserStringInput (String msg);
    public abstract String awaitUserButtonsClicked (String msg, String... buttonsString);
    public abstract String awaitUserButtonsClicked (String msg, String name, String... buttonsString);
    public abstract String awaitDropDownSelected(String msg, String name, String... selections);
    public abstract int awaitUserIntegerInput (String msg);
    public abstract int awaitUserIntegerInput (String msg, String name);
    public abstract int awaitUserIntegerInput (String msg, int min, int max);

    public abstract void setGuiPlayerBalance(String name, int balance);
    public abstract void setGUIFieldOwner(String name, int fieldIndex);
    public abstract void setGuiCard(String text);
    public abstract void setDices(int dieValue1, int dieValue2);
    public abstract void setGuiPlayersCount(int playerCount);

    public abstract void hideGuiCard();

    public abstract void movePlayerToField(String name, int fieldIndex);

    public abstract void setFieldHouses(int fieldIndex, int houseCount);
    public abstract void setFieldHotel(int fieldIndex, boolean hasHotel);
    public abstract void setFieldPawned(int fieldIndex, boolean pawned);

    public abstract void setGuiPlayerLost(String name);

    public abstract void shutDown();
}
