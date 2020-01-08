package Matador.Field;

public class FerryField extends OwnableField {
    private final int rent[] = new int[]{0, 25, 50, 100, 200};

    public FerryField(String title, int price, int mortgage) {
        super(title, "Pris: " + price, price, mortgage);
    }

    public int getRent(int index){
        return rent[index];
    }
}
