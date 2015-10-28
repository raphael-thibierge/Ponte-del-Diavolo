package IA;

import Game.Cell;
import Game.Color;
import Game.Pawn;
import Game.Tray;
import Model.GameModel;
import Network.Message;

import java.util.List;

/**
 * Created by raph on 19/10/15.
 */
public class MinMax {

    int depth = 4;
    Tray tray = null;
    Color color = null;
    Color oppositeColor = null;
    String answer = null;
    Boolean pruning = true;

    public String minMax(Tray tray, Color color)
    {
        this.tray = tray;
        int actualDepth = 0;
        this.color = color;
        if (this.color == Color.White)
            this.oppositeColor = Color.Black;
        else this.oppositeColor = Color.White;
        long i = System.currentTimeMillis();
        MAX(0, new MutableInteger(-1000), new MutableInteger(1000));
        long j = System.currentTimeMillis();

        long time = (j - i) ;
        System.out.println("Time end : " + time);

        if (answer != null && answer.length() == 5) {

            tray.placeFromString(this.answer, this.color);
        }
        else answer = null;
        return this.answer;


        /*
        * NOT FINISHED...
        * */
    }


    private int MIN(int actualDepth, MutableInteger alpha, MutableInteger beta)
    {
        // equals positive infinite
        int min = 100;

        if (this.tray.nbPawnAllowed(this.oppositeColor) >= 2 && actualDepth < this.depth) {
            for (int line1 = 0; line1 < this.tray.getSize(); line1++) {
                for (int column1 = 0; column1 < this.tray.getSize(); column1++) {

                    if (this.tray.placePawn(line1, column1, this.color)) {

                        for (int line2 = line1; line2 < this.tray.getSize(); line2++) {
                            for (int column2 = column1 + 1; column2 < this.tray.getSize(); column2++) {

                                if (this.tray.placePawn(line2, column2, this.oppositeColor)) {

                                    int returned = MAX(actualDepth + 1, alpha, beta);
                                    min = Math.min(returned, min);

                                    this.tray.cancelPawn(line2, column2);

                                    // pruning
                                    if (pruning){
                                        if (returned < alpha.getValue()){
                                            this.tray.cancelPawn(line1, column1);
                                            return alpha.getValue();
                                        }
                                        beta.setValue( Math.min(beta.getValue(), returned));
                                    }
                                }
                            }
                        }
                        this.tray.cancelPawn(line1, column1);
                    }
                }
            }
        }

        else return evaluate();

        return min;
    }

    private int MAX(int actualDepth, MutableInteger alpha, MutableInteger beta)
    {
        // equals negative infinite
        int max = -100;

        if (this.tray.nbPawnAllowed(this.color) >= 2 && actualDepth < depth) {
            for (int line1 = 0; line1 < this.tray.getSize(); line1++) {
                for (int column1 = 0; column1 < this.tray.getSize(); column1++) {

                    if (this.tray.placePawn(line1, column1, this.color)) {

                        for (int line2 = line1; line2 < this.tray.getSize(); line2++) {
                            for (int column2 = column1+1; column2 < this.tray.getSize(); column2++) {

                                if (this.tray.placePawn(line2, column2, this.color)){

                                    int returned = MIN(actualDepth+1, alpha, beta);

                                    max = Math.max(max, returned);
                                    if (actualDepth == 0) {
                                        this.answer = Message.firstPawn(line1, column1);
                                        this.answer += Message.secondPawn(line2, column2);
                                    }
                                    this.tray.cancelPawn(line2, column2);

                                    // pruning
                                    if (pruning){
                                        if (returned >= beta.getValue()){
                                            this.tray.cancelPawn(line1, column1);
                                            return returned;
                                        }
                                        alpha.setValue(Math.max(alpha.getValue(), returned));
                                    }
                                }
                            }
                        }
                    this.tray.cancelPawn(line1, column1);
                    }
                }
            }
        }
        else return evaluate();

        return max;
    }

    private int evaluate() {

        int scoreColor = GameModel.scoreFromTrayForColor(this.color, this.tray);
        int scoreOppositeColor = GameModel.scoreFromTrayForColor(this.oppositeColor, this.tray);

        return scoreColor-scoreOppositeColor;
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
                        if (tray.placeBridge(line1, column1, line2, column2)){
                            this.answer = Message.bridge(line1, column1, line2, column2);
                           // this.bridgePlaced = true;
                            return;
                        }
                    }
                }
            }
        }
    }


}
