package Matador.Field;

import java.awt.*;

public class TaxField extends Field {
    double percentage;

    public TaxField(String title, String subTitle, double percentage) {
        super(title, subTitle);
        this.percentage = percentage;
    }
}
