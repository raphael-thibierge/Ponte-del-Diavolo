import Model.GameModel;

public class Main {

    public static void main(String[] args) {

        String IpAddress = "192.168.2.46";
        int port = 1025;
        int size = 5;



        GameModel game = new GameModel(IpAddress, port, size);
        game.run();
    }
}
