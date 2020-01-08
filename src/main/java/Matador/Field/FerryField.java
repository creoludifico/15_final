package Matador.Field;

import java.awt.*;

public class FerryField extends Field {
    Boolean pawned;
    int price, mortgage;
    int[] rent; //array of 4 rent prices indexed by number of ferry companies owned (1-4)
    public FerryField(String title,
                      int price, int mortgage, int[] rent) {
        super(title, "Pris: " + price);
        this.pawned = false;
        this.price = price;
        this.mortgage = mortgage;
        this.rent = rent;
    }

    public void setPawned(Boolean pawned) {
        this.pawned = pawned;
    }
}
