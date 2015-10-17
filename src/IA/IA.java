package IA;

import Game.Cell;
import Game.Color;
import Game.Pawn;
import Game.Tray;
import Model.Player;
import Network.Message;

import java.util.List;
import java.util.Random;

/**
 * Created by raphael on 13/10/15.
 */
public class IA extends Player {

    public IA(Color color)
    {
        super(color);
    }

    @Override
    public String playInTray(Tray tray) {
        System.out.println("IA " + this.color.name() + " :");
        String string ="";
        if (canPlay(tray))
        {

            List<Pawn> pawnList = tray.getPawns(color);
            if (pawnList != null){
                for (int i = 0 ; i < pawnList.size() ; i ++){
                    for (int j = 0 ; j < pawnList.size() ; j++){
                        Cell cell1 = pawnList.get(i).getCell();
                        Cell cell2 = pawnList.get(j).getCell();

                        if (cell1 != null && cell2 != null){

                            int line1 = cell1.getLine();
                            int column1 = cell1.getColumn();
                            int line2 = cell2.getLine();
                            int column2 = cell2.getColumn();

                            if (tray.placeBridge(line1, column1, line2, column2))
                                return Message.bridge(line1, column1, line2, column2);

                        }

                    }
                }
            }


            string = fillTray(tray);

        } else string = Message.STOP;
        return string;
    }


    private String fullRandom(Tray tray){
        int line, column;
        String string ="";
        Random random = new Random();
        for (int i = 0; i < 2; i++) {
            do {
                line = random.nextInt() % tray.getSize();
                column = random.nextInt() % tray.getSize();
            } while (!tray.placePawn(line, column, this.color));

            // to string
            string += line;
            string += column;
            if (i == 0)
                string += "+";
        }
        return string;
    }


    private String fillTray(Tray tray){
        String msg = "";

        boolean onePlaced = false;
        for (int line = 0 ; line < tray.getSize() ; line++){
            for (int colum = 0 ; colum < tray.getSize() ; colum++){
                if (tray.placePawn(line, colum, this.color)){
                    if (!onePlaced){
                        msg += Message.firstPawn(line, colum);
                        onePlaced = true;
                    } else {
                        msg += Message.secondPawn(line, colum);
                        return msg;
                    }
                }
            }
        }
        return Message.STOP;
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
        System.out.println("Color choosen by IA : " + this.color.name());
        return colorChoice;
    }
}
