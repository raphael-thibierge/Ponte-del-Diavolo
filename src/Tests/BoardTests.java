package Tests;

import Game.Board;
import Game.Box;
import Game.Color;
import Game.Direction;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by raphael on 10/10/2015.
 */
public class BoardTests {

    private Board board;
    private int size = 5;

    @Test
    public void initBoardTest()
    {
        board = new Board(size);
        board.initBoard();

        for (int line = 0 ; line < size ;  line++){
            for (int column = 0 ; column < size ; column++)
            {
                assertFalse(board.getBox(line, column) == null);
            }
        }
    }


    @Test
    public void initNearbyBoxes_InCornerTest()
    {
        // A ECRIRE
        board = new Board(size);
        initBoardTest();


        // top left corner
        Box box = board.getBox(0,0);
        assertTrue(box.getNearbyBox(Direction.EST).equals(board.getBox(0, 1)));
        assertTrue(box.getNearbyBox(Direction.SOUTH).equals(board.getBox(1, 0)));
        assertTrue(box.getNearbyBox(Direction.SOUTH_EST).equals(board.getBox(1, 1)));

        assertTrue("sould be null", box.getNearbyBox(Direction.NORTH) == null);
        assertTrue("sould be null", box.getNearbyBox(Direction.WEST) == null);
        assertTrue("sould be null", box.getNearbyBox(Direction.NORTH_WEST) == null);
        assertTrue("sould be null", box.getNearbyBox(Direction.NORTH_EST) == null);
        assertTrue("sould be null", box.getNearbyBox(Direction.SOUTH_WEST) == null);

        // top right corner
        box = board.getBox(0,size-1);
        assertTrue(box.getNearbyBox(Direction.WEST).equals(board.getBox(0, size-2)));
        assertTrue(box.getNearbyBox(Direction.SOUTH).equals(board.getBox(1, size-1)));
        assertTrue(box.getNearbyBox(Direction.SOUTH_WEST).equals(board.getBox(1, size-2)));
        assertTrue("sould be null", box.getNearbyBox(Direction.NORTH) == null);
        assertTrue("sould be null", box.getNearbyBox(Direction.NORTH_EST) == null);
        assertTrue("sould be null", box.getNearbyBox(Direction.NORTH_WEST) == null);
        assertTrue("sould be null", box.getNearbyBox(Direction.SOUTH_EST) == null);
        assertTrue("sould be null", box.getNearbyBox(Direction.EST) == null);

        // down left corner
        box = board.getBox(size-1, 0);
        assertTrue(box.getNearbyBox(Direction.EST).equals(board.getBox(size-1, 1)));
        assertTrue(box.getNearbyBox(Direction.NORTH).equals(board.getBox(size-2, 0)));
        assertTrue(box.getNearbyBox(Direction.NORTH_EST).equals(board.getBox(size-2, 1)));
        assertTrue("sould be null", box.getNearbyBox(Direction.SOUTH) == null);
        assertTrue("sould be null", box.getNearbyBox(Direction.SOUTH_WEST) == null);
        assertTrue("sould be null", box.getNearbyBox(Direction.SOUTH_EST) == null);
        assertTrue("sould be null", box.getNearbyBox(Direction.NORTH_WEST) == null);
        assertTrue("sould be null", box.getNearbyBox(Direction.WEST) == null);

        // top right corner
        box = board.getBox(size-1, size-1);
        assertTrue(box.getNearbyBox(Direction.WEST).equals(board.getBox(size-1, size-2)));
        assertTrue(box.getNearbyBox(Direction.NORTH).equals(board.getBox(size-2, size-1)));
        assertTrue(box.getNearbyBox(Direction.NORTH_WEST).equals(board.getBox(size-2, size-2)));
        assertTrue("sould be null", box.getNearbyBox(Direction.SOUTH) == null);
        assertTrue("sould be null", box.getNearbyBox(Direction.SOUTH_WEST) == null);
        assertTrue("sould be null", box.getNearbyBox(Direction.SOUTH_EST) == null);
        assertTrue("sould be null", box.getNearbyBox(Direction.NORTH_EST) == null);
        assertTrue("sould be null", box.getNearbyBox(Direction.EST) == null);
    }

    @Test
    public void getBoxTest()
    {
        board = new Board(size);

        // boxes not initialised
        assertTrue(board.getBox(0, 0) == null);

        board.initBoard();

        // box exist
        assertTrue(board.getBox(0, 0) != null);
        assertTrue(board.getBox(size-1, size-1) != null);

        // box out of table
        assertTrue(board.getBox(size+1, size) == null);
        assertTrue(board.getBox(size, size+1) == null);
        assertTrue(board.getBox(size+1, size+1) == null);

        // negative indice
        assertTrue(board.getBox(-1, 0) == null);
        assertTrue(board.getBox(0, -1) == null);

    }



