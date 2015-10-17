package Tests;

import Game.*;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by raphael on 10/10/2015.
 */
public class CellTests {
    Tray tray;
    int traySize = 4;


    @Test
    public void pawnAllwedHerePicture_1_Test()
    {
        tray = new Tray();
        tray.init(4);

        // ===== PICTURE ONE ========
        // pawn already set
        assertTrue(tray.placePawn(0, 1 ,Color.Black));
        assertTrue(tray.placePawn(0, 2, Color.Black));
        assertTrue(tray.placePawn(0, 3, Color.Black));
        assertTrue(tray.placePawn(1, 3, Color.Black));
        assertTrue(tray.placePawn(2, 1, Color.Black));

        // new Point
        assertTrue(tray.placePawn(2, 0 ,Color.Black));
        assertTrue(tray.placePawn(3, 2, Color.Black));
    }


    @Test
    public void pawnAllwedHerePicture_2_Test()
    {
        tray = new Tray();
        tray.init(4);

        // pawn already set
        assertTrue(tray.placePawn(0, 1 ,Color.Black));
        assertTrue(tray.placePawn(0, 2, Color.Black));
        assertTrue(tray.placePawn(0, 3, Color.Black));
        assertTrue(tray.placePawn(1, 3, Color.Black));
        assertTrue(tray.placePawn(2, 1, Color.Black));
        assertTrue(tray.placePawn(2, 0 ,Color.Black));
        assertTrue(tray.placePawn(3, 2, Color.Black));

        // new Point
        assertFalse(tray.placePawn(2, 2, Color.Black));
    }

    @Test
    public void pawnAllwedHerePicture_3_Test()
    {
        tray = new Tray();
        tray.init(4);

        // pawn already set
        assertTrue(tray.placePawn(0, 1 ,Color.Black));
        assertTrue(tray.placePawn(0, 2, Color.Black));
        assertTrue(tray.placePawn(0, 3 ,Color.Black));
        assertTrue(tray.placePawn(1, 3, Color.Black));
        assertTrue(tray.placePawn(2, 1, Color.Black));
        assertTrue(tray.placePawn(2, 0 ,Color.Black));
        assertTrue(tray.placePawn(3, 2, Color.Black));

        // new Point
        assertTrue(tray.placePawn(3, 1, Color.Black));
    }



    @Test
    public void pawnAllwedHerePicture_4_Test(){
        tray = new Tray();
        tray.init(4);

        // ===== PICTURE FOUR ========
        // pawn already set
        assertTrue(tray.placePawn(0, 1 ,Color.Black));
        assertTrue(tray.placePawn(0, 2, Color.Black));
        assertTrue(tray.placePawn(0, 3 ,Color.Black));
        assertTrue(tray.placePawn(1, 3, Color.Black));
        assertTrue(tray.placePawn(2, 0, Color.Black));
        assertTrue(tray.placePawn(2, 1 ,Color.Black));
        assertTrue(tray.placePawn(3, 2, Color.Black));

        // new Point
        assertTrue(tray.placePawn(3, 3, Color.Black));
    }


    @Test
    public void pawnAllwedHerePicture_5_Test(){
        tray = new Tray();
        tray.init(4);

        // pawn already set
        assertTrue(tray.placePawn(0, 1 ,Color.Black));
        assertTrue(tray.placePawn(0, 2, Color.Black));
        assertTrue(tray.placePawn(0, 3 ,Color.Black));
        assertTrue(tray.placePawn(1, 3, Color.Black));
        assertTrue(tray.placePawn(2, 1, Color.Black));
        assertTrue(tray.placePawn(2, 0 ,Color.Black));
        assertTrue(tray.placePawn(3, 2, Color.Black));
        assertTrue(tray.placePawn(3,3, Color.Black));

        // ===== PICTURE FIVE ========
        // new Point
        assertFalse(tray.placePawn(3, 1, Color.Black));
    }


