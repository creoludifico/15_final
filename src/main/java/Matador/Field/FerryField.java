package Matador.Field;

import Matador.User.Player;

import java.awt.*;

public class FerryField extends OwnableField {
    private final int rent[] = new int[]{25, 50, 100, 200};

    public FerryField(String title, int price, int mortgage) {
        super(title, "Pris: " + price, price, mortgage);
    }

    public int getRent(int index){
        return rent[index];
    }
}
