package Tests;

import Game.Color;
import Game.Tray;
import Model.GameModel;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;

/**
 * Created by raphael on 12/10/2015.
 */
public class GameModelTests {

    @Test
    public void static_scoreFromNbIsland_Test()
    {
        assertTrue("With 4 linked Island, score must be 10", GameModel.scoreFromNbIsland(4, 0) == 10);
        assertTrue("With 4 Island, score must be 10", GameModel.scoreFromNbIsland(2, 2) == 5);
    }

    @Test
    public void static_scoreFromTrayForColor_PictureScore_Test()
    {
        Tray tray = new Tray();
        tray.init(7);

        // place black pawn
        // first alone island
        assertTrue(tray.placePawn(0, 0, Color.Black));
        assertTrue(tray.placePawn(0, 1, Color.Black));
        assertTrue(tray.placePawn(1, 0, Color.Black));
        assertTrue(tray.placePawn(1, 1, Color.Black));

        // second alone island
        assertTrue(tray.placePawn(4, 5, Color.Black));
        assertTrue(tray.placePawn(5, 5, Color.Black));
        assertTrue(tray.placePawn(6, 5, Color.Black));
        assertTrue(tray.placePawn(5, 6, Color.Black));

        // first linked island
        assertTrue(tray.placePawn(0, 5, Color.Black));
        assertTrue(tray.placePawn(0, 6, Color.Black));
        assertTrue(tray.placePawn(1, 6, Color.Black));
        assertTrue(tray.placePawn(2, 6, Color.Black));
        // second
        assertTrue(tray.placePawn(1, 3, Color.Black));
        assertTrue(tray.placePawn(2, 3, Color.Black));
        assertTrue(tray.placePawn(2, 4, Color.Black));
        // fird
        assertTrue(tray.placePawn(4, 1, Color.Black));
        assertTrue(tray.placePawn(4, 2, Color.Black));
        assertTrue(tray.placePawn(4, 3, Color.Black));
        assertTrue(tray.placePawn(5, 2, Color.Black));

        // place bridge
        assertTrue(tray.placeBridge(0, 5, 1, 3));
        assertTrue(tray.placeBridge(2, 3, 4, 3));

        // score
        assertTrue(GameModel.scoreFromTrayForColor(Color.Black, tray) == 5);

        // white pawn
        assertTrue(tray.placePawn(0, 2, Color.White));
        assertTrue(tray.placePawn(0, 3, Color.White));
        assertTrue(tray.placePawn(1, 2, Color.White));
        assertTrue(tray.placePawn(2, 2, Color.White));

        assertTrue(tray.placePawn(2, 0, Color.White));
        assertTrue(tray.placePawn(3, 0, Color.White));
        assertTrue(tray.placePawn(4, 0, Color.White));
        assertTrue(tray.placePawn(5, 0, Color.White));

        assertTrue(tray.placePawn(6, 2, Color.White));
        assertTrue(tray.placePawn(6, 3, Color.White));
        assertTrue(tray.placePawn(5, 3, Color.White));
        assertTrue(tray.placePawn(5, 4, Color.White));

        assertTrue(tray.placePawn(3, 4, Color.White));
        assertTrue(tray.placePawn(3, 5, Color.White));
        assertTrue(tray.placePawn(3, 6, Color.White));
        assertTrue(tray.placePawn(4, 6, Color.White));

        assertTrue(tray.placeBridge(2, 2, 2, 0));
        assertTrue(tray.placeBridge(5, 0, 6, 2));
        assertTrue(tray.placeBridge(5, 4, 3, 4));

        assertTrue(GameModel.scoreFromTrayForColor(Color.White, tray) == 10);
        //System.out.println("Big Picture");
        //GameModel.displayInConsole(tray);

    }

    @Test
    public void displayTest()
    {
        Tray tray = new Tray();
        tray.init(5);
        tray.placePawn(0, 0, Color.Black);
        tray.placePawn(0, 2, Color.Black);
        tray.placePawn(1, 2, Color.Black);
        tray.placeBridge(0, 0, 1, 2);
        tray.placePawn(1, 0, Color.White);
        //GameModel.displayInConsole(tray);
    }

}
