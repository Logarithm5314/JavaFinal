package sample.Creature;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Snake extends Creature{
    public Snake(){
        id = 8;
        symbol = 'S';
        camp = 1;
        image = new Image(getClass().getResourceAsStream("/image/Snake.png"), 50, 40, false, false);
        deadImage = new Image(getClass().getResourceAsStream("/image/deadSnake.png"), 50, 40, false, false);
        label = new Label();
        label.setGraphic(new ImageView(image));
        hitPoints = maxHP = 5;
        progressBar.setProgress((double)hitPoints / maxHP);
    }
}
