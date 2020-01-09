package Matador.ChanceCard;
import GUI.InterfaceGUI;
import Matador.Field.*;
import Matador.User.Player;
import Matador.User.PlayerController;

import java.util.Arrays;
import java.util.Random;

public class ChanceCardController {
    private ChanceCard[] chanceCards;
    private FieldController fieldController;
    private PlayerController playerController;

    public void setFieldController(FieldController fieldController) {
        this.fieldController = fieldController;
    }

    public void setPlayerController(PlayerController playerController) {
        this.playerController = playerController;
    }

    public ChanceCardController() {
        chanceCards = new ChanceCard[]{
                new CashInOutCard("Modtag udbytte af deres aktier. kr 50", 50),
                new CashInDependentAssetsCard("De modtager >>Matador-legatet<< for værdig trængende, stort kr. 2000. Ved værdig trængende forstås, at deres formue, d.v.s. deres kontakte penge + skøder + bygninger ikke overstiger kr. 750."),
                new MoveAbsoluteCard("Tag ind til Rådhuspladsen.", 39),
                new MoveAbsoluteCard("Tag med Øresundsbåden - Flyt brikken frem, og hvis De passerer >>Start<< indkasser kr. 200.", 5),
                new MoveToJailCard("Gå i fængsel. Ryk direkte til fængslet. Selv om de passerer >>Start<< modtager de ikke kr. 200."),
                new MoveToJailCard("Gå i fængsel. Ryk direkte til fængslet. Selv om de passerer >>Start<< modtager de ikke kr. 200."),
                new MoveToJailCard("Gå i fængsel. Ryk direkte til fængslet. Selv om de passerer >>Start<< modtager de ikke kr. 200."),
                new CashInOutCard("De har måtte vedtage en parkeringsbøde. Betal kr 20 til banken.", -20),
                new MoveBackwardsCard("Ryk tre felter tilbage", 3),
                new MoveBackwardsCard("Ryk tre felter tilbage", 3),
                new MoveAbsoluteCard("Ryk frem til >>Start<<.", 0),
                new CashOutDependentBuildingCard("Kul- og kokspriserne er steget, og De skal betale: kr. 25 pr. hus og kr. 125 pr. hotel.", 25, 125),
                new CashOutDependentBuildingCard("Ejendomsskatterne er steget, ekstraudgifterne er: kr. 50 pr. hus, kr. 125 pr. hotel.", 50, 125),
                new CashInFromOtherPlayersCard("De har lagt penge ud til sammenskudsgilde. Mærkværdigvis betaler alle straks. Modtag fra hver medspiller kr. 25."),
                new CashInOutCard("Værdien af egen avl fra nyttehaven udgør kr. 200 som De modtager af banken.", 200),
                new FerryCard("Ryk brikken frem til det nærmeste dampskibsselskab og betal ejeren to gange den leje, han ellers er berettiget til. Hvis selskabet ikke ejes af nogen kan De købe det af  banken."),
                new FerryCard("Ryk brikken frem til det nærmeste dampskibsselskab og betal ejeren to gange den leje, han ellers er berettiget til. Hvis selskabet ikke ejes af nogen kan De købe det af  banken."),
                new CashInOutCard("De har anskaffet et nyt dæk til Deres vogn Indbetal kr. 100.", -100),
                new CashInOutCard("De har kørt frem for »Fuld Stop«. Betal kr. 100 i bøde.", -100),
                new CashInOutCard("Betal for vognvask og smøring kr. 10", -10),
                new CashInOutCard("De har været en tur i udlandet og haft for mange cigarer med hjem. betal told kr. 20.", -20),
                new CashInOutCard("Grundet på dyrtiden har de fået gageforhøjelse Modtag kr. 25.", 25),
                new PardonCard("I anledning af kongens fødselsdag benådes De herved for fængsel. Dette kort kan opbevares, indtil de får brug for det, eller de kan sælge det."),
                new CashInOutCard("Betal for vognvask og smøring kr. 10.", -10),
                new CashInOutCard("De har solgt Deres gamle klude. Modtag kr. 20.", 20),
                new CashInOutCard("De har rettidigt afleveret deres abonnementskort. Depositum kr. 1. udbetales Dem af banken.", 1),
                new CashInOutCard("Manufakturvarerne er blevet billigere og bedre, herved sparet De kr. 50. som De modtager af banken", 50),
                new CashInOutCard("Efter auktionen på Assistenshuset, hvor de havde pantsat Deres tøj, modtager De ekstra kr. 108.", 108),
                new CashInOutCard("Deres præmieobligation er kommet ud. De modtager kr. 100 af banken.", 100)
        };
        shuffleCards();
    }

