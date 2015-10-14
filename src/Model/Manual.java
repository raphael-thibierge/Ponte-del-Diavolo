package Model;

import Game.Color;
import Game.Tray;

import java.util.Scanner;

/**
 * Created by raphael on 13/10/15.
 */
public class Manual extends Player {

    public Manual(int number)
    {
        super(number);
    }


    @Override
    public String playInTray(Tray tray) {
        String string = "a";
        if (tray != null){

            System.out.println("It's player " + this.number + " turn !");


            Scanner scChoice = new Scanner(System.in);
            int value;
            do {
                System.out.println("Enter \"1\" to place 2 pawn or \"2\" to place a bridge :");
                value = scChoice.nextInt();
            } while (value != 1 && value != 2);

            if (value == 1){
                string = this.placePawn(tray, 1);
                string += "+";
                string += this.placePawn(tray, 2);
            }
            else {
                string = this.placeBridge(tray);
            }

            System.out.println("Next Player !");

        }
        return string;
    }

    @Override
    public String chooseColor() {
        this.color = Color.White;
        return "c";
    }

    private String placePawn(Tray tray, int i)
    {
        String string = "";

        Scanner scLine = new Scanner(System.in);
        Scanner scColumn = new Scanner(System.in);
        int line, column;
        do {
            System.out.println("Pawn "+ i + " :");
            // line
            System.out.println("Line :");
            line = scLine.nextInt();
            // column
            System.out.println("Column :");
            column = scColumn.nextInt() ;


        } while (!tray.placePawn(line, column, this.color));

        // to string
        string += line;
        string += column;
        return string;
    }

    private String placeBridge(Tray tray)
    {
        String string = "";
        Scanner scLine = new Scanner(System.in);
        Scanner scColumn = new Scanner(System.in);
        int line1, column1, line2, column2;
        do {
            System.out.println("Bridge :");

            System.out.println("Pawn 1 :");
            // line
            System.out.println("Line :");
            line1 = scLine.nextInt();
            // column
            System.out.println("Column :");
            column1 = scColumn.nextInt() ;

            System.out.println("Pawn 2 :");
            // line
            System.out.println("Line :");
            line2 = scLine.nextInt();
            // column
            System.out.println("Column :");
            column2 = scColumn.nextInt() ;

        } while (!tray.placeBridge(line1, column1, line2, column2));
        string += line1 ; string += column1 ;
        string += "-";
        string += line2 ; string += column2;
        return string;
    }

}
