package Model;

import Game.Color;
import Game.SandBar;
import Game.Tray;
import Network.ClientTCP;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

/**
 * Created by raphael on 12/10/2015.
 */
public class GameModel {
    Tray tray;
    ClientTCP clientTCP;



    public GameModel(String serveurIPAddress, int port, int size){
        // init tray
        this.tray = new Tray();
        tray.init(abs(size));
        // init tcp client
        clientTCP = new ClientTCP("thibierge", port, serveurIPAddress);
    }

    public void run(){
        // connect to serveur
        if (clientTCP.isConnected()){
            // wait to know player order

            setPlayerNumber();

            runGame();


        }

        //end of the game, disconnect of serveur
        clientTCP.disconnect();
    }

    public void runForTest()
    {

    }

    private void runGame()
    {

    }

    private void setPlayerNumber()
    {

    }


    public static int scoreFromTrayForColor(Color color, Tray tray) {
        int nbLinkedIsland = 0;
        int nbAloneIsland = 0;

        // not optimized
        // TODO to optimize

        if (tray != null &&  tray.isInitialised() && color != null ){
            List<SandBar> islandList = new ArrayList<>();
            // test all box in tray
            for (int line = 0 ; line < tray.getSize() ; line++){
                for (int column = 0 ; column < tray.getSize() ; column++){
                    SandBar sandBar = tray.getSandBarInBox(line, column);
                    if (sandBar != null && sandBar.getColor() == color && sandBar.isIsland())
                    {
                        if (!islandList.contains(sandBar)){
                            islandList.add(sandBar);

                            if (sandBar.isLinked())
                                nbLinkedIsland++;
                            else
                                nbAloneIsland++;
                        }
                    }
                }
            }
            return scoreFromNbIsland(nbLinkedIsland, nbAloneIsland);
        }
        return -1;
    }

    public static int scoreFromNbIsland(int nbLinkedIsland, int nbAloneIsland){
        if (nbLinkedIsland >= 0 && nbAloneIsland >= 0)
            return ( nbLinkedIsland * ( nbLinkedIsland + 1 ) ) / 2 + nbAloneIsland;
        return 0;
    }

    public static void printInConsole(Tray tray)
    {
        for (int line = 0 ; line < tray.getSize() ; line++){
            for (int column = 0 ; column < tray.getSize(); column++){
                System.out.print("+----");
            }
            System.out.print("+\n");

            for (int column = 0 ; column < tray.getSize(); column++){
                System.out.print("| " );
                if (tray.getBox(line, column).getPawn() != null) {
                    switch (tray.getBox(line, column).getPawn().getColor()) {
                        case Black:
                            if (tray.getBox(line, column).getPawn().hasBridge())
                                System.out.print("B/ ");
                            else
                                System.out.print("B  ");
                            break;
                        case White:
                            if (tray.getBox(line, column).getPawn().hasBridge())
                                System.out.print("W/ ");
                            else
                                System.out.print("W  ");
                            break;
                        default:
                            break;
                    }
                } else{
                    if (tray.getBox(line, column).isLocked())
                        System.out.print(" x ");
                    else
                        System.out.print("   ");
                }
            }
            System.out.print("|\n" );
        }
        for (int column = 0 ; column < tray.getSize(); column++){
            System.out.print("+----");
        }
        System.out.print("+\n");
    }

}
