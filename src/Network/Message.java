package Network;

import static java.lang.Math.abs;

/**
 * Created by raphael on 14/10/15.
 */
public class Message {
    final public static String FIRST = "P";
    final public static String SECOND = "S";
    final public static String WHITE = "c";
    final public static String BLACK = "f";
    final public static String STOP = "a";
    final public static String END = "F";

    public static String firstPawn(int line, int column){
        return Integer.toString(abs(line)) + Integer.toString(abs(column));
    }

    public static String secondPawn(int line, int column){
        return "+" + Integer.toString(abs(line)) + Integer.toString(abs(column));
    }

    public static String bridge(int line1, int column1, int line2, int column2){
        return firstPawn(line1, column1) + "-" + firstPawn(line2, column2);
    }

}
