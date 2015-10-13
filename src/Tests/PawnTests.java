package Tests;

import Game.Tray;
import Game.Box;
import Game.Color;
import Game.Pawn;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by raphael on 10/10/2015.
 */
public class PawnTests
{
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void newPawn_ExceptionTest()
    {
        exception.expect(NullPointerException.class);
        Pawn pawn = new Pawn(null, new Box(0, 0));

        exception.expect(NullPointerException.class);
        pawn = new Pawn(Color.Black, null);

        exception.expect(NullPointerException.class);
        pawn = new Pawn(null, null);
    }

    @Test
    public void belongsToIsland()
    {
        // is isIsland already tested in SandBarTest
    }

    @Test
    public void updateSandBar_NotSameColorTest()
    {
        Tray tray = new Tray();
        tray.init(4);

        // place a black pawn
        assertTrue("Can place pawn", tray.placePawn(0,0, Color.Black));

        // place a white pawn next
        assertTrue("Can place pawn", tray.placePawn(0,1,Color.Black));

        // test white and black sand bar
        assertFalse("can't be the same sandbar", tray.getSandBarInBox(0,1).equals(tray.getSandBarInBox(1,1)));

        //

    }

}
