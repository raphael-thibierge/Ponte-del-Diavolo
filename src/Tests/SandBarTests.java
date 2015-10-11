package Tests;

import Game.*;
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
        assertFalse(sandBar.addPawn(null));
        // not same color
        assertFalse(sandBar.addPawn(new Pawn(Color.White, new Box(0, 0))));

        assertTrue(sandBar.addPawn(new Pawn(Color.Black, new Box(0, 0))));
        assertTrue(sandBar.addPawn(new Pawn(Color.Black, new Box(0, 0))));
        assertTrue(sandBar.addPawn(new Pawn(Color.Black, new Box(0, 0))));
        assertFalse("to much pawn in the andbar", sandBar.addPawn(new Pawn(Color.Black, new Box(0, 0))));
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
        Board board = new Board(4);
        board.initBoard();

        assertTrue(board.placePawn(0, 1, Color.Black));
        assertTrue("should have a sandbar", board.getSandBarInBox(0, 1) != null);

        assertTrue(board.placePawn(0, 2, Color.Black));
        assertTrue("should be the same sandbar",
                board.getSandBarInBox(0, 1).equals(board.getSandBarInBox(0, 2)));

        assertTrue(board.placePawn(0, 3, Color.Black));
        assertTrue("should be the same sandbar",
                board.getSandBarInBox(0, 1).equals(board.getSandBarInBox(0, 3)));

        assertTrue(board.placePawn(1, 3, Color.Black));
        assertTrue("should be the same sandbar",
                board.getSandBarInBox(0, 1).equals(board.getSandBarInBox(1, 3)));

        assertTrue("should be an Island", board.getSandBarInBox(0, 1).isIsland());
    }

    @Test
    public void updateSandBar_merge2sandBar()
    {
        Board board = new Board(4);
        board.initBoard();

        assertTrue(board.placePawn(2, 0, Color.Black));
        assertTrue("should have a sandbar", board.getSandBarInBox(2, 0) != null);

        assertTrue(board.placePawn(2, 1, Color.Black));
        assertTrue("should be the same sandbar",
                board.getSandBarInBox(2, 0).equals(board.getSandBarInBox(2, 1)));

        // not orhogonal to other sandbar
        assertTrue(board.placePawn(3, 2, Color.Black));
        assertFalse("should not be the same sandbar",
                board.getSandBarInBox(2, 1).equals(board.getSandBarInBox(3, 2)));

        // merging the two sand bar
        assertTrue(board.placePawn(2, 2, Color.Black));

        assertTrue("should be the same sandbar than left one",
                board.getSandBarInBox(2, 2).equals(board.getSandBarInBox(2, 1)));
        assertTrue("should be the same sandbar than down one",
                board.getSandBarInBox(2, 2).equals(board.getSandBarInBox(3, 2)));

        assertTrue("should be an Island", board.getSandBarInBox(2, 2).isIsland());
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
        Board board = new Board(4);
        board.initBoard();

        board.placePawn(0, 0, Color.Black);
        SandBar sandBar = board.getSandBarInBox(0,0);
        SandBar sandBar1 = board.getSandBarInBox(0,0);

        assertFalse("can't merge same sandBar", sandBar.mergeSandBar(sandBar1));
    }

    @Test
    public void mergeSandBar_3sandBar()
    {
        Board board = new Board(4);
        board.initBoard();

        assertTrue(board.placePawn(3, 0, Color.Black));
        assertTrue(board.placePawn(3, 2, Color.Black));
        assertTrue(board.placePawn(2, 1, Color.Black));
        assertTrue(board.placePawn(3, 1, Color.Black));

        assertTrue(board.getSandBarInBox(3,1).equals(board.getSandBarInBox(3, 0)));
        assertTrue(board.getSandBarInBox(3,1).equals(board.getSandBarInBox(3, 2)));
        assertTrue(board.getSandBarInBox(3,1).equals(board.getSandBarInBox(2,1)));
    }

}
