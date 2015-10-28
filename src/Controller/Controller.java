package Controller;

import Model.GameMode;
import Model.GameModel;
import View.IntroPannel;
import View.MainView;
import View.TrayPannel;

/**
 * Created by raph on 27/10/15.
 */
public class Controller {

    private GameModel gameModel;
    private MainView mainView;

    private static final int TRAY_SIZE = 10;

    public Controller(){

    }

    public void loadManualAgainstDistantPlayer(){}

    public void loadIAlAgainstDistantPlayer(){}

    public void loadOnePlayer(){
        gameModel = new GameModel(10);
        //gameModel.setGameMode(GameMode.OnePlayerVsAI);
        mainView.setTrayPannel(new TrayPannel(gameModel, 800));
        mainView.setSize(800,800);
        mainView.setContentPane(mainView.getTrayPannel());
        mainView.setVisible(true);
        mainView.repaint();
        //gameModel.run();

    }

    public void loadTwoPlayer(){}


    public void run() {
        gameModel = new GameModel(TRAY_SIZE);
        mainView = new MainView(this);

    }
}
