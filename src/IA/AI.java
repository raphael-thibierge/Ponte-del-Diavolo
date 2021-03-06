package IA;

import Game.*;
import Model.Player;
import Network.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.abs;
import static java.lang.Math.random;

/**
 * Created by raphael on 13/10/15.
 */
public class AI extends Player {

    private Strategy lastStrategy = null;
    private Strategy currentStrategy = null;
    private int turn = 0;

    private Tray tray = null;
    private int nbFreeCells = 0;
    private boolean bridgePlaced;

    String answer = null;
    int cptIsland = 0;


    public AI(Color color)
    {
        super(color);
        setCurrentStrategy(Strategy.MIN_MAX);
    }

    @Override
    public String playInTray(Tray tray) {
        System.out.println("AI " + this.color.name() + " :");
        turn++;
        /*
        *   Each turn init
        * */
        // At each
        this.answer = null;

        this.bridgePlaced = false;

        this.tray = tray; // maybe provisory
        // update avalable cell on tray for AI
        this.nbFreeCells = countNbFreeCell(this.tray);

        this.nbPawnPlaced = 0;

        switch (this.currentStrategy){

            case FILL:
                fillTray();
                break;

            case BUILD_ISLAND:
                if (cptIsland >= 2)
                    placeFirstBridge();
                /*if (nbFreeCells < 8){
                    minMax();
                    if (this.answer != null && this.answer.length() == 5)
                        return this.answer;
                    System.out.println("Null answer");

                }*/

                buildIsland();

                if (!bridgePlaced && nbPawnPlaced < 2)
                    if (nbFreeCells > 10)
                        fullRandom();
                    else
                        fillTray();
                break;

            case RANDOM:
                fullRandom();
                if (!bridgePlaced && nbPawnPlaced < 2)
                    fillTray();
                break;

            case MIN_MAX:
                minMax();
                System.out.println("after MIN MAX");
                if (nbPawnPlaced < 2) {
                    if (nbFreeCells > 6){
                        buildIsland();
                        fullRandom();
                        System.out.println("after full random");

                    }
                    else{
                        fillTray();
                        System.out.println("after fill tray");

                    }
                }
                break;
        }


        if (this.answer == null || (nbPawnPlaced < 2 && !bridgePlaced))
            return Message.STOP;
        return this.answer;
    }


    private void minMax(){
        if (turn >= (this.tray.getSize())/2 ){
            if (this.nbFreeCells >= 2){
                MinMax minMax = new MinMax();
                this.answer = minMax.minMax(this.tray, this.color);
                if (answer != null){
                    System.out.println(">> AI : " +answer);
                    this.nbPawnPlaced = 2;
                }
            }
        }
    }

    private void placeFirstBridge(){
        List<Pawn> pawnList = tray.getPawns(color);
        if (pawnList != null){
            // tests all bridge possibilities
            for (int i = 0 ; i < pawnList.size() ; i ++){
                for (int j = 0 ; j < pawnList.size() ; j++){

                    Cell cell1 = pawnList.get(i).getCell();
                    Cell cell2 = pawnList.get(j).getCell();
                    if (cell1 != null && cell2 != null){

                        // get lines and columns
                        int line1 = cell1.getLine();
                        int column1 = cell1.getColumn();
                        int line2 = cell2.getLine();
                        int column2 = cell2.getColumn();

                        // test bridge
                        if (tray.canBridge(line1, column1, line2, column2)
                                &&( (tray.getCell(line1,column1).getPawn().belongsToIsland() && tray.getCell(line2,column2).getPawn().getSandBar().getSize() > 2)
                                || (tray.getCell(line2,column2).getPawn().belongsToIsland() && tray.getCell(line1,column1).getPawn().getSandBar().getSize() > 2)
                                )){
                            tray.placeBridge(line1, column1, line2, column2);
                            this.answer = Message.bridge(line1, column1, line2, column2);
                            this.bridgePlaced = true;
                            return;
                        }
                    }
                }
            }
        }
    }

