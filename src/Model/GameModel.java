package Model;

import Game.Color;
import Game.SandBar;
import Game.Tray;
import IA.AI;
import Network.ClientTCP;
import Network.Message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.Math.abs;

/**
 * Created by raphael on 12/10/2015.
 */
public class GameModel {
    private Tray tray;
    private ClientTCP clientTCP;
    private Player firstPlayerIA;
    private Player secondPlayerDistant;

    private boolean onLineMode = true;
    private boolean verbose = true;

    private boolean quit = false;
    private boolean end = false;
    private boolean lastTurn = false;
    private Color turn;

    public GameModel(String serveurIPAddress, int port, int size){
        // init tray
        this.tray = new Tray();
        tray.init(abs(size));
        // init tcp client
        try{
            clientTCP = new ClientTCP(serveurIPAddress, port);
            printInConsole("Connected");
            this.onLineMode = true;
        } catch (IOException e) {
            // e.printStackTrace();
            printInConsole("Not connected");
            this.onLineMode = false;
        }
    }

    private void nextPlayer()
    {
        if (this.turn == Color.White)
            this.turn = Color.Black;
        else
            turn = Color.White;
    }

    public void run(){

        if (this.onLineMode) {
            firstPlayerIA = new AI(Color.White);
            secondPlayerDistant = new DistantPlayer(Color.Black, clientTCP);
        }
        else {
            firstPlayerIA = new AI(Color.White);
            secondPlayerDistant = new Manual(Color.Black);
        }

        // run game
        while(!this.quit) {
            // treat messages
            this.treatMessage(this.readMessage());

            if (!quit && this.turn == this.firstPlayerIA.getColor()) {

                writeMessage(firstPlayerIA.playInTray(tray));

                this.nextPlayer();
            }
            if (verbose)
                displayInConsole(this.tray);
        }

        printInConsole("Player 1 score : " + scoreFromTrayForColor(firstPlayerIA.getColor(), tray));
        printInConsole("Player 2 score : " + scoreFromTrayForColor(secondPlayerDistant.getColor(), tray) + "\n");

        //end of the game, wait for end signal
        this.treatMessage(this.readMessage());

    }

    public void treatMessage(String message) {

        switch (message) {

            case Message.FIRST: // AI is the first player
                // init players
                firstPlayerIA.setColor(Color.White);
                secondPlayerDistant.setColor(Color.Black);
                // AI place two pawn on the tray
                writeMessage(firstPlayerIA.playInTray(this.tray));

                turn = Color.Black;
                break;

            case Message.SECOND: // AI is the second player
                // init player
                firstPlayerIA.setColor(Color.Black);
                secondPlayerDistant.setColor(Color.White);

                this.treatMessage(readMessage());

                writeMessage(firstPlayerIA.chooseColor());

                if (firstPlayerIA.getColor() == Color.White) {
                    secondPlayerDistant.setColor(Color.Black);
                }

                turn = Color.Black;
                break;

            case Message.BLACK: // Distant player chose black pawn
                secondPlayerDistant.setColor(Color.Black);
                firstPlayerIA.setColor(Color.White);
                break;

            case Message.WHITE: // Distant player chose white pawn
                secondPlayerDistant.setColor(Color.White);
                firstPlayerIA.setColor(Color.Black);
                break;

            case Message.STOP: // Other player pass his turn
                if (firstPlayerIA.getColor() == Color.Black){
                    writeMessage(firstPlayerIA.playInTray(this.tray));
                    nextPlayer();
                }

                lastTurn = true;
                quit = true;

                break;

            case Message.END: // End of the game
                this.end = true;
                this.quit = true;

                if (onLineMode)
                    this.clientTCP.disconnect();
                break;

            default:
                if (onLineMode){
                    while (message.length() < 5){
                        message += clientTCP.read();
                    }

                }
                if (message.length() == 5)
                {
                    // get lines and columns
                    int line1 = Integer.parseInt(String.valueOf(message.charAt(0)));
                    int column1 = Integer.parseInt(String.valueOf(message.charAt(1)));
                    int line2 = Integer.parseInt(String.valueOf(message.charAt(3)));
                    int column2 = Integer.parseInt(String.valueOf(message.charAt(4)));

                    // if distant player wants to place a bridge
                    if (message.charAt(2) == '-') {
                        if (!this.tray.placeBridge(line1, column1, line2, column2))
                            printInConsole("Can't place distant player bridge !");
                    } else { // distant player wants to place 2 pawns
                        if (!this.tray.placePawn(line1, column1, secondPlayerDistant.getColor())
                                || !this.tray.placePawn(line2, column2, secondPlayerDistant.getColor()))
                            printInConsole("Can't place distant pawn !");
                    }
                    if (lastTurn)
                        this.quit = true;
                    else
                        this.nextPlayer();
                }
                else {
                    printInConsole("Strange message received ! : \"" + message + "\"");
                }
                break;
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

    public static void displayInConsole(Tray tray) {
        for (int line = 0 ; line < tray.getSize() ; line++){
            for (int column = 0 ; column < tray.getSize(); column++){
                System.out.print("+----");
            }
            System.out.print("+\n");

            for (int column = 0 ; column < tray.getSize(); column++){
                System.out.print("| " );
                if (tray.getCell(line, column).getPawn() != null) {
                    switch (tray.getCell(line, column).getPawn().getColor()) {
                        case Black:
                            if (tray.getCell(line, column).getPawn().hasBridge())
                                System.out.print("B/ ");
                            else
                                System.out.print("B  ");
                            break;
                        case White:
                            if (tray.getCell(line, column).getPawn().hasBridge())
                                System.out.print("W/ ");
                            else
                                System.out.print("W  ");
                            break;
                        default:
                            break;
                    }
                } else{
                    if (tray.getCell(line, column).isLocked())
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


    private String readMessage() {
        if (onLineMode){
            return clientTCP.read();
        }
        System.out.println("Read console :");
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    private void writeMessage(String message){
        if (onLineMode)
            clientTCP.write(message);
        else
            printInConsole(message);
        if (message == Message.STOP){
            lastTurn = true;
        }
    }

    private void printInConsole(String message){
        if (verbose)
            System.out.println(message);
    }

}
