package sample.Creature;

import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;

import java.util.Random;

import sample.Controller;
import sample.BattleGround.*;

public abstract class Creature extends Thread{
    // private static int max_id = 0;
    public static final int WAIT_TIME = 50;
    int id;
    char symbol;
    int camp;
    boolean alive;

    int atkRange;
    int detectRange;
    int hitPoints;
    int maxHP;
    int atk;

    public Label label;
    public ProgressBar progressBar;
    public Image image;
    public Image deadImage;

    Creature(){
        alive = true;
        atkRange = 3;
        detectRange = 2;
        atk = 1;
        progressBar = new ProgressBar();
        progressBar.setMinHeight(1);
        progressBar.setPrefHeight(10);
        progressBar.setStyle("-fx-accent: red");
    }

    public static final Object ob = "aa";
    public static int move = 0, round = 0;
    public static int turn = 0, lastTurn = 0;
    public static int newX = 0, newY = 0, deadID = 0, deadX = 0, deadY = 0, atkID = 0;
    public static boolean winning = false, replaying = false, engaging = false;

    public void run(){
        //Random rand = new Random();

        //int ranNum = rand.nextInt(4);
        synchronized (ob) {
            while (alive && !winning) {
                //int ranNum = rand.nextInt(4);
                //System.out.println(id + " " + ranNum);
                if (move == 0 && turn == id) {
                    try{
                        sleep(WAIT_TIME);
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    //BattleGround.print();
                    deadID = -1;
                    atkID = -1;
                    if (replaying){
                        replayMove(id);
                        replayAtk();
                    }
                    else {
                        move(id);
                        Controller.replay.updateReplayMove(round, id, newX, newY);
                        //BattleGround.print();
                        atk(id);
                    }
                    BattleGround.print();
                    move++;
                    round++;
                    lastTurn = id;
                    do{
                        Creature.turn++;
                        Creature.turn = Creature.turn % Controller.combatNum;
                    } while(!Controller.creatures[Creature.turn].alive);
                    try {
                        ob.notifyAll();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else {
                    try {
                        ob.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        System.out.println("Creature " + id + "thread terminated");
    }

    public boolean move(int id){
        Random rand = new Random();
        int ranNum = rand.nextInt(8);
        int fromX = -1, fromY = -1;
        for (int i = 0; i < Controller.N; i++) {
            for (int j = 0; j < Controller.N; j++) {
                if (BattleGround.battleGround[i][j] == id) {
                    fromX = j;
                    fromY = i;
                    break;
                }
            }
        }
        System.out.println(id + " from " + fromX + " " + fromY + " " + BattleGround.battleGround[fromY][fromX]);
        if (fromX == -1)
            return false;
        int toY = fromY, toX = fromX;
        newY = toY;
        newX = toX;

        int dist = 100, dX = 0, dY = 0;
        for (int i = 0; i < Controller.N; i++){
            for (int j = 0; j < Controller.N; j++){
                if ((Math.abs(i - fromY) + Math.abs(j - fromX) < dist) &&
                        BattleGround.battleGround[i][j] != -1 &&
                        Controller.creatures[id].camp != Controller.creatures[BattleGround.battleGround[i][j]].camp){
                    dist = Math.abs(i - fromY) + Math.abs(j - fromX);
                    if (i - fromY < 0)
                        dY = -1;
                    else
                        dY = 1;
                    if (j - fromX < 0)
                        dX = -1;
                    else
                        dX = 1;
                }
            }
        }

        if (engaging) {
            switch (ranNum) {
                case 0:
                    toY += dY;
                    break;
                case 1:
                    toY += dY;
                    break;
                case 2:
                    toY -= dY;
                    break;
                case 3:
                    toX += dX;
                    break;
                case 4:
                    toX += dX;
                    break;
                case 5:
                    toX -= dX;
                    break;
                case 6:
                    toY += dY;
                    break;
                case 7:
                    toX += dX;
                    break;
            }
        }
        else if (Controller.creatures[id].camp == 0)
            toX++;
        else
            toX--;

        if (toY < 0 || toY >= Controller.N || toX < 0 || toX >= Controller.N)
            return false;
        System.out.println(id + " to " + toX + " " + toY + " " + BattleGround.battleGround[toY][toX]);
        if (BattleGround.battleGround[toY][toX] != -1) {
            return false;
        }
        else {
            BattleGround.battleGround[fromY][fromX] = -1;
            BattleGround.battleGround[toY][toX] = id;
            newY = toY;
            newX = toX;
            System.out.println(id + " from " + fromX + " " + fromY + " to " + toX + " " + toY);
            //Controller.replay.updateReplayMove(round, id, fromX, fromY, toX, toY);

            return true;
        }
    }
    public void replayMove(int id){
        int fromX = -1, fromY = -1;
        for (int i = 0; i < Controller.N; i++) {
            for (int j = 0; j < Controller.N; j++) {
                if (BattleGround.battleGround[i][j] == id) {
                    fromX = j;
                    fromY = i;
                    break;
                }
            }
        }
        int toX = Controller.replay.replayMoveX(round);
        int toY = Controller.replay.replayMoveY(round);
        newY = toY;
        newX = toX;
        System.out.println("ReplayMove: " + id + " " + fromX + " " + fromY + " " + round);
        BattleGround.battleGround[fromY][fromX] = -1;
        BattleGround.battleGround[toY][toX] = id;
    }

    public void atk(int id){
        int fromX = -1, fromY = -1;
        for (int i = 0; i < Controller.N; i++) {
            for (int j = 0; j < Controller.N; j++) {
                if (BattleGround.battleGround[i][j] == id) {
                    fromX = j;
                    fromY = i;
                    break;
                }
            }
        }
        int atkRange = Controller.creatures[BattleGround.battleGround[fromY][fromX]].atkRange;
        int camp = Controller.creatures[BattleGround.battleGround[fromY][fromX]].camp;
        int flag = 0;

        for (int i = 0; i < Controller.N; i++) {
            for (int j = 0; j < Controller.N; j++) {
                if (BattleGround.battleGround[i][j] != -1 &&
                        Controller.creatures[BattleGround.battleGround[i][j]].alive &&
                        camp != Controller.creatures[BattleGround.battleGround[i][j]].camp  &&
                        Math.abs(i - fromY) <= atkRange && Math.abs(j - fromX) <= atkRange) {
                    Random rand = new Random();
                    int ranNum = rand.nextInt(4);
                    if (ranNum <= 1){
                        Controller.creatures[BattleGround.battleGround[i][j]].hitPoints -= atk;
                        atkID = BattleGround.battleGround[i][j];
                        deadX = j;
                        deadY = i;
                        if (Controller.creatures[BattleGround.battleGround[i][j]].hitPoints <= 0){
                            Controller.creatures[BattleGround.battleGround[i][j]].alive = false;

                            deadID = BattleGround.battleGround[i][j];
                            BattleGround.battleGround[i][j] = -1;
                        }
                        /*Controller.creatures[BattleGround.battleGround[i][j]].alive = false;
                        deadID = BattleGround.battleGround[i][j];
                        BattleGround.battleGround[i][j] = -1;
                        System.out.println(id + " kill " + deadID + " at " + i + " " + j);
                        deadX = j;
                        deadY = i;*/
                        flag = 1;
                        break;
                    }
                    if (Math.abs(i - fromY) <= atkRange && Math.abs(j - fromX) <= detectRange)
                        engaging = true;
                }
            }
            if (flag == 1)
                break;
        }
        if (flag == 1){
            Controller.replay.updateReplayAtk(round, id, atkID);
        }
        else{
            Controller.replay.updateReplayAtk(round, id, -1);
        }
    }
    public void replayAtk(){
        int atkID = Controller.replay.replayAtkID(round);
        if (atkID == -1)
            return;
        for (int i = 0; i < Controller.N; i++) {
            int flag = 0;
            for (int j = 0; j < Controller.N; j++) {
                if (BattleGround.battleGround[i][j] == atkID){
                    Controller.creatures[BattleGround.battleGround[i][j]].hitPoints -= atk;
                    Creature.atkID = BattleGround.battleGround[i][j];
                    deadX = j;
                    deadY = i;
                    if (Controller.creatures[BattleGround.battleGround[i][j]].hitPoints <= 0){
                        Controller.creatures[BattleGround.battleGround[i][j]].alive = false;

                        deadID = BattleGround.battleGround[i][j];
                        BattleGround.battleGround[i][j] = -1;
                    }
                    flag = 1;
                    break;
                }
            }
            if (flag == 1)
                break;
        }
    }

    public void updateProgressBar(){
        progressBar.setProgress((double)hitPoints / maxHP);
    }
    public boolean isCreatureAlive(){
        return alive;
    }
    public void setCreatureAlive(){
        alive = true;
    }
    public int getCamp(){
        return camp;
    }
}
