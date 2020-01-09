package Matador.Field;

import GUI.InterfaceGUI;
import Matador.ChanceCard.ChanceCardController;
import Matador.RaffleCup.RaffleCup;
import Matador.TradeController;
import Matador.User.Player;
import Matador.User.PlayerController;

import java.awt.*;

public class FieldController {
    private Field[] fields;
    private PlayerController playerController;
    private ChanceCardController chanceCardController;
    private TradeController tradeController;

    public void setPlayerController(PlayerController playerController) {
        this.playerController = playerController;
    }
    public void setChanceCardController(ChanceCardController chanceCardController) {
        this.chanceCardController = chanceCardController;
    }
    public void setTradeController(TradeController tradeController) {
        this.tradeController = tradeController;
    }

    public FieldController() {
        fields = new Field[] {
                new StartField(),
                new StreetField("Rødovrevej", 60, 30, 50, new int[] {2, 10, 30, 90, 160, 250}, "BLÅ"),
                new ChanceField(),
                new StreetField("Hvidovrevej", 60,30, 50, new int[] {4, 20, 60, 180, 320, 450}, "BLÅ"),
                new TaxField("Betal indkomstskat", "10% el. 200", 0.10),
                new FerryField("Øresund", 200, 100),
                new StreetField("Roskildevej", 100, 50, 50, new int[] {6, 30, 90, 270, 400, 550}, "ORANGE"),
                new ChanceField(),
                new StreetField("Valby Langgade", 100, 50, 50, new int[] {6, 30, 90, 270, 400, 550}, "ORANGE"),
                new StreetField("Allégade", 120, 60, 50, new int[] {8, 40, 100, 300, 450, 600}, "ORANGE"),
                new VisitJailField(),
                new StreetField("Frederiksberg Allé", 140 , 70, 100, new int[] {10, 50, 150, 450, 625, 750}, "GRØN"),
                new BeerField("Tuborg", 150, 75),
                new StreetField("Büllowsvej", 140, 70, 100, new int[] {10, 50, 150, 450, 625, 750}, "GRØN"),
                new StreetField("Gammel Kongevej", 140, 80, 100, new int[] {12, 60, 180, 500, 700, 900}, "GRØN"),
                new FerryField("D.F.D.S", 200, 100),
                new StreetField("Bernstorffsvej", 180, 90, 100, new int[] {14, 70, 200, 550, 750, 950}, "GRÅ"),
                new ChanceField(),
                new StreetField("Hellerupvej", 180, 90, 100, new int[] {14, 70, 200, 50, 750, 950}, "GRÅ"),
                new StreetField("Strandvejen", 180, 100, 100, new int[] {16, 80, 220, 600, 800, 1000}, "GRÅ"),
                new RefugeField(),
                new StreetField("Trianglen", 220, 110, 150, new int[] {18, 90, 250, 700, 875, 1050}, "RØD"),
                new ChanceField(),
                new StreetField("Østerbrogade", 220, 110, 150, new int[] {18, 90, 250, 700, 875, 1050}, "RØD"),
                new StreetField("Grønningen", 220, 120, 150, new int[] {20, 100, 300, 750, 925, 110}, "RØD"),
                new FerryField("Ø.S.", 200, 100),
                new StreetField("Bredgade", 260, 130, 150, new int[] {22, 110, 330, 800, 975, 1150}, "HVID"),
                new StreetField("Kgs.Nytorv", 260, 130, 150, new int[] { 22, 110, 330, 800, 975, 1150}, "HVID"),
                new BeerField("Carlsberg", 150, 75),
                new StreetField("Østergade", 280, 140, 150, new int[] {22, 110, 330, 800, 975, 1150}, "HVID"),
                new JailField(),
                new StreetField("AmagerTorv", 300, 150, 200, new int[] {26, 130, 390, 900, 1100, 1275}, "GUL"),
                new StreetField("Vimmelskaftet", 300, 150, 200, new int[] {26, 130, 390, 900, 1100, 1275}, "GUL"),
                new ChanceField(),
                new StreetField("Nygade", 320, 160, 200, new int[] {28, 150, 450, 1000, 1200, 1400}, "GUL"),
                new FerryField("Bornholm", 200, 100),
                new ChanceField(),
                new StreetField("Frederiksborggade", 350, 175, 200, new int[] {35, 175, 500, 1100, 1300, 1500}, "LILLA"),
                new TaxField("Ekstraordinær statsskat", "Betal 100", 0.0),
                new StreetField("Rådhuspladsen", 400, 200, 200, new int[] {50, 200, 600, 1400, 1700, 2000}, "LILLA")
                };

            InterfaceGUI.initGUIFields(fields);
        }

