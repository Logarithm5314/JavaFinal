package sample.Creature;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CalabashBrother extends Creature{
    private Feature feature;
    public enum Feature{
        FIRST("老大", "红色"), SECOND("老二", "橙色"), THIRD("老三", "黄色"),
        FORTH("老四", "绿色"), FIFTH("老五","青色"), SIXTH("老六", "蓝色"), SEVENTH("老七", "紫色");

        private String name;
        private String color;

        Feature(String name, String color){
            this.name = name;
            this.color = color;
        }
    }

    public CalabashBrother(Feature feature){
        this.feature = feature;
        id = feature.ordinal() + 1;
        symbol = (char)(id + '0');
        camp = 0;
        alive = true;

        switch (this.feature) {
            case FIRST: image = new Image(getClass().getResourceAsStream("/image/CB1.png"), 50, 40, false, false); break;
            case SECOND: image = new Image(getClass().getResourceAsStream("/image/CB2.png"), 50, 40, false, false); break;
            case THIRD: image = new Image(getClass().getResourceAsStream("/image/CB3.png"), 50, 40, false, false); break;
            case FORTH: image = new Image(getClass().getResourceAsStream("/image/CB4.png"), 50, 40, false, false); break;
            case FIFTH: image = new Image(getClass().getResourceAsStream("/image/CB5.png"), 50, 40, false, false); break;
            case SIXTH: image = new Image(getClass().getResourceAsStream("/image/CB6.png"), 50, 40, false, false); break;
            case SEVENTH: image = new Image(getClass().getResourceAsStream("/image/CB7.png"), 50, 40, false, false); break;
        }
        switch (this.feature) {
            case FIRST: deadImage = new Image(getClass().getResourceAsStream("/image/deadCB1.png"), 50, 40, false, false); break;
            case SECOND: deadImage = new Image(getClass().getResourceAsStream("/image/deadCB1.png"), 50, 40, false, false); break;
            case THIRD: deadImage = new Image(getClass().getResourceAsStream("/image/deadCB1.png"), 50, 40, false, false); break;
            case FORTH: deadImage = new Image(getClass().getResourceAsStream("/image/deadCB1.png"), 50, 40, false, false); break;
            case FIFTH: deadImage = new Image(getClass().getResourceAsStream("/image/deadCB1.png"), 50, 40, false, false); break;
            case SIXTH: deadImage = new Image(getClass().getResourceAsStream("/image/deadCB1.png"), 50, 40, false, false); break;
            case SEVENTH: deadImage = new Image(getClass().getResourceAsStream("/image/deadCB1.png"), 50, 40, false, false); break;
        }
        label = new Label();
        label.setGraphic(new ImageView(image));

        hitPoints = maxHP = 5;
        progressBar.setProgress((double)hitPoints / maxHP);
        System.out.println("Progress: " + (double)hitPoints / maxHP);
    }
}
