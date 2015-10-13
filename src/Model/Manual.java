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
    public void playInTray(Tray tray) {
        if (tray != null){

            System.out.println("It's player " + this.number + " turn !");


            Scanner scChoice = new Scanner(System.in);
            int value;
            do {
                System.out.println("Enter \"1\" to place 2 pawn or \"2\" to place a bridge :");
                value = scChoice.nextInt();
            } while (value != 1 && value != 2);

            if (value == 1){
                this.placePawn(tray, 1);
                this.placePawn(tray, 2);
            }
            else {
                this.placeBridge(tray);
            }

            System.out.println("Next Player !");

        }
    }

    @Override
    public void chooseColor() {
        this.color = Color.White;
    }

    private void placePawn(Tray tray, int i)
    {

        Scanner scLine = new Scanner(System.in);;
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
    }

    private void placeBridge(Tray tray)
    {
        Scanner scLine = new Scanner(System.in);;
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
    }

}
