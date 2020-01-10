package Matador.Models.ChanceCard;

public class CashOutDependentBuildingCard extends ChanceCard {
    private int housePrice, hotelPrice;

    public CashOutDependentBuildingCard(String name, int housePrice, int hotelPrice) {
        super(name);
        this.housePrice = housePrice;
        this.hotelPrice = hotelPrice;
    }

    public int getHousePrice() {
        return housePrice;
    }

    public int getHotelPrice() {
        return hotelPrice;
    }
}
