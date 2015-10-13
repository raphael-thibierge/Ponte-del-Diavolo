package Tests;

import Game.*;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Rule;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertFalse;


import org.junit.rules.ExpectedException;

/**
 * Created by raphael on 10/10/2015.
 */
public class SandBarTests {
    SandBar sandBar;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void createSandBar_Exception()
    {
        exception.expect(NullPointerException.class);
        SandBar sandBar = new SandBar(null);
    }

    @Test
    public void addPawn()
    {
        sandBar = new SandBar(new Pawn(Color.Black, new Box(0, 0)));
        // null pawn
        assertFalse("can't add a null pawn in sandbar",sandBar.addPawn(null));

        // not same color
        assertFalse("can't add a pawn with a different color than sandbar's one",
                sandBar.addPawn(new Pawn(Color.White, new Box(0, 0))));

        // add normal pawn
        assertTrue(sandBar.addPawn(new Pawn(Color.Black, new Box(0, 0))));
        assertTrue(sandBar.addPawn(new Pawn(Color.Black, new Box(0, 0))));
        assertTrue(sandBar.addPawn(new Pawn(Color.Black, new Box(0, 0))));

        assertFalse("to much pawn in the sandbar", sandBar.addPawn(new Pawn(Color.Black, new Box(0, 0))));
    }

    @Test
    public void isIslandTest()
    {
        sandBar = new SandBar(new Pawn(Color.Black, new Box(0, 0)));
        assertFalse("Only one pawn", sandBar.isIsland());
        sandBar.addPawn(new Pawn(Color.Black, new Box(0, 0)));
        assertFalse("Only two pawn", sandBar.isIsland());
        sandBar.addPawn(new Pawn(Color.Black, new Box(0, 0)));
        assertFalse("Only three pawn", sandBar.isIsland());
        sandBar.addPawn(new Pawn(Color.Black, new Box(0, 0)));
        assertTrue("There is fourth  pawn", sandBar.isIsland());
    }

    @Test
    public void updateSandBar_ConstructNormalIsland()
    {
        Tray tray = new Tray();
        tray.init(5);

        assertTrue(tray.placePawn(0, 1, Color.Black));
        assertTrue("should have a sandbar", tray.getSandBarInBox(0, 1) != null);

        assertTrue(tray.placePawn(0, 2, Color.Black));
        assertTrue("should be the same sandbar",
                tray.getSandBarInBox(0, 1).equals(tray.getSandBarInBox(0, 2)));

        assertTrue(tray.placePawn(0, 3, Color.Black));
        assertTrue("should be the same sandbar",
                tray.getSandBarInBox(0, 1).equals(tray.getSandBarInBox(0, 3)));

        assertTrue(tray.placePawn(1, 3, Color.Black));
        assertTrue("should be the same sandbar",
                tray.getSandBarInBox(0, 1).equals(tray.getSandBarInBox(1, 3)));

        assertTrue("should be an Island", tray.getSandBarInBox(0, 1).isIsland());
    }

    @Test
    public void updateSandBar_merge2sandBar()
    {
        Tray tray = new Tray();
        tray.init(5);

        assertTrue(tray.placePawn(2, 0, Color.Black));
        assertTrue("should have a sandbar", tray.getSandBarInBox(2, 0) != null);

        assertTrue(tray.placePawn(2, 1, Color.Black));
        assertTrue("should be the same sandbar",
                tray.getSandBarInBox(2, 0).equals(tray.getSandBarInBox(2, 1)));

        // not orhogonal to other sandbar
        assertTrue(tray.placePawn(3, 2, Color.Black));
        assertFalse("should not be the same sandbar",
                tray.getSandBarInBox(2, 1).equals(tray.getSandBarInBox(3, 2)));

        // merging the two sand bar
        assertTrue(tray.placePawn(2, 2, Color.Black));

        assertTrue("should be the same sandbar than left one",
                tray.getSandBarInBox(2, 2).equals(tray.getSandBarInBox(2, 1)));
        assertTrue("should be the same sandbar than down one",
                tray.getSandBarInBox(2, 2).equals(tray.getSandBarInBox(3, 2)));

        assertTrue("should be an Island", tray.getSandBarInBox(2, 2).isIsland());
    }

    @Test
    public void mergeSandBar_ColorTest(){
        sandBar = new SandBar(new Pawn(Color.Black, new Box(0,0)));
        SandBar sandBar1 = new SandBar(new Pawn(Color.White, new Box(0,1)));
        SandBar sandBar2 = new SandBar(new Pawn(Color.Black, new Box(1,0)));

        assertFalse("not same color", sandBar.mergeSandBar(sandBar1));
        assertTrue("same color", sandBar.mergeSandBar(sandBar2));
    }

