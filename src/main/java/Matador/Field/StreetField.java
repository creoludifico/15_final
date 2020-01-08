package Matador.Field;

import Matador.User.Player;

import java.awt.*;

public class StreetField extends OwnableField {
    private int buildings = 0;

    private final int buildingPrice;
    private final int[] rent;

    public StreetField(String title, int price, int mortgage, int buildingPrice, int[] rent) {
        super(title, "Pris: " + price, price, mortgage);
        this.buildingPrice = buildingPrice;
        this.rent = rent;
    }

    public int getBuildingPrice() { return buildingPrice; }

    public int getRent() { return rent[buildings]; }

    public int getBuildings() { return buildings; }

    public void setBuildings(int buildings){this.buildings = buildings;}
}