    public ChanceCard[] getDeck() {
        return chanceCards;
    }

    public void shuffleCards() {
        Random rand = new Random();
        for (int i = 0; i < chanceCards.length; i++) {
            int indexToSwap = rand.nextInt(chanceCards.length);
            ChanceCard temp = chanceCards[indexToSwap];
            chanceCards[indexToSwap] = chanceCards[i];
            chanceCards[i] = temp;
        }
    }

    public ChanceCard pickCard() {
        ChanceCard temp = chanceCards[0];
        for (int i = 1; i < chanceCards.length; i++) {
            chanceCards[i - 1] = chanceCards[i];
        }
        chanceCards[chanceCards.length - 1] = temp;
        return chanceCards[0];
    }

    public void action (Player player){
        ChanceCard pickedCard = pickCard();
        String cardName = pickedCard.getName();
        InterfaceGUI.setGuiCard(cardName);
        InterfaceGUI.showMessage(player.getName() + ": Du har trukket chancekortet \"" + cardName + "\"");

        // Bonus hvis given total værdi
        if (pickedCard instanceof CashInDependentAssetsCard) {
            CashInDependentAssetsCard cidac = (CashInDependentAssetsCard) pickedCard;
            if (collectAsssets(player) < cidac.getMaxCashLimit()) {
                playerController.modifyBalance(cidac.getCash(), player);
                InterfaceGUI.showMessage(player.getName() + ": Du besidder under " + cidac.getMaxCashLimit() + " i samlede værdier. Derfor modtager du " + cidac.getCash() + " i Matador Legatet");
            } else {
                InterfaceGUI.showMessage(player.getName() + ": Du besidder for mange aktiver til at modtage Matador Legatet");
            }
        }

        // Alle spiller betaler 25kr til spilleren
        else if (pickedCard instanceof CashInFromOtherPlayersCard) {
            CashInFromOtherPlayersCard cifopc = (CashInFromOtherPlayersCard) pickedCard;
            int totalMe = 0;
            for (int i = 0; i < playerController.getPlayers().length; i++) {
                if (playerController.getPlayer(i) != player) {
                    playerController.modifyBalance(-cifopc.getCash(), playerController.getPlayer(i));
                } else {
                    totalMe += (playerController.getPlayers().length - 1) * cifopc.getCash();
                    playerController.modifyBalance(totalMe, player);
                }
            }
            InterfaceGUI.showMessage(player.getName() + ": Du har modtaget sammenlagt " + totalMe + " og de andre spillere har mistet hver " + cifopc.getCash());
        }

        // Betal eller modtag penge
        else if (pickedCard instanceof CashInOutCard) {
            CashInOutCard cioc = (CashInOutCard) pickedCard;
            playerController.modifyBalance(cioc.getCash(), player);
            InterfaceGUI.showMessage( player.getName() + (cioc.getCash() < 0 ? ": Du har betalt "  + (-cioc.getCash()) : ": Du har indkasseret " + cioc.getCash()));
        }

        // Betal penge udfra antal huse og hoteller
        else if (pickedCard instanceof CashOutDependentBuildingCard) {
            CashOutDependentBuildingCard codbc = (CashOutDependentBuildingCard) pickedCard;
            Field[] fields = fieldController.getFields();
            int total = 0;
            for (int index = 0; index < fields.length; index++) {
                Field field = fields[index];
                if (field instanceof StreetField) {
                    StreetField streetField = (StreetField) field;
                    if (streetField.getOwner() == player) {
                        if (streetField.getBuildings() <= 4) {
                            total += -codbc.getHousePrice() * streetField.getBuildings();
                        } else if (streetField.getBuildings() > 4) {
                            total += -codbc.getHotelPrice();
                        }
                    }
                }
            }
            playerController.modifyBalance(total, player);
            InterfaceGUI.showMessage(player.getName() + ": Din samlede beskatning er på " + total);

            // Ryk til nærmeste færge + dobbelt leje hvis ejet
        } else if (pickedCard instanceof FerryCard) {
            FerryCard fc = (FerryCard) pickedCard;
            int playerFieldIndex = player.getFieldIndex();
            if (playerFieldIndex < fc.getOeresond()) {
                playerController.movePlayerToField(player, fc.getOeresond());
            } else if (playerFieldIndex < fc.getDfds()) {
                playerController.movePlayerToField(player, fc.getDfds());
            } else if (playerFieldIndex < fc.getOes()) {
                playerController.movePlayerToField(player, fc.getOes());
            } else if (playerFieldIndex < fc.getBornholm()) {
                playerController.movePlayerToField(player, fc.getBornholm());
            } else {
                playerController.movePlayerToField(player, fc.getOeresond());
            }
            if(fieldController.getFields()[playerFieldIndex] instanceof FerryField) {
                FerryField field = (FerryField) fieldController.getFields()[playerFieldIndex];
                if(field.getOwner() != null) {
                    playerController.modifyBalance(-field.getRent(getFerries(player)) * 2, player);
                    InterfaceGUI.showMessage(player.getName() + ": Du er landet på " + field.getTitle() + ", som er ejet af " + field.getOwner().getName() + ". Du skal derfor betale leje");
                } else {
                    fieldController.fieldAction(player, playerFieldIndex, null);
                }
            }
            // Ryk til given position
        } else if (pickedCard instanceof MoveAbsoluteCard){
            MoveAbsoluteCard mac = (MoveAbsoluteCard) pickedCard;
            playerController.movePlayerToField(player, mac.getFieldIndex());
            InterfaceGUI.showMessage(player.getName() + ": Du er flyttet til " + fieldController.getFields()[mac.getFieldIndex()].getTitle());
            // Ryk given felter bagud
        } else if (pickedCard instanceof MoveBackwardsCard) {
            MoveBackwardsCard mbc = (MoveBackwardsCard) pickedCard;
            playerController.movePlayerToField(player, (player.getFieldIndex() - mbc.getBackward()) % fieldController.getFields().length);
            InterfaceGUI.showMessage(player.getName() + ": Du er flyttet 3 tilbage til " + fieldController.getFields()[player.getFieldIndex()].getTitle());
            // Ryk i fængsel
        } else if (pickedCard instanceof MoveToJailCard) {
            MoveToJailCard mtjc = (MoveToJailCard) pickedCard;
            playerController.movePlayerToField(player, mtjc.getFieldIndex());
            player.setInJail(true);
            InterfaceGUI.showMessage(player.getName() + ": Du er nu i fængsel");
            // Gratis ud af fængsel kort
        } else if (pickedCard instanceof PardonCard) {
            player.setHasEscapeJailCard(true);
            InterfaceGUI.showMessage(player.getName() + ": Du er nu i besiddelse af Løsladelseskortet");
        }
    }

    private int collectAsssets (Player player){
        Field[] fields = fieldController.getFields();
        int total = 0;
        for (int index = 0; index < fields.length; index++) {
            Field field = fields[index];
            if (field instanceof OwnableField) {
                OwnableField ownableField = (OwnableField) field;
                if (ownableField.getOwner() == player) {
                    total += ownableField.getPrice();
                    if (ownableField instanceof StreetField) {
                        StreetField streetField = (StreetField) ownableField;
                        total += streetField.getBuildingPrice() * streetField.getBuildings();
                    }
                }
            }
        }
        total += player.getAccount().getBalance();
        return total;
    }

    private int getFerries(Player player) {
        Field[] fields =fieldController.getFields();
        int total = 0;
        for (int i = 0; i < fields.length; i++) {
            if (fields[i] instanceof FerryField && ((OwnableField) fields[i]).getOwner() == player) {
                total++;
            }
        }
        return total;
    }
}

