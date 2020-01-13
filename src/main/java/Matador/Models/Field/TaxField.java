package Matador.Models.Field;

public class TaxField extends Field {
    private double percentage;

    public TaxField(String title, String subTitle, double percentage) {
        super(title, subTitle);
        this.percentage = percentage;
    }

    public double getPercentage() {
        return percentage;
    }
}
