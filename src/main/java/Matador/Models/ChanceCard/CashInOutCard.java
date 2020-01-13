package Matador.Models.ChanceCard;

public class CashInOutCard extends ChanceCard {
    private int cash;

    public CashInOutCard(String name, int cash) {
        super(name);
        this.cash = cash;
    }

    public int getCash() {
        return cash;
    }
}