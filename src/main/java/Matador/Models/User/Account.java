package Matador.Models.User;

import Matador.GUI.InterfaceGUI;

public class Account {
    private int balance;

    public Account(int balance){
        this.balance = balance;
    }
    public void modifyBalance(int appendedBalance, String name){
        this.balance += appendedBalance;
        InterfaceGUI.setGuiPlayerBalance(name, this.balance);
    }
    public int getBalance() {
        return balance;
    }
    public String getBalanceAsString(){
        return Integer.toString(balance);
    }
}
