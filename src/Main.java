import Controller.Controller;
import Game.Color;
import Model.GameModel;
import View.MainView;

public class Main {

    public static void main(String[] args) {

        GameModel game;
        if (args.length == 3 && args[0] != null && args[1] != null && args[2] != null){

            try {
                String IpAddress = args[0];
                int port = Integer.parseInt(args[1]);
                int size = Integer.parseInt(args[2]);
                game = new GameModel(IpAddress, port, size);
            } catch (Exception e) {
                return;
            }
        }
        else game = new GameModel(10);


//        Controller controller = new Controller();
//        controller.run();




        MainView mainView = new MainView(game);
        game.setMainView(mainView);
//   //     game.runTest();
////        game.runWithString("13+30 c 01+21 00+02 03+10 11+12", 4, Color.White);
////        game.runWithString("03+14 c 11+12 00+01 13+24 02+20", 5, Color.White);
          game.run();
//        System.out.printlnc("End of the game !");
    }
}
