package Matador;

import Matador.Field.Field;
import Matador.Field.FieldController;
import Matador.Field.OwnableField;
import Matador.User.Player;
import Matador.User.PlayerController;

public class TradeController {
    private FieldController fieldController;
    private PlayerController playerController;

    public void setFieldController(FieldController fieldController) {
        this.fieldController = fieldController;
    }

    public void setPlayerController(PlayerController playerController) {
        this.playerController = playerController;
    }

    public void auction(OwnableField ownableField) {
    }

    public void pawnField(Player player){

    }

    public void sellHouse(Player player) {
    }

    public void buyHouse(Player player) {
    }

    public void tradeWithPlayer(Player player) {
    }
}
