package Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.abs;

/**
 * Created by raphael on 10/10/2015.
 */
public class Tray {
    private int size;
    private Box[][] grid;
    private List<Bridge> bridgeList = new ArrayList<>();



    public Tray(int size)
    {

        // in case negative number
        if (size == 0)
            size = 1;
        else if (size < 0)
            size *= -1;
        this.size = size;

        grid = null;

    }



    public void initBoard()
    {
        // init board
        grid = new Box[this.size][this.size];

        // init all boxes
        for (int line = 0; line < this.size ; line++){
            for (int column = 0; column < this.size ; column++){
                grid[line][column] = new Box(line, column);
            }
        }

        this.initNearbyBoxes();
    }

    private void initNearbyBoxes()
    {
        for (int line = 0; line < this.size ; line++){
            for (int column = 0 ; column < this.size ; column++){
                Map<Direction, Box> nearbyBoxes = new HashMap<Direction, Box>();

                // WEST BOX
                if (column > 0){
                    nearbyBoxes.put(Direction.WEST, getBox(line, column - 1));
                } else {
                    nearbyBoxes.put(Direction.WEST, null);
                }

                // EST BOX
                if (column < this.size-1){
                    nearbyBoxes.put(Direction.EST, getBox(line, column + 1));
                }else {
                    nearbyBoxes.put(Direction.EST, null);
                }

                // NORTH BOX
                if (line > 0){
                    nearbyBoxes.put(Direction.NORTH, getBox(line - 1, column));
                }else {
                    nearbyBoxes.put(Direction.NORTH, null);
                }

                // SOUTH BOX
                if (line < this.size-1){
                    nearbyBoxes.put(Direction.SOUTH, getBox(line + 1, column));
                }else {
                    nearbyBoxes.put(Direction.SOUTH, null);
                }

                // NORTH_WEST BOX
                if (line != 0 && column != 0){
                    nearbyBoxes.put(Direction.NORTH_WEST, getBox(line-1, column-1));
                }else {
                    nearbyBoxes.put(Direction.NORTH_WEST, null);
                }

                // NORTH_EST BOX
                if (line != 0 && column != this.size-1){
                    nearbyBoxes.put(Direction.NORTH_EST, getBox(line-1, column+1));
                }else {
                    nearbyBoxes.put(Direction.NORTH_EST, null);
                }

                // SOUTH_WEST BOX
                if (line != this.size-1 && column != 0){
                    nearbyBoxes.put(Direction.SOUTH_WEST, getBox(line+1, column-1));
                }else {
                    nearbyBoxes.put(Direction.SOUTH_WEST, null);
                }

                // SOUTH_EST BOX
                if (line != this.size-1 && column != this.size-1){
                    nearbyBoxes.put(Direction.SOUTH_EST, getBox(line+1, column+1));
                }else {
                    nearbyBoxes.put(Direction.SOUTH_EST, null);
                }

                // insert nearbyBoxes in box
                getBox(line, column).setNearbyBoxes(nearbyBoxes);
            }
        }
    }


    public Box getBox(int line, int column){
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
            return getBox(line, column).getPawn().getSandBar();
        } catch (Exception e){
            return null;
        }
    }

    public boolean placePawn(int line, int column, Color color){
        Box box = getBox(line, column);
        if ( box != null){
            return box.placePawn(color);
        }
        return false;
    }


    public boolean canBridge(int line1, int column1, int line2, int column2)
    {
        Box box1 = getBox(line1, column1);
        Box box2 = getBox(line2, column2);
        // boxes are compatible
        if (box1 != null && box2 != null && Bridge.compatiblePositions(line1, column1, line2, column2)){
            Pawn pawn2 = box2.getPawn();
            Pawn pawn1 = box1.getPawn();

            if (pawn1 != null && pawn2 != null ){

                if (pawn1.getColor() == pawn2.color // if pawn are the same color
                        && !pawn1.hasBridge() // has no bridge
                        && !pawn2.hasBridge()
                        && !pawnBetween2Boxes(box1, box2) // already a pawn between to pawn
                        ){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean placeBridge(int line1, int column1, int line2, int column2){
        if (canBridge(line1, column1,  line2, column2)){
            Bridge bridge = new Bridge(getBox(line1, column1).getPawn(), getBox(line2, column2).getPawn());
            bridge.lockPawnBetween2Boxes(this);
            bridgeList.add(bridge);
            return true;
        }
        return false;
    }


    public boolean pawnBetween2Boxes(Box box1, Box box2){

        if (box1 != null && box2 != null){
            int lineOffset = box1.getLine() + box2.getLine();
            int columnOffset = box1.getColumn() + box2.getColumn();

            if ( lineOffset % 2 == 0){
                if ( columnOffset % 2 == 0)
                    return getBox(lineOffset/2, columnOffset/2).isTaken();

                else
                    return (getBox(lineOffset/2, columnOffset/2).isTaken()
                            || getBox(lineOffset/2, columnOffset/2+1).isTaken() );
            }
            else
                return (getBox(lineOffset/2, columnOffset/2).isTaken()
                            || getBox(lineOffset/2+1, columnOffset/2).isTaken() );

        }
        return false;
    }


    /* ========================
     *        ACCESSORS
     * ======================== */

    public int getSize() {
        return size;
    }

    public Box[][] getGrid() {
        return grid;
    }

}
