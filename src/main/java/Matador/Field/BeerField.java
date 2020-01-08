package Matador.Field;

import java.awt.*;

public class BeerField extends Field {
    int price, rent, mortgage;

    int getPrice() { return price; }
    int getRent() { return rent; }
    int getMortgage() { return mortgage; }

    public BeerField(String title, int price) {
        super(title, "");
        this.price = price;
    }
}
