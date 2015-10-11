package Game;

/**
 * Created by raphael on 10/10/2015.
 */
public class Pawn {
    Box box;
    Color color;

    private SandBar sandBar;


    public Pawn(Color color, Box box) throws NullPointerException
    {
        if (color == null || box == null)
            throw new NullPointerException("Try to construct pawn with null color");
        this.color = color;
        this.box = box;

        updateSandBar();
    }


    public boolean belongsToIsland()
    {
        if (this.sandBar != null && this.sandBar.isIsland())
            return true;
        return false;
    }

    private void updateSandBar()
    {
        if ( this.box !=null)
        {
            boolean founded = false;
            for (Box nearbyBox : this.box.getNearbyBoxesOrthogonal().values()){
                if (nearbyBox != null
                        && ! founded
                        && nearbyBox.isTaken()
                        && nearbyBox.getPawn().getColor() == this.color){
                    this.sandBar = nearbyBox.getPawn().getSandBar();
                    founded = this.sandBar.addPawn(this);
                    break;
                }
            }
            if (!founded){
                this.sandBar = new SandBar(this);
            }
        }
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
}
