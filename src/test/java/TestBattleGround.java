import org.junit.Assert;
import org.junit.Test;

import sample.BattleGround.BattleGround;
import sample.Controller;
import sample.Formation.CalabashFormation;
import sample.Formation.Formation;
import sample.Formation.MonsterFormation;

public class TestBattleGround {
    @Test
    public void testBattleGround(){
        BattleGround battleGround = new BattleGround();
        CalabashFormation CBformaiton = new CalabashFormation();
        MonsterFormation Mformation = new MonsterFormation();
        for (int f1 = 0; f1 < 8; f1++){
            for (int f2 = 0; f2 < 8; f2++){
                switch (f1){
                    case 0: CBformaiton.changeFormation(Formation.Name.HEYI); break;
                    case 1: CBformaiton.changeFormation(Formation.Name.YANXING); break;
                    case 2: CBformaiton.changeFormation(Formation.Name.HENGE); break;
                    case 3: CBformaiton.changeFormation(Formation.Name.CHANGSHE); break;
                    case 4: CBformaiton.changeFormation(Formation.Name.YULIN); break;
                    case 5: CBformaiton.changeFormation(Formation.Name.FANGYUAN); break;
                    case 6: CBformaiton.changeFormation(Formation.Name.YANYUE); break;
                    case 7: CBformaiton.changeFormation(Formation.Name.FENGSHI); break;
                }
                switch (f2){
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
                int combatNum = battleGround.combatNumber();
                boolean[] check = new boolean[combatNum];
                for (int i = 0; i < combatNum; i++){
                    check[i] = false;
                }
                int sum = 0;
                for (int i = 0; i < Controller.N; i++){
                    for (int j = 0; j < Controller.N; j++){
                        if (BattleGround.battleGround[i][j] != -1){
                            sum++;
                            check[BattleGround.battleGround[i][j]] = true;
                        }
                    }
                }

                Assert.assertEquals(combatNum, sum);
                //System.out.println(f1 + " " + f2);
                //BattleGround.print();
                for (int i = 0; i < combatNum; i++){
                    Assert.assertTrue(check[i]);
                }
            }
        }
    }
}
