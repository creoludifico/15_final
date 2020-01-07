package Matador;

import GUI.InterfaceGUI;
import Matador.User.Account;
import Matador.User.Player;
import Matador.User.PlayerController;

public class GameBoard {
    PlayerController playerController;

    public GameBoard(){
        //Opsætter gui boardet
        InterfaceGUI.initGUI();

        //Spillerne oprettes
        playerController = new PlayerController();
    }

    public void runGame(){

    }
}