package Tests;

import Game.*;
import Model.GameMode;
import Model.GameModel;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by raphael on 11/10/2015.
 */


public class BridgeTests {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void createBridgeTest_Exception()
    {
        Bridge bridge;

        exception.expect(NullPointerException.class);
        bridge = new Bridge(null, new Pawn(Color.Black, new Cell(0,0)));

        exception.expect(NullPointerException.class);
        bridge = new Bridge(null, new Pawn(Color.Black, new Cell(0, 2)));

        exception.expect(NullPointerException.class);
        bridge = new Bridge(null, null);

    }

    public void testTwoCaseTrue(int y1, int x1, int y2, int x2){
        assertTrue(Bridge.compatiblePositions(y1, x1, y2, x2));
        assertTrue(Bridge.compatiblePositions(y2, x2, y1, x1));
    }
    public void testTwoCaseFalse(int y1, int x1, int y2, int x2){
        assertFalse(Bridge.compatiblePositions(y1, x1, y2, x2));
        assertFalse(Bridge.compatiblePositions(y2, x2, y1, x1));
    }

    @Test
    public void compatiblePositionsTest()
    {
        for (int y = 0 ; y < 5 ; y++){
            for (int x = 0; x < 5 ; x++){
                if (y == 0 || x == 0 || y == 4 || x == 4)
                    testTwoCaseTrue(2, 2, y, x);
                else
                    testTwoCaseFalse(2,2, y, x);
            }
        }
    }

    @Test
    public void createBridge_SandbarIsLinked()
    {
        Tray tray = new Tray();
        tray.init(5);

        // place pawn
        assertTrue(tray.placePawn(0,0,Color.Black));
        assertTrue(tray.placePawn(0,2,Color.Black));

        // place bridge
        assertTrue(tray.placeBridge(0,0,0,2));

        // test link
        assertTrue("Sansbar must be linked", tray.getSandBarInBox(0,0).isLinked());
        assertTrue("Sansbar must be linked", tray.getSandBarInBox(0,2).isLinked());
    }

    @Test
    public void cancelBridge_SandbarNotLinkedAnyMore(){
        Tray tray = new Tray();
        tray.init(5);

        assertTrue(tray.placePawn(0,0, Color.Black));
        assertTrue(tray.placePawn(0,1, Color.Black));
        assertTrue(tray.placePawn(0,3, Color.Black));
        assertTrue(tray.placePawn(0,4, Color.Black));
        assertTrue(tray.placeBridge(0,1,0,3));

        tray.cancelBridge(tray.getCell(0,1), tray.getCell(0,3));
        assertFalse(tray.getSandBarInBox(0,1).isLinked());
        assertFalse(tray.getSandBarInBox(0,3).isLinked());
    }


    @Test
    public void lockPawnBetween2BoxesTest()
    {
        // TODO but same methods already tested in tray ( pawnBetweenTwoBridge(...) )
    }

    @Test
    public void crossedBridgeTest(){
        Tray tray = new Tray();
        tray.init(3);

        assertTrue(tray.placePawn(0,0, Color.Black));
        assertTrue(tray.placePawn(0,2, Color.Black));
        assertTrue(tray.placePawn(2,0, Color.Black));
        assertTrue(tray.placePawn(2,2, Color.Black));

        assertTrue(tray.placeBridge(0,0,2,2));
        assertFalse(tray.placeBridge(1,0,1,2));
        assertFalse(tray.placeBridge(0,1, 2,1));
        assertFalse(tray.placeBridge(0,2, 2,0));

    }

    @Test
    public void insertPawn_horizontal_underPridge(){
        Tray tray = new Tray();
        tray.init(4);

        assertTrue(tray.placePawn(0,0, Color.Black));
        assertTrue(tray.placePawn(0,2, Color.Black));
        assertTrue(tray.placeBridge(tray.getCell(0,0), tray.getCell(0,2)));

        assertTrue(tray.getCell(0,1).isLocked());

        assertFalse(tray.placePawn(0,1, Color.Black));
        assertFalse(tray.placePawn(0, 1, Color.White));

    }

    @Test
    public void unlockPawnsBetweenBases(){
        Tray tray = new Tray();
        tray.init(3);

        assertTrue(tray.placePawn(0,0, Color.Black));
        assertTrue(tray.placePawn(1, 2, Color.Black));
        assertTrue(tray.placeBridge(0,0,1,2));

        assertTrue(tray.getCell(0, 1).isLocked());
        assertTrue(tray.getCell(1, 1).isLocked());
        assertFalse(tray.getCell(0, 1).pawnAllowedHere(Color.Black));
        assertFalse(tray.getCell(1, 1).pawnAllowedHere(Color.Black));

        tray.cancelBridge(tray.getCell(0, 0), tray.getCell(1, 2));

        assertFalse(tray.getCell(0, 1).isLocked());
        assertFalse(tray.getCell(1, 1).isLocked());
        assertTrue(tray.getCell(0,1).pawnAllowedHere(Color.Black));
        assertTrue(tray.getCell(1,1).pawnAllowedHere(Color.Black));

    }

    @Test
    public void BasesHasNoBridgeAfterRemove(){
        Tray tray = new Tray();
        tray.init(3);

        assertTrue(tray.placePawn(0,0, Color.Black));
        assertTrue(tray.placePawn(1, 2, Color.Black));
        assertTrue(tray.placeBridge(0,0,1,2));
        tray.cancelBridge(tray.getCell(0,0), tray.getCell(1,2));

        assertFalse(tray.getCell(0,0).getPawn().hasBridge());
        assertFalse(tray.getCell(1, 2).getPawn().hasBridge());

    }

    @Test
    public void bridgeSameBases(){
        Tray tray = new Tray();
        tray.init(3);

        assertTrue(tray.placePawn(0,0, Color.Black));
        assertFalse(tray.placeBridge(0,0,0,0));
    }

    @Test
    public void crossBridge_Test(){
        Tray tray = new Tray();
        tray.init(4);

        assertTrue(tray.placePawn(1,0, Color.Black));
        assertTrue(tray.placePawn(2,0, Color.Black));
        assertTrue(tray.placePawn(0,2, Color.Black));
        assertTrue(tray.placePawn(3,2, Color.Black));

        assertTrue(tray.placeBridge(1, 0, 0, 2));
        assertFalse(tray.placeBridge(2, 0, 3, 2));
    }

    @Test
    public void pawnAllowedAfter(){
        
    }

}
