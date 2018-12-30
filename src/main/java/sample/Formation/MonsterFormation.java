package sample.Formation;

public class MonsterFormation extends Formation {
    public MonsterFormation(){
        changeSize(Name.HEYI);
    }
    @Override
    public void changeFormation(Name name){
        changeSize(name);
        int start = 10;
        switch (name){
            case HEYI:{
                formation[3][3] = 9;
                for (int i = 6, j = 0; j < name.length - 1; i--, j++)
                    formation[i][j] = start++;
                for (int i = 0, j = 0; j < name.length - 1; i++, j++)
                    formation[i][j] = start++;
                break;
            }
            case YANXING:{
                formation[6][0] = 9;
                for (int i = 5, j = 1; j < name.length; i--, j++)
                    formation[i][j] = start++;
                break;
            }
            case HENGE:{
                formation[1][0] = 9;
                for (int i = 3, j = 0; i < name.width; i += 2)
                    formation[i][j] = start++;
                for (int i = 0, j = 1; i < name.width; i += 2)
                    formation[i][j] = start++;
                break;
            }
            case YULIN:{
                formation[2][0] = 9;
                for (int i = 1, j = 1, k = 0; k < 3; i++, k++)
                    formation[i][j] = start++;
                for (int i = 0, j = 2, k = 0; k < 5; i++, k++)
                    formation[i][j] = start++;
                formation[2][3] = start++;
                break;
            }
            case FANGYUAN:{
                formation[2][0] = 9;
                for (int i = 1, j = 1; i < name.width; i += 2)
                    formation[i][j] = start++;
                for (int i = 0, j = 2; i < name.width; i += 4)
                    formation[i][j] = start++;
                for (int i = 1, j = 3; i < name.width; i += 2)
                    formation[i][j] = start++;
                formation[2][4] = start++;
                break;
            }
            case CHANGSHE:{
                formation[0][0] = 9;
                for (int i = 1; i < name.width; i++){
                    formation[i][0] = start++;
                }
                break;
            }
            case YANYUE:{
                formation[4][0] = 9;
                for (int i = 3, j = 0; j < name.length; i--, j++)
                    formation[i][j] = start++;
                for (int i = 3, j = 1; j < name.length; i--, j++)
                    formation[i][j] = start++;
                for (int i = 5, j = 0; j < name.length; i++, j++)
                    formation[i][j] = start++;
                for (int i = 5, j = 1; j < name.length; i++, j++)
                    formation[i][j] = start++;
                formation[4][1] = start++;
                for (int i = 3, j = 2, k = 0; k < 3; i++, k++)
                    formation[i][j] = start++;
                break;
            }
            case FENGSHI:{
                formation[2][0] = 9;
                for (int i = 1, j = 1; i >= 0; i--, j++)
                    formation[i][j] = start++;
                for (int i = 2, j = 1; j < name.length; j++)
                    formation[i][j] = start++;
                for (int i = 3, j = 1; i < name.width; i++, j++)
                    formation[i][j] = start++;
                break;
            }
        }
    }
}
