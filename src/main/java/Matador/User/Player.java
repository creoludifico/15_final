package Matador.User;

import GUI.InterfaceGUI;

public class Player {
    private String name;
    private Account account;

    private boolean inJail = false;
    private boolean hasEscapeJailCard = false;
    private boolean bonusOnNextRaffle = false;

    private int jailForRounds = 0;

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

    public void setInJail(boolean inJail){
        this.inJail = inJail;
    }
    public boolean isInJail() {
        return inJail;
    }

    public void setHasEscapeJailCard(boolean hasEscapeJailCard) {
        this.hasEscapeJailCard = hasEscapeJailCard;
    }
    public boolean hasEscapeJailCard() {
        return hasEscapeJailCard;
    }

    public void setFieldIndexx(int fieldIndex){
        this.fieldIndex = fieldIndex;
        InterfaceGUI.movePlayerToField(name, fieldIndex);
    }
    public int getFieldIndex() {
        return fieldIndex;
    }

    public boolean isBonusOnNextRaffle() {
        return bonusOnNextRaffle;
    }

    public void setBonusOnNextRaffle(boolean bonusOnNextRaffle) {
        this.bonusOnNextRaffle = bonusOnNextRaffle;
    }

    public int getJailForRounds() {
        return jailForRounds;
    }

    public void setJailForRounds(int jailForRounds) {
        this.jailForRounds = jailForRounds;
    }
}
