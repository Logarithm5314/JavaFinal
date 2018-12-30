package sample.Formation;

public class CalabashFormation extends Formation {
    public CalabashFormation(){
        changeSize(Name.CHANGSHE);
        //random();
    }
    private boolean contains(int num) {
        for (int i = 0; i < name.width; i++) {
            if (formation[i][0] == num)
                return true;
        }
        return false;
    }
    private void random() {
        for (int i = 0; i < name.width; i++)
            formation[i][0] = -1;
        for (int i = 0; i < name.width; i++) {
            int num = (int) (Math.random() * 7 + 1);
            if (contains(num))
                i--;
            else {
                formation[i][0] = num;
                //brothers[num].rePos(i);
            }
        }
    }
    public void Sort() {
        for (int i = 0; i < name.width - 1; i++){
            for (int j = 0; j < name.width - i - 1; j++){
                if (formation[j][0] > formation[j + 1][0]) {
                    System.out.println();
                    int temp = formation[j][0];
                    formation[j][0] = formation[j + 1][0];
                    formation[j + 1][0] = temp;
                }
            }
        }
    }
    @Override
    public void changeFormation(Name name){
        changeSize(name);
        int start = 1;
        switch (name){
            case HEYI:
                formation[0][3] = 1;
                formation[1][2] = 2;
                formation[2][1] = 3;
                formation[3][0] = 4;
                formation[4][1] = 5;
                formation[5][2] = 6;
                formation[6][3] = 7;
                break;
            case YANXING:
                for (int i = 6, j = 0; j < name.length; i--, j++){
                    formation[i][j] = start++;
                }
                break;
            case HENGE:
                formation[0][1] = 1;
                formation[1][0] = 2;
                formation[2][1] = 3;
                formation[3][0] = 4;
                formation[4][1] = 5;
                formation[5][0] = 6;
                formation[6][1] = 7;
                break;
            case CHANGSHE:
                for (int i = 0 ; i < name.width; i++){
                    formation[i][0] = i + 1;
                }
                break;
            case YULIN:
                formation[2][2] = start++;
                formation[1][1] = start++;
                formation[3][1] = start++;
                for (int i = 0; i < name.width; i++){
                    formation[i][0] = start++;
                }
                formation[4][0] = 0;
                break;
            case FANGYUAN:
                formation[2][4] = start++;
                formation[1][3] = start++;
                formation[3][3] = start++;
                formation[0][2] = start++;
                formation[4][2] = start++;
                formation[1][1] = start++;
                formation[3][1] = start++;
                formation[2][0] = 0;
                break;
            case YANYUE:
                formation[4][3] = start++;
                formation[3][2] = start++;
                formation[4][2] = start++;
                formation[5][2] = start++;
                formation[1][1] = start++;
                formation[2][1] = start++;
                formation[6][1] = start++;
                formation[7][1] = 0;
                break;
            case FENGSHI:
                formation[2][0] = 0;
                formation[2][5] = start++;
                formation[1][4] = start++;
                formation[2][4] = start++;
                formation[3][4] = start++;
                formation[2][3] = start++;
                formation[2][2] = start++;
                formation[2][1] = start++;
                break;
        }
    }
}
