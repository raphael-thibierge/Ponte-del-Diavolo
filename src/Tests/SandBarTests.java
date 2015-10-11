package Tests;

import Game.*;
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

    
}
