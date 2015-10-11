package Tests;

import Game.Board;
import Game.Box;
import Game.Color;
import org.junit.Test;

import java.util.function.BinaryOperator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by raphael on 10/10/2015.
 */
public class BoxTests {
    Board board;
    int boardSize = 4;


    @Test
    public void pawnAllwedHerePicture_1_Test()
    {
        board = new Board(4);
        // ===== PICTURE ONE ========
        board.initBoard();
        // pawn already set
        assertTrue(board.placePawn(0, 1 ,Color.Black));
        assertTrue(board.placePawn(0, 2, Color.Black));
        assertTrue(board.placePawn(0, 3 ,Color.Black));
        assertTrue(board.placePawn(1, 3, Color.Black));
        assertTrue(board.placePawn(2, 1, Color.Black));

        // new Point
        assertTrue(board.placePawn(2, 0 ,Color.Black));
        assertTrue(board.placePawn(3, 2, Color.Black));
    }

    /*
    @Test
    public void pawnAllwedHerePicture_2_Test()
    {
        board = new Board(4);
        board.initBoard();

        // pawn already set
        assertTrue(board.placePawn(0, 1 ,Color.Black));
        assertTrue(board.placePawn(0, 2, Color.Black));
        assertTrue(board.placePawn(0, 3, Color.Black));
        assertTrue(board.placePawn(1, 3, Color.Black));
        assertTrue(board.placePawn(2, 1, Color.Black));
        assertTrue(board.placePawn(2, 0 ,Color.Black));
        assertTrue(board.placePawn(3, 2, Color.Black));

        // new Point
        assertFalse(board.placePawn(2, 2, Color.Black));
    }

    @Test
    public void pawnAllwedHerePicture_3_Test()
    {
        board = new Board(4);
        board.initBoard();

        // pawn already set
        assertTrue(board.placePawn(0, 1 ,Color.Black));
        assertTrue(board.placePawn(0, 2, Color.Black));
        assertTrue(board.placePawn(0, 3 ,Color.Black));
        assertTrue(board.placePawn(1, 3, Color.Black));
        assertTrue(board.placePawn(2, 1, Color.Black));
        assertTrue(board.placePawn(2, 0 ,Color.Black));
        assertTrue(board.placePawn(3, 2, Color.Black));

        // new Point
        assertTrue(board.placePawn(3, 1, Color.Black));
    }



    @Test
    public void pawnAllwedHerePicture_4_Test(){
        board = new Board(4);

        // ===== PICTURE FOUR ========
        board.initBoard();
        // pawn already set
        assertTrue(board.placePawn(0, 1 ,Color.Black));
        assertTrue(board.placePawn(0, 2, Color.Black));
        assertTrue(board.placePawn(0, 3 ,Color.Black));
        assertTrue(board.placePawn(1, 3, Color.Black));
        assertTrue(board.placePawn(2, 0, Color.Black));
        assertTrue(board.placePawn(2, 1 ,Color.Black));
        assertTrue(board.placePawn(3, 2, Color.Black));

        // new Point
        assertTrue(board.placePawn(3, 3, Color.Black));

    }

    @Test
    public void pawnAllwedHerePicture_5_Test(){
        board = new Board(4);
        board.initBoard();

        // pawn already set
        assertTrue(board.placePawn(0, 1 ,Color.Black));
        assertTrue(board.placePawn(0, 2, Color.Black));
        assertTrue(board.placePawn(0, 3 ,Color.Black));
        assertTrue(board.placePawn(1, 3, Color.Black));
        assertTrue(board.placePawn(2, 1, Color.Black));
        assertTrue(board.placePawn(2, 0 ,Color.Black));
        assertTrue(board.placePawn(3, 2, Color.Black));
        assertTrue(board.placePawn(3,3, Color.Black));

        // ===== PICTURE FIVE ========
        // new Point
        assertFalse(board.placePawn(3, 1, Color.Black));
    }


    @Test
    public void pawnAllwedHerePicture_6_Test() {
        board = new Board(4);
        board.initBoard();

        // pawn already set
        assertTrue(board.placePawn(0, 1 ,Color.Black));
        assertTrue(board.placePawn(0, 2, Color.Black));
        assertTrue(board.placePawn(0, 3 ,Color.Black));
        assertTrue(board.placePawn(1, 3, Color.Black));
        assertTrue(board.placePawn(2, 0, Color.Black));
        assertTrue(board.placePawn(2, 1, Color.Black));
        assertTrue(board.placePawn(3, 2, Color.Black));

        // new Point
        assertTrue(board.placePawn(3, 3, Color.White));
        assertTrue(board.placePawn(2, 3, Color.White));
        assertTrue(board.placePawn(2, 2, Color.White));
        assertTrue(board.placePawn(1, 2, Color.White));
        assertTrue(board.placePawn(1, 0, Color.White));
        assertTrue(board.placePawn(0, 0, Color.White));

    }
*/

