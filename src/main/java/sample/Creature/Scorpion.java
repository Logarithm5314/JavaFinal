package sample.Creature;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Scorpion extends Creature{
    public Scorpion(){
        id = 9;
        symbol = 's';
        camp = 1;
        image = new Image(getClass().getResourceAsStream("/image/hulu10.png"), 33, 40, false,false);
        deadImage = new Image(getClass().getResourceAsStream("/image/deadhulu10.png"), 33, 40, false,false);
        label = new Label();
        label.setGraphic(new ImageView(image));
        hitPoints = maxHP = 5;
        progressBar.setProgress((double)hitPoints / maxHP);
    }
}