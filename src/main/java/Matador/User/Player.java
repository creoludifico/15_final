package Matador.User;

public class Player {
    private String name;
    private Account account;

    private boolean inJail = false;
    private boolean hasEscapeJailCard = false;

    private int fieldIndex = 0;

    public Player(String name, Account account){
        this.name = name;
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public Account getAccount() {
        return account;
    }

    public boolean isInJail() {
        return inJail;
    }

    public boolean isHasEscapeJailCard() {
        return hasEscapeJailCard;
    }

    public int getFieldIndex() {
        return fieldIndex;
    }
}
