package Tests;

import Game.Cell;
import Game.Color;
import Game.Direction;
import Game.Tray;
import Model.GameModel;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
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
        tray = new Tray();
        tray.init(size);

        for (int line = 0 ; line < size ;  line++){
            for (int column = 0 ; column < size ; column++)
            {
                assertFalse(tray.getCell(line, column) == null);
                assertTrue(tray.getCell(line, column).getNearbyBoxes().size() == 8);
            }
        }
    }


    @Test
    public void initNearbyBoxes_InCornerTest()
    {
        // A ECRIRE
        tray = new Tray();
        initBoardTest();

        tray.init(5);

        // top left corner
        Cell cell = tray.getCell(0, 0);
        assertTrue(cell.getNearbyBox(Direction.EST).equals(tray.getCell(0, 1)));
        assertTrue(cell.getNearbyBox(Direction.SOUTH).equals(tray.getCell(1, 0)));
        assertTrue(cell.getNearbyBox(Direction.SOUTH_EST).equals(tray.getCell(1, 1)));

        assertTrue("sould be null", cell.getNearbyBox(Direction.NORTH) == null);
        assertTrue("sould be null", cell.getNearbyBox(Direction.WEST) == null);
        assertTrue("sould be null", cell.getNearbyBox(Direction.NORTH_WEST) == null);
        assertTrue("sould be null", cell.getNearbyBox(Direction.NORTH_EST) == null);
        assertTrue("sould be null", cell.getNearbyBox(Direction.SOUTH_WEST) == null);

        // top right corner
        cell = tray.getCell(0, size - 1);
        assertTrue(cell.getNearbyBox(Direction.WEST).equals(tray.getCell(0, size - 2)));
        assertTrue(cell.getNearbyBox(Direction.SOUTH).equals(tray.getCell(1, size - 1)));
        assertTrue(cell.getNearbyBox(Direction.SOUTH_WEST).equals(tray.getCell(1, size - 2)));
        assertTrue("sould be null", cell.getNearbyBox(Direction.NORTH) == null);
        assertTrue("sould be null", cell.getNearbyBox(Direction.NORTH_EST) == null);
        assertTrue("sould be null", cell.getNearbyBox(Direction.NORTH_WEST) == null);
        assertTrue("sould be null", cell.getNearbyBox(Direction.SOUTH_EST) == null);
        assertTrue("sould be null", cell.getNearbyBox(Direction.EST) == null);

        // down left corner
        cell = tray.getCell(size - 1, 0);
        assertTrue(cell.getNearbyBox(Direction.EST).equals(tray.getCell(size - 1, 1)));
        assertTrue(cell.getNearbyBox(Direction.NORTH).equals(tray.getCell(size - 2, 0)));
        assertTrue(cell.getNearbyBox(Direction.NORTH_EST).equals(tray.getCell(size - 2, 1)));
        assertTrue("sould be null", cell.getNearbyBox(Direction.SOUTH) == null);
        assertTrue("sould be null", cell.getNearbyBox(Direction.SOUTH_WEST) == null);
        assertTrue("sould be null", cell.getNearbyBox(Direction.SOUTH_EST) == null);
        assertTrue("sould be null", cell.getNearbyBox(Direction.NORTH_WEST) == null);
        assertTrue("sould be null", cell.getNearbyBox(Direction.WEST) == null);

        // top right corner
        cell = tray.getCell(size - 1, size - 1);
        assertTrue(cell.getNearbyBox(Direction.WEST).equals(tray.getCell(size - 1, size - 2)));
        assertTrue(cell.getNearbyBox(Direction.NORTH).equals(tray.getCell(size - 2, size - 1)));
        assertTrue(cell.getNearbyBox(Direction.NORTH_WEST).equals(tray.getCell(size - 2, size - 2)));
        assertTrue("sould be null", cell.getNearbyBox(Direction.SOUTH) == null);
        assertTrue("sould be null", cell.getNearbyBox(Direction.SOUTH_WEST) == null);
        assertTrue("sould be null", cell.getNearbyBox(Direction.SOUTH_EST) == null);
        assertTrue("sould be null", cell.getNearbyBox(Direction.NORTH_EST) == null);
        assertTrue("sould be null", cell.getNearbyBox(Direction.EST) == null);

    }

    @Test
    public void initNearbyBoxes_middleCaseTest(){
        Tray tray = new Tray();
        tray.init(3);

        Cell middleCell = tray.getCell(1, 1);
        assertTrue(middleCell.getNearbyBox(Direction.NORTH_WEST).equals(tray.getCell(0, 0)));
        assertTrue(middleCell.getNearbyBox(Direction.NORTH).equals(tray.getCell(0, 1)));
        assertTrue(middleCell.getNearbyBox(Direction.NORTH_EST).equals(tray.getCell(0, 2)));
        assertTrue(middleCell.getNearbyBox(Direction.WEST).equals(tray.getCell(1, 0)));
        assertTrue(middleCell.getNearbyBox(Direction.EST).equals(tray.getCell(1, 2)));
        assertTrue(middleCell.getNearbyBox(Direction.SOUTH_WEST).equals(tray.getCell(2, 0)));
        assertTrue(middleCell.getNearbyBox(Direction.SOUTH).equals(tray.getCell(2, 1)));
        assertTrue(middleCell.getNearbyBox(Direction.SOUTH_EST).equals(tray.getCell(2, 2)));


    }

    @Test
    public void getBoxTest()
    {
        tray = new Tray();

        // boxes not initialised
        assertTrue(tray.getCell(0, 0) == null);
        tray.init(size);


        // box exist
        assertTrue(tray.getCell(0, 0) != null);
        assertTrue(tray.getCell(size - 1, size - 1) != null);

        // box out of table
        assertTrue(tray.getCell(size + 1, size) == null);
        assertTrue(tray.getCell(size, size + 1) == null);
        assertTrue(tray.getCell(size + 1, size + 1) == null);

        // negative indice
        assertTrue(tray.getCell(-1, 0) == null);
        assertTrue(tray.getCell(0, -1) == null);

    }



    @Test
    public void pawnBetween2BoxesTest()
    {
        tray = new Tray();
        tray.init(5);

        // main pawn
        assertTrue(tray.placePawn(2, 2, Color.Black));
        Cell cell1 = tray.getCell(2, 2);
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
        assertFalse("no pawn between", tray.pawnBetween2Boxes(cell1, tray.getCell(0, 0)));
        assertFalse("no pawn between", tray.pawnBetween2Boxes(cell1, tray.getCell(0, 2)));
        assertFalse("no pawn between", tray.pawnBetween2Boxes(cell1, tray.getCell(0, 4)));
        assertFalse("no pawn between", tray.pawnBetween2Boxes(cell1, tray.getCell(2, 0)));
        assertFalse("no pawn between", tray.pawnBetween2Boxes(cell1, tray.getCell(2, 4)));
        assertFalse("no pawn between", tray.pawnBetween2Boxes(cell1, tray.getCell(3, 0)));
        assertFalse("no pawn between", tray.pawnBetween2Boxes(cell1, tray.getCell(3, 4)));
        assertFalse("no pawn between", tray.pawnBetween2Boxes(cell1, tray.getCell(4, 1)));
        assertFalse("no pawn between", tray.pawnBetween2Boxes(cell1, tray.getCell(4, 3)));

        assertTrue(tray.placePawn(1, 2, Color.White));
        assertTrue(tray.placePawn(1, 3, Color.White));
        assertTrue(tray.placePawn(2, 3, Color.White));
        assertTrue(tray.placePawn(3, 1, Color.White));

        // no pawn
        assertFalse("no pawn between", tray.pawnBetween2Boxes(cell1, tray.getCell(0, 0)));
        assertTrue(" pawn between", tray.pawnBetween2Boxes(cell1, tray.getCell(0, 2)));
        assertTrue("no pawn between", tray.pawnBetween2Boxes(cell1, tray.getCell(0, 4)));
        assertFalse("no pawn between", tray.pawnBetween2Boxes(cell1, tray.getCell(2, 0)));
        assertTrue("no pawn between", tray.pawnBetween2Boxes(cell1, tray.getCell(2, 4)));
        assertTrue("no pawn between", tray.pawnBetween2Boxes(cell1, tray.getCell(3, 0)));
        assertTrue("no pawn between", tray.pawnBetween2Boxes(cell1, tray.getCell(3, 4)));
        assertTrue("no pawn between", tray.pawnBetween2Boxes(cell1, tray.getCell(4, 1)));
        assertFalse("no pawn between", tray.pawnBetween2Boxes(cell1, tray.getCell(4, 3)));
    }

    @Test
    public void canBridge_DiagonalTest()
    {
        tray = new Tray();
        tray.init(5);

        assertTrue(tray.placePawn(2,2, Color.Black));

        // place pawn
        assertTrue(tray.placePawn(0,0, Color.Black));
        assertTrue(tray.placePawn(0,1, Color.Black));
        assertTrue(tray.placePawn(1,0, Color.Black));

        assertTrue(tray.placePawn(3,0, Color.Black));
        assertTrue(tray.placePawn(4,0, Color.Black));
        assertTrue(tray.placePawn(4,1, Color.Black));

        assertTrue(tray.placePawn(4,3, Color.Black));
        assertTrue(tray.placePawn(4,4, Color.Black));
        assertTrue(tray.placePawn(3,4, Color.Black));

        assertTrue(tray.placePawn(0,3, Color.Black));
        assertTrue(tray.placePawn(0,4, Color.Black));
        assertTrue(tray.placePawn(1,4, Color.Black));

        assertTrue("can bridge !", tray.canBridge(2, 2, 0, 0));
        assertTrue("can bridge !", tray.canBridge(2, 2, 0, 1));
        assertTrue("can bridge !", tray.canBridge(2, 2, 1, 0));

        assertTrue("can bridge !", tray.canBridge(2, 2, 3, 0));
        assertTrue("can bridge !", tray.canBridge(2, 2, 4, 0));
        assertTrue("can bridge !", tray.canBridge(2, 2, 4, 1));

        assertTrue("can bridge !", tray.canBridge(2, 2, 4, 3));
        assertTrue("can bridge !", tray.canBridge(2, 2, 4, 4));
        assertTrue("can bridge !", tray.canBridge(2, 2, 3, 4));

        assertTrue("can bridge !", tray.canBridge(2, 2, 0, 3));
        assertTrue("can bridge !", tray.canBridge(2, 2, 0, 4));
        assertTrue("can bridge !", tray.canBridge(2 ,2 ,1 ,4));
    }

    @Test
    public void canBridge_OrthogonalTest()
    {
        tray = new Tray();
        tray.init(5);

        assertTrue(tray.placePawn(2,2, Color.Black));

        // place pawn
        assertTrue(tray.placePawn(0,2, Color.Black));
        assertTrue(tray.placePawn(2,0, Color.Black));
        assertTrue(tray.placePawn(2,4, Color.Black));
        assertTrue(tray.placePawn(4,2, Color.Black));

        assertTrue("can bridge !", tray.canBridge(2, 2, 0, 2));
        assertTrue("can bridge !", tray.canBridge(2, 2, 2, 0));
        assertTrue("can bridge !", tray.canBridge(2, 2, 2, 4));
        assertTrue("can bridge !", tray.canBridge(2, 2, 4, 2));
    }

    @Test
    public void placeBridge_Picture1()
    {
        tray = new Tray();
        tray.init(5);

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
        tray = new Tray();
        tray.init(5);

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
        tray = new Tray();
        tray.init(5);

        // pawn
        assertTrue(tray.placePawn(0,0,Color.Black));
        assertTrue(tray.placePawn(0, 1, Color.Black));
        assertTrue(tray.placePawn(0,2,Color.Black));
        assertTrue(tray.placePawn(1,2,Color.Black));
        assertTrue(tray.placePawn(2,0,Color.Black));
        assertTrue(tray.placePawn(3,1,Color.Black));
        assertTrue(tray.placePawn(2, 4, Color.Black));

        // bridge
        assertTrue(tray.placeBridge(0, 0, 2, 0));
        assertTrue(tray.placeBridge(1, 2, 3, 1));
        assertTrue(tray.placeBridge(0, 2, 2, 4));

        // new Pawn
        assertFalse(tray.placePawn(1, 0, Color.White));
        assertFalse(tray.placePawn(2, 1, Color.White));
        assertFalse(tray.placePawn(2, 2, Color.White));
        assertFalse(tray.placePawn(1, 3, Color.White));
    }

    @Test
    public void isInitialized_Test()
    {
        tray = new Tray();
        assertFalse("Tray is not already initialized", tray.isInitialised());
        tray.init(5);
        assertTrue("Tray is initialized", tray.isInitialised());
    }

    @Test
    public void pawnBetween2Boxes_Test()
    {
        tray = new Tray();
        tray.init(5);

        assertTrue(tray.placePawn(0, 0, Color.Black));
        assertTrue(tray.placePawn(0,3, Color.Black));
        Cell cell1 = tray.getCell(0, 0);
        Cell cell2 = tray.getCell(0, 3);
        assertFalse(tray.pawnBetween2Boxes(cell1, cell2));

    }

    @Test
     public void cancelPawn_Test(){
        tray = new Tray();
        tray.init(2);

        assertTrue(tray.placePawn(0,0, Color.Black));
        tray.cancelPawn(0,0);
        assertTrue(tray.getCell(0,0).getPawn() == null);
        assertFalse(tray.getCell(0, 0).isTaken());
        assertFalse(tray.getCell(0, 0).isLocked());

    }

    @Test
    public void cancelPawn_RemovedFromSandBar_Test(){
        tray = new Tray();
        tray.init(2);

        assertTrue(tray.placePawn(0,0, Color.Black));
        assertTrue(tray.placePawn(0,1, Color.Black));

        assertTrue("should be in sandBar",
                tray.getSandBarInBox(0,0).getPawnList().contains(tray.getCell(0, 1).getPawn()));

        tray.cancelPawn(0,1);
        assertFalse("should not be in sandBar",
                tray.getSandBarInBox(0, 0).getPawnList().contains(tray.getCell(0, 1).getPawn()));
        assertTrue("should have only one pawn", tray.getSandBarInBox(0,0).getSize() == 1);


        tray.cancelPawn(0,0);

    }

/*
    @Test
    public void cancelBridge(){
        tray = new Tray();
        tray.init(3);

        assertTrue(tray.placePawn(0,0, Color.Black));
        assertTrue(tray.placePawn(2,2, Color.Black));
        assertTrue(tray.placeBridge(2,2, 0,0));

        tray.cancelBridge(0,0, 2, 2);
        assertFalse(tray.getCell(0,0).getPawn().hasBridge());
        assertFalse(tray.getCell(2,2).getPawn().hasBridge());

        assertTrue(tray.getCell(0,0).getPawn().getBridge() == null);
        assertTrue(tray.getCell(2,2).getPawn().getBridge() == null);
        assertFalse(tray.getCell(1,1).isLocked());
    }*/

}
