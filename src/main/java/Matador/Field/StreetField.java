package Matador.Field;

import java.awt.*;

public class StreetField extends Field {
    private int price, mortgage;
    private int buildingPrice;
    private int buildings;
    private int[] rent;
    public int getPrice() { return price; }
    public int getRent() { return rent[buildings]; }
    public int getMortgage() { return mortgage; }
    public int getBuildingPrice() { return buildingPrice; }
    public StreetField(String title, int price, int mortgage, int buildingPrice, int[] rent) {
        super(title, "Pris: " + price);
        this.price = price;
        this.mortgage = mortgage;
        this.buildingPrice = buildingPrice;
        this.buildings = 0;
        this.rent = rent;
    }
}
