import Game.Color;
import Model.GameModel;

public class Main {

    public static void main(String[] args) {

        String IpAddress = args[0];
        int port = Integer.parseInt(args[1]);
        int size = Integer.parseInt(args[2]);

        GameModel game = new GameModel(IpAddress, port, size);
        //game.runTest();
//        game.runWithString("13+30 c 01+21 00+02 03+10 11+12", 4, Color.White);
        game.runWithString("03+14 c 11+12 00+01 13+24 02+20", 5, Color.White);
        game.run();
//        System.out.printlnc("End of the game !");
    }
}
