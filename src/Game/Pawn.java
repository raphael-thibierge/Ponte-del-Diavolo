package Game;

/**
 * Created by raphael on 10/10/2015.
 */
public class Pawn {
    Cell cell;
    Color color;

    private SandBar sandBar;
    private Bridge bridge;


    public Pawn(Color color, Cell cell) throws NullPointerException {
        if (color == null || cell == null)
            throw new NullPointerException("Try to construct pawn with null color or null cell");
        this.color = color;
        this.cell = cell;

        updateSandBar();
    }

    public boolean belongsToIsland()
    {
        return this.sandBar != null && this.sandBar.isIsland();
    }

    private void updateSandBar() {
        if ( this.cell !=null)
        {
            // no nearby sandbar founded
            boolean founded = false;
            // check all orthogonal cell
            for (Cell nearbyCell : this.cell.getNearbyBoxesOrthogonal().values()){
                if (nearbyCell != null
                        && nearbyCell.isTaken()
                        && nearbyCell.getPawn().getColor() == this.color)
                {
                    //if no previous sandbar has been found
                    if (!founded){
                        // add the pawn to the existing sandbar
                        this.sandBar = nearbyCell.getPawn().getSandBar();
                        founded = this.sandBar.addPawn(this);
                    } else {
                        // else we merge the sandbar founded with pawn's sandbar
                        this.sandBar.mergeSandBar(nearbyCell.getPawn().sandBar);
                    }
                }
            }
            if (!founded){
                this.sandBar = new SandBar(this);
            }

            // check diagonal nearby cell to add sandbar neighbor
            updateNearbyBoxNearbySandBar(this.cell.getNearbyBox(Direction.NORTH_EST));
            updateNearbyBoxNearbySandBar(this.cell.getNearbyBox(Direction.NORTH_WEST));
            updateNearbyBoxNearbySandBar(this.cell.getNearbyBox(Direction.SOUTH_WEST));
            updateNearbyBoxNearbySandBar(this.cell.getNearbyBox(Direction.SOUTH_EST));
        }
    }

    void updateNearbyBoxNearbySandBar(Cell cell) {
        if (cell != null){
            // if pawn's color of nearby cell is the same as this pawn
            if (cell.getPawn() != null
                    && cell.getPawn().getColor() == this.color){
                SandBar nearbySandBar = cell.getPawn().getSandBar();
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

    public void removeBridge(){
        this.bridge = null;
    }

    // ACCESSORS

    public Cell getCell() {
        return cell;
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
