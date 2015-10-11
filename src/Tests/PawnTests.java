package Tests;

import Game.Box;
import Game.Color;
import Game.Pawn;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

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
    public void updateSandBar()
    {

    }

}
