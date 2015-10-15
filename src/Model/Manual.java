package Model;

import Game.Color;
import Game.Tray;

import java.util.Scanner;

/**
 * Created by raphael on 13/10/15.
 */
public class Manual extends Player {

    public Manual(Color color)
    {
        super(color);
    }


    @Override
    public String playInTray(Tray tray) {
        String string = "a";
        if (tray != null){

            System.out.println("It's player " + this.color + " turn !");

            Scanner scChoice = new Scanner(System.in);

            boolean valid;


            do {
                System.out.println("Enter your move :");
                string = scChoice.nextLine();


                if (string.equals("a")) {
                    valid = true;
                } else {
                    int line1 = Integer.parseInt(String.valueOf(string.charAt(0)));
                    int column1 = Integer.parseInt(String.valueOf(string.charAt(1)));
                    int line2 = Integer.parseInt(String.valueOf(string.charAt(3)));
                    int column2 = Integer.parseInt(String.valueOf(string.charAt(4)));
                    if (string.charAt(2) == '-') {
                         valid = tray.placeBridge(line1, column1, line2, column2);
                    }
                    else {
                        valid = tray.placePawn(line1, column1, this.color) && tray.placePawn(line2, column2, this.color);
                    }
                }
            } while (!valid);

        }
        return string;
    }

    @Override
    public String chooseColor() {
        Scanner sc = new Scanner(System.in);
        String s ;

        do {
            System.out.println("Choose your color : ");
            s = sc.nextLine();
        } while (!s.equals( "f" ) && !s.equals( "c" ));

        if (s.equals("f"))
            this.color = Color.Black;
        else this.color = Color.White;

        return s;
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