    public Field[] getFields() {
        return fields;
    }

    public void fieldAction(Player player, int fieldIndex, RaffleCup raffleCup){
        Field field = this.getFields()[fieldIndex];

        if(field instanceof BeerField){
            BeerField beerField = (BeerField) field;
            ownableFieldAction(player, fieldIndex, beerField, raffleCup);
        }
        else if(field instanceof ChanceField){
            //ChanceField chanceField = (ChanceField) field; //Feltet i sig selv skal ikke bruges her.
            chanceCardController.action(player);
        }
        else if(field instanceof FerryField){
            FerryField ferryField = (FerryField) field;
            ownableFieldAction(player, fieldIndex, ferryField);
        }
        else if(field instanceof JailField){
            //JailField jailField = (JailField) field; //Feltet i sig selv skal ikke bruges her.
            player.setInJail(true);
            playerController.movePlayerToField(player, 10);
        }
        else if(field instanceof RefugeField){
            //RefugeField refugeField = (RefugeField) field; //Der sker ikke en dyt her "Pause felt"
        }
        else if(field instanceof StartField){
            //StartField startField = (StartField) field; //Der sker heller ikke en dyt her
        }
        else if(field instanceof StreetField){
            StreetField streetField = (StreetField) field;
            ownableFieldAction(player, fieldIndex, streetField);
        }
        else if(field instanceof TaxField){
            TaxField taxField = (TaxField) field;

            String[] buttons;

            String pay10Percent = "Betal 10%";
            String pay100 = "Betal kr. 100";
            String pay200 = "Betal kr. 200";

            if(taxField.percentage > 0){
                buttons = new String[]{
                        pay10Percent,
                        pay200
                };
            }else{
                buttons = new String[]{
                        pay100
                };
            }
            String answer = InterfaceGUI.awaitUserButtonsClicked("Du skal betale skat.", player.getName(), buttons);
            if(answer.equals(pay10Percent)){
                double totalPrice = 0;
                totalPrice += player.getAccount().getBalance();
                for(Field f : this.getFields()){
                    if(f instanceof StreetField){
                        StreetField streetField = (StreetField) f;
                        if(player == streetField.getOwner()){
                            totalPrice += streetField.getPrice();
                            totalPrice += streetField.getBuildingPrice() * streetField.getBuildings();
                        }
                    }
                    else if(f instanceof FerryField){
                        FerryField ferryField = (FerryField) f;
                        if(player == ferryField.getOwner()){
                            totalPrice += ferryField.getPrice();
                        }
                    }
                    else if(f instanceof BeerField){
                        BeerField beerField = (BeerField) f;
                        if(player == beerField.getOwner()){
                            totalPrice += beerField.getPrice();
                        }
                    }
                }
                totalPrice = totalPrice * taxField.percentage;
                int totalPriceInt = (int) Math.round(totalPrice); //Runder enten op eller ned
                playerController.modifyBalance(-totalPriceInt, player);
            }
            else if(answer.equals(pay200)){
                playerController.modifyBalance(-200, player);
            }
            else if(answer.equals(pay100)){
                playerController.modifyBalance(-100, player);
            }
        }
        else if(field instanceof VisitJailField){
            //VisitJailField visitJailField = (VisitJailField) field; //Sker heller ikke en dyt her
        }
    }

