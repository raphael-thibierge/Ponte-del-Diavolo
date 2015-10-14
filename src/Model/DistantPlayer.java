package Model;

import Game.Tray;
import Network.ClientTCP;

/**
 * Created by raphael on 13/10/15.
 */
public class DistantPlayer extends Player {

    ClientTCP clientTCP;
    GameModel game;

    public DistantPlayer(int number, ClientTCP clientTCP, GameModel gameModel) {
        super(number);
        this.clientTCP = clientTCP;
        this.game = gameModel;
    }

    @Override
    public String playInTray(Tray tray) {
        String string = clientTCP.read();

        if (!string.equals("F") ){
            int line1 = Integer.parseInt(String.valueOf(string.charAt(0)));
            int column1 = Integer.parseInt(String.valueOf(string.charAt(1)));
            int line2 = Integer.parseInt(String.valueOf(string.charAt(3)));
            int column2 = Integer.parseInt(String.valueOf(string.charAt(4)));
            if (string.charAt(2) == '-')
            {
                tray.placeBridge(line1, column1, line2, column2);
            }
            else {
                tray.placePawn(line1, column1, this.color);
                tray.placePawn(line2, column2, this.color);
            }

        }
        else
            clientTCP.disconnect();
        return string;
    }

    @Override
    public String chooseColor() {
        return clientTCP.read();
    }
}
