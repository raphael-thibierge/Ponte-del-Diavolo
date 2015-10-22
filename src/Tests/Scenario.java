package Tests;

        import Game.Color;
        import Game.Tray;
        import Model.GameModel;
        import org.junit.Test;

        import static org.junit.Assert.assertFalse;
        import static org.junit.Assert.assertTrue;

/**
 * Created by raph on 22/10/15.
 */
public class Scenario {

    @Test
    public void erreurMrGuibert0() {
        Tray tray = new Tray();
        tray.init(5);
        assertTrue(tray.placePawn(0, 0, Color.White));
        assertTrue(tray.placePawn(0, 1, Color.White));
        assertTrue(tray.placePawn(0, 2, Color.Black));
        assertTrue(tray.placePawn(0, 3, Color.Black));
        assertTrue(tray.placePawn(1, 0, Color.White));
        assertTrue(tray.placePawn(3, 1, Color.White));
        assertTrue(tray.placePawn(0, 4, Color.Black));
        assertTrue(tray.placePawn(1, 1, Color.Black));
        assertTrue(tray.placePawn(3, 4, Color.White));
        assertTrue(tray.placePawn(4, 2, Color.White));
        assertTrue(tray.placePawn(2, 0, Color.Black));
        assertTrue(tray.placePawn(2, 1, Color.Black));
        assertTrue(tray.placePawn(2, 4, Color.White));
        assertTrue(tray.placePawn(4, 0, Color.White));
        assertFalse(tray.placePawn(2, 2, Color.Black));
        assertTrue(tray.placePawn(4, 1, Color.Black));
        GameModel.displayInConsole(tray);
    }

    @Test
    public void erreurMrGuibert1() {
        Tray tray = new Tray();
        tray.init(4);
//        13+30 c 01+21 00+02 03+10 11+12
        assertTrue(tray.placeFromString("13+30", Color.White));
        assertTrue(tray.placeFromString("01+21", Color.Black));
        assertTrue(tray.placeFromString("00+02", Color.White));
        assertTrue(tray.placeFromString("03+10", Color.Black));
        assertFalse(tray.placeFromString("11+12", Color.White));
        GameModel.displayInConsole(tray);

    }

    @Test
    public void erreurMrGuibert2() {
        Tray tray = new Tray();
        tray.init(5);
//        03+14 c 11+12 00+01 13+24 02+20
        assertTrue(tray.placeFromString("03+14", Color.White));
        assertTrue(tray.placeFromString("11+12", Color.Black));
        assertTrue(tray.placeFromString("00+01", Color.White));
        assertTrue(tray.placeFromString("13+24", Color.Black));
        assertFalse(tray.placeFromString("02+20", Color.White));
        GameModel.displayInConsole(tray);

    }


    @Test
    public void erreurMrGuibert3() {
        Tray tray = new Tray();
        tray.init(5);
//        00+01 f 13+31 02+03 13-31 20+21 04+11 23+24 40+42 30+32 12+41 33+44
        assertTrue(tray.placeFromString("00+01", Color.White));
        assertTrue(tray.placeFromString("13+31", Color.Black));
        assertTrue(tray.placeFromString("02+03", Color.White));
        assertTrue(tray.placeFromString("13-31", Color.Black));
        assertTrue(tray.placeFromString("20+21", Color.White));
        assertTrue(tray.placeFromString("04+11", Color.Black));
        assertTrue(tray.placeFromString("23+24", Color.White));
        assertTrue(tray.placeFromString("40+42", Color.Black));
        assertTrue(tray.placeFromString("30+32", Color.White));
        assertTrue(tray.placeFromString("12+41", Color.Black));
        assertFalse(tray.placeFromString("33+44", Color.White));
        GameModel.displayInConsole(tray);
    }

    @Test
    public void erreurMrGuibert4() {
        Tray tray = new Tray();
        tray.init(6);
//        14+21 c 04+30 00+01 24+44 02+05 24-44 13+15
        assertTrue(tray.placeFromString("14+21", Color.White));
        assertTrue(tray.placeFromString("04+30", Color.Black));
        assertTrue(tray.placeFromString("00+01", Color.White));
        assertTrue(tray.placeFromString("24+44", Color.Black));
        assertTrue(tray.placeFromString("02+05", Color.White));
        assertTrue(tray.placeFromString("24-44", Color.Black));
        assertFalse(tray.placeFromString("13+15", Color.White));
        GameModel.displayInConsole(tray);

    }

    @Test
    public void erreurMrGuibert5() {
        Tray tray = new Tray();
        tray.init(6);
//        13+33 c 43+50 00+01 31+44 02+04 31-50 05+14
        assertTrue(tray.placeFromString("13+33", Color.White));
        assertTrue(tray.placeFromString("43+50", Color.Black));
        assertTrue(tray.placeFromString("00+01", Color.White));
        assertTrue(tray.placeFromString("31+44", Color.Black));
        assertTrue(tray.placeFromString("02+04", Color.White));
        assertTrue(tray.placeFromString("31-50", Color.Black));
        assertFalse(tray.placeFromString("05+14", Color.White));


        GameModel.displayInConsole(tray);
    }

    @Test
    public void erreurMrGuibert6() {
        Tray tray = new Tray();
        tray.init(6);
//        21+43 c 00+04 01+02 05+20 03+10 22+41 14+15 22-41 23+25 42+45 30+33 11+55 40+44
        assertTrue(tray.placeFromString("21+43", Color.White));
        assertTrue(tray.placeFromString("00+04", Color.Black));
        assertTrue(tray.placeFromString("01+02", Color.White));
        assertTrue(tray.placeFromString("05+20", Color.Black));
        assertTrue(tray.placeFromString("03+10", Color.White));
        assertTrue(tray.placeFromString("22+41", Color.Black));
        assertTrue(tray.placeFromString("14+15", Color.White));
        assertTrue(tray.placeFromString("22-41", Color.Black));
        assertTrue(tray.placeFromString("23+25", Color.White));
        assertTrue(tray.placeFromString("42+45", Color.Black));
        assertTrue(tray.placeFromString("30+33", Color.White));
        assertTrue(tray.placeFromString("11+55", Color.Black));
        assertFalse(tray.placeFromString("40+44", Color.Black));
        GameModel.displayInConsole(tray);
    }

    @Test
    public void erreurMrGuibert7() {
        Tray tray = new Tray();
        tray.init(7);
        //    13+32 c 06+36 00+01 26+40 02+04 03+35 05+14
        assertTrue(tray.placeFromString("13+32", Color.White));
        assertTrue(tray.placeFromString("06+36", Color.Black));
        assertTrue(tray.placeFromString("00+01", Color.White));
        assertTrue(tray.placeFromString("26+40", Color.Black));
        assertTrue(tray.placeFromString("02+04", Color.White));
        assertTrue(tray.placeFromString("03+35", Color.Black));
        assertFalse(tray.placeFromString("05+14", Color.White));
        GameModel.displayInConsole(tray);
    }

}