    @Test
    public void pawnAllwedHerePicture_6_Test() {
        tray = new Tray();
        tray.init(4);

        // pawn already set
        assertTrue(tray.placePawn(0, 1 ,Color.Black));
        assertTrue(tray.placePawn(0, 2, Color.Black));
        assertTrue(tray.placePawn(0, 3 ,Color.Black));
        assertTrue(tray.placePawn(1, 3, Color.Black));
        assertTrue(tray.placePawn(2, 0, Color.Black));
        assertTrue(tray.placePawn(2, 1, Color.Black));
        assertTrue(tray.placePawn(3, 2, Color.Black));

        // new Point
        assertTrue(tray.placePawn(3, 3, Color.White));
        assertTrue(tray.placePawn(2, 3, Color.White));
        assertTrue(tray.placePawn(2, 2, Color.White));
        assertTrue(tray.placePawn(1, 2, Color.White));
        assertTrue(tray.placePawn(1, 0, Color.White));
        assertTrue(tray.placePawn(0, 0, Color.White));
    }


    @Test
    public void pawnAllowHere_TakenTest() {

        tray = new Tray();
        tray.init(traySize);
        Cell cell = tray.getCell(0, 0);

        // No pawn in cell
        assertFalse("no pawn in the cell", cell.isTaken());

        // place a pawn in cell
        assertTrue(cell.placePawn(Color.Black));
        // Test cell is Taken and not allowed
        assertTrue("there is pawn on the cell", cell.isTaken());
        assertFalse("There is already a pawn in the cell",
                tray.getCell(0, 0).pawnAllowedHere(Color.Black));
    }

    @Test public void pawnAllowHere_LockedByPawnTest()
    {
        tray = new Tray();
        tray.init(traySize);

        Cell cell = tray.getCell(0, 0);

        // No pawn in cell
        assertFalse("no pawn in the cell, and no bridge", cell.isLocked());

        // place a pawn in cell
        assertTrue(cell.placePawn(Color.Black));

        // test cell locked and pawn not allowed
        assertTrue("there is pawn on the cell, cell must be locked", cell.isLocked());
        assertFalse("Pawn not allowed, there is already one in the cell",
                tray.getCell(0, 0).pawnAllowedHere(Color.Black));
    }

    @Test public void pawnAllowHere_LockedByBridgeTest()
    {
        tray = new Tray();
        tray.init(traySize);

        Cell cell = tray.getCell(0, 1);

        // place a pawn in cell
        assertTrue(tray.placePawn(0,0,Color.Black));
        assertTrue(tray.placePawn(0, 2, Color.Black));

        // No pawn in cell and no bridge
        assertFalse("no pawn in the cell, and no bridge", cell.isLocked());
        assertFalse("no pawn in the cell, and no bridge", cell.isTaken());

        // place bridge
        assertTrue("can place bridge", tray.placeBridge(0, 0, 0, 2));

        // test cell locked and pawn not allowed
        assertTrue("there is a bridge over, cell must be locked", cell.isLocked());
        assertFalse("Pawn not allowed, there is a bridge over it",
                tray.getCell(0, 0).pawnAllowedHere(Color.Black));
    }

    @Test
    public void pawnAllowHere_nextIslandTest()
    {
        tray = new Tray();
        tray.init(10);

        // place a Island on the tray
        assertTrue(tray.placePawn(1, 2, Color.Black));
        assertTrue(tray.placePawn(1, 3, Color.Black));
        assertTrue(tray.placePawn(1, 4, Color.Black));
        assertTrue(tray.placePawn(1, 5, Color.Black));

        // try place a same color pawn next the island
        for (int i = 0 ; i < 4; i++)
        {
            // get pawn
            Cell cell = tray.getCell(1, 2 + i);
            Pawn pawn = cell.getPawn();
            assertTrue("Must have a pawn here", pawn != null);

            // try place pawn next all nearby boxes of all pawn of this Island
            for (Cell cell1 : cell.getNearbyBoxes().values()){
                if (cell1 != null){
                    assertFalse("can't place pawn next island", cell1.placePawn(Color.Black));
                }
            }
        }
    }