    private void ownableFieldAction(Player player, int fieldIndex){
        Field field = this.getFields()[fieldIndex];
        OwnableField ownableField = (OwnableField) field;
        if(ownableField.getOwner() == null){
            String yes = "Ja";
            String no = "Nej";
            String[] buttons = new String[]{
                    yes, no
            };
            String answer = InterfaceGUI.awaitUserButtonsClicked("Denne grund er ikke købt. Vil du købe?", player.getName(), buttons);
            if(answer.equals(yes)){
                if(player.getAccount().getBalance() < ownableField.getPrice())
                {
                    InterfaceGUI.showMessage("Du har ikke råd til at købe den grund.", player.getName());
                } else {
                    playerController.modifyBalance(-ownableField.getPrice(), player);
                    ownableField.setOwner(player, fieldIndex);
                }
            }
            if(answer.equals(no) || player.getAccount().getBalance() < ownableField.getPrice()){
                tradeController.auction(ownableField, fieldIndex);
            }
        }
    }

    private void ownableFieldAction(Player player, int fieldIndex, BeerField beerField, RaffleCup raffleCup){
        if(beerField.getOwner() != player && beerField.getOwner() != null){
            InterfaceGUI.showMessage(beerField.getOwner().getName() + " ejer bryggeriet og der skal nu betales til vedkommende.", player.getName());

            int rent = raffleCup.getTotalValue();
            if(raffleCup.isSameDie()){
                rent *= 2;
                InterfaceGUI.showMessage("Prisen skal ganges med to da du slog dobbelt!", player.getName());
            }

            int sameOwnerCounter = 0;
            for(Field field : fields){
                if(field instanceof BeerField){
                    BeerField bf = (BeerField) field;
                    if(bf.getOwner() == beerField.getOwner()){
                        sameOwnerCounter++;
                    }
                }
            }
            if(sameOwnerCounter == 2) {
                InterfaceGUI.showMessage("Da " + beerField.getOwner().getName() + " ejer begge bryggerier skal der betales dobbelt!", player.getName());
            }
            rent *= 25 * sameOwnerCounter;

            InterfaceGUI.showMessage("Du skal betale " + rent, player.getName());
            playerController.modifyBalance(-rent, player);
            playerController.modifyBalance(rent, beerField.getOwner());
        }else{
            ownableFieldAction(player, fieldIndex);
        }
    }
    private void ownableFieldAction(Player player, int fieldIndex, FerryField ferryField){
        if(ferryField.getOwner() != player && ferryField.getOwner() != null){
            InterfaceGUI.showMessage(ferryField.getOwner().getName() + " ejer færgen og der skal nu betales til vedkommende.", player.getName());

            int sameOwnerCounter = 0;
            for(Field field : fields){
                if(field instanceof FerryField){
                    FerryField ff = (FerryField) field;
                    if(ff.getOwner() == ferryField.getOwner()){
                        sameOwnerCounter++;
                    }
                }
            }
            int rent = ferryField.getRent(sameOwnerCounter);
            InterfaceGUI.showMessage(ferryField.getOwner().getName() + " ejer " + sameOwnerCounter + " færger.", player.getName());

            InterfaceGUI.showMessage("Du skal betale " + rent, player.getName());
            playerController.modifyBalance(-rent, player);
            playerController.modifyBalance(rent, ferryField.getOwner());
        }else{
            ownableFieldAction(player, fieldIndex);
        }
    }
    private void ownableFieldAction(Player player, int fieldIndex, StreetField streetField){
        if(streetField.getOwner() != player && streetField.getOwner() != null){
            InterfaceGUI.showMessage(streetField.getOwner().getName() + " ejer grunden og der skal nu betales til vedkommende.", player.getName());

            int rent = streetField.getRent();

            if(streetField.getBuildings() == 0) {
                //Hvis man ikke ejer nogle bygninger på den pågælende grund men ejer ALLE grunde i gruppen så skal man betale dobbelt leje
                boolean ownAllInSameGroup = true;
                for (Field field : fields) {
                    if (field instanceof StreetField) {
                        StreetField sf = (StreetField) field;
                        if (sf.getGroupName().equals(streetField.getGroupName())) {
                            if (sf.getOwner() != streetField.getOwner()) {
                                ownAllInSameGroup = false;
                            }
                        }
                    }
                }
                if(ownAllInSameGroup) rent *= 2;
            }

            InterfaceGUI.showMessage("Du skal betale " + rent, player.getName());
            playerController.modifyBalance(-rent, player);
            playerController.modifyBalance(rent, streetField.getOwner());
        }else{
            ownableFieldAction(player, fieldIndex);
        }
    }


