package Matador.Controllers;

import Matador.GUI.InterfaceGUI;
import Matador.Models.Field.*;
import Matador.Models.User.Player;

public class FieldController {
    private Field[] fields;
    private PlayerController playerController;
    private ChanceCardController chanceCardController;
    private TradeController tradeController;

    public Field[] getFields() {
        return fields;
    }

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

    public void fieldAction(Player player, int fieldIndex, RaffleCupController raffleCupController){
        Field field = this.getFields()[fieldIndex];

        if(field instanceof BeerField){
            BeerField beerField = (BeerField) field;
            ownableFieldAction(player, fieldIndex, beerField, raffleCupController);
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

            if(taxField.getPercentage() > 0){
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
                totalPrice = totalPrice * taxField.getPercentage();
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
    private void ownableFieldAction(Player player, int fieldIndex, BeerField beerField, RaffleCupController raffleCupController){
        if(beerField.getOwner() != player && beerField.getOwner() != null){
            InterfaceGUI.showMessage(beerField.getOwner().getName() + " ejer bryggeriet og der skal nu betales til vedkommende.", player.getName());

            int rent = raffleCupController.getTotalValue();
            if(raffleCupController.isSameDie()){
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

    public OwnableField[] getOwnableFields(){
        int newSize = 0;
        for(Field field : fields){
            if(field instanceof OwnableField){
                newSize++;
            }
        }
        OwnableField[] result = new OwnableField[newSize];
        int i = 0;
        for(Field field : fields){
            if(field instanceof OwnableField){
                OwnableField ownableField = (OwnableField) field;
                result[i] = ownableField;
                i++;
            }
        }
        return result;
    }
    public OwnableField[] getOwnableFields(Player player){
        int newSize = 0;
        OwnableField[] ownableFields = getOwnableFields();
        for(OwnableField ownableField : ownableFields){
            if(ownableField.getOwner() == player){
                newSize++;
            }
        }
        OwnableField[] result = new OwnableField[newSize];
        int i = 0;
        for(OwnableField ownableField : ownableFields){
            if(ownableField.getOwner() == player){
                result[i] = ownableField;
                i++;
            }
        }
        return result;
    }
    public OwnableField[] getOwnableFields(Player player, boolean withBuildings){
        if(withBuildings) {
            return getStreetFields(player, withBuildings);
        }
        int newSize = 0;
        OwnableField[] ownableFields = getOwnableFields(player);
        for(OwnableField ownableField : ownableFields){
            if(ownableField instanceof StreetField) {
                StreetField streetField = (StreetField)ownableField;
                if(streetField.getBuildings() == 0){
                    newSize++;
                }
            }
            else {
                newSize++;
            }
        }
        OwnableField[] result = new OwnableField[newSize];
        int i = 0;
        for(OwnableField ownableField : ownableFields){
            if(ownableField instanceof StreetField) {
                StreetField streetField = (StreetField)ownableField;
                if(streetField.getBuildings() == 0){
                    result[i] = ownableField;
                    i++;
                }
            }
            else {
                result[i] = ownableField;
                i++;
            }
        }
        return result;
    }
    public StreetField[] getStreetFields(){
        int newSize = 0;
        for(Field field : fields){
            if(field instanceof StreetField){
                newSize++;
            }
        }
        StreetField[] result = new StreetField[newSize];
        int i = 0;
        for(Field field : fields){
            if(field instanceof StreetField){
                result[i] = (StreetField) field;
                i++;
            }
        }
        return result;
    }
    public StreetField[] getStreetFields(Player player){
        int newSize = 0;
        StreetField[] streetFields = getStreetFields();
        for(StreetField streetField : streetFields){
            if(streetField.getOwner() == player){
                newSize++;
            }
        }
        StreetField[] result = new StreetField[newSize];
        int i = 0;
        for(StreetField streetField : streetFields){
            if(streetField.getOwner() == player){
                result[i] = streetField;
                i++;
            }
        }
        return result;
    }
    public StreetField[] getStreetFields(Player player, boolean withBuildings){
        int newSize = 0;
        StreetField[] streetFields = getStreetFields(player);
        for(StreetField streetField : streetFields){
            if(withBuildings && streetField.getBuildings() > 0){
                newSize++;
            }
            else if(!withBuildings && streetField.getBuildings() == 0){
                newSize++;
            }
        }
        StreetField[] result = new StreetField[newSize];
        int i = 0;
        for(StreetField streetField : streetFields){
            if(withBuildings && streetField.getBuildings() > 0){
                result[i] = streetField;
                i++;
            }
            else if(!withBuildings && streetField.getBuildings() == 0){
                result[i] = streetField;
                i++;
            }
        }
        return result;
    }
    public FerryField[] getFerryFields(){
        int newSize = 0;
        for(Field field : fields){
            if(field instanceof FerryField){
                newSize++;
            }
        }
        FerryField[] result = new FerryField[newSize];
        int i = 0;
        for(Field field : fields){
            if(field instanceof FerryField){
                FerryField ferryField = (FerryField) field;
                result[i] = ferryField;
                i++;
            }
        }
        return result;
    }
    public FerryField[] getFerryFields(Player player){
        int newSize = 0;
        FerryField[] ferryFields = getFerryFields();
        for(FerryField ferryField : ferryFields){
            if(ferryField.getOwner() == player){
                newSize++;
            }
        }
        FerryField[] result = new FerryField[newSize];
        int i = 0;
        for(FerryField ferryField : ferryFields){
            if(ferryField.getOwner() == player){
                result[i] = ferryField;
                i++;
            }
        }
        return result;
    }
    public OwnableField[] getPawnedFields(){
        int newSize = 0;
        for(Field field : fields){
            if(field instanceof OwnableField){
                OwnableField ownableField = (OwnableField) field;
                if(ownableField.getPawned()){
                    newSize++;
                }
            }
        }
        OwnableField[] result = new OwnableField[newSize];
        int i = 0;
        for(Field field : fields){
            if(field instanceof OwnableField){
                OwnableField ownableField = (OwnableField) field;
                if(ownableField.getPawned()){
                    result[i] = ownableField;
                    i++;
                }
            }
        }
        return result;
    }
    public OwnableField[] getPawnedFields(Player player){
        int newSize = 0;
        for(OwnableField pawnedField : getPawnedFields()){
            if(pawnedField.getOwner() == player){
                newSize++;
            }
        }
        OwnableField[] result = new OwnableField[newSize];
        int i = 0;
        for(OwnableField pawnedField : getPawnedFields()){
            if(pawnedField.getOwner() == player){
                result[i] = pawnedField;
            }
        }
        return result;
    }

    public OwnableField[] getTradeableFields(OwnableField[] ownableFields) {
        //Et godt eksempel hvor array list havde været til en kæmpe hjælp så det samme ikke skulle kaldes 2 gange
        int newSize = 0;
        for(OwnableField ownableField1 : ownableFields){
            if(!(ownableField1 instanceof StreetField)){
                newSize++;
            }
            else {
                if(canBeTraded(ownableField1)){
                    newSize++;
                }
            }
        }
        OwnableField[] tradeableFields = new OwnableField[newSize];

        int i = 0;
        for(OwnableField ownableField1 : ownableFields){
            if(!(ownableField1 instanceof StreetField)){
                tradeableFields[i] = ownableField1;
                i++;
            }
            else {
                if(canBeTraded(ownableField1)){
                    tradeableFields[i] = ownableField1;
                    i++;
                }
            }
        }

        return tradeableFields;
    }
    public StreetField[] getBuildableFields(StreetField[] streetFields){
        //Et godt eksempel hvor array list havde været til en kæmpe hjælp så det samme ikke skulle kaldes 2 gange
        int newSize = 0;
        for(StreetField streetField1 : streetFields){
            if(streetField1.getBuildings() == 5){
                continue;
            }
            if(canBeModified(streetField1, true)){
                newSize++;
            }
        }
        StreetField[] buildableFields = new StreetField[newSize];
        int i = 0;
        for(StreetField streetField1 : streetFields){
            if(streetField1.getBuildings() == 5){
                continue;
            }
            if(canBeModified(streetField1, true)){
                buildableFields[i] = streetField1;
                i++;
            }
        }
        return buildableFields;
    }
    public StreetField[] getDemolitionableFields(StreetField[] streetFields){
        //Et godt eksempel hvor array list havde været til en kæmpe hjælp så det samme ikke skulle kaldes 2 gange
        int newSize = 0;
        for(StreetField streetField1 : streetFields){
            if(canBeModified(streetField1, false)){
                newSize++;
            }
        }
        StreetField[] buildableFields = new StreetField[newSize];

        int i = 0;
        for(StreetField streetField1 : streetFields){
            if(canBeModified(streetField1, false)){
                buildableFields[i] = streetField1;
                i++;
            }
        }
        return buildableFields;
    }

    public String[] getTitlesFromFields(Field[] fields){
        String[] stringArray = new String[fields.length];
        for(int i = 0;i<fields.length;i++){
            stringArray[i] = fields[i].getTitle();
        }
        return stringArray;
    }

    public int getFieldIndex(Field field){
        for(int i = 0;i<fields.length;i++){
            if(fields[i] == field){
                return i;
            }
        }
        return -1;
    }
    public Field getFieldFromTitle(String title){
        for(Field field : fields){
            if(field.getTitle().equals(title)){
                return field;
            }
        }
        return null;
    }

    private boolean canBeTraded(OwnableField ownableField){
        StreetField streetField1 = (StreetField) ownableField;
        if(streetField1.getBuildings() > 0 || streetField1.getPawned()){
            return false;
        }
        for(StreetField streetField2 : getStreetFields()){
            if(streetField1.getGroupName().equals(streetField2.getGroupName()) && streetField2.getBuildings() > 0){
                return false;
            }
        }
        return true;
    }
    private boolean canBeModified(StreetField streetField1, boolean isBuildOrDemolish){
        for(StreetField streetField2 : getStreetFields()){
            if(streetField1.getGroupName().equals(streetField2.getGroupName()) && streetField1.getOwner() != streetField2.getOwner()){
                return false;
            }
            if(isBuildOrDemolish && streetField1.getGroupName().equals(streetField2.getGroupName()) && streetField2.getBuildings() < streetField1.getBuildings()){
                return false;
            }
            if(!isBuildOrDemolish && streetField1.getGroupName().equals(streetField2.getGroupName()) && streetField2.getBuildings() > streetField1.getBuildings()){
                return false;
            }
        }
        return true;
    }
}