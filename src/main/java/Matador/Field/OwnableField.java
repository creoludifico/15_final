package Matador.Field;

import Matador.User.Player;

public abstract class OwnableField extends Field {
    private Player owner = null;
    private Boolean pawned = false;
    private final int price, mortgage;

    public OwnableField(String title, String subTitle, int price, int mortgage) {
        super(title, subTitle);
        this.price = price;
        this.mortgage = mortgage;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public Boolean getPawned() {
        return pawned;
    }

    public void setPawned(Boolean pawned) {
        this.pawned = pawned;
    }

    public int getPrice() {
        return price;
    }

    public int getMortgage() {
        return mortgage;
    }

}
