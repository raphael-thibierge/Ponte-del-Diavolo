package Model;

import Game.Color;
import Game.Tray;

/**
 * Created by raphael on 13/10/15.
 */
public abstract class Player {

    protected int nbPawnPlaced = 0;

    protected Color color;

    public Player(Color color){
        this.color = color;
    }

    public abstract String playInTray(Tray tray);

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    protected boolean canPlay(Tray tray){
        int cptPawnPossibilities = 0;

        if (tray.nbPawnAllowed(this.color) >= 2 )
        {
            return true;
        }

        return false;
    }


    public void incrementNbPawnPlaced(){
        this.nbPawnPlaced++;
    }

    public void resetNbPawnPlaced(){
        this.nbPawnPlaced = 0 ;
    }

    public int getNbPawnPlaced() {
        return nbPawnPlaced;
    }

    public abstract String chooseColor();




}
