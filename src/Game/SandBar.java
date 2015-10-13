package Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by raphael on 10/10/2015.
 */
public class SandBar {

    private Color color;
    private Pawn[] pawnList;
    private int size = 0;

    private int MAX_SIZE = 4;
    private boolean linked;

    public SandBar(Pawn pawn) throws NullPointerException
    {

        if (pawn == null) {
            throw new NullPointerException();
        }

        this.color = pawn.getColor();
        this.pawnList = new Pawn[MAX_SIZE];
        this.pawnList[0] = pawn;
        this.size++;
    }


    public boolean addPawn(Pawn pawn) {
        // different condition to add a Pawn
        if (pawn != null
                && this.size < this.MAX_SIZE
                && pawn.getColor() == this.color){

                // Vérifier case d'à côté

            // add pawn to list
            this.pawnList[this.size] = pawn;
            this.size++;

            pawn.setSandBar(this);

            // added successfully
            return true;
        }
        return false;
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
            for (Pawn pawn : sandBar.pawnList){
                this.addPawn(pawn);
            }
            return true;
        }
        return false;
    }

    public boolean mergeSandBarAllowed(SandBar sandBar)
    {
        return (sandBar != null && this != sandBar && sandBar.size + this.size <= 4 && sandBar.color == this.color);
    }

}