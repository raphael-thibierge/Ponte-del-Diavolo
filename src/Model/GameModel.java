package Model;

import Game.*;
import IA.AI;
import IA.Strategy;
import Network.ClientTCP;
import Network.Message;
import View.MainView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.Math.abs;

/**
 * Created by raphael on 12/10/2015.
 */
public class GameModel extends Thread{

    private Tray tray;
    private ClientTCP clientTCP;

    private Player firstPlayerIA;
    private Player secondPlayerDistant;
    private Player currentPlayer = null;

    private boolean onLineMode = false;
    private boolean verbose = true;

    private boolean quit = false;
    private boolean end = false;
    private boolean lastTurn = false;
    private Color turn;

    private MainView mainView;
    private GameMode gameMode = GameMode.OnePlayerVsAI;

    public GameModel(String serveurIPAddress, int port, int size){
        // init tray
        this.tray = new Tray();
        tray.init(abs(size));
        // init tcp client
        try{
            clientTCP = new ClientTCP(serveurIPAddress, port);
            printInConsole("Connected");
            this.onLineMode = true;
            this.gameMode = GameMode.AiVSDistant;
        } catch (IOException e) {
           // e.printStackTrace();
            printInConsole("Not connected");
            this.onLineMode = false;
        }
    }

    public GameModel(int size){
        // init tray
        this.tray = new Tray();
        tray.init(abs(size));
        this.onLineMode = false;
    }

    @Override
    public void run() {
        super.run();

        switch (this.gameMode){
            case OnePlayerVsAI:
                runOnePlayerVsAI();
                break;

            case TwoPLayer:
                runTwoPlayers();
                break;

            case AiVSDistant:
                runVsDistant();
                break;

            default:
                break;
        }
    }

    private void nextPlayer() {
        if (this.turn == Color.White)
            this.turn = Color.Black;
        else
            turn = Color.White;

        if (currentPlayer.equals(firstPlayerIA)){
            currentPlayer = secondPlayerDistant;
        } else if (currentPlayer.equals(secondPlayerDistant))
            currentPlayer = firstPlayerIA;
    }

