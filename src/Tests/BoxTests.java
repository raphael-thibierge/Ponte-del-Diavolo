package Tests;

import Game.*;
import Model.GameModel;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by raphael on 10/10/2015.
 */
public class BoxTests {
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
        Box box = tray.getBox(0, 0);

        // No pawn in box
        assertFalse("no pawn in the box", box.isTaken());

        // place a pawn in box
        assertTrue(box.placePawn(Color.Black));
        // Test box is Taken and not allowed
        assertTrue("there is pawn on the box", box.isTaken());
        assertFalse("There is already a pawn in the box",
                tray.getBox(0, 0).pawnAllowedHere(Color.Black));
    }

    @Test public void pawnAllowHere_LockedByPawnTest()
    {
        tray = new Tray();
        tray.init(traySize);

        Box box = tray.getBox(0,0);

        // No pawn in box
        assertFalse("no pawn in the box, and no bridge", box.isLocked());

        // place a pawn in box
        assertTrue(box.placePawn(Color.Black));

        // test box locked and pawn not allowed
        assertTrue("there is pawn on the box, box must be locked", box.isLocked());
        assertFalse("Pawn not allowed, there is already one in the box",
                tray.getBox(0, 0).pawnAllowedHere(Color.Black));
    }

    @Test public void pawnAllowHere_LockedByBridgeTest()
    {
        tray = new Tray();
        tray.init(traySize);

        Box box = tray.getBox(0,1);

        // place a pawn in box
        assertTrue(tray.placePawn(0,0,Color.Black));
        assertTrue(tray.placePawn(0, 2, Color.Black));

        // No pawn in box and no bridge
        assertFalse("no pawn in the box, and no bridge", box.isLocked());
        assertFalse("no pawn in the box, and no bridge", box.isTaken());

        // place bridge
        assertTrue("can place bridge", tray.placeBridge(0, 0, 0, 2));

        // test box locked and pawn not allowed
        assertTrue("there is a bridge over, box must be locked", box.isLocked());
        assertFalse("Pawn not allowed, there is a bridge over it",
                tray.getBox(0, 0).pawnAllowedHere(Color.Black));
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
            Box box = tray.getBox(1, 2+i);
            Pawn pawn = box.getPawn();
            assertTrue("Must have a pawn here", pawn != null);

            // try place pawn next all nearby boxes of all pawn of this Island
            for (Box box1 : box.getNearbyBoxes().values()){
                if (box1 != null){
                    assertFalse("can't place pawn next island", box1.placePawn(Color.Black));
                }
            }
        }
    }

    @Test
    public void pawnAllowHereTest()
    {
        tray = new Tray();
        tray.init(traySize);
        Box box = tray.getBox(0,0);

        // No pawn in box
        assertFalse("pawn can't be null", box.placePawn(null)); // to keep here !

        // Box must be free
        assertFalse("no pawn in the box", box.isTaken());
        assertFalse("no pawn in the box, and no bridge", box.isLocked());

        // place a pawn in box
        assertTrue(box.placePawn(Color.Black));
        // tests
        assertTrue("there is pawn on the box", box.isTaken());
        assertTrue("there is pawn on the box", box.isLocked());
        assertFalse("There is already a pawn in the box",
                tray.getBox(0, 0).placePawn(Color.Black));
    }

    @Test
    public void placePawnTest()
    {
        tray = new Tray();
        tray.init(traySize);
        Box box = tray.getBox(0,0);

        // No pawn in box
        assertFalse("pawn can't be null", box.placePawn(null));
        assertFalse("no pawn in the box", box.isTaken());
        assertFalse("no pawn in the box, and no bridge", box.isLocked());

        // place a pawn in box
        assertTrue(box.placePawn(Color.Black));
        assertTrue("there is pawn on the box", box.isTaken());
        assertTrue("there is pawn on the box", box.isLocked());
        assertFalse("There is already a pawn in the box",
                tray.getBox(0, 0).placePawn(Color.Black));
    }


    @Test
    public void isTakenTest(){
        tray = new Tray();
        tray.init(traySize);

        Box box = tray.getBox(0,0);
        assertFalse("No pawn in box", box.isTaken());

        box.placePawn(Color.Black);
        assertTrue("There is a pawn in the box", box.isTaken());
    }

    @Test
    public void isLockedTest(){
        tray = new Tray();
        tray.init(traySize);

        Box box = tray.getBox(0,0);
        assertFalse("No pawn in box and no bridge", box.isLocked());

        box.placePawn(Color.Black);
        assertTrue("There is a pawn in the box", box.isLocked());
    }

    @Test
    public void isNearbyOfTest()
    {
        tray = new Tray();
        tray.init(traySize);

        Box box = tray.getBox(0,0);

        assertFalse(box.isNearbyOf(null));

        Box box1 = tray.getBox(0,1);
        assertTrue(box.isNearbyOf(box1));

        box1 = tray.getBox(0,5);
        assertFalse(box.isNearbyOf(box1));

    }

    @Test
    public void inDiagonalTest()
    {
        tray = new Tray();
        tray.init(10);

        assertFalse("can't be diagonal with null box", tray.getBox(0, 0).inDiagonal(null));

        for (int y = 0; y < tray.getSize() ; y++){
            for (int x = 0 ; x < tray.getSize(); x++ ){

                Box mainBox = tray.getBox(y,x);
                for (int line = 0 ; line < tray.getSize() ; line++){
                    for (int column = 0 ; column < tray.getSize(); column++){
                        if ((line == y-1 && column == x-1)
                                || (line == y-1 && column == x+1 )
                                || (line == y+1 && column == x-1)
                                || (line == y+1 && column == x+1 ))
                        {
                            assertTrue( "("+y+";"+x+") et ("+line+";"+column+") are diagonals" ,
                                    tray.getBox(y, x).inDiagonal(tray.getBox(line, column)));
                        } else {
                            assertFalse("("+y+";"+x+") et ("+line+";"+column+") are not diagonals" ,
                                    tray.getBox(y, x).inDiagonal(tray.getBox(line, column)));
                        }
                    }
                }
            }
        }
        Box box = tray.getBox(1,1);
        assertTrue(box.inDiagonal(tray.getBox(0,0)));
        assertTrue(box.inDiagonal(tray.getBox(0, 2)));
        assertTrue(box.inDiagonal(tray.getBox(2,2)));
        assertTrue(box.inDiagonal(tray.getBox(2, 0)));


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

        assertFalse("pawn here forbidden !", tray.getBox(2, 2).pawnAllowedHere(Color.Black));
        assertFalse("pawn here forbidden !", tray.placePawn(2, 2, Color.Black));

    }

    @Test
    public void getnearbyBoxes_Test()
    {
        tray = new Tray();
        tray.init(4);

        Box box = tray.getBox(1,1);

        assertTrue(box.getNearbyBox(Direction.NORTH_WEST).equals(tray.getBox(0, 0)));
        assertTrue(box.getNearbyBox(Direction.NORTH).equals(tray.getBox(0, 1)));
        assertTrue(box.getNearbyBox(Direction.NORTH_EST).equals(tray.getBox(0, 2)));
        assertTrue(box.getNearbyBox(Direction.WEST).equals(tray.getBox(1, 0)));
        assertTrue(box.getNearbyBox(Direction.EST).equals(tray.getBox(1, 2)));
        assertTrue(box.getNearbyBox(Direction.SOUTH_WEST).equals(tray.getBox(2, 0)));
        assertTrue(box.getNearbyBox(Direction.SOUTH).equals(tray.getBox(2, 1)));
        assertTrue(box.getNearbyBox(Direction.SOUTH_EST).equals(tray.getBox(2, 2)));

        for (int line = 0 ; line < tray.getSize() ; line++){
            for (int column = 0 ; column < tray.getSize() ; column++){
                assertFalse(tray.getBox(line, column) == null);
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
