package Matador.ChanceCard;

public class MoveAbsoluteCard extends ChanceCard {
    private int fieldIndex;

    public MoveAbsoluteCard(String name, int fieldIndex) {
        super(name);
        this.fieldIndex = fieldIndex;
    }

    public int getFieldIndex() {
        return fieldIndex;
    }
}
