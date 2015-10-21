import Game.Color;
import Model.GameModel;

public class Main {

    public static void main(String[] args) {

        String IpAddress = args[0];
        int port = Integer.parseInt(args[1]);
        int size = Integer.parseInt(args[2]);

        GameModel game = new GameModel(IpAddress, port, size);
        game.runTest();
        //game.runWithString("00+01 c 02+03 10+31 04+11 34+42 20+21", 5, Color.Black);
        //game.run();
        System.out.println("End of the game !");
    }
}