    private int countNbFreeCell(Tray tray){
        int cpt = 0;
        for (int line = 0 ; line < tray.getSize() ; line++){
            for (int column = 0 ; column < tray.getSize() ; column++){
                if (tray.getCell(line, column).pawnAllowedHere(color)){
                    cpt++;
                }
            }
        }
        return cpt;
    }

    private void buildIsland() {
        /*
        * If there a lot of free cells, AI can choose randomly the place of the Island
        * then it has to choose a place
        * then it need to verify if it's possible to build an Island with not to much risk
        * */


        // we need to find a sandbar to finish the Island
        lastStrategy = Strategy.BUILD_ISLAND;
        if (lastStrategy == Strategy.BUILD_ISLAND){
            List<Pawn> pawnList = this.tray.getPawns(this.color);
            List<SandBar> sandBarsTreated = new ArrayList<>();

            /*
            * First, try to finish the Islands
            * */
            if (pawnList != null && !pawnList.isEmpty())
            {
                for (int i = 0 ; i < pawnList.size() ; i++){
                    Pawn pawn = pawnList.get(i);
                    // if pawn's sandbar is not an island and has not been treated
                    if (!pawn.getSandBar().isIsland() && !sandBarsTreated.contains(pawn.getSandBar())){
                        sandBarsTreated.add(pawn.getSandBar());

                        // try to put a pawn near each pawn of the sandbar

                        List<Pawn> sandbarPawnList = new ArrayList<>();
                        // to have no concurence
                        for (Pawn sandBarPawn : pawn.getSandBar().getPawnList()) {
                            sandbarPawnList.add(sandBarPawn);

                        }

                        for (Pawn sandBarPawn : sandbarPawnList) {

                            for (Cell nearbyCell : sandBarPawn.getCell().getNearbyBoxesOrthogonal().values()) {
                                if (nearbyCell != null && this.tray.placePawn(nearbyCell.getLine(), nearbyCell.getColumn(), this.color)) {
                                    if (nbPawnPlaced == 0) {
                                        answer = Message.firstPawn(nearbyCell.getLine(), nearbyCell.getColumn());
                                        if (nearbyCell.getPawn().belongsToIsland())
                                            cptIsland++;
                                        nbPawnPlaced++;
                                    } else {
                                        answer += Message.secondPawn(nearbyCell.getLine(), nearbyCell.getColumn());
                                        if (nearbyCell.getPawn().belongsToIsland())
                                            cptIsland++;
                                        nbPawnPlaced++;
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void fullRandom(){
        int line, column;
        String string ="";
        Random random = new Random();
        int nbTry = 0; // security
        for (int i = nbPawnPlaced; i < 2; i++) {
            do {
                line = abs(random.nextInt() % this.tray.getSize());
                column = abs(random.nextInt() % this.tray.getSize());
                nbTry++;
            } while (nbTry < 1000 && !tray.getCell(line, column).pawnAllowedHere(this.color));

            // security
            if (nbTry == 1000){
                return;
            }

            else if (tray.getCell(line, column).pawnAllowedHere(this.color) && this.nbPawnPlaced < 2) {
                if (this.nbPawnPlaced == 0){
                    this.tray.placePawn(line, column, this.color);
                    this.nbPawnPlaced++;
                    answer = Message.firstPawn(line, column);
                } else if (this.nbPawnPlaced == 1){
                    this.tray.placePawn(line, column, this.color);
                    this.nbPawnPlaced++;
                    answer += Message.secondPawn(line, column);
                    return;
                }
            }

        }
    }

    private void fillTray(){
        for (int line = 0 ; line < tray.getSize() ; line++){
            for (int colum = 0 ; colum < tray.getSize() ; colum++){
                if (nbPawnPlaced < 2 && tray.placePawn(line, colum, this.color)){
                    if (this.nbPawnPlaced == 0 ){
                        answer = Message.firstPawn(line, colum);
                        this.nbPawnPlaced++;
                    } else {
                        answer += Message.secondPawn(line, colum);
                        this.nbPawnPlaced++;
                        return;
                    }
                }
            }
        }
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
        System.out.println("Color choosen by AI : " + this.color.name());
        return colorChoice;
    }

    public void setCurrentStrategy(Strategy currentStrategy) {
        this.currentStrategy = currentStrategy;
    }
}
