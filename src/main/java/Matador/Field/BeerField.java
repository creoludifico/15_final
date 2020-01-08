package Matador.Field;

import java.awt.*;

public class BeerField extends OwnableField {
    public BeerField(String title, int price, int mortgage) {
        super(title, "Pris: " + price, price, mortgage);
    }
}