    @Test
    public void pawnAllowHere_TakenTest() {

        board = new Board(boardSize);
        board.initBoard();
        Box box = board.getBox(0, 0);

        // No pawn in box
        assertFalse("no pawn in the box", box.isTaken());

        // place a pawn in box
        assertTrue(box.placePawn(Color.Black));
        assertTrue("there is pawn on the box", box.isTaken());
        assertFalse("There is already a pawn in the box",
                board.getBox(0, 0).pawnAllowedHere(Color.Black));
    }

    @Test public void pawnAllowHere_LockedTest()
    {

        // NEED TEST WITH BRIDGE

        board = new Board(boardSize);
        board.initBoard();
        Box box = board.getBox(0,0);

        // No pawn in box
        assertFalse("no pawn in the box, and no bridge", box.isLocked());

        // place a pawn in box
        assertTrue(box.placePawn(Color.Black));
        assertTrue("there is pawn on the box", box.isLocked());
        assertFalse("There is already a pawn in the box",
                board.getBox(0, 0).pawnAllowedHere(Color.Black));
    }

    @Test
    public void pawnAllowHere_nextIslandTest()
    {
        /*
        board = new Board(10);
        board.initBoard();
        Box box1 = board.getBox(2,0);
        Box box2 = board.getBox(2,0);
        Box box3= board.getBox(2,0);
        Box box4 = board.getBox(2,0);
        Box box5 = board.getBox(2,0);

        // place a pawn in box
        box1.placePawn(Color.Black);
        box2.placePawn(Color.Black);
        box3.placePawn(Color.Black);
        box4.placePawn(Color.Black);

        assertFalse("There is already a pawn in the box",
                board.getBox(0, 0).placePawn(Color.Black));
                */
    }

    @Test
    public void pawnAllowHereTest()
    {
        board = new Board(boardSize);
        board.initBoard();
        Box box = board.getBox(0,0);

        // No pawn in box
        assertFalse("pawn can't be null", box.placePawn(null)); // to keep here !


        assertFalse("no pawn in the box", box.isTaken());
        assertFalse("no pawn in the box, and no bridge", box.isLocked());

        // place a pawn in box
        assertTrue(box.placePawn(Color.Black));
        assertTrue("there is pawn on the box", box.isTaken());
        assertTrue("there is pawn on the box", box.isLocked());
        assertFalse("There is already a pawn in the box",
                board.getBox(0, 0).placePawn(Color.Black));


        // CASE FORBIDEN


        // box next under a bridge

        // box next an island of the same color

        // box making an island biggerThan a 4 boxes
    }

    @Test
    public void placePawnTest()
    {
        board = new Board(boardSize);
        board.initBoard();
        Box box = board.getBox(0,0);

        // No pawn in box
        assertFalse("pawn can't be null", box.placePawn(null));
        assertFalse("no pawn in the box", box.isTaken());
        assertFalse("no pawn in the box, and no bridge", box.isLocked());

        // place a pawn in box
        assertTrue(box.placePawn(Color.Black));
        assertTrue("there is pawn on the box", box.isTaken());
        assertTrue("there is pawn on the box", box.isLocked());
        assertFalse("There is already a pawn in the box",
                board.getBox(0, 0).placePawn(Color.Black));

    }



    @Test
    public void isTakenTest(){
        board = new Board(boardSize);
        board.initBoard();

        Box box = board.getBox(0,0);
        assertFalse("No pawn in box", box.isTaken());

        box.placePawn(Color.Black);
        assertTrue("There is a pawn in the box", box.isTaken());
    }

    @Test
    public void isLockedTest(){
        board = new Board(boardSize);
        board.initBoard();

        Box box = board.getBox(0,0);
        assertFalse("No pawn in box and no bridge", box.isLocked());

        box.placePawn(Color.Black);
        assertTrue("There is a pawn in the box", box.isLocked());
    }

    @Test
    public void isNearbyOfTest()
    {
        board = new Board(boardSize);
        board.initBoard();

        Box box = board.getBox(0,0);

        assertFalse(box.isNearbyOf(null));

        Box box1 = board.getBox(0,1);
        assertTrue(box.isNearbyOf(box1));

        box1 = board.getBox(0,5);
        assertFalse(box.isNearbyOf(box1));

    }

}
