package Matador.Models.ChanceCard;

public class MoveBackwardsCard extends ChanceCard {
    private int backward;

    public MoveBackwardsCard(String name, int backward) {
        super(name);
        this.backward = backward;
    }

    public int getBackward() {
        return backward;
    }
}