    @Test
    public void mergeSandBar_SizeTest()
    {
        sandBar = new SandBar(new Pawn(Color.Black, new Box(0,0)));
        sandBar.addPawn(new Pawn(Color.Black, new Box(0,0)));

        SandBar sandBar1 = new SandBar(new Pawn(Color.Black, new Box(0,1)));
        sandBar1.addPawn(new Pawn(Color.Black, new Box(0,0)));
        sandBar1.addPawn(new Pawn(Color.Black, new Box(0, 0)));

        assertFalse(" 2 pawn + 3 pawn > 4", sandBar.mergeSandBar(sandBar1));

        SandBar sandBar2 = new SandBar(new Pawn(Color.Black, new Box(1,0)));
        assertTrue(" 3 pawn + 1 pawn = 4", sandBar1.mergeSandBar(sandBar2));

        sandBar2.addPawn(new Pawn(Color.Black, new Box(0, 0)));
        assertTrue(" 2 pawn + 2 pawn = 4", sandBar.mergeSandBar(sandBar2));

    }

    @Test
    public void mergeSandBar_nullSandbar()
    {
        sandBar = new SandBar(new Pawn(Color.Black, new Box(1,1)));
        assertFalse("can't merge null sandbar", sandBar.mergeSandBar(null));
    }

    @Test
    public void mergeSandBar_SameSandBar()
    {
        Tray tray = new Tray();
        tray.init(5);

        tray.placePawn(0, 0, Color.Black);
        SandBar sandBar = tray.getSandBarInBox(0,0);
        SandBar sandBar1 = tray.getSandBarInBox(0,0);

        assertFalse("can't merge same sandBar", sandBar.mergeSandBar(sandBar1));
    }

    @Test
    public void mergeSandBar_3sandBar()
    {
        Tray tray = new Tray();
        tray.init(5);

        assertTrue(tray.placePawn(3, 0, Color.Black));
        assertTrue(tray.placePawn(3, 2, Color.Black));
        assertTrue(tray.placePawn(2, 1, Color.Black));
        assertTrue(tray.placePawn(3, 1, Color.Black));

        assertTrue(tray.getSandBarInBox(3,1).equals(tray.getSandBarInBox(3, 0)));
        assertTrue(tray.getSandBarInBox(3,1).equals(tray.getSandBarInBox(3, 2)));
        assertTrue(tray.getSandBarInBox(3, 1).equals(tray.getSandBarInBox(2, 1)));
    }

    @Test
    public void makeIsland_configSquare()
    {
        Tray tray = new Tray();
        tray.init(5);

        assertTrue(tray.placePawn(0, 0, Color.Black)); // top left
        assertTrue(tray.placePawn(0, 1, Color.Black)); // top right
        assertTrue(tray.placePawn(1, 0, Color.Black)); // down left
        assertTrue(tray.placePawn(1, 1, Color.Black)); // down right
        TestCase.assertTrue("must be an island", tray.getSandBarInBox(0, 0).isIsland());

        assertTrue(tray.placePawn(0, 2, Color.White)); // top left
        assertTrue(tray.placePawn(1, 3, Color.White)); // down right
        assertTrue(tray.placePawn(1, 2, Color.White)); // down left
        assertTrue(tray.placePawn(0, 3, Color.White)); // top right
        TestCase.assertTrue("must be an island", tray.getSandBarInBox(0, 2).isIsland());

    }

    @Test
    public void makeIsland_configTetris()
    {
        Tray tray = new Tray();
        tray.init(5);

        assertTrue(tray.placePawn(0, 0, Color.Black)); // top left
        assertTrue(tray.placePawn(0, 2, Color.Black)); // top right
        assertTrue(tray.placePawn(1, 1, Color.Black)); // down middle
        assertTrue(tray.placePawn(0, 1, Color.Black)); // to middle
        TestCase.assertTrue("must be an island", tray.getSandBarInBox(0, 0).isIsland());

        assertTrue(tray.placePawn(1, 2, Color.White)); // down left
        assertTrue(tray.placePawn(1, 4, Color.White)); // down right
        assertTrue(tray.placePawn(1, 3, Color.White)); // down middle
        assertTrue(tray.placePawn(0, 3, Color.White)); // top middle
        TestCase.assertTrue("must be an island", tray.getSandBarInBox(1, 2).isIsland());
    }

}
