package sample;

import java.io.*;
import java.util.ArrayList;

public class Replay {
    private class Data{
        private int round, id;
        private int toX, toY;
        private int atkID;
        Data(){
            round = -1;
            id = -1;
            toX = -1;
            toY = -1;
            atkID = -1;
        }
    }
    private int formationCB, formationM;
    private ArrayList<Data> data;

    public Replay(){
        formationCB = -1;
        formationM = -1;
        data = new ArrayList<>();
    }
    public Replay(int formationCB, int formationM){
        this.formationCB = formationCB;
        this.formationM = formationM;
        data = new ArrayList<>();
    }

    public void updateFormation(int formationCB, int formationM){
        this.formationCB = formationCB;
        this.formationM = formationM;
    }

    public void updateReplayMove(int round, int id, int toX, int toY){
        Data newData = new Data();
        newData.round = round;
        newData.id = id;
        newData.toX = toX;
        newData.toY = toY;
        System.out.println("round: " + newData.round);
        data.add(newData);
    }
    public void updateReplayAtk(int round, int id, int atkID){
        System.out.println("round: " + round);
        Data newData;
        if (round == 0)
            newData = data.get(0);
        else
            newData = data.get(round);
        if (id != newData.id)
            System.out.println("---------------Replay update error(id not equal)---------------");
        newData.atkID = atkID;
        data.set(round, newData);
    }
    public void saveReplay(File file){
        StringBuilder content = new StringBuilder();
        content.append(formationCB);
        content.append(" ");
        content.append(formationM);
        content.append("\r\n");
        for (Data data : data){
            content.append(data.round);
            content.append(" ");
            content.append(data.id);
            content.append(" move to ");
            content.append(data.toX);
            content.append(",");
            content.append(data.toY);
            content.append(" atk ");
            content.append(data.atkID);
            content.append("\r\n");
        }

        BufferedWriter bw = null;
        try {
            if (!file.exists())
                file.createNewFile();
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            bw = new BufferedWriter(fw);
            bw.write(content.toString());
            bw.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void loadReplay(File file){
        try {
            if (file.isFile() && file.exists()) {
                InputStreamReader read = new InputStreamReader(new FileInputStream(file));
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineText = null;
                lineText = bufferedReader.readLine();
                String formation = lineText.substring(0, 1);
                formationCB = Integer.parseInt(formation);
                formation = lineText.substring(2, 3);
                formationM = Integer.parseInt(formation);
                while ((lineText = bufferedReader.readLine()) != null) {
                    String round = null, id = null, toX = null, toY = null, atkID = null;
                    int count = 0;
                    for (int i = 0; i < lineText.length(); i++){
                        if (lineText.charAt(i) >= '0' && lineText.charAt(i) <= '9'){
                            if (count == 4) {
                                if (lineText.charAt(i - 1) == '-')
                                    atkID = "-1";
                                else
                                    atkID = lineText.substring(i);
                                break;
                            }
                            else {
                                for (int j = i; j < lineText.length(); j++) {
                                    if (!(lineText.charAt(j) >= '0' && lineText.charAt(j) <= '9')) {
                                        switch (count) {
                                            case 0:
                                                round = lineText.substring(i, j);
                                                break;
                                            case 1:
                                                id = lineText.substring(i, j);
                                                break;
                                            case 2:
                                                toX = lineText.substring(i, j);
                                                break;
                                            case 3:
                                                toY = lineText.substring(i, j);
                                                break;
                                            case 4:
                                                atkID = lineText.substring(i, j);
                                                break;
                                        }
                                        count++;
                                        i = j;
                                        break;
                                    }
                                }
                            }
                        }
                    }

                    /*System.out.println("round: " + round);
                    System.out.println("id: " + id);
                    System.out.println("toX: " + toX);
                    System.out.println("toY: " + toY);
                    System.out.println("atkID: " + atkID);*/
                    Data newData = new Data();
                    newData.round = Integer.parseInt(round);
                    newData.id = Integer.parseInt(id);
                    newData.toX = Integer.parseInt(toX);
                    newData.toY = Integer.parseInt(toY);
                    newData.atkID = Integer.parseInt(atkID);
                    data.add(newData);
                }
                read.close();
            } else {
                System.out.println("No file error");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public int getFormationCB(){
        return formationCB;
    }
    public int getFormationM(){
        return formationM;
    }
    public int replayMoveX(int round){
        return data.get(round).toX;
    }
    public int replayMoveY(int round){
        return data.get(round).toY;
    }
    public int replayAtkID(int round){
        return data.get(round).atkID;
    }
}