    public OwnableField[] getOwnerOfFieldsArray(Player player){
        int playerOwnFieldCounter = 0;
        for(Field field : fields){
            if(field instanceof OwnableField){
                OwnableField ownableField = (OwnableField) field;
                if(ownableField.getOwner() == player){
                    playerOwnFieldCounter++;
                }
            }
        }
        OwnableField[] ownerOfFieldsArray = new OwnableField[playerOwnFieldCounter];
        playerOwnFieldCounter = 0;
        for(int i = 0;i<fields.length;i++){
            if(fields[i] instanceof OwnableField){
                OwnableField ownableField = (OwnableField) fields[i];
                if(ownableField.getOwner() == player){
                    ownerOfFieldsArray[playerOwnFieldCounter] = ownableField;
                    playerOwnFieldCounter++;
                }
            }
        }
        return ownerOfFieldsArray;
    }

    public StreetField[] getOwnerOfStreetFieldsArray(Player player){
        int count = 0;
        for(Field field : fields){
            if(field instanceof StreetField){
                OwnableField ownableField = (OwnableField) field;
                if(ownableField.getOwner() == player){
                    count++;
                }
            }
        }
        StreetField[] result = new StreetField[count];
        int index = 0;
        for(int i = 0;i<fields.length;i++){
            if(fields[i] instanceof StreetField){
                StreetField streetField = (StreetField) fields[i];
                if(streetField.getOwner() == player){
                    result[index++] = streetField;
                }
            }
        }
        return result;
    }
    public StreetField[] getStreetGroupArray(String groupColor) {
        int count = 0;
        for(Field field : fields) {
            if (field instanceof StreetField) {
                StreetField streetField = (StreetField) field;
                if (streetField.getGroupName().equals(groupColor)) {
                    count++;
                }
            }
        }
        int index = 0;
        StreetField[] result = new StreetField[count];
        for(Field field : fields) {
            if (field instanceof StreetField) {
                StreetField streetField = (StreetField) field;
                if (streetField.getGroupName().equals(groupColor)) {
                    result[index++] = streetField;
                }
            }
        }
        return result;
    }

    public StreetField[] getOwnerOfStreetBuildingsArray(Player player){
        int count = 0;
        for(Field field : fields){
            if(field instanceof StreetField){
                StreetField streetField = (StreetField) field;
                if(streetField.getOwner() == player && streetField.getBuildings() > 0){
                    count++;
                }
            }
        }
        StreetField[] result = new StreetField[count];
        int index = 0;
        for(int i = 0;i<fields.length;i++){
            if(fields[i] instanceof StreetField){
                StreetField streetField = (StreetField) fields[i];
                if(streetField.getOwner() == player && streetField.getBuildings() > 0){
                    result[index++] = streetField;
                }
            }
        }
        return result;
    }

    public String[] transformToStringArray(Field[] fields){
        String[] stringArray = new String[fields.length];
        for(int i = 0;i<fields.length;i++){
            stringArray[i] = fields[i].getTitle();
        }
        return stringArray;
    }

    public int getFieldIndex(Field field){
        for(int i = 0;i<fields.length;i++){
            if(fields[i].getTitle().equals(field.getTitle())){
                return i;
            }
        }
        return -1;
    }
}