    @Test
    public void pawnAllowHereTest()
    {
        tray = new Tray();
        tray.init(traySize);
        Cell cell = tray.getCell(0, 0);

        // No pawn in cell
        assertFalse("pawn can't be null", cell.placePawn(null)); // to keep here !

        // Cell must be free
        assertFalse("no pawn in the cell", cell.isTaken());
        assertFalse("no pawn in the cell, and no bridge", cell.isLocked());

        // place a pawn in cell
        assertTrue(cell.placePawn(Color.Black));
        // tests
        assertTrue("there is pawn on the cell", cell.isTaken());
        assertTrue("there is pawn on the cell", cell.isLocked());
        assertFalse("There is already a pawn in the cell",
                tray.getCell(0, 0).placePawn(Color.Black));
    }

    @Test
    public void placePawnTest()
    {
        tray = new Tray();
        tray.init(traySize);
        Cell cell = tray.getCell(0, 0);

        // No pawn in cell
        assertFalse("pawn can't be null", cell.placePawn(null));
        assertFalse("no pawn in the cell", cell.isTaken());
        assertFalse("no pawn in the cell, and no bridge", cell.isLocked());

        // place a pawn in cell
        assertTrue(cell.placePawn(Color.Black));
        assertTrue("there is pawn on the cell", cell.isTaken());
        assertTrue("there is pawn on the cell", cell.isLocked());
        assertFalse("There is already a pawn in the cell",
                tray.getCell(0, 0).placePawn(Color.Black));
    }


    @Test
    public void isTakenTest(){
        tray = new Tray();
        tray.init(traySize);

        Cell cell = tray.getCell(0, 0);
        assertFalse("No pawn in cell", cell.isTaken());

        cell.placePawn(Color.Black);
        assertTrue("There is a pawn in the cell", cell.isTaken());
    }

    @Test
    public void isLockedTest(){
        tray = new Tray();
        tray.init(traySize);

        Cell cell = tray.getCell(0, 0);
        assertFalse("No pawn in cell and no bridge", cell.isLocked());

        cell.placePawn(Color.Black);
        assertTrue("There is a pawn in the cell", cell.isLocked());
    }

    @Test
    public void isNearbyOfTest()
    {
        tray = new Tray();
        tray.init(traySize);

        Cell cell = tray.getCell(0, 0);

        assertFalse(cell.isNearbyOf(null));

        Cell cell1 = tray.getCell(0, 1);
        assertTrue(cell.isNearbyOf(cell1));

        cell1 = tray.getCell(0, 5);
        assertFalse(cell.isNearbyOf(cell1));

    }

    @Test
    public void inDiagonalTest()
    {
        tray = new Tray();
        tray.init(10);

        assertFalse("can't be diagonal with null cell", tray.getCell(0, 0).inDiagonal(null));

        for (int y = 0; y < tray.getSize() ; y++){
            for (int x = 0 ; x < tray.getSize(); x++ ){

                Cell mainCell = tray.getCell(y, x);
                for (int line = 0 ; line < tray.getSize() ; line++){
                    for (int column = 0 ; column < tray.getSize(); column++){
                        if ((line == y-1 && column == x-1)
                                || (line == y-1 && column == x+1 )
                                || (line == y+1 && column == x-1)
                                || (line == y+1 && column == x+1 ))
                        {
                            assertTrue( "("+y+";"+x+") et ("+line+";"+column+") are diagonals" ,
                                    tray.getCell(y, x).inDiagonal(tray.getCell(line, column)));
                        } else {
                            assertFalse("("+y+";"+x+") et ("+line+";"+column+") are not diagonals" ,
                                    tray.getCell(y, x).inDiagonal(tray.getCell(line, column)));
                        }
                    }
                }
            }
        }
        Cell cell = tray.getCell(1, 1);
        assertTrue(cell.inDiagonal(tray.getCell(0, 0)));
        assertTrue(cell.inDiagonal(tray.getCell(0, 2)));
        assertTrue(cell.inDiagonal(tray.getCell(2, 2)));
        assertTrue(cell.inDiagonal(tray.getCell(2, 0)));


    }

