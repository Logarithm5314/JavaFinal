package sample;

import javafx.animation.PathTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.*;

import sample.Formation.*;
import sample.Creature.*;
import sample.BattleGround.*;

public class Controller implements Initializable {

    public static Creature[] creatures;
    public static final int NUM = 40;
    public static final int N = 15;
    public static int combatNum = 0;
    public static final int PROJECTILE_FLYING_TIME = 175;

    BattleGround battleGround;
    CalabashFormation CBformaiton;
    MonsterFormation Mformation;
    public static Replay replay;

    private int oldID;
    private boolean available;
    private boolean ready;
    private Label projectile;
    @FXML public GridPane gridPane;
    @FXML private Label infoLabel;
    private Alert infoDialog;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gridPane.setStyle("-fx-background-image: url(" + "'/image/map15x15+road.png'" + "); ");

        oldID = -1;
        available = true;
        ready = false;

        infoDialog = new Alert(Alert.AlertType.INFORMATION);
        infoDialog.setTitle("帮助");
        infoDialog.setContentText("D键：随机设置阵型。\nL键：读取文件。\nS键：保存回放。\nSPACE键：开始战斗。");

        creatures = new Creature[NUM];
        creatures[0] = new Grandpa();
        creatures[1] = new CalabashBrother(CalabashBrother.Feature.FIRST);
        creatures[2] = new CalabashBrother(CalabashBrother.Feature.SECOND);
        creatures[3] = new CalabashBrother(CalabashBrother.Feature.THIRD);
        creatures[4] = new CalabashBrother(CalabashBrother.Feature.FORTH);
        creatures[5] = new CalabashBrother(CalabashBrother.Feature.FIFTH);
        creatures[6] = new CalabashBrother(CalabashBrother.Feature.SIXTH);
        creatures[7] = new CalabashBrother(CalabashBrother.Feature.SEVENTH);
        creatures[8] = new Snake();
        creatures[9] = new Scorpion();
        for (int i = 10; i < NUM; i++)
            creatures[i] = new Creep();

