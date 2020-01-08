package Matador.User;

import GUI.InterfaceGUI;

public class Account {
    private int balance;

    public Account(int balance){
        this.balance = balance;
    }
    public void setBalance(int balance, String name){
        this.balance = balance;
        InterfaceGUI.setGuiPlayerBalance(name, this.balance);
    }
    public int getBalance() {
        return balance;
    }
    public String getBalanceAsString(){
        return Integer.toString(balance);
    }
}
