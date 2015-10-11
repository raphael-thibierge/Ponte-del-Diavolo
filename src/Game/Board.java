package Game;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by raphael on 10/10/2015.
 */
public class Board {
    private int size;
    private Box[][] grid;



    public Board(int size)
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

    public boolean placePawn(int line, int colum, Color color){
        Box box = getBox(line, colum);
        if ( box != null){
            return box.placePawn(color);
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
