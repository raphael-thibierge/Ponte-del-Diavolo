package Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        // TODO need to be optimized

        // first condition
        if ( color != null
                && !isLocked()
                && !isTaken()
                )
        {
            /*{
                // new sandBar size
                int sandBarSize = 1; // because if we place pawn, there is on more pawn in sandBar
                List<SandBar> sandbarFounds = new ArrayList<>();
                List<SandBar> diagSandBarFounds = new ArrayList<>();
                SandBar lastSandbar = null;

                // get nearbyBox of all direction
                for (Box nearbyBox : this.nearbyBoxes.values()) {
                    // if there is a nearby box in this direction
                    if (nearbyBox != null) {
                        //try to get pawn
                        Pawn pawn1 = nearbyBox.pawn;
                        // if there is a same color sandBand
                        if (pawn1 != null && pawn1.getColor() == color) {
                            // if it's an island

                            if (pawn1.belongsToIsland()) {
                                // pawn not allowed
                                return false;
                            }

                            // if the sandbar is not in diagonal
                            if (!this.inDiagonal(nearbyBox) && pawn1.getSandBar() != lastSandbar) {
                                //  we count merging all nearby sandBar
                                sandBarSize += pawn1.getSandBar().getSize();
                                lastSandbar = pawn1.getSandBar();
                                sandbarFounds.add(pawn1.getSandBar());


                                if (!pawn1.getSandBar().canReceiveAPawn())
                                    return false;

                                if (sandBarSize > 4)
                                    return false;

                            } else if (this.inDiagonal(nearbyBox) && pawn1.getSandBar() != lastSandbar) {
                                diagSandBarFounds.add(pawn1.getSandBar());
                            }
                        }
                    }
                }

                for (SandBar sandBar : diagSandBarFounds) {
                    if (!sandbarFounds.contains(sandBar) && sandBarSize == 4)
                        return false;
                }

            }*/


            int newSandBarSize = 1; // because if we place pawn, there is on more pawn in sandBar
            List<SandBar> sandbarFounds = new ArrayList<>();

            // testing orthogonal sandbar around to merge to know they would be allowed to be merged
            for (Box nearbyBox : this.getNearbyBoxesOrthogonal().values()) {
                if (nearbyBox != null) {
                    // if there is a pawn in the nearby box and it has the same color as color param
                    Pawn nearbyPawn = nearbyBox.getPawn();
                    if (nearbyPawn != null && nearbyPawn.getColor() == color) {
                        // if he has a sandbar, he always have to !
                        SandBar nearbySandbar = nearbyPawn.getSandBar();
                        if (nearbySandbar != null) {

                            // TESTS begin here

                            // it's forbidden to place a pawn near an Island
                            if (nearbySandbar.isIsland())
                                return false;

                            // if sandbar has not already been treated
                            if (!sandbarFounds.contains(nearbySandbar)) {
                                sandbarFounds.add(nearbySandbar);

                                newSandBarSize += nearbySandbar.getSize();

                                if (newSandBarSize == 4 && nearbySandbar.hasNeighbors())
                                {
                                    for (SandBar sandBar : nearbySandbar.getNearbySandBars())
                                    {
                                        if (!sandbarFounds.contains(sandBar))
                                            return false;
                                    }
                                }

                                // if newSandBar can't have more than 4 pawn inside
                                if (newSandBarSize > 4)
                                    return false;
                            }
                        }
                    }
                }
            }

            // then testing diagonal sandbar
            for (Box nearbyBox : this.getNearbyBoxesDiagonal().values()) {
                if (nearbyBox != null) {
                    // if there is a pawn in the nearby box and it has the same color as color param
                    Pawn nearbyPawn = nearbyBox.getPawn();
                    if (nearbyPawn != null && nearbyPawn.getColor() == color) {
                        // if he has a sandbar, he always have to !
                        SandBar nearbySandbar = nearbyPawn.getSandBar();
                        if (nearbySandbar != null) {
                            // it's forbidden to place a pawn near an Island even if it's in diagonal
                            if (nearbySandbar.isIsland())
                                return false;

                            // the new sandbar can't be an island if there is nearby sandbar in Diagonal
                            // which has not been treated
                            if (newSandBarSize == 4 && !sandbarFounds.contains(nearbySandbar))
                                return false;

                        }
                    }
                }
            }




            return true;
        }
        return false;
    }


    public boolean isNearbyOf(Box box){
        return box != null && this.nearbyBoxes.containsValue(box);
    }

    public boolean inDiagonal(Box box)
    {
        return box != null &&
                (box == this.nearbyBoxes.get(Direction.NORTH_EST)
                        || box == this.nearbyBoxes.get(Direction.NORTH_WEST)
                        || box == this.nearbyBoxes.get(Direction.SOUTH_EST)
                        || box == this.nearbyBoxes.get(Direction.SOUTH_WEST)
                );
    }


    /* ========================
     *        ACCESSORS
     * ======================== */


    public int getColumn() {
        return column;
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

    public int getLine() {
        return line;
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
        Map<Direction, Box> map = new HashMap<>();
        map.put(Direction.NORTH, this.nearbyBoxes.get(Direction.NORTH));
        map.put(Direction.EST, this.nearbyBoxes.get(Direction.EST));
        map.put(Direction.WEST, this.nearbyBoxes.get(Direction.WEST));
        map.put(Direction.SOUTH, this.nearbyBoxes.get(Direction.SOUTH));
        return map;
    }

    public Map<Direction, Box> getNearbyBoxesDiagonal() {
        Map<Direction, Box> map = new HashMap<>();
        map.put(Direction.NORTH_EST, this.nearbyBoxes.get(Direction.NORTH_EST));
        map.put(Direction.NORTH_WEST, this.nearbyBoxes.get(Direction.NORTH_WEST));
        map.put(Direction.SOUTH_EST, this.nearbyBoxes.get(Direction.SOUTH_EST));
        map.put(Direction.SOUTH_WEST, this.nearbyBoxes.get(Direction.SOUTH_WEST));
        return map;
    }

}
