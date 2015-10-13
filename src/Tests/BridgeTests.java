package Tests;

import Game.*;
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
        bridge = new Bridge(null, new Pawn(Color.Black, new Box(0,0)));

        exception.expect(NullPointerException.class);
        bridge = new Bridge(null, new Pawn(Color.Black, new Box(0, 2)));

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
    public void lockPawnBetween2BoxesTest()
    {
        // TODO but same methods already tested in tray ( pawnBetweenTwoBridge(...) )
    }

}