    @Test
    public void pawnBetween2BoxesTest()
    {
        board = new Board(5);

        board.initBoard();

        // main pawn
        assertTrue(board.placePawn(2, 2, Color.Black));
        Box box1 = board.getBox(2,2);
        //other one
        assertTrue(board.placePawn(0,0, Color.Black));
        assertTrue(board.placePawn(0,2, Color.Black));
        assertTrue(board.placePawn(0,4, Color.Black));
        assertTrue(board.placePawn(2,0, Color.Black));
        assertTrue(board.placePawn(2,4, Color.Black));
        assertTrue(board.placePawn(3,0, Color.Black));
        assertTrue(board.placePawn(3,4, Color.Black));
        assertTrue(board.placePawn(4, 1, Color.Black));
        assertTrue(board.placePawn(4,3, Color.Black));

        // no pawn
        assertFalse("no pawn between", board.pawnBetween2Boxes(box1, board.getBox(0, 0)));
        assertFalse("no pawn between", board.pawnBetween2Boxes(box1, board.getBox(0, 2)));
        assertFalse("no pawn between", board.pawnBetween2Boxes(box1, board.getBox(0, 4)));
        assertFalse("no pawn between", board.pawnBetween2Boxes(box1, board.getBox(2, 0)));
        assertFalse("no pawn between", board.pawnBetween2Boxes(box1, board.getBox(2, 4)));
        assertFalse("no pawn between", board.pawnBetween2Boxes(box1, board.getBox(3, 0)));
        assertFalse("no pawn between", board.pawnBetween2Boxes(box1, board.getBox(3, 4)));
        assertFalse("no pawn between", board.pawnBetween2Boxes(box1, board.getBox(4, 1)));
        assertFalse("no pawn between", board.pawnBetween2Boxes(box1, board.getBox(4, 3)));

        assertTrue(board.placePawn(1, 2, Color.White));
        assertTrue(board.placePawn(1, 3, Color.White));
        assertTrue(board.placePawn(2, 3, Color.White));
        assertTrue(board.placePawn(3, 1, Color.White));

        // no pawn
        assertFalse("no pawn between", board.pawnBetween2Boxes(box1, board.getBox(0,0)));
        assertTrue(" pawn between", board.pawnBetween2Boxes(box1, board.getBox(0, 2)));
        assertTrue("no pawn between", board.pawnBetween2Boxes(box1, board.getBox(0, 4)));
        assertFalse("no pawn between", board.pawnBetween2Boxes(box1, board.getBox(2, 0)));
        assertTrue("no pawn between", board.pawnBetween2Boxes(box1, board.getBox(2, 4)));
        assertTrue("no pawn between", board.pawnBetween2Boxes(box1, board.getBox(3, 0)));
        assertTrue("no pawn between", board.pawnBetween2Boxes(box1, board.getBox(3, 4)));
        assertTrue("no pawn between", board.pawnBetween2Boxes(box1, board.getBox(4, 1)));
        assertFalse("no pawn between", board.pawnBetween2Boxes(box1, board.getBox(4, 3)));
    }

    @Test
    public void canBridgeTest()
    {
        // TODO
    }

    @Test
    public void placeBridge_Picture1()
    {
        board = new Board(5); board.initBoard();

        // pawn
        assertTrue(board.placePawn(0,0,Color.Black));
        assertTrue(board.placePawn(0,1,Color.Black));
        assertTrue(board.placePawn(0,2,Color.Black));
        assertTrue(board.placePawn(1,2,Color.Black));
        assertTrue(board.placePawn(2,0,Color.Black));
        assertTrue(board.placePawn(3,1,Color.Black));
        assertTrue(board.placePawn(2,4,Color.Black));

        // bridge
        assertTrue(board.placeBridge(0,0,2,0));
        assertTrue(board.placeBridge(1,2,3,1));
        assertTrue(board.placeBridge(0,2,2,4));

    }

    @Test
    public void placeBridge_Picture2()
    {
        board = new Board(5); board.initBoard();

        // pawn
        assertTrue(board.placePawn(0, 0, Color.Black));
        assertTrue(board.placePawn(0, 1, Color.Black));
        assertTrue(board.placePawn(0, 2, Color.Black));
        assertTrue(board.placePawn(1, 2, Color.Black));
        assertTrue(board.placePawn(2, 0, Color.Black));
        assertTrue(board.placePawn(3, 1, Color.Black));
        assertTrue(board.placePawn(2, 4, Color.Black));
        assertTrue(board.placePawn(1, 3, Color.White));

        // bridge
        assertTrue(board.placeBridge(0,0,2,0));
        assertTrue(board.placeBridge(1,2,3,1));
        assertFalse(board.placeBridge(0, 2, 2, 4));
        assertFalse(board.placeBridge(0, 1, 2, 0));
    }

    @Test
    public void placeBridge_Picture3()
    {
        board = new Board(5); board.initBoard();

        // pawn
        assertTrue(board.placePawn(0,0,Color.Black));
        assertTrue(board.placePawn(0,1,Color.Black));
        assertTrue(board.placePawn(0,2,Color.Black));
        assertTrue(board.placePawn(1,2,Color.Black));
        assertTrue(board.placePawn(2,0,Color.Black));
        assertTrue(board.placePawn(3,1,Color.Black));
        assertTrue(board.placePawn(2, 4, Color.Black));

        // bridge
        assertTrue(board.placeBridge(0,0,2,0));
        assertTrue(board.placeBridge(1, 2, 3, 1));
        assertTrue(board.placeBridge(0, 2, 2, 4));

        // new Pawn
        assertFalse(board.placePawn(1,0, Color.White));
        assertFalse(board.placePawn(2,1, Color.White));
        assertFalse(board.placePawn(2,2, Color.White));
        assertFalse(board.placePawn(1,3, Color.White));
    }

}
