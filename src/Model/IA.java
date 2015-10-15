package Model;

import Game.Color;
import Game.Tray;
import Network.ClientTCP;
import Network.Message;

import java.util.Random;

/**
 * Created by raphael on 13/10/15.
 */
public class IA extends Player {

    ClientTCP clientTCP;

    public IA(Color color, ClientTCP clientTCP)
    {
        super(color);
        this.clientTCP = clientTCP;
    }

    @Override
    public String playInTray(Tray tray) {
        System.out.println("IA " + this.color.name() + " :");
        String string ="";
        if (canPlay(tray))
        {
            int line, column;
            Random random = new Random();
            for (int i = 0; i < 2; i++) {
                do {
                    line = random.nextInt()%tray.getSize();
                    column = random.nextInt()%tray.getSize();
                } while (!tray.placePawn(line, column, this.color));

                // to string
                string += line;
                string += column;
                if (i==0)
                    string += "+";
            }
        } else string = Message.END;
        return string;
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
