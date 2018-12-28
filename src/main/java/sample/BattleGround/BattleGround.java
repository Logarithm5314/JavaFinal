package sample.BattleGround;

import sample.Controller;
import sample.Formation.Formation;

public class BattleGround {
    public static int[][] battleGround;
    public BattleGround(){
        battleGround = new int[Controller.N][Controller.N];
        for (int i = 0; i < Controller.N; i++)
            for (int j = 0; j < Controller.N; j++)
                battleGround[i][j] = -1;
    }

    public void deploy(Formation formation, int camp){
        battleGround[0][14] = 8;
        if(camp == 0){
            for (int i = 0; i < Controller.N; i++)
                for (int j = 0; j < Controller.N; j++)
                    if (battleGround[i][j] <= 7 && battleGround[i][j] > -1)
                        battleGround[i][j] = -1;
            for (int i = 7 - formation.getWidth() / 2, m = 0; m < formation.getWidth(); i++, m++)
                for (int j = 3 - formation.getLength() / 2, n = 0; n < formation.getLength(); j++, n++)
                    battleGround[i][j] = formation.getFormation(m, n);
        }else{
            for (int i = 0; i < Controller.N; i++)
                for (int j = 0; j < Controller.N; j++)
                    if (battleGround[i][j] >= 9)
                        battleGround[i][j] = -1;
            for (int i = 7 - formation.getWidth() / 2, m = 0; m < formation.getWidth(); i++, m++)
                for (int j = 11 - formation.getLength() / 2, n = 0; n < formation.getLength(); j++, n++)
                    battleGround[i][j] = formation.getFormation(m, n);
        }
        int count = 0;
        for (int i = 0; i < Controller.N; i++) {
            for (int j = 0; j < Controller.N; j++) {
                if (battleGround[i][j] == 0) {
                    count++;
                }
            }
        }
        if (count == 0)
            battleGround[14][0] = 0;
    }

    public static void print(){
        System.out.println("-----------------------------------------");
        for (int i = 0; i < Controller.N; i++) {
            for (int j = 0; j < Controller.N; j++) {
                switch (battleGround[i][j]) {
                    case -1:
                        System.out.print("."); break;
                    default:
                        System.out.print(battleGround[i][j]);
                }
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println("-----------------------------------------");
    }

    public static boolean winning(){
        int num1 = 0, num2 = 0;
        for (int i = 0; i < Controller.N; i++) {
            for (int j = 0; j < Controller.N; j++){
                if (battleGround[i][j] != -1) {
                    if (Controller.creatures[battleGround[i][j]].isCreatureAlive()) {
                        if (Controller.creatures[battleGround[i][j]].getCamp() == 0)
                            num1++;
                        else
                            num2++;
                    }
                }
            }
        }
        System.out.println("num1: " + num1 + " num2: " + num2);
        if (num1 == 0 || num2 == 0){
            return true;
        }
        else {
            return false;
        }
    }

    public int combatNumber(){
        int sum = 0;
        for (int i = 0; i < Controller.N; i++){
            for (int j = 0; j < Controller.N; j++){
                if (battleGround[i][j] != -1)
                    sum++;
            }
        }
        return sum;
    }
}
