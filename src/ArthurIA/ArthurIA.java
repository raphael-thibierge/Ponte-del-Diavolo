package ArthurIA;

import Game.Color;
import Game.Tray;
import Model.Player;
import Network.Message;

import java.util.Random;

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
            boolean hasBeenPlaced;
            int cptPawn=0;

            /*
                 if you want to place a pawn
            */
            /*hasBeenPlaced = tray.placePawn(line1, colonne1, this.color);
            hasBeenPlaced = tray.placePawn(line2, colonne2, this.color);

            messageReturned = Integer.toString(line1) + Integer.toString(colonne1);
            messageReturned += "+";
            messageReturned = Integer.toString(line2) + Integer.toString(colonne2);*/


            /*
                if place bridge
            */
            /*hasBeenPlaced = tray.placeBridge(line1, colonne1, line2, colonne2);
            messageReturned = Integer.toString(line1) + Integer.toString(colonne1);
            messageReturned += "-";
            messageReturned = Integer.toString(line2) + Integer.toString(colonne2);*/

            /*
                you can use also :
            */

            // to know if you can place a pawn
           // boolean possible = tray.getBox(line1, colonne2).pawnAllowedHere(this.color);

            // to know if you can place a bridge
            //possible = tray.canBridge(line1, colonne1, line2, colonne2);

            for(int i =0; i<tray.getSize();i++)
            {
                for(int j=0;j<tray.getSize();j++)
                {
                    boolean possiblePawn = tray.getBox(i, j).pawnAllowedHere(this.color);
                    boolean possibleBridge = tray.canBridge(i, j, i, j-2);
                    if(possibleBridge)
                    {
                        hasBeenPlaced=tray.placeBridge(i,j,i,j-2);
                        messageReturned = Integer.toString(i) + Integer.toString(j);
                        messageReturned += "-";
                        messageReturned = Integer.toString(i-2) + Integer.toString(j-2);
                    }
                    else if(possiblePawn && cptPawn < 2)
                    {

                        if(cptPawn==0)
                        {
                            hasBeenPlaced=tray.placePawn(i,j,this.color);
                            messageReturned = Integer.toString(i) + Integer.toString(j);
                            messageReturned += "+";
                        }
                        else
                        {
                            hasBeenPlaced=tray.placePawn(i,j,this.color);
                            messageReturned += Integer.toString(i) + Integer.toString(j);
                        }
                        cptPawn++;
                    }
                }
            }



        }
        else {
            messageReturned = Message.END;
        }

        return messageReturned;
    }

    @Override
    public String chooseColor() {
        String colorChoice = "";
        Random random = new Random();
        if (random.nextInt()%2 == 0){
            this.color = Color.White;
            colorChoice = Message.WHITE;
        } else {
            this.color = Color.Black;
            colorChoice = Message.BLACK;
        }
        System.out.println("Color choosen by IA : " + this.color.name());
        return colorChoice;
    }
}
