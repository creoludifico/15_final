package Matador.Field;

//Knyttet via StreetField
public class Building {
    private int housePrice;
    private int rentPrice;

    public int getHousePrice() {
        return housePrice;
    }

    public int getRentPrice() {
        return rentPrice;
    }

    public Building(int housePrice, int rentPrice) {
        this.housePrice = housePrice;
        this.rentPrice = rentPrice;
    }
}
