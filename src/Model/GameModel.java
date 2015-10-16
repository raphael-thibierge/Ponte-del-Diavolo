package Model;

import ArthurIA.ArthurIA;
import Game.Color;
import Game.SandBar;
import Game.Tray;
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

    private boolean quit = false;
    private boolean end = false;
    private Color turn;

    public GameModel(String serveurIPAddress, int port, int size){
        // init tray
        this.tray = new Tray();
        tray.init(abs(size));
        // init tcp client
        try{
            clientTCP = new ClientTCP(serveurIPAddress, port);
            this.onLineMode = true;
        } catch (IOException e) {
           // e.printStackTrace();
            this.onLineMode = false;
            clientTCP = null;
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
        // connect to serveur
        if ( this.onLineMode && this.clientTCP.isConnected()) {

            firstPlayerIA = new IA(Color.White);
            secondPlayerDistant = new DistantPlayer(Color.Black, clientTCP);

            if ( this.clientTCP.isConnected()) {

                // run game
                while(!this.end) {
                    // to treating server messages
                    this.treatMessage(clientTCP.read());

                    if (!quit && this.turn == this.firstPlayerIA.getColor()) {
                        this.clientTCP.write(firstPlayerIA.playInTray(tray));
                        this.nextPlayer();
                    }
                    displayInConsole(this.tray);
                }

                System.out.println("Player 1 score : " + scoreFromTrayForColor(firstPlayerIA.getColor(), tray));
                System.out.println("Player 2 score : " + scoreFromTrayForColor(secondPlayerDistant.getColor(), tray) + "\n");

                //end of the game, disconnect of serveur
                clientTCP.disconnect();
            }
        }

        else
        {
            firstPlayerIA = new ArthurIA(Color.White);
            secondPlayerDistant = new Manual(Color.Black);
            // run game
            while(!this.end) {
                // to treating server messages
                this.treatMessage(readConsole());

                if (!quit && this.turn == this.firstPlayerIA.getColor()) {
                    System.out.println("Played by IA : " + firstPlayerIA.playInTray(tray));
                    this.nextPlayer();
                }
                displayInConsole(this.tray);
            }

            System.out.println("Player 1 score : " + scoreFromTrayForColor(firstPlayerIA.getColor(), tray));
            System.out.println("Player 2 score : " + scoreFromTrayForColor(secondPlayerDistant.getColor(), tray) + "\n");

        }
    }

    public void treatMessage(String message) {

        switch (message) {

            case "" :
                break;

            case " ":
                break;

            case Message.FIRST: // IA is the first player
                // init players

                firstPlayerIA.setColor(Color.White);
                secondPlayerDistant.setColor(Color.Black);

                // IA place two pawn on the tray
                String msg = firstPlayerIA.playInTray(this.tray);
                if (this.onLineMode)
                    this.clientTCP.write(msg);
                else
                    printInConsole(msg);


                turn = Color.Black;
                break;

            case Message.SECOND: // IA is the second player
                // init player

                firstPlayerIA.setColor(Color.Black);
                secondPlayerDistant.setColor(Color.White);

                if (this.onLineMode)
                    this.treatMessage(clientTCP.read());
                else
                    this.treatMessage(readConsole());

                String colorChoice = firstPlayerIA.chooseColor();
                if (onLineMode)
                    clientTCP.write(colorChoice);
                else
                    printInConsole(colorChoice);

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
                firstPlayerIA.playInTray(this.tray);
                this.quit = true;
                break;

            case Message.END: // End of the game
                this.end = true;
                break;

            default:
                if (message.length() == 5) {
                    // get lines and columns
                    int line1 = Integer.parseInt(String.valueOf(message.charAt(0)));
                    int column1 = Integer.parseInt(String.valueOf(message.charAt(1)));
                    int line2 = Integer.parseInt(String.valueOf(message.charAt(3)));
                    int column2 = Integer.parseInt(String.valueOf(message.charAt(4)));

                    // if distant player wants to place a bridge
                    if (message.charAt(2) == '-') {
                        if (!this.tray.placeBridge(line1, column1, line2, column2))
                            System.err.println("Can't place distant player bridge !");
                    } else { // distant player wants to place 2 pawns
                        if (!this.tray.placePawn(line1, column1, secondPlayerDistant.getColor())
                                || !this.tray.placePawn(line2, column2, secondPlayerDistant.getColor()))
                            System.err.println("Can't place distant pawn !");
                    }
                    this.nextPlayer();
                } else {
                    System.err.println("Strange message received ! : \"" + message + "\"");
                    if (!onLineMode)
                        treatMessage(readConsole());
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


    private String readConsole()
    {
        System.out.println("Read console :");
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    private void printInConsole(String message){
        System.out.println(message);
    }


}