    public void runWithString(String string, int size, Color ia){

        Color second ;
        if (ia == Color.White)
            second = Color.Black;
        else
            second = Color.White;


        if (this.onLineMode) {
            firstPlayerIA = new AI(ia);
            secondPlayerDistant = new DistantPlayer(second, clientTCP);
        }else {
            firstPlayerIA = new AI(ia);
            secondPlayerDistant = new DistantPlayer(second, clientTCP);
        }

        turn = tray.initWithString(size, string);
        printInConsole(turn.toString());

        display();

        if (turn == ia){
            writeMessage(firstPlayerIA.playInTray(tray));
            display();
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
    }

    public void runTest(){
        firstPlayerIA = new AI(Color.White);
        secondPlayerDistant = new AI(Color.Black);
        tray.init(10);
        turn = Color.White;

        if (firstPlayerIA instanceof AI)
            ((AI) firstPlayerIA).setCurrentStrategy(Strategy.BUILD_ISLAND);

        if (secondPlayerDistant instanceof AI)
            ((AI) secondPlayerDistant).setCurrentStrategy(Strategy.MIN_MAX);


        while (firstPlayerIA.canPlay(tray) || secondPlayerDistant.canPlay(tray)) {
            if (turn == Color.Black) {
                String string = secondPlayerDistant.playInTray(tray);
                if (string.equals("a")) break;
                printInConsole(string);
            } else {
                String string = firstPlayerIA.playInTray(tray);
                if (string.equals("a")) break;
                printInConsole(string);
            }
            nextPlayer();
            display();
        }

        printInConsole(" Black : " + Integer.toString(scoreFromTrayForColor(Color.Black, tray)));
        printInConsole(" White : " + Integer.toString(scoreFromTrayForColor(Color.White, tray)));

    }

    private void runOnePlayerVsAI() {

        firstPlayerIA = new Manual(Color.White);
        secondPlayerDistant = new AI(Color.Black);

        currentPlayer = firstPlayerIA;

        while (firstPlayerIA.canPlay(tray) || secondPlayerDistant.canPlay(tray)){
            if (currentPlayer instanceof AI){

                currentPlayer.playInTray(tray);

                nextPlayer();

            }
            else {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void runVsDistant() {
        if (this.onLineMode) {

            // run game
            while (!this.quit) {
                // treat messages
                this.treatMessage(this.readMessage());

                display();

                if (!quit && this.turn == this.firstPlayerIA.getColor()) {

                    writeMessage(firstPlayerIA.playInTray(tray));

                    this.nextPlayer();

                    display();
                }
            }

            printInConsole("Player 1 score : " + scoreFromTrayForColor(firstPlayerIA.getColor(), tray));
            printInConsole("Player 2 score : " + scoreFromTrayForColor(secondPlayerDistant.getColor(), tray) + "\n");

            //end of the game, wait for end signal
            if (!this.end)
                this.treatMessage(this.readMessage());
        }
    }

    private void runTwoPlayers() {

        firstPlayerIA = new Manual(Color.White);
        secondPlayerDistant = new Manual(Color.Black);
        currentPlayer = firstPlayerIA;


    }

    public void currentPlayerPlacePawns(int line, int column){
        if (currentPlayer instanceof Manual){
            if (tray.placePawn(line, column, currentPlayer.getColor())){
                currentPlayer.incrementNbPawnPlaced();
                if (currentPlayer.getNbPawnPlaced() == 2) {
                    currentPlayer.resetNbPawnPlaced();
                    nextPlayer();
                }
            }
        }
    }

    public void currentPlayerPlaceBridge(Cell cell1, Cell cell2){
        if (currentPlayer instanceof Manual) {
            if (cell1 != null && cell1.getPawn() != null && cell1.getPawn().getColor() != null && currentPlayer != null
                    && cell1.getPawn().getColor() == currentPlayer.getColor()) {
                if (this.tray.placeBridge(cell1, cell2))
                    nextPlayer();

            }
        }
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

                String msg = readMessage();
                this.treatMessage(msg);

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

               try {
                    /*// get lines and columns
                    int line1 = Integer.parseInt(String.valueOf(message.charAt(0)));
                    int column1 = Integer.parseInt(String.valueOf(message.charAt(1)));
                    int line2 = Integer.parseInt(String.valueOf(message.charAt(3)));
                    int column2 = Integer.parseInt(String.valueOf(message.charAt(4)));

                    // if distant player wants to place a bridge
                    if (message.charAt(2) == '-') {
                        if (!this.tray.placeBridge(line1, column1, line2, column2))
                            System.err.print("Can't place distant player bridge !");
                    }

                    else { // distant player wants to place 2 pawns
                        if (!this.tray.placePawn(line1, column1, secondPlayerDistant.getColor())
                                || !this.tray.placePawn(line2, column2, secondPlayerDistant.getColor()))
                            System.err.print("Can't place distant pawn !");
                    }*/

                    if (!tray.placeFromString(message, secondPlayerDistant.getColor()))
                        printInConsole("GRRRR");

                    if (lastTurn)
                        this.quit = true;
                    else
                        this.nextPlayer();
                } catch (Exception e){
                    System.err.print("Error found in LECTURE CASE DEFAULT");
                }
                break;
        }
    }

    public static int scoreFromTrayForColor(Color color, Tray tray) {

        if (tray != null &&  tray.isInitialised() && color != null ){

            List<List<SandBar>>linkedSandbarList = new ArrayList<>();
            List<Pawn> pawnsTreated = new ArrayList<>();
            // for each pawn not treated
            for (Pawn pawn : tray.getPawns(color)) {
                if (pawn != null && !pawnsTreated.contains(pawn)) {

                    // try get sandbar in cell
                    SandBar sandBar = pawn.getSandBar();

                        for (Pawn sandbarPawn : sandBar.getPawnList()) {

                            Bridge bridge = sandbarPawn.getBridge();

                            if (bridge != null) {
                                boolean founded = false;
                                for (List<SandBar> linkedSandbar : linkedSandbarList) {
                                    if (linkedSandbar.contains(bridge.getBase1().getSandBar())
                                            || linkedSandbar.contains(bridge.getBase2().getSandBar())) {

                                        founded = true;

                                        if (!linkedSandbar.contains(bridge.getBase1().getSandBar()))
                                            linkedSandbar.add(bridge.getBase1().getSandBar());

                                        if (!linkedSandbar.contains(bridge.getBase2().getSandBar()))
                                            linkedSandbar.add(bridge.getBase2().getSandBar());
                                    }
                                }

                                if (!founded) {
                                    // create a new linked sandbar list
                                    List<SandBar> linkedSandbar = new ArrayList<>();
                                    linkedSandbar.add(bridge.getBase1().getSandBar());
                                    linkedSandbar.add(bridge.getBase2().getSandBar());
                                    linkedSandbarList.add(linkedSandbar);
                                }
                            } else if (sandBar.isIsland()) {

                                boolean founded = false;
                                for (List<SandBar> sandBarList : linkedSandbarList) {
                                    if (sandBarList.contains(sandBar)) {
                                        founded = true;
                                    }
                                }
                                if (!founded) {
                                    List<SandBar> sandBarList = new ArrayList<>();
                                    sandBarList.add(sandBar);
                                    linkedSandbarList.add(sandBarList);
                                }
                            }
                            pawnsTreated.add(sandbarPawn);
                    }
                }
            }

            int result = 0 ;
            for (List<SandBar> sandBarList : linkedSandbarList){
                int cpt = 0;

                for (SandBar sandBar : sandBarList){
                    if (sandBar.isIsland())
                        cpt++;
                }
                result += scoreFromNbIsland(cpt);
            }

            return result;
        }
        return -1;
    }

    public static int scoreFromNbIsland(int nbLinkedIsland){
        if (nbLinkedIsland >= 0)
            return ( nbLinkedIsland * ( nbLinkedIsland + 1 ) ) / 2 ;
        return 0;
    }

    public static void displayInConsole(Tray tray) {
          /*final String ANSI_BLACK = "\u001B[30m";
          final String ANSI_RED = "\u001B[31m";
          final String ANSI_GREEN = "\u001B[32m";
          final String ANSI_YELLOW = "\u001B[33m";
          final String ANSI_BLUE = "\u001B[34m";
          final String ANSI_PURPLE = "\u001B[35m";
          final String ANSI_CYAN = "\u001B[36m";
        final String ANSI_RESET = "\u001B[0m";*/

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
                                System.out.print(/*ANSI_BLUE +*/ "B/ " /*+ ANSI_RESET*/);
                            else
                            System.out.print(/*ANSI_BLUE +*/ "B  " /*+ ANSI_RESET*/);
                            break;
                        case White:
                            if (tray.getCell(line, column).getPawn().hasBridge())
                                System.out.print(/*ANSI_GREEN +*/ "W/ " /*+ ANSI_RESET*/);
                            else
                                System.out.print(/*ANSI_GREEN +*/ "W  " /*+ ANSI_RESET*/);
                            break;
                        default:
                            break;
                    }
                } else{
                    if (tray.getCell(line, column).isLocked())
                        System.out.print(" x ");
                    else if (tray.getCell(line, column).isTaken())
                        System.out.print(" - ");

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

    public Tray getTray() {
        return tray;
    }

    public void setMainView(MainView mainView) {
        this.mainView = mainView;
    }

    private void display(){
        if (verbose)
            displayInConsole(this.tray);
        if (mainView != null)
            mainView.repaint();
    }


    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }
}
