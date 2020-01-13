package Matador.Models.ChanceCard;

public abstract class ChanceCard {
    private String name;

    public ChanceCard(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}