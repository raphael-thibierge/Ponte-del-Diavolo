import Model.GameModel;

public class Main {

    public static void main(String[] args) {

        String IpAddress = "192.168.2.40";
        int port = 1024;
        int size = 6;

        GameModel game = new GameModel(IpAddress, port, 6);
        game.run();
    }
}
