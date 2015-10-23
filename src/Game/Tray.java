package Game;

import IA.Strategy;
import Network.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by raphael on 10/10/2015.
 */
public class Tray {
    private int size = 0;
    private Cell[][] grid = null;
    private List<Bridge> bridgeList = null ;
    private List<Pawn> whitePawns = null;
    private List<Pawn> blackPawns = null;
    boolean initialised = false;

    public Tray() {}

    public void init(int size) {
        // in case negative number
        if (size == 0)
            size = 1;
        else if (size < 0)
            size = size * -1;
        this.size = size;

        // init board
        grid = new Cell[this.size][this.size];

        // init all boxes
        for (int line = 0; line < this.size ; line++){
            for (int column = 0; column < this.size ; column++){
                grid[line][column] = new Cell(line, column);
            }
        }
        this.initNearbyBoxes();
        this.initialised = true;
        bridgeList = new ArrayList<>();
        blackPawns = new ArrayList<>();
        whitePawns = new ArrayList<>();
    }

    private void initNearbyBoxes(){
        for (int line = 0; line < this.size ; line++){
            for (int column = 0 ; column < this.size ; column++){
                Map<Direction, Cell> nearbyBoxes = new HashMap<Direction, Cell>();

                // WEST BOX
                if (column > 0){
                    nearbyBoxes.put(Direction.WEST, getCell(line, column - 1));
                } else {
                    nearbyBoxes.put(Direction.WEST, null);
                }

                // EST BOX
                if (column < this.size-1){
                    nearbyBoxes.put(Direction.EST, getCell(line, column + 1));
                }else {
                    nearbyBoxes.put(Direction.EST, null);
                }

                // NORTH BOX
                if (line > 0){
                    nearbyBoxes.put(Direction.NORTH, getCell(line - 1, column));
                }else {
                    nearbyBoxes.put(Direction.NORTH, null);
                }

                // SOUTH BOX
                if (line < this.size-1){
                    nearbyBoxes.put(Direction.SOUTH, getCell(line + 1, column));
                }else {
                    nearbyBoxes.put(Direction.SOUTH, null);
                }



                // NORTH_WEST BOX
                if (line != 0 && column != 0){
                    nearbyBoxes.put(Direction.NORTH_WEST, getCell(line - 1, column - 1));
                }else {
                    nearbyBoxes.put(Direction.NORTH_WEST, null);
                }

                // NORTH_EST BOX
                if (line != 0 && column != this.size-1){
                    nearbyBoxes.put(Direction.NORTH_EST, getCell(line - 1, column + 1));
                }else {
                    nearbyBoxes.put(Direction.NORTH_EST, null);
                }

                // SOUTH_WEST BOX
                if (line != this.size-1 && column != 0){
                    nearbyBoxes.put(Direction.SOUTH_WEST, getCell(line + 1, column - 1));
                }else {
                    nearbyBoxes.put(Direction.SOUTH_WEST, null);
                }

                // SOUTH_EST BOX
                if (line != this.size-1 && column != this.size-1){
                    nearbyBoxes.put(Direction.SOUTH_EST, getCell(line + 1, column + 1));
                }else {
                    nearbyBoxes.put(Direction.SOUTH_EST, null);
                }

                // insert nearbyBoxes in cell
                getCell(line, column).setNearbyBoxes(nearbyBoxes);
            }
        }
    }

    public Cell getCell(int line, int column){
        if ( this.grid != null
                && line >= 0
                &&  line < size
                && column >= 0
                && column < size )
            return this.grid[line][column];
        return null;
    }

    public SandBar getSandBarInBox(int line, int column){

        try {
            return getCell(line, column).getPawn().getSandBar();
        } catch (Exception e){
            return null;
        }
    }

    public boolean placePawn(int line, int column, Color color){
        Cell cell = getCell(line, column);
        if ( cell != null){
            if( cell.placePawn(color)){
                getPawns(color).add(cell.getPawn());


                return true;
            }
        }
        return false;
    }

