package Matador.Models.ChanceCard;

public class CashInDependentAssetsCard extends ChanceCard {
    // Hvis given resource kapital under 750,- modtag 2000
    private final int cash = 2000;
    private final int maxCashLimit = 750;

    public CashInDependentAssetsCard(String name) {
        super(name);
    }

    public int getCash() {
        return cash;
    }
    public int getMaxCashLimit() {
        return maxCashLimit;
    }
}
