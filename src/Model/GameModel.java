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
    Player whitePlayer;
    Player blackPlayer;

    // state
    boolean onLineMode = false;
    boolean displayGame = true;


    public GameModel(String serveurIPAddress, int port, int size){
        // init tray
        this.tray = new Tray();
        tray.init(abs(size));
        // init tcp client
        clientTCP = new ClientTCP("thibierge", port, serveurIPAddress);
    }

    public void run(){
        // connect to serveur
        if ( onLineMode ) {
            if (clientTCP.isConnected()) {
                // wait to know player order

                // to begin online game
                treatServer();
                // then run game
                runGame();

                //end of the game, disconnect of serveur
                clientTCP.disconnect();
            }
        } else {
            runForTest();
        }

    }

    public void runForTest()
    {
        whitePlayer = new IA(1, clientTCP);
        blackPlayer = new IA(2, clientTCP);

        whitePlayer.setColor(Color.White);
        blackPlayer.setColor(Color.Black);

        whitePlayer.playInTray(this.tray);
        if (blackPlayer.chooseColor() ==  "c"){
            Player player = whitePlayer;
            whitePlayer = blackPlayer;
            blackPlayer = player;
        }

        whitePlayer.setColor(Color.White);
        blackPlayer.setColor(Color.Black);

        blackPlayer.playInTray(tray);
        this.runGame();
    }



    private void runGame()
    {
        boolean quit = false;

        displayInConsole(this.tray);

        while (!quit){
            // to get move string
            String move;

            // =======================================
            // white player is always the first player
            // =======================================

            // he plays
            move = whitePlayer.playInTray(this.tray);
            // if it's an online game, and the player is the the IA
            if (onLineMode && whitePlayer instanceof IA)
                // send the move to server
                clientTCP.write(move);
            // then display in console

            if (displayGame)
                displayInConsole(this.tray);
            // TODO PROV ( TO DELEDE )
            System.out.println("Move White " + move + "\n");

            // if he can't play, game is finished at the end of the turn
            if (move == "a")
                quit = true;

            // =================================
            // black player is the second player
            // =================================
            // he plays
            move = blackPlayer.playInTray(this.tray);
            // if it's an online game, and the player is the the IA
            if (onLineMode && whitePlayer instanceof IA)
                // send the move to server
                clientTCP.write(move);
            // then display in console
            if (displayGame)
                displayInConsole(this.tray);
            // TODO PROV ( TO DELEDE )
            System.out.println("Move Black " + move + "\n");


            // if he can't play, game is finished at the end of the turn
            if (move == "a")
                quit = true;

            System.out.println("Player 1 score : " + scoreFromTrayForColor(whitePlayer.getColor(), tray));
            System.out.println("Player 2 score : " + scoreFromTrayForColor(blackPlayer.getColor(), tray) + "\n");
        }
    }

    private void beginOnlineGame(String str)
    {
        onLineMode = true;

        switch (str) {
            case "P": // IA is the first player
                whitePlayer = new IA(1, clientTCP);
                blackPlayer = new DistantPlayer(2, this.clientTCP, this);

                clientTCP.write(
                        whitePlayer.playInTray(this.tray));

                if (blackPlayer.chooseColor() == "c") {
                    // invert black and white player
                    Player player = whitePlayer;
                    whitePlayer = blackPlayer;
                    blackPlayer = player;
                }

                whitePlayer.setColor(Color.White);
                blackPlayer.setColor(Color.Black);

                String move = blackPlayer.playInTray(this.tray);
                if (blackPlayer instanceof IA){
                    clientTCP.write(move);
                }

                break;

            case "S": // IA is the second player
                whitePlayer = new DistantPlayer(1, this.clientTCP, this);
                blackPlayer = new IA(2, clientTCP);

                whitePlayer.playInTray(this.tray);

                if (blackPlayer.chooseColor() == "c") {
                    clientTCP.write("f");
                    // invert black and white player
                    Player player = whitePlayer;
                    whitePlayer = blackPlayer;
                    blackPlayer = player;
                } else clientTCP.write("c");

                whitePlayer.setColor(Color.White);
                blackPlayer.setColor(Color.Black);
                break;
        }
    }


    public void treatServer()
    {
        // get server message
        if (clientTCP.isConnected())
        {
            String message = clientTCP.read();

            switch (message){
                case "P": // IA is the first player
                    beginOnlineGame("P");
                    break;

                case "S": // IA is the second player
                    beginOnlineGame("S");
                    break;

                case "f": // IA is the first player and play with black pawns
                    if (whitePlayer != null && blackPlayer != null) {
                        whitePlayer.setColor(Color.Black);
                        blackPlayer.setColor(Color.White);
                    } else {
                        System.err.println("Players not inited !");
                    }
                    break;

                case "c": // IA is the first player and play with white pawns
                    if (whitePlayer != null && blackPlayer != null) {
                        whitePlayer.setColor(Color.White);
                        blackPlayer.setColor(Color.Black);
                    } else {
                        System.err.println("Players not inited !");
                    }
                    break;

                case "a": // Other player pass his turn
                    // TODO other player pass his turn
                    break;

                case "F" : // End of the game
                    // TODO
                    break;

                default:
                    // it's the other player move !
                    // TODO

            }

        }
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

    public static void displayInConsole(Tray tray)
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
