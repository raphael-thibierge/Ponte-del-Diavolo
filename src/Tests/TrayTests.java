package Tests;

import Game.Tray;
import Game.Box;
import Game.Color;
import Game.Direction;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by raphael on 10/10/2015.
 */
public class TrayTests {

    private Tray tray;
    private int size = 5;

    @Test
    public void initBoardTest()
    {
        tray = new Tray(size);
        tray.initBoard();

        for (int line = 0 ; line < size ;  line++){
            for (int column = 0 ; column < size ; column++)
            {
                assertFalse(tray.getBox(line, column) == null);
            }
        }
    }


    @Test
    public void initNearbyBoxes_InCornerTest()
    {
        // A ECRIRE
        tray = new Tray(size);
        initBoardTest();


        // top left corner
        Box box = tray.getBox(0,0);
        assertTrue(box.getNearbyBox(Direction.EST).equals(tray.getBox(0, 1)));
        assertTrue(box.getNearbyBox(Direction.SOUTH).equals(tray.getBox(1, 0)));
        assertTrue(box.getNearbyBox(Direction.SOUTH_EST).equals(tray.getBox(1, 1)));

        assertTrue("sould be null", box.getNearbyBox(Direction.NORTH) == null);
        assertTrue("sould be null", box.getNearbyBox(Direction.WEST) == null);
        assertTrue("sould be null", box.getNearbyBox(Direction.NORTH_WEST) == null);
        assertTrue("sould be null", box.getNearbyBox(Direction.NORTH_EST) == null);
        assertTrue("sould be null", box.getNearbyBox(Direction.SOUTH_WEST) == null);

        // top right corner
        box = tray.getBox(0,size-1);
        assertTrue(box.getNearbyBox(Direction.WEST).equals(tray.getBox(0, size-2)));
        assertTrue(box.getNearbyBox(Direction.SOUTH).equals(tray.getBox(1, size-1)));
        assertTrue(box.getNearbyBox(Direction.SOUTH_WEST).equals(tray.getBox(1, size-2)));
        assertTrue("sould be null", box.getNearbyBox(Direction.NORTH) == null);
        assertTrue("sould be null", box.getNearbyBox(Direction.NORTH_EST) == null);
        assertTrue("sould be null", box.getNearbyBox(Direction.NORTH_WEST) == null);
        assertTrue("sould be null", box.getNearbyBox(Direction.SOUTH_EST) == null);
        assertTrue("sould be null", box.getNearbyBox(Direction.EST) == null);

        // down left corner
        box = tray.getBox(size-1, 0);
        assertTrue(box.getNearbyBox(Direction.EST).equals(tray.getBox(size-1, 1)));
        assertTrue(box.getNearbyBox(Direction.NORTH).equals(tray.getBox(size-2, 0)));
        assertTrue(box.getNearbyBox(Direction.NORTH_EST).equals(tray.getBox(size-2, 1)));
        assertTrue("sould be null", box.getNearbyBox(Direction.SOUTH) == null);
        assertTrue("sould be null", box.getNearbyBox(Direction.SOUTH_WEST) == null);
        assertTrue("sould be null", box.getNearbyBox(Direction.SOUTH_EST) == null);
        assertTrue("sould be null", box.getNearbyBox(Direction.NORTH_WEST) == null);
        assertTrue("sould be null", box.getNearbyBox(Direction.WEST) == null);

        // top right corner
        box = tray.getBox(size-1, size-1);
        assertTrue(box.getNearbyBox(Direction.WEST).equals(tray.getBox(size-1, size-2)));
        assertTrue(box.getNearbyBox(Direction.NORTH).equals(tray.getBox(size-2, size-1)));
        assertTrue(box.getNearbyBox(Direction.NORTH_WEST).equals(tray.getBox(size-2, size-2)));
        assertTrue("sould be null", box.getNearbyBox(Direction.SOUTH) == null);
        assertTrue("sould be null", box.getNearbyBox(Direction.SOUTH_WEST) == null);
        assertTrue("sould be null", box.getNearbyBox(Direction.SOUTH_EST) == null);
        assertTrue("sould be null", box.getNearbyBox(Direction.NORTH_EST) == null);
        assertTrue("sould be null", box.getNearbyBox(Direction.EST) == null);
    }

    @Test
    public void getBoxTest()
    {
        tray = new Tray(size);

        // boxes not initialised
        assertTrue(tray.getBox(0, 0) == null);

        tray.initBoard();

        // box exist
        assertTrue(tray.getBox(0, 0) != null);
        assertTrue(tray.getBox(size-1, size-1) != null);

        // box out of table
        assertTrue(tray.getBox(size+1, size) == null);
        assertTrue(tray.getBox(size, size+1) == null);
        assertTrue(tray.getBox(size+1, size+1) == null);

        // negative indice
        assertTrue(tray.getBox(-1, 0) == null);
        assertTrue(tray.getBox(0, -1) == null);

    }



