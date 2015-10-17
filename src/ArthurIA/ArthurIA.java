package ArthurIA;

import Game.Box;
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

    private boolean canBridgeOnTray(Tray tray,Color color)
    {
        for(int i =0;i<tray.getSize();i++)
        {
            for(int j=0;j<tray.getSize();j++)
            {
                tray.getBox(i,j).getPawn();
                tray.getBox(i,j).getPawn().getColor();
            }
        }

        return false;
    }


    @Override
    public String playInTray(Tray tray) {
        // method return string
        String messageReturned = "";
        if (canPlay(tray)){

            boolean hasBeenPlaced;
            int cptPawn=0;


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
