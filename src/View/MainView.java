package View;

import Controller.Controller;
import Model.GameModel;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import javax.swing.text.View;
import java.awt.*;

/**
 * Created by raph on 24/10/15.
 */
public class MainView extends JFrame{

    private Controller controller;
    private GameModel gameModel;
    private int width = 800;
    private int height = 800;
    private JPanel trayPannel;
    private JPanel introPannel;

    private JPanel mainPannel;


    public MainView(Controller controller) throws NullPointerException {


        if (controller == null)
            throw new NullPointerException();

        this.controller = controller;

        this.setTitle("Ponte Del Diavolo");
        this.setSize(this.width, this.height);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);


       // trayPannel = new TrayPannel(model, this.height);
        introPannel = new IntroPannel(controller, this.height);

        //trayPannel.setSize(this.width, this.height);

        this.setContentPane(introPannel);

        //Et enfin, la rendre visible
        this.setVisible(true);
    }

    public MainView(GameModel gameModel) throws NullPointerException {


        if (gameModel == null)
            throw new NullPointerException();

        this.gameModel = gameModel;


        this.setTitle("Ponte Del Diavolo");
        this.setSize(this.width, this.height);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);


         trayPannel = new TrayPannel(gameModel, this.height);

        trayPannel.setSize(this.width, this.height);

        this.setContentPane(trayPannel);

        //Et enfin, la rendre visible
        this.setVisible(true);
    }


    public JPanel getMainPannel() {
        return mainPannel;
    }

    public void setMainPannel(JPanel mainPannel) {
        this.mainPannel = mainPannel;
    }

    public JPanel getIntroPannel() {
        return introPannel;
    }

    public void setIntroPannel(JPanel introPannel) {
        this.introPannel = introPannel;
    }

    public JPanel getTrayPannel() {
        return trayPannel;
    }

    public void setTrayPannel(JPanel trayPannel) {
        this.trayPannel = trayPannel;
    }
}