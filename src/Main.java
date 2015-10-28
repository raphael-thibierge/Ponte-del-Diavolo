import Controller.Controller;
import Game.Color;
import Model.GameModel;
import View.MainView;

public class Main {

    public static void main(String[] args) {

        GameModel game;
        if (args.length == 3 && args[0] != null && args[1] != null && args[2] != null) {

            try {
                String IpAddress = args[0];
                int port = Integer.parseInt(args[1]);
                int size = Integer.parseInt(args[2]);
                game = new GameModel(IpAddress, port, size);
                game.run();
            } catch (Exception e) {
                return;
            }
        }

        else {
            Controller controller = new Controller();
            controller.run();
        }
    }
}
