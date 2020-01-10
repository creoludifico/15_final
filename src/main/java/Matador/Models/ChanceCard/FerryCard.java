package Matador.Models.ChanceCard;

public class FerryCard extends ChanceCard {
    private final int oeresond = 5, dfds = 15, oes = 25, bornholm = 35;

    public FerryCard(String name) {
        super(name);
    }

    public int getOeresond() {
        return oeresond;
    }

    public int getDfds() {
        return dfds;
    }

    public int getOes() {
        return oes;
    }

    public int getBornholm() {
        return bornholm;
    }
}
