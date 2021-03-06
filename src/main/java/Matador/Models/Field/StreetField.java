package Matador.Models.Field;

import Matador.GUI.InterfaceGUI;

public class StreetField extends OwnableField {
    private int buildings = 0;

    private final String groupName;
    private final int buildingPrice;
    private final int[] rent;

    public StreetField(String title, int price, int mortgage, int buildingPrice, int[] rent, String groupName) {
        super(title, "Pris: " + price, price, mortgage);
        this.buildingPrice = buildingPrice;
        this.rent = rent;
        this.groupName = groupName;
    }

    public String getGroupName() { return groupName; }

    public int getBuildingPrice() { return buildingPrice; }

    public int getRent() { return rent[buildings]; }

    public int getBuildings() { return buildings; }

    public void setBuildings(int buildings, int fieldIndex){
        this.buildings = buildings;
        if(buildings > 4){
            InterfaceGUI.setFieldHotel(fieldIndex, true);
        }else{
            InterfaceGUI.setFieldHotel(fieldIndex, false);
            InterfaceGUI.setFieldHouses(fieldIndex, buildings);
        }
    }
}
