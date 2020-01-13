package Matador.Models.ChanceCard;

public class MoveToJailCard extends ChanceCard {
    private final int fieldIndex = 10;
    public MoveToJailCard(String name) {
        super(name);
    }

    public int getFieldIndex() {
        return fieldIndex;
    }
}
