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

        this.base1.getSandBar().setLinked(true);
        this.base2.getSandBar().setLinked(true);

        this.base1.setBridge(this);
        this.base2.setBridge(this);
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
        Cell cell1 = base1.getCell();
        Cell cell2 = base2.getCell();
        if (cell1 != null && cell2 != null){
            int lineOffset = cell1.getLine() + cell2.getLine();
            int columnOffset = cell1.getColumn() + cell2.getColumn();

            if ( lineOffset % 2 == 0){
                if ( columnOffset % 2 == 0)
                    tray.getCell(lineOffset / 2, columnOffset / 2).setLocked(true);

                else {
                    tray.getCell(lineOffset / 2, columnOffset / 2).setLocked(true);
                    tray.getCell(lineOffset / 2, columnOffset / 2 + 1).setLocked(true);
                }
            }
            else {
                tray.getCell(lineOffset / 2, columnOffset / 2).setLocked(true);
                tray.getCell(lineOffset / 2 + 1, columnOffset / 2).setLocked(true);
            }
        }
    }

    public Pawn getBase1() {
        return base1;
    }

    public void setBase1(Pawn base1) {
        this.base1 = base1;
    }

    public Pawn getBase2() {
        return base2;
    }

    public void setBase2(Pawn base2) {
        this.base2 = base2;
    }
}
