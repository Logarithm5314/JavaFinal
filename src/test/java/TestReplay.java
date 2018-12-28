import org.junit.Assert;
import sample.Replay;

import org.junit.Test;

import java.io.File;
import java.net.URL;

public class TestReplay {

    @Test
    public void testReplay(){
        URL url = TestReplay.class.getClassLoader().getResource("loadTest.txt");
        //System.out.println(url);
        Replay replay = new Replay();
        replay.loadReplay(new File(url.getFile()));
        Assert.assertEquals(3, replay.getFormationCB() );
        Assert.assertEquals(2, replay.getFormationM());

        Assert.assertEquals(1, replay.replayMoveX(0));
        Assert.assertEquals(14, replay.replayMoveY(0));
        Assert.assertEquals(9, replay.replayAtkID(0));

        Assert.assertEquals(4, replay.replayMoveX(1));
        Assert.assertEquals(4, replay.replayMoveY(1));
        Assert.assertEquals(8, replay.replayAtkID(1));

        Assert.assertEquals(4, replay.replayMoveX(2));
        Assert.assertEquals(5, replay.replayMoveY(2));
        Assert.assertEquals(9, replay.replayAtkID(2));
    }

}
