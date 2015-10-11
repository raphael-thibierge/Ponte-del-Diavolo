package Game;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by raphael on 10/10/2015.
 */
public class Box {

    private int line;
    private int column;
    private Pawn pawn;
    private Map<Direction, Box> nearbyBoxes;


// states
    boolean locked = false;
    boolean taken = false;


    public Box(int line, int column){
        this.line = line;
        this.column = column;
        pawn = null;


        // init nearbyBoxes
        this.nearbyBoxes = new HashMap<>();
        this.nearbyBoxes.put(Direction.NORTH, null);
        this.nearbyBoxes.put(Direction.NORTH_EST, null);
        this.nearbyBoxes.put(Direction.NORTH_WEST, null);
        this.nearbyBoxes.put(Direction.SOUTH, null);
        this.nearbyBoxes.put(Direction.SOUTH_EST, null);
        this.nearbyBoxes.put(Direction.SOUTH_WEST, null);
        this.nearbyBoxes.put(Direction.EST, null);
        this.nearbyBoxes.put(Direction.WEST, null);
    }

    public boolean placePawn(Color color){
        if (pawnAllowedHere(color)) {

            // add pawn
            pawn = new Pawn(color, this);

            // set box locked and taken
            this.taken = true;
            this.locked = true;

            return true;
        }
        return false;
    }

    public boolean pawnAllowedHere(Color color)
    {
        if ( color != null
                && !isLocked()
                && !isTaken()
                )
        {

            return true;
        }

        return false;
    }

    public boolean isNearbyOf(Box box){
        if (box != null && this.nearbyBoxes.containsValue(box)){
            return true;
        }
        return false;
    }


    /* ========================
     *        ACCESSORS
     * ======================== */


    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isTaken() {
        return taken;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public Pawn getPawn() {
        return pawn;
    }

    public void setNearbyBoxes(Map<Direction, Box> nearbyBoxes) {
        this.nearbyBoxes = nearbyBoxes;
    }

    public Box getNearbyBox(Direction direction){
        return nearbyBoxes.get(direction);
    }

    public Map<Direction, Box> getNearbyBoxes() {
        return nearbyBoxes;
    }

    public Map<Direction, Box> getNearbyBoxesOrthogonal() {
        Map<Direction, Box> map = this.nearbyBoxes;
        map.remove(Direction.NORTH_EST);
        map.remove(Direction.NORTH_WEST);
        map.remove(Direction.SOUTH_EST);
        map.remove(Direction.SOUTH_WEST);
        return map;
    }

}
