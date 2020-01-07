package Matador.User;

public class Account {
    private int balance;

    public Account(int balance){
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }
    public String getBalanceAsString(){
        return Integer.toString(balance);
    }
}