    @Test
    public void pawnBetween2BoxesTest()
    {
        tray = new Tray(5);

        tray.initBoard();

        // main pawn
        assertTrue(tray.placePawn(2, 2, Color.Black));
        Box box1 = tray.getBox(2,2);
        //other one
        assertTrue(tray.placePawn(0,0, Color.Black));
        assertTrue(tray.placePawn(0,2, Color.Black));
        assertTrue(tray.placePawn(0,4, Color.Black));
        assertTrue(tray.placePawn(2,0, Color.Black));
        assertTrue(tray.placePawn(2,4, Color.Black));
        assertTrue(tray.placePawn(3,0, Color.Black));
        assertTrue(tray.placePawn(3,4, Color.Black));
        assertTrue(tray.placePawn(4, 1, Color.Black));
        assertTrue(tray.placePawn(4,3, Color.Black));

        // no pawn
        assertFalse("no pawn between", tray.pawnBetween2Boxes(box1, tray.getBox(0, 0)));
        assertFalse("no pawn between", tray.pawnBetween2Boxes(box1, tray.getBox(0, 2)));
        assertFalse("no pawn between", tray.pawnBetween2Boxes(box1, tray.getBox(0, 4)));
        assertFalse("no pawn between", tray.pawnBetween2Boxes(box1, tray.getBox(2, 0)));
        assertFalse("no pawn between", tray.pawnBetween2Boxes(box1, tray.getBox(2, 4)));
        assertFalse("no pawn between", tray.pawnBetween2Boxes(box1, tray.getBox(3, 0)));
        assertFalse("no pawn between", tray.pawnBetween2Boxes(box1, tray.getBox(3, 4)));
        assertFalse("no pawn between", tray.pawnBetween2Boxes(box1, tray.getBox(4, 1)));
        assertFalse("no pawn between", tray.pawnBetween2Boxes(box1, tray.getBox(4, 3)));

        assertTrue(tray.placePawn(1, 2, Color.White));
        assertTrue(tray.placePawn(1, 3, Color.White));
        assertTrue(tray.placePawn(2, 3, Color.White));
        assertTrue(tray.placePawn(3, 1, Color.White));

        // no pawn
        assertFalse("no pawn between", tray.pawnBetween2Boxes(box1, tray.getBox(0,0)));
        assertTrue(" pawn between", tray.pawnBetween2Boxes(box1, tray.getBox(0, 2)));
        assertTrue("no pawn between", tray.pawnBetween2Boxes(box1, tray.getBox(0, 4)));
        assertFalse("no pawn between", tray.pawnBetween2Boxes(box1, tray.getBox(2, 0)));
        assertTrue("no pawn between", tray.pawnBetween2Boxes(box1, tray.getBox(2, 4)));
        assertTrue("no pawn between", tray.pawnBetween2Boxes(box1, tray.getBox(3, 0)));
        assertTrue("no pawn between", tray.pawnBetween2Boxes(box1, tray.getBox(3, 4)));
        assertTrue("no pawn between", tray.pawnBetween2Boxes(box1, tray.getBox(4, 1)));
        assertFalse("no pawn between", tray.pawnBetween2Boxes(box1, tray.getBox(4, 3)));
    }

    @Test
    public void canBridgeTest()
    {
        // TODO
    }

    @Test
    public void placeBridge_Picture1()
    {
        tray = new Tray(5); tray.initBoard();

        // pawn
        assertTrue(tray.placePawn(0,0,Color.Black));
        assertTrue(tray.placePawn(0,1,Color.Black));
        assertTrue(tray.placePawn(0,2,Color.Black));
        assertTrue(tray.placePawn(1,2,Color.Black));
        assertTrue(tray.placePawn(2,0,Color.Black));
        assertTrue(tray.placePawn(3,1,Color.Black));
        assertTrue(tray.placePawn(2,4,Color.Black));

        // bridge
        assertTrue(tray.placeBridge(0,0,2,0));
        assertTrue(tray.placeBridge(1,2,3,1));
        assertTrue(tray.placeBridge(0,2,2,4));

    }

    @Test
    public void placeBridge_Picture2()
    {
        tray = new Tray(5); tray.initBoard();

        // pawn
        assertTrue(tray.placePawn(0, 0, Color.Black));
        assertTrue(tray.placePawn(0, 1, Color.Black));
        assertTrue(tray.placePawn(0, 2, Color.Black));
        assertTrue(tray.placePawn(1, 2, Color.Black));
        assertTrue(tray.placePawn(2, 0, Color.Black));
        assertTrue(tray.placePawn(3, 1, Color.Black));
        assertTrue(tray.placePawn(2, 4, Color.Black));
        assertTrue(tray.placePawn(1, 3, Color.White));

        // bridge
        assertTrue(tray.placeBridge(0,0,2,0));
        assertTrue(tray.placeBridge(1,2,3,1));
        assertFalse(tray.placeBridge(0, 2, 2, 4));
        assertFalse(tray.placeBridge(0, 1, 2, 0));
    }

    @Test
    public void placeBridge_Picture3()
    {
        tray = new Tray(5); tray.initBoard();

        // pawn
        assertTrue(tray.placePawn(0,0,Color.Black));
        assertTrue(tray.placePawn(0,1,Color.Black));
        assertTrue(tray.placePawn(0,2,Color.Black));
        assertTrue(tray.placePawn(1,2,Color.Black));
        assertTrue(tray.placePawn(2,0,Color.Black));
        assertTrue(tray.placePawn(3,1,Color.Black));
        assertTrue(tray.placePawn(2, 4, Color.Black));

        // bridge
        assertTrue(tray.placeBridge(0,0,2,0));
        assertTrue(tray.placeBridge(1, 2, 3, 1));
        assertTrue(tray.placeBridge(0, 2, 2, 4));

        // new Pawn
        assertFalse(tray.placePawn(1,0, Color.White));
        assertFalse(tray.placePawn(2,1, Color.White));
        assertFalse(tray.placePawn(2,2, Color.White));
        assertFalse(tray.placePawn(1,3, Color.White));
    }

}
