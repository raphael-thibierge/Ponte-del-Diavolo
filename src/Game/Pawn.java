package Game;

import static junit.framework.Assert.assertTrue;

/**
 * Created by raphael on 10/10/2015.
 */
public class Pawn {
    Box box;
    Color color;

    private SandBar sandBar;
    private Bridge bridge;


    public Pawn(Color color, Box box) throws NullPointerException
    {
        if (color == null || box == null)
            throw new NullPointerException("Try to construct pawn with null color or null box");
        this.color = color;
        this.box = box;

        updateSandBar();
    }


    public boolean belongsToIsland()
    {
        return this.sandBar != null && this.sandBar.isIsland();
    }

    private void updateSandBar()
    {
        if ( this.box !=null)
        {
            // no nearby sandbar founded
            boolean founded = false;
            // check all orthogonal box
            for (Box nearbyBox : this.box.getNearbyBoxesOrthogonal().values()){
                if (nearbyBox != null
                        && nearbyBox.isTaken()
                        && nearbyBox.getPawn().getColor() == this.color)
                {
                    //if no previous sandbar has been found
                    if (!founded){
                        // add the pawn to the existing sandbar
                        this.sandBar = nearbyBox.getPawn().getSandBar();
                        founded = this.sandBar.addPawn(this);
                    } else {
                        // else we merge the sandbar founded with pawn's sandbar
                        this.sandBar.mergeSandBar(nearbyBox.getPawn().sandBar);
                    }
                }
            }
            if (!founded){
                this.sandBar = new SandBar(this);
            }

            // check diagonal nearby box to add sandbar neighbor
            updateNearbyBoxNearbySandBar(this.box.getNearbyBox(Direction.NORTH_EST));
            updateNearbyBoxNearbySandBar(this.box.getNearbyBox(Direction.NORTH_WEST));
            updateNearbyBoxNearbySandBar(this.box.getNearbyBox(Direction.SOUTH_WEST));
            updateNearbyBoxNearbySandBar(this.box.getNearbyBox(Direction.SOUTH_EST));
        }
    }


    void updateNearbyBoxNearbySandBar(Box box)
    {
        if (box != null){
            // if pawn's color of nearby box is the same as this pawn
            if (box.getPawn() != null
                    && box.getPawn().getColor() == this.color){
                SandBar nearbySandBar = box.getPawn().getSandBar();
                if (nearbySandBar != null){
                    // add sandbar neighbors
                    this.sandBar.addNeighbor(nearbySandBar);
                    nearbySandBar.addNeighbor(this.sandBar);

                }
            }
        }
    }


    public boolean hasBridge(){
        return !(this.bridge == null);
    }


    // ACCESSORS


    public Box getBox() {
        return box;
    }

    public Color getColor() {
        return color;
    }

    public SandBar getSandBar() {
        return sandBar;
    }

    public void setSandBar(SandBar sandBar) {
        this.sandBar = sandBar;
    }

    public Bridge getBridge() {
        return this.bridge;
    }

    public void setBridge(Bridge bridge) {
        this.bridge = bridge;
    }
}
