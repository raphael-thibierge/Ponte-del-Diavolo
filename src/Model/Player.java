package Model;

import Game.Box;
import Game.Color;
import Game.Tray;

/**
 * Created by raphael on 13/10/15.
 */
public abstract class Player {
    int number;
    Color color;

    public Player(int number){
        if (number == 1)
            this.color = Color.White;
    }

    public abstract void playInTray(Tray tray);

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    protected boolean canPlay(Tray tray){
        int cptPawnPossibilities = 0;

        for (int line = 0 ; line < tray.getSize() ; line++){
            for (int column = 0 ; column < tray.getSize() ; column++){
                Box box = tray.getBox(line, column);
                if (box != null && box.pawnAllowedHere(this.color)){
                    cptPawnPossibilities++;
                    if (cptPawnPossibilities == 2)
                        return true;
                }
            }
        }

        return false;
    }



}