    @Test
    public void pawnAllowHere_touchingDiagonalSandBar()
    {
        // like PICTURE 2 but without paw in box (0,1)
        Tray tray = new Tray();
        tray.init(4);

        assertTrue(tray.placePawn(0, 2, Color.Black));
        assertTrue(tray.placePawn(0, 3, Color.Black));
        assertTrue(tray.placePawn(1, 3, Color.Black));

        assertTrue(tray.placePawn(2, 0, Color.Black));
        assertTrue(tray.placePawn(2, 1, Color.Black));
        assertTrue(tray.placePawn(3, 2, Color.Black));

        assertFalse("pawn here forbidden !", tray.getCell(2, 2).pawnAllowedHere(Color.Black));
        assertFalse("pawn here forbidden !", tray.placePawn(2, 2, Color.Black));

    }

    @Test
    public void getnearbyBoxes_Test()
    {
        tray = new Tray();
        tray.init(4);

        Cell cell = tray.getCell(1, 1);

        assertTrue(cell.getNearbyBox(Direction.NORTH_WEST).equals(tray.getCell(0, 0)));
        assertTrue(cell.getNearbyBox(Direction.NORTH).equals(tray.getCell(0, 1)));
        assertTrue(cell.getNearbyBox(Direction.NORTH_EST).equals(tray.getCell(0, 2)));
        assertTrue(cell.getNearbyBox(Direction.WEST).equals(tray.getCell(1, 0)));
        assertTrue(cell.getNearbyBox(Direction.EST).equals(tray.getCell(1, 2)));
        assertTrue(cell.getNearbyBox(Direction.SOUTH_WEST).equals(tray.getCell(2, 0)));
        assertTrue(cell.getNearbyBox(Direction.SOUTH).equals(tray.getCell(2, 1)));
        assertTrue(cell.getNearbyBox(Direction.SOUTH_EST).equals(tray.getCell(2, 2)));

        for (int line = 0 ; line < tray.getSize() ; line++){
            for (int column = 0 ; column < tray.getSize() ; column++){
                assertFalse(tray.getCell(line, column) == null);
            }
        }
    }

    @Test
    public void allowedHere_makeIsland_touchingSandBarInDiagonal()
    {
        Tray tray = new Tray();
        tray.init(4);
        //first sandbar
        assertTrue(tray.placePawn(0, 2, Color.Black));
        assertTrue(tray.placePawn(0, 3, Color.Black));
        assertTrue(tray.placePawn(1, 3, Color.Black));
        // second sandbar touching the first one in diagonal
        assertTrue(tray.placePawn(2, 0, Color.Black));
        assertTrue(tray.placePawn(2, 1, Color.Black));
        assertTrue(tray.placePawn(2, 2, Color.Black));


        // all other empty box
        assertFalse(tray.placePawn(0, 1, Color.Black));
        assertFalse(tray.placePawn(1, 0, Color.Black));
        assertFalse(tray.placePawn(1, 1, Color.Black));
        assertFalse(tray.placePawn(1, 2, Color.Black));
        assertFalse(tray.placePawn(2, 3, Color.Black));
        assertFalse(tray.placePawn(3, 0, Color.Black));
        assertFalse(tray.placePawn(3, 1, Color.Black));
        assertFalse(tray.placePawn(3, 2, Color.Black));
        // the last ones are allowed
        assertTrue(tray.placePawn(0, 0, Color.Black));
        assertTrue(tray.placePawn(3, 3, Color.Black));

    }

}
