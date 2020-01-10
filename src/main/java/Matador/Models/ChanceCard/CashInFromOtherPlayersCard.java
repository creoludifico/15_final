package Matador.Models.ChanceCard;

public class CashInFromOtherPlayersCard extends ChanceCard {
    private final int cash = 25;

    public CashInFromOtherPlayersCard(String name) {
        super(name);
    }

    public int getCash(){
    return cash;
    }
}