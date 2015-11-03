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

    int depth = 2;
    Tray tray = null;
    Color color = null;
    Color oppositeColor = null;
    String answer = null;
    Boolean pruning = false;

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

        if (/*this.tray.nbPawnAllowed(this.oppositeColor) >= 2 &&*/ actualDepth < this.depth) {

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
                    // else if it's the good color, try building a bridge
                    else if (this.tray.getCell(line1, column1).getPawn() != null
                        && tray.getCell(line1, column1).getPawn().getColor() == this.oppositeColor){

                        Pawn base1 = this.tray.getCell(line1, column1).getPawn();

                        List<Pawn> pawnList = this.tray.getPawns(this.oppositeColor);
                        int size = pawnList.size();
                        for (int i = 0 + 1; i < size; i++) {
                            Pawn base2 = pawnList.get(i);

                            if (base2 != null) {


                                if (this.tray.placeBridge(base1.getCell(), base2.getCell())){
                                    // algorithme begin here
                                    int returned = MAX(actualDepth++, alpha, beta);

                                    min = Math.min(min, returned);
                                    if (actualDepth == 0) {
                                        this.answer = Message.bridge(base1.getCell().getLine(), base1.getCell().getColumn(),
                                                base2.getCell().getLine(), base2.getCell().getColumn());
                                    }

                                    this.tray.cancelBridge(base1.getCell(), base2.getCell());

                                    // pruning
                                    if (pruning){
                                        if (returned < alpha.getValue()){
                                            return alpha.getValue();
                                        }
                                        beta.setValue( Math.min(beta.getValue(), returned));
                                    }
                                }
                            }
                        }
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

            // place first pawn
            for (int line1 = 0; line1 < this.tray.getSize(); line1++) {
                for (int column1 = 0; column1 < this.tray.getSize(); column1++) {

                    if (this.tray.placePawn(line1, column1, this.color)) {

                        // then place the second pawn
                        for (int line2 = line1; line2 < this.tray.getSize(); line2++) {
                            for (int column2 = column1+1; column2 < this.tray.getSize(); column2++) {

                                if (this.tray.placePawn(line2, column2, this.color)){

                                    // try max
                                    int returned = MIN(actualDepth+1, alpha, beta);

                                    max = Math.max(max, returned);
                                    if (actualDepth == 0) {
                                        this.answer = Message.firstPawn(line1, column1);
                                        this.answer += Message.secondPawn(line2, column2);
                                    }
                                    // delete second pawn placed
                                    this.tray.cancelPawn(line2, column2);

                                    // pruning alpha beta
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
                        // delete first pawn placed
                        this.tray.cancelPawn(line1, column1);
                    }

                    // else if it's the good color, try building a bridge
                    else if (this.tray.getCell(line1, column1).getPawn() != null
                            && tray.getCell(line1, column1).getPawn().getColor() == this.color){

                        // get pawn from the cell
                        Pawn base1 = this.tray.getCell(line1, column1).getPawn();

                        // try to make a bridge with all other pawns
                        List<Pawn> pawnList = this.tray.getPawns(this.color);
                        int size = pawnList.size();
                        for (int i = 0 + 1; i < size; i++) {
                            Pawn base2 = pawnList.get(i);

                            if (base2 != null) {

                                // try make bridge
                                if (this.tray.placeBridge(base1.getCell(), base2.getCell())){

                                    // called MAX then
                                    int returned = MAX(actualDepth++, alpha, beta);

                                    max = Math.max(max, returned);
                                    if (actualDepth == 0) {
                                        this.answer = Message.bridge(base1.getCell().getLine(), base1.getCell().getColumn(),
                                                base2.getCell().getLine(), base2.getCell().getColumn());
                                    }

                                    this.tray.cancelBridge(base1.getCell(), base2.getCell());

                                    // pruning
                                    if (pruning){
                                        if (returned >= beta.getValue()){
                                            return returned;
                                        }
                                        alpha.setValue(Math.max(alpha.getValue(), returned));
                                    }

                                }
                            }
                        }
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
