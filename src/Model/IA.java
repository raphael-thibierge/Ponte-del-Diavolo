package Model;

import Game.Color;
import Game.Tray;
import Network.ClientTCP;

import java.util.Random;

/**
 * Created by raphael on 13/10/15.
 */
public class IA extends Player {

    ClientTCP clientTCP;

    public IA(int number, ClientTCP clientTCP)
    {
        super(number);
        this.clientTCP = clientTCP;
    }

    @Override
    public void playInTray(Tray tray) {
        System.out.println("IA " + this.number + " :");
        String string ="";
        int line, column;
        Random random = new Random();
        for (int i = 0; i < 2; i++) {
            do {
                line = random.nextInt()%tray.getSize();
                column = random.nextInt()%tray.getSize();
            } while (!tray.placePawn(line, column, this.color));
            System.out.println("Pawn " + i + " placed !");
            string += line;
            string += column;
            if (i==0)
                string += "+";
        }
        clientTCP.write(string);
    }

    @Override
    public void chooseColor() {
        Random random = new Random();
        if (random.nextInt()%2 == 0){
            this.color = Color.White;
        } else
            this.color = Color.Black;
        System.out.println("Color choosen by IA : " + this.color.name());
    }
}
