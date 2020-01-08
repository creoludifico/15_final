package Matador.Field;

import java.awt.*;

public abstract class Field {
    private String title, subTitle;

    public Field(String title, String subTitle) {
        this.title = title;
        this.subTitle = subTitle;
    }

    public String getTitle() { return title; }
    public String getSubTitle() { return subTitle; }
}
