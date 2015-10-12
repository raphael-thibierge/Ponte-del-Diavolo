package Game;

import static java.lang.StrictMath.abs;

/**
 * Created by raphael on 11/10/2015.
 */
public class Bridge {

    Pawn base1;
    Pawn base2;

    public Bridge(Pawn base1, Pawn base2) throws NullPointerException
    {
        if (base1 == null || base2 == null){
            throw new  NullPointerException("Try to build a bridge without two bases");
        }

        this.base1 = base1;
        this.base2 = base2;

        base1.setBridge(this);
        base2.setBridge(this);
    }

    public static boolean compatiblePositions(int y1, int x1, int y2, int x2) {

        int ord = abs(y2-y1);
        int abs = abs(x2-x1);
        if (ord == 2 && abs <= 2)
            return true;
        else if (ord < 2 && abs == 2)
            return true;
        return false;
    }

    public void lockPawnBetween2Boxes(Tray tray){
        Box box1 = base1.getBox();
        Box box2 = base2.getBox();
        if (box1 != null && box2 != null){
            int lineOffset = box1.getLine() + box2.getLine();
            int columnOffset = box1.getColumn() + box2.getColumn();

            if ( lineOffset % 2 == 0){
                if ( columnOffset % 2 == 0)
                    tray.getBox(lineOffset / 2, columnOffset / 2).setLocked(true);

                else {
                    tray.getBox(lineOffset / 2, columnOffset / 2).setLocked(true);
                    tray.getBox(lineOffset / 2, columnOffset / 2 + 1).setLocked(true);
                }
            }
            else {
                tray.getBox(lineOffset / 2, columnOffset / 2).setLocked(true);
                tray.getBox(lineOffset/2+1, columnOffset/2).setLocked(true);
            }
        }
    }
}
