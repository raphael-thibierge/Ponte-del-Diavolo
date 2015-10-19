package IA;

import Game.Color;
import Game.Tray;
import Model.GameModel;
import Network.Message;

/**
 * Created by raph on 19/10/15.
 */
public class MinMax {

    int depth =  3;
    Tray tray = null;
    Color color = null;
    Color oppositeColor = null;
    String answer = null;

    public String minMax(Tray tray, Color color)
    {
        this.tray = tray;
        int actualDepth = 0;
        this.color = color;
        if (this.color == Color.White)
            this.oppositeColor = Color.Black;
        else this.oppositeColor = Color.White;
        long i = System.currentTimeMillis();
        MAX(0);
        long j = System.currentTimeMillis();

        long time = (j - i) ;
        System.out.println("Time end : " + time);


        int line1 = Integer.parseInt(String.valueOf(answer.charAt(0)));
        int column1 = Integer.parseInt(String.valueOf(answer.charAt(1)));
        int line2 = Integer.parseInt(String.valueOf(answer.charAt(3)));
        int column2 = Integer.parseInt(String.valueOf(answer.charAt(4)));

        // if distant player wants to place a bridge
        if (answer.charAt(2) == '-') {
            this.tray.placeBridge(line1, column1, line2, column2);
        } else { // distant player wants to place 2 pawns
            this.tray.placePawn(line1, column1, this.color);
            this.tray.placePawn(line2, column2, this.color);
        }

        return this.answer;
    }


    private int MIN(int actualDepth)
    {
        // equals positive infinite
        int min = 100000;

        if (this.tray.nbPawnAllowed(this.oppositeColor) >= 2 && actualDepth < this.depth) {
            for (int line1 = 0; line1 < this.tray.getSize(); line1++) {
                for (int column1 = 0; column1 < this.tray.getSize(); column1++) {

                    if (this.tray.placePawn(line1, column1, this.color)) {

                        for (int line2 = line1; line2 < this.tray.getSize(); line2++) {
                            for (int column2 = column1+1; column2 < this.tray.getSize(); column2++) {

                                if (this.tray.placePawn(line2, column2, this.oppositeColor)) {

                                    int max = MAX(actualDepth+1);

                                    if (min >= max) {
                                        min = max;
                                    }
                                    this.tray.cancelPawn(line2, column2);
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

    private int MAX(int actualDepth)
    {
        // equals negative infinite
        int max = -100000;

        if (this.tray.nbPawnAllowed(this.color) >= 2 && actualDepth < depth) {
            for (int line1 = 0; line1 < this.tray.getSize(); line1++) {
                for (int column1 = 0; column1 < this.tray.getSize(); column1++) {

                    if (this.tray.placePawn(line1, column1, this.color)) {

                        for (int line2 = line1; line2 < this.tray.getSize(); line2++) {
                            for (int column2 = column1+1; column2 < this.tray.getSize(); column2++) {

                                if (this.tray.placePawn(line2, column2, this.color)){

                                    int min = MIN(actualDepth+1);

                                    if (max <= min) {

                                        max = min;

                                        if (actualDepth == 0) {
                                            this.answer = Message.firstPawn(line1, column1);
                                            this.answer += Message.secondPawn(line2, column2);
                                        }
                                    }
                                    this.tray.cancelPawn(line2, column2);
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

        if (scoreColor > scoreOppositeColor){
            return 1000;
        } else if (scoreColor < scoreOppositeColor){
            return -1000;
        } else {
            return 0;
        }
    }


}