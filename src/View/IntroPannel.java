package View;

import Controller.Controller;
import Model.GameModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by raph on 27/10/15.
 */
public class IntroPannel extends JPanel {

    private Controller controller;

    private JButton onePlayerButton;
    private JButton twoPlayerButton;

//    private final int BUTTON_WIDTH  = 400;
//    private final int BUTTON_HEIGHT = 100;

    public IntroPannel(Controller controller, int height){
        super();

        this.controller = controller;

        onePlayerButton = new JButton("One Player");
        onePlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.loadOnePlayer();
            }
        });
//        onePlayerButton.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
//        onePlayerButton.setLocation(100, 100);


        twoPlayerButton = new JButton("Two Players");
        twoPlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.loadTwoPlayer();
            }
        });
//
//      twoPlayerButton.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
//      twoPlayerButton.setLocation(100, 300);

        this.add(onePlayerButton);
        this.add(twoPlayerButton);

        this.setVisible(true);

    }
}