        battleGround = new BattleGround();
        CBformaiton = new CalabashFormation();
        Mformation = new MonsterFormation();
        //CBformaiton.Sort();
    }

    private void startUpdate(){
        Thread thread = new Thread(() -> {
            synchronized (Creature.ob) {
                while (!Creature.winning) {
                    System.out.println("Main Thread " + Creature.move);
                    if (Creature.move == 1) {
                        Platform.runLater(() -> {
                            /*@Override
                            public void run() {*/

                                //gridPane.getChildren().clear();
                                if (creatures[Creature.lastTurn].isCreatureAlive()) {
                                    gridPane.getChildren().remove(creatures[Creature.lastTurn].label);
                                    gridPane.getChildren().remove(creatures[Creature.lastTurn].progressBar);
                                    System.out.println("Main Thread delete " + (Creature.lastTurn));
                                    gridPane.add(creatures[Creature.lastTurn].label, Creature.newX, Creature.newY);
                                    creatures[Creature.lastTurn].updateProgressBar();
                                    gridPane.add(creatures[Creature.lastTurn].progressBar, Creature.newX, Creature.newY);
                                    GridPane.setHalignment(creatures[Creature.lastTurn].label, HPos.CENTER);
                                    GridPane.setHalignment(creatures[Creature.lastTurn].progressBar, HPos.CENTER);
                                    GridPane.setValignment(creatures[Creature.lastTurn].progressBar, VPos.TOP);
                                    System.out.println("Main Thread add " + (Creature.lastTurn) + " " + Creature.newX + " " + Creature.newY);
                                }
                                if (Creature.atkID  > -1  /*&& oldID != Creature.deadID*/) {
                                    System.out.println("Main Thread update " + (Creature.atkID) + "'s under attack");
                                    //creatures[Creature.atkID].updateProgressBar();

                                    //Path path = new Path();
                                    //path.getElements().add(new MoveTo(0.0f, 0.0f));
                                    //path.getElements().add(new LineTo(Creature.deadX * 50.0f, Creature.deadY * 40.0f));
                                    projectile = null;
                                    projectile = new Label();
                                    //projectile.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/image/BSL1544881545862.png"), 40, 40, false, false)));
                                    projectile.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/image/Arrow2.png"), 25, 25, false, false)));
                                    //projectileList.add(projectile);
                                    int lastX = 0, lastY = 0;
                                    for (int i = 0; i < N; i++){
                                        for (int j = 0; j < N; j++){
                                            if (BattleGround.battleGround[i][j] == Creature.lastTurn){
                                                lastX = j;
                                                lastY = i;
                                            }
                                        }
                                    }
                                    double gapX = Creature.deadX - lastX, gapY = Creature.deadY - lastY;
                                    double angle = 45.0 - Math.atan2(-gapY, gapX) / 3.1416 * 180.0;
                                    System.out.println("Main Thread atan2: " + Math.atan2(-gapY, gapX) / 3.1416 * 180.0 + " angle: " + angle);
                                    System.out.println(Math.atan2(1, 1) / 3.1416 * 180.0);
                                    projectile.setRotate(angle);
                                    if (gapX < 0)
                                        gapX += 0.5;
                                    if (gapY < 0)
                                        gapY += 0.5;
                                    //path.getElements().add(new LineTo(gapX * 50.0, gapY * 40.0));

                                    gridPane.add(projectile, lastX, lastY);
                                    GridPane.setHalignment(projectile, HPos.CENTER);
                                    GridPane.setValignment(projectile, VPos.CENTER);
                                    TranslateTransition tt = new TranslateTransition(Duration.millis(PROJECTILE_FLYING_TIME), projectile);
                                    tt.setToX(gapX * 50.0);
                                    tt.setToY(gapY * 40.0);
                                    tt.play();
                                        /*try {
                                            Thread.sleep(1000);
                                        } catch (InterruptedException e){
                                            e.printStackTrace();
                                        }*/
                                    //gridPane.getChildren().remove(label);

                                    //creatures[Creature.deadID].label.setGraphic(new ImageView(creatures[Creature.deadID].deadImage));
                                    //oldID = Creature.deadID;
                                }
                                    /*if (BattleGround.winning()){
                                        Creature.winning = true;
                                        available = true;
                                        ready = false;
                                        System.out.println("Main Thread winning");
                                    }
                                    System.out.println("Main Thread move: " + Creature.move + " winning: " + Creature.winning);*/

                            //}
                        });
                        try {
                            Thread.sleep(PROJECTILE_FLYING_TIME);
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                        Platform.runLater(() -> {
                            /*@Override
                            public void run() {*/
                                if (projectile != null) {
                                    gridPane.getChildren().remove(projectile);
                                }
                                if (Creature.atkID > -1){
                                    creatures[Creature.atkID].updateProgressBar();
                                }
                                if (Creature.deadID  > -1  && oldID != Creature.deadID) {
                                    creatures[Creature.deadID].label.setGraphic(new ImageView(creatures[Creature.deadID].deadImage));
                                    gridPane.getChildren().remove(creatures[Creature.deadID].progressBar);

                                    /*for (Label label : projectileList){
                                        gridPane.getChildren().remove(label);
                                    }
                                    projectileList.clear();*/
                                    oldID = Creature.deadID;
                                }
                                if (BattleGround.winning()){
                                    Creature.winning = true;
                                    available = true;
                                    ready = false;
                                    System.out.println("Main Thread winning");
                                }
                                System.out.println("Main Thread move: " + Creature.move + " winning: " + Creature.winning);
                            //}
                        });
                        Creature.move--;
                    }
                    try {
                        Creature.ob.notifyAll();
                        Creature.ob.wait();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println("Main thread terminated");
        });
        thread.start();
    }

    @FXML protected void onKeyReleased(KeyEvent event){
        if(event.getCode() == KeyCode.SPACE && ready) {
            System.out.println("SPACE pressed.");
            available = false;
            ready = false;
            //gridPane.getChildren().clear();
            System.out.println(combatNum);
            for (int i = 0; i < combatNum; i++){
                creatures[i].start();
            }

            startUpdate();
        }
        else if (event.getCode() == KeyCode.D && available){
            System.out.println("D pressed");

            replay = null;
            replay = new Replay();

            Random rand = new Random();
            int ranNum0 = rand.nextInt(8);
            int ranNum1 = rand.nextInt(8);
            restart(ranNum0, ranNum1);
            //restart(3, 6);
            replay.updateFormation(ranNum0, ranNum1);

            ready = true;
        }
        else if (event.getCode() == KeyCode.L && available){
            Stage stage = (Stage) gridPane.getScene().getWindow();
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Battle Log", "*.log"));
            File file = fileChooser.showOpenDialog(stage);
            if (file == null)
                return;
            replay = null;
            replay = new Replay();
            replay.loadReplay(file);
            restart(replay.getFormationCB(), replay.getFormationM());
            Creature.replaying = true;
            ready = true;
        }
        else if (event.getCode() == KeyCode.S && available){
            Stage stage = (Stage) gridPane.getScene().getWindow();
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Battle Log", "*.log"));
            File file = fileChooser.showSaveDialog(stage);
            if (file == null)
                return;
            replay.saveReplay(file);
        }
    }

    @FXML private void onHelpButton(ActionEvent event){
        if (available && !ready){
            infoDialog.setHeaderText("当前可操作：\n按下D随机双方阵型。\n按下L读取文件。");
        }
        if (available && !ready && Creature.winning){
            infoDialog.setHeaderText("当前可操作：\n按下D随机双方阵型。\n按下L读取文件。\n按下S保存战斗回放");
        }
        if (ready){
            infoDialog.setHeaderText("当前可操作：\n按下SPACE开始战斗。\n按下D随机双方阵型。\n按下L读取文件。");
        }
        infoDialog.showAndWait();
        gridPane.requestFocus();
    }

    private void restart(int formationCB, int formationM){
        oldID = -1;
        Creature.round = 0;
        Creature.lastTurn = 0;
        Creature.turn = 0 ;
        Creature.deadID = -1;
        Creature.atkID = -1;
        Creature.move = 0;
        Creature.newX = 0;
        Creature.newY = 0;
        Creature.deadX = 0;
        Creature.deadY = 0;
        Creature.winning = false;
        Creature.replaying = false;
        Creature.engaging = false;

        for (Creature creature : creatures){
            gridPane.getChildren().remove(creature.label);
            gridPane.getChildren().remove(creature.progressBar);
            //gridPane.getChildren().clear();
            //Button button = new Button();
            //gridPane.add(button, 15, 0);
            creature.label.setGraphic(new ImageView(creature.image));
        }
        gridPane.requestFocus();

        for (Creature creature : creatures){
            creature = null;
        }
        Creep.start = 10;
        creatures[0] = new Grandpa();
        creatures[1] = new CalabashBrother(CalabashBrother.Feature.FIRST);
        creatures[2] = new CalabashBrother(CalabashBrother.Feature.SECOND);
        creatures[3] = new CalabashBrother(CalabashBrother.Feature.THIRD);
        creatures[4] = new CalabashBrother(CalabashBrother.Feature.FORTH);
        creatures[5] = new CalabashBrother(CalabashBrother.Feature.FIFTH);
        creatures[6] = new CalabashBrother(CalabashBrother.Feature.SIXTH);
        creatures[7] = new CalabashBrother(CalabashBrother.Feature.SEVENTH);
        creatures[8] = new Snake();
        creatures[9] = new Scorpion();
        for (int i = 10; i < NUM; i++)
            creatures[i] = new Creep();

        for (Creature creature : creatures){
            creature.setCreatureAlive();
        }
        for (int i = 0; i < Controller.N; i++) {
            for (int j = 0; j < Controller.N; j++) {
                BattleGround.battleGround[i][j] = -1;
            }
        }

        switch (formationCB){
            case 0: CBformaiton.changeFormation(Formation.Name.HEYI); break;
            case 1: CBformaiton.changeFormation(Formation.Name.YANXING); break;
            case 2: CBformaiton.changeFormation(Formation.Name.HENGE); break;
            case 3: CBformaiton.changeFormation(Formation.Name.CHANGSHE); break;
            case 4: CBformaiton.changeFormation(Formation.Name.YULIN); break;
            case 5: CBformaiton.changeFormation(Formation.Name.FANGYUAN); break;
            case 6: CBformaiton.changeFormation(Formation.Name.YANYUE); break;
            case 7: CBformaiton.changeFormation(Formation.Name.FENGSHI); break;
        }

        switch (formationM){
            case 0: Mformation.changeFormation(Formation.Name.HEYI); break;
            case 1: Mformation.changeFormation(Formation.Name.YANXING); break;
            case 2: Mformation.changeFormation(Formation.Name.HENGE); break;
            case 3: Mformation.changeFormation(Formation.Name.CHANGSHE); break;
            case 4: Mformation.changeFormation(Formation.Name.YULIN); break;
            case 5: Mformation.changeFormation(Formation.Name.FANGYUAN); break;
            case 6: Mformation.changeFormation(Formation.Name.YANYUE); break;
            case 7: Mformation.changeFormation(Formation.Name.FENGSHI); break;
        }
        battleGround.deploy(CBformaiton, 0);
        battleGround.deploy(Mformation, 1);
        combatNum = battleGround.combatNumber();
        BattleGround.print();

        for (int i = 0; i < Controller.N; i++) {
            for (int j = 0; j < Controller.N; j++) {
                if (BattleGround.battleGround[i][j] != -1) {
                    //Label label = new Label(String.valueOf(Controller.creatures[BattleGround.battleGround[i][j]].symbol));
                    //label.setGraphic(new ImageView(creatures[BattleGround.battleGround[i][j]].image));
                    gridPane.add(creatures[BattleGround.battleGround[i][j]].label, j, i);
                    gridPane.add(creatures[BattleGround.battleGround[i][j]].progressBar, j, i);
                    GridPane.setHalignment(creatures[BattleGround.battleGround[i][j]].label, HPos.CENTER);
                    GridPane.setHalignment(creatures[BattleGround.battleGround[i][j]].progressBar, HPos.CENTER);
                    GridPane.setValignment(creatures[BattleGround.battleGround[i][j]].progressBar, VPos.TOP);
                }
            }
        }
    }
}


