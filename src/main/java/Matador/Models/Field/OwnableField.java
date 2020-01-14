package Matador.Models.Field;

import Matador.GUI.InterfaceGUI;
import Matador.Models.User.Player;
import gui_fields.GUI_Player;

import java.awt.*;

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

    public void setOwner(Player owner, int fieldIndex) {
        this.owner = owner;
        String ownerName = "";
        Color color = Color.white;
        if(owner != null){
            ownerName = owner.getName();
            color = InterfaceGUI.getGuiPlayer(owner.getName()).getPrimaryColor();
        }
        InterfaceGUI.setGUIFieldOwner(ownerName, fieldIndex, color);
    }

    public Boolean getPawned() {
        return pawned;
    }

    public void setPawned(Boolean pawned, int fieldIndex) {
        this.pawned = pawned;
        InterfaceGUI.setFieldPawned(fieldIndex, pawned);
    }

    public int getPrice() {
        return price;
    }

    public int getMortgage() {
        return mortgage;
    }

}
