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
    private int height = 900;
    private JPanel trayPannel;
    private JPanel introPannel;


    public MainView(Controller controller) throws NullPointerException {


        if (controller == null)
            throw new NullPointerException();

        this.controller = controller;

        this.setTitle("Ponte Del Diavolo");
        this.setSize(this.width, this.height);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        introPannel = new IntroPannel(controller);
        this.setContentPane(introPannel);

        this.setVisible(true);
    }

    public JPanel getTrayPannel() {
        return trayPannel;
    }

    public void setTrayPannel(JPanel trayPannel) {
        this.trayPannel = trayPannel;
    }
}