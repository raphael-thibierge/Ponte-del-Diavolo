import Model.GameModel;

public class Main {

    public static void main(String[] args) {



        String IpAddress = args[0];
        int port = Integer.parseInt(args[1]);
        int size = Integer.parseInt(args[2]);

        GameModel game = new GameModel(IpAddress, port, size);
        game.run();
        System.out.println("End of the game !");
    }
}
