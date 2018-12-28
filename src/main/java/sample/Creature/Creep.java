package sample.Creature;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Creep extends Creature{
    public static int start = 10;
    public Creep(){
        id = start;
        start++;
        symbol = 'c';
        camp = 1;
        image = new Image(getClass().getResourceAsStream("/image/hulu11.png"), 33, 40, false, false);
        deadImage = new Image(getClass().getResourceAsStream("/image/deadhulu11.png"), 33, 40, false, false);
        label = new Label();
        label.setGraphic(new ImageView(image));
        hitPoints = maxHP = 2;
        progressBar.setProgress((double)hitPoints / maxHP);
    }
}