    public boolean canBridge(Cell cell1, Cell cell2){
        // test if boxes are compatible
        if (cell1 != null && cell2 != null && Bridge.compatiblePositions(cell1.getLine(), cell1.getColumn(), cell2.getLine(), cell2.getColumn())){
            Pawn pawn2 = cell2.getPawn();
            Pawn pawn1 = cell1.getPawn();

            if (pawn1 != null && pawn2 != null ){

                if (pawn1.getColor() == pawn2.color // if pawn are the same color
                        && !pawn1.hasBridge() // has no bridge
                        && !pawn2.hasBridge()
                        && !pawnBetween2Boxes(cell1, cell2) // already a pawn between to pawn
                        ){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean canBridge(int line1, int column1, int line2, int column2){
        if ( line1 < 0 || column1 < 0 || line2 < 0 || column2 < 0)
            return false;

        Cell cell1 = getCell(line1, column1);
        Cell cell2 = getCell(line2, column2);
        return canBridge(cell1, cell2);
    }

    public boolean placeBridge(int line1, int column1, int line2, int column2){
        if (canBridge(line1, column1,  line2, column2)){
            Bridge bridge = new Bridge(getCell(line1, column1).getPawn(), getCell(line2, column2).getPawn());
            bridge.lockPawnBetween2Boxes(this);
            bridgeList.add(bridge);
            return true;
        }
        return false;
    }

    public boolean placeBridge(Cell cell1, Cell cell2){
        if (cell1 != null && cell2 != null)
            return placeBridge(cell1.getLine(), cell1.getColumn(), cell2.getLine(), cell2.getColumn());
        return false;
    }

    public boolean pawnBetween2Boxes(Cell cell1, Cell cell2){

        if (cell1 != null && cell2 != null){
            int lineOffset = cell1.getLine() + cell2.getLine();
            int columnOffset = cell1.getColumn() + cell2.getColumn();

            if ( lineOffset % 2 == 0){
                if ( columnOffset % 2 == 0)
                    return getCell(lineOffset / 2, columnOffset / 2).isTaken()
                            || getCell(lineOffset / 2, columnOffset / 2).isLocked();

                else
                    return (getCell(lineOffset / 2, columnOffset / 2).isTaken()
                            || getCell(lineOffset / 2, columnOffset / 2).isLocked()
                            || getCell(lineOffset / 2, columnOffset / 2 + 1).isTaken()
                            ||getCell(lineOffset / 2, columnOffset / 2 + 1).isLocked());
            }
            else
                return (getCell(lineOffset / 2, columnOffset / 2).isTaken()
                            || getCell(lineOffset / 2, columnOffset / 2).isLocked()
                            || getCell(lineOffset / 2 + 1, columnOffset / 2).isTaken()
                            || getCell(lineOffset / 2 + 1, columnOffset / 2).isLocked() );

        }
        return false;
    }

    public int nbPawnAllowed(Color color){
        int cptPawnPossibilities = 0;

        for (int line = 0 ; line < this.size ; line++){
            for (int column = 0 ; column < this.size ; column++){
                Cell cell = this.getCell(line, column);
                if (cell != null && cell.pawnAllowedHere(color)){
                    cptPawnPossibilities++;
                }
            }
        }
        return cptPawnPossibilities;
    }

    public void cancelPawn(int line, int column){
        Cell cell = this.getCell(line, column);

        if (cell != null && cell.getPawn() != null){
            if (cell.getPawn().getColor() == Color.White)
                whitePawns.remove(cell.getPawn());
            else blackPawns.remove(cell.getPawn());
            // remove pawn from sand bar
            cell.getPawn().getSandBar().removePawn(cell.getPawn());
            cell.removePawn();
        }
    }
/*
    public void cancelBridge(int line1, int column1, int line2, int column2){
        Cell cell1 = getCell(line1, column1);
        Cell cell2 = getCell(line2, column2);

        if (cell1 != null && cell2 != null
                && cell1.getPawn() != null && cell2.getPawn() != null){

            if (cell1.getPawn().getBridge() != null
                    && cell1.getPawn().getBridge().equals(cell2.getPawn().getBridge())){
                this.bridgeList.remove(cell1.getPawn().getBridge());
                cell1.getPawn().removeBridge();
                cell2.getPawn().removeBridge();
            }
        }
    }*/



    public Color initWithString(int size, String string){
        init(size);
        String msg ="";
        Color turn = Color.White;
        for (char c : string.toCharArray()){
            if (!Message.isMessage(msg) && c != ' '){
                msg += c;
            } else if (msg.length() == 5){
                placeFromString(msg, turn);
                if (turn == Color.White) {
                    turn = Color.Black;
                }
                else {
                    turn = Color.White;
                }
                msg = "";
            } else
                msg = "";
        }
        if (msg.length() == 5){
            placeFromString(msg, turn);
        }
        return turn;
    }

    public boolean placeFromString (String string, Color color){
        try {
            // get lines and columns

            if (string.length() == 5){
                int line1 = Integer.parseInt(String.valueOf(string.charAt(0)));
                int column1 = Integer.parseInt(String.valueOf(string.charAt(1)));
                int line2 = Integer.parseInt(String.valueOf(string.charAt(3)));
                int column2 = Integer.parseInt(String.valueOf(string.charAt(4)));

                // if distant player wants to place a bridge
                if (string.charAt(2) == '-') {
                    if (this.placeBridge(line1, column1, line2, column2))
                        return true;
                    else
                        System.err.print("Can't place distant player bridge in (" + line1 + "," + column1 + ") (" + line2 + "," + column2 + ")"  );
                }
                else { // distant player wants to place 2 pawns
                    if (this.placePawn(line1, column1, color))
                        if (this.placePawn(line2, column2, color))
                            return true;
                        else
                            System.out.println("Can't place " + color + " pawn in (" + line2 + "," + column2 + ")");
                    else
                        System.out.println("Can't place " + color + " pawn in (" + line1 + "," + column1 + ")");
                }
            }
        } catch (Exception e){
            System.err.print("Error found in LECTURE CASE DEFAULT");

        }
        return false;
    }


    /* ========================
     *        ACCESSORS
     * ======================== */

    public int getSize() {
        return size;
    }

    public boolean isInitialised() {
        return initialised;
    }

    public List<Pawn> getPawns(Color color){
        if (color == null)
            return null;
        if (color == Color.White)
            return whitePawns;
        else return blackPawns;
    }



}
