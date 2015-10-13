package Model;

import Game.Tray;

import java.util.Random;

/**
 * Created by raphael on 13/10/15.
 */
public class IA extends Player {

    public IA(int number)
    {
        super(number);
    }

    @Override
    public void playInTray(Tray tray) {
        System.out.println("IA " + this.number + " :");
        int line, column;
        Random random = new Random();
        for (int i = 0; i < 2; i++) {
            do {
                line = random.nextInt()%tray.getSize();
                column = random.nextInt()%tray.getSize();
            } while (!tray.placePawn(line, column, this.color));
            System.out.println("Pawn " + i + " placed !");
        }
    }
}
