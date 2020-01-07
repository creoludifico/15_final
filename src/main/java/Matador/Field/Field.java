package Matador.Field;

import java.awt.*;

public abstract class Field {
    String title, subTitle, description;
    Color foregroundColor, backgroundColor;

    public Field(String title, Color fieldColor, String subTitle, String description) {
        this.title = title;
        this.subTitle = subTitle;
        this.description = description;
        this.foregroundColor = fieldColor;
    }

    String getTitle() { return title; }
    String getSubTitle() { return subTitle; }
    String getDescription() { return description; }
    Color getForegroundColor() { return foregroundColor; }
    Color getBackgroundColor() { return backgroundColor; }
}
