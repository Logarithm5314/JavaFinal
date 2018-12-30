package sample.Formation;

public abstract class Formation{
    protected int[][] formation;
    protected Name name;
    public enum Name{
        HEYI(4, 7), YANXING(7, 7), HENGE(2, 7), CHANGSHE(1, 7),
        YULIN(4, 5), FANGYUAN(5, 5), YANYUE(4, 9), FENGSHI(6, 5);

        protected int length, width;

        Name(int length, int width){
            this.length = length;
            this.width = width;
        }
    }
    protected void changeSize(Name name){
        this.name = name;
        formation = new int[name.width][name.length];
        for (int i = 0; i < name.width; i++)
            for (int j = 0; j < name.length; j++)
                formation[i][j] = -1;
    }
    public int getFormation(int i, int j){
        return formation[i][j];
    }
    public int getWidth(){
        return name.width;
    }
    public int getLength(){
        return name.length;
    }
    abstract void changeFormation(Name name);

    /*protected void clear(){
        for (int i = 0; i < name.width; i++)
            for (int j = 0; j < name.length; j++)
                formation[i][j] = -1;
    }*/
}
