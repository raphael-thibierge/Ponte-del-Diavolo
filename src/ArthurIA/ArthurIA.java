package ArthurIA;

import Game.Color;
import Game.Tray;
import Model.Player;
import Network.Message;

/**
 * Created by raphael on 16/10/15.
 */
public class ArthurIA extends Player {

    public ArthurIA(Color color)
    {
        super(color);
    }


    @Override
    public String playInTray(Tray tray) {
        // method return string
        String messageReturned = "";
        if (canPlay(tray)){

            // your code here

            /*
                Little examples
            */
            int line1 = 0;
            int colonne1 = 0;
            int line2 = 0;
            int colonne2 = 2;
            boolean hasBeenPlaced;

            /*
                 if you want to place a brige
            */
            hasBeenPlaced = tray.placePawn(line1, colonne1, this.color);
            hasBeenPlaced = tray.placePawn(line2, colonne2, this.color);

            messageReturned = Integer.toString(line1) + Integer.toString(colonne1);
            messageReturned += "+";
            messageReturned = Integer.toString(line2) + Integer.toString(colonne2);


            /*
                if place bridge
            */
            hasBeenPlaced = tray.placeBridge(line1, colonne1, line2, colonne2);
            messageReturned = Integer.toString(line1) + Integer.toString(colonne1);
            messageReturned += "-";
            messageReturned = Integer.toString(line2) + Integer.toString(colonne2);

            /*
                you can use also :
            */

            // to know if you can place a pawn
            boolean possible = tray.getBox(line1, colonne2).pawnAllowedHere(this.color);

            // to know if you can place a bridge
            possible = tray.canBridge(line1, colonne1, line2, colonne2);

        }
        else {
            messageReturned = Message.END;
        }

        return messageReturned;
    }

    @Override
    public String chooseColor() {
        return null;




        // do not delete;
    }
}
