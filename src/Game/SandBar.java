package Game;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raphael on 10/10/2015.
 */
public class SandBar {

    private Color color;
    private List<Pawn> pawnList = new ArrayList<>() ;
    private int size = 0;

    private int MAX_SIZE = 4;
    private boolean linked;

    private List<SandBar> nearbySandBars = new ArrayList<>();

    public SandBar(Pawn pawn) throws NullPointerException
    {

        if (pawn == null) {
            throw new NullPointerException();
        }

        this.color = pawn.getColor();
        this.pawnList = new ArrayList<>(); //Pawn[MAX_SIZE];
        this.pawnList.add(pawn);
        this.size++;
    }

    public boolean addPawn(Pawn pawn) {
        // different condition to add a Pawn
        if (pawn != null
                && canReceiveAPawn(pawn)
                && pawn.getColor() == this.color){

                // Vérifier case d'à côté

            // add pawn to list
            this.pawnList.add(pawn);
            this.size++;

            pawn.setSandBar(this);

            // added successfully
            return true;
        }
        return false;
    }

    /*
    * Can receive null pawn to only know if it's possible but pawn doesn't exist
    * */
    public boolean canReceiveAPawn(Pawn pawn){

        if (this.size < 3)
            return true;
        else if (this.size == 3)
            if ( pawn != null && hasNeighbors()){
                if (this.nearbySandBars.size() == 1
                        && this.nearbySandBars.contains(pawn.getSandBar())
                        && (this.nearbySandBars.size() + pawn.getSandBar().getSize()) <= 4)
                    return true;
            }
            else if (!hasNeighbors())
                return true;
        return false;
    }

    public void removePawn(Pawn pawn){
        if (pawn != null && this.pawnList.contains(pawn)){
            this.pawnList.remove(pawn);
            this.size--;





        }
    }

    public boolean hasNeighbors(){
        return this.nearbySandBars.size() > 0;
    }

    public boolean addNeighbor(SandBar sandBar){
        if (sandBar != null && sandBar != this && !sandBar.isIsland() && !this.isIsland()){
            this.nearbySandBars.add(sandBar);
            return true;
        }
        return false;
    }

    public boolean isNeighbor(SandBar sandBar)
    {
        return sandBar != null && sandBar != this && this.nearbySandBars.contains(sandBar);
    }




    public boolean isIsland() {
        return this.size == MAX_SIZE;
    }

    public int getSize() {
        return size;
    }

    public boolean isLinked() {
        return linked;
    }

    public void setLinked(boolean linked) {
        this.linked = linked;
    }

    public Color getColor() {
        return color;
    }

    public boolean mergeSandBar(SandBar sandBar){
        if (mergeSandBarAllowed(sandBar)){

            sandBar.pawnList.forEach(this::addPawn);
            if (this.nearbySandBars.contains(sandBar))
                this.nearbySandBars.remove(sandBar);


            return true;
        }
        return false;
    }

    public boolean mergeSandBarAllowed(SandBar sandBar)
    {
        return (sandBar != null && this != sandBar && sandBar.size + this.size <= 4 && sandBar.color == this.color);
    }

    public List<SandBar> getNearbySandBars() {
        return nearbySandBars;
    }

    public List<Pawn> getPawnList() {
        return pawnList;
    }

    @Override
    public String toString() {
        return "SandBar{" +
                "color=" + color +
                ", size=" + size +
                ", nearbor=" + nearbySandBars.size() +
                '}';
    }

    public void updateLinked() {
        this.linked = false;
        for (Pawn pawn : pawnList){
            if (pawn.hasBridge()){
                this.linked = true;
                break;
            }
        }
    }
}