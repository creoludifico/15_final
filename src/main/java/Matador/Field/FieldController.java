package Matador.Field;

import GUI.InterfaceGUI;

import java.awt.*;

public class FieldController {
    private Field[] fields;

    public FieldController() {
        fields = new Field[] {
                new StartField(),
                new StreetField("Rødovrevej", 60, 30, 50, new int[] {2, 10, 30, 90, 160, 250}),
                new ChanceField(),
                new StreetField("Hvidovrevej", 60,30, 50, new int[] {4, 20, 60, 180, 320, 450}),
                new TaxField("Betal indkomstskat", "10% el. 200", 200, 0.10f),
                new FerryField("Øresund",200, 100, new int[] {25, 50, 100, 200}),
                new StreetField("Roskildevej", 100, 50, 50, new int[] {6, 30, 90, 270, 400, 550}),
                new ChanceField(),
                new StreetField("Valby Langgade", 100, 50, 50, new int[] {6, 30, 90, 270, 400, 550}),
                new StreetField("Allégade", 120, 60, 50, new int[] {8, 40, 100, 300, 450, 600}),
                new VisitJailField(),
                new StreetField("Frederiksberg Allé", 140 , 70, 100, new int[] {10, 50, 150, 450, 625, 750}),
                new BeerField("Tuborg", 150),
                new StreetField("Büllowsvej", 140, 70, 100, new int[] {10, 50, 150, 450, 625, 750}),
                new StreetField("Gammel Kongevej", 140, 80, 100, new int[] {12, 60, 180, 500, 700, 900}),
                new FerryField("D.F.D.S", 200, 100, new int[] {25, 50, 100, 200}),
                new StreetField("Bernstorffsvej", 180, 90, 100, new int[] {14, 70, 200, 550, 750, 950}),
                new ChanceField(),
                new StreetField("Hellerupvej", 180, 90, 100, new int[] {14, 70, 200, 50, 750, 950}),
                new StreetField("Strandvejen", 180, 100, 100, new int[] {16, 80, 220, 600, 800, 1000}),
                new RefugeField(),
                new StreetField("Trianglen", 220, 110, 150, new int[] {18, 90, 250, 700, 875, 1050}),
                new ChanceField(),
                new StreetField("Østerbrogade", 220, 110, 150, new int[] {18, 90, 250, 700, 875, 1050}),
                new StreetField("Grønningen", 220, 120, 150, new int[] {20, 100, 300, 750, 925, 110}),
                new FerryField("Ø.S.", 200, 100, new int[] {25, 50, 100, 200}),
                new StreetField("Bredgade", 260, 130, 150, new int[] {22, 110, 330, 800, 975, 1150}),
                new StreetField("Kgs.Nytorv", 260, 130, 150, new int[] { 22, 110, 330, 800, 975, 1150}),
                new BeerField("Carlsberg", 150),
                new StreetField("Østergade", 280, 140, 150, new int[] {22, 110, 330, 800, 975, 1150}),
                new JailField(),
                new StreetField("AmagerTorv", 300, 150, 200, new int[] {26, 130, 390, 900, 1100, 1275}),
                new StreetField("Vimmelskaftet", 300, 150, 200, new int[] {26, 130, 390, 900, 1100, 1275}),
                new ChanceField(),
                new StreetField("Nygade", 320, 160, 200, new int[] {150, 450, 1000, 1200, 1400}),
                new FerryField("Bornholm", 200, 100, new int [] {25, 50, 100, 200}),
                new ChanceField(),
                new StreetField("Frederiksborggade", 350, 175, 200, new int[] {35, 175, 500, 1100, 1300, 1500}),
                new TaxField("Ekstraordinær statsskat", "Betal 100", 100, 0.0f),
                new StreetField("Rådhuspladsen", 400, 200, 200, new int[] {50, 200, 600, 1400, 1700, 2000})
                };

            InterfaceGUI.initGUIFields(fields);
        }

    public Field[] getFields() {
        return fields;
    }

}
