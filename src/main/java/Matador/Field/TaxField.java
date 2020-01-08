package Matador.Field;

import java.awt.*;

public class TaxField extends Field {
    int amount;
    float percentage;

    public TaxField(String title, String subTitle, int amount, float percentage) {
        super(title, subTitle);
        this.amount = amount;
        this.percentage = percentage;
    }
}
