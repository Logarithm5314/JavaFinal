package sample.Creature;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Grandpa extends Creature{
    public Grandpa(){
        id = 0;
        symbol = 'G';
        camp = 0;
        image = new Image(getClass().getResourceAsStream("/image/Grandpa.png"), 50, 40, false, false);
        deadImage = new Image(getClass().getResourceAsStream("/image/deadGrandpa.png"), 50, 40, false, false);
        label = new Label();
        label.setGraphic(new ImageView(image));
        hitPoints = maxHP = 5;
        progressBar.setProgress((double)hitPoints / maxHP);
    }
}