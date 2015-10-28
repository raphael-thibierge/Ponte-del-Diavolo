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

    public IntroPannel(Controller controller){
        super();

        this.controller = controller;

        onePlayerButton = new JButton("One Player");
        onePlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.loadOnePlayer();
            }
        });

        twoPlayerButton = new JButton("Two Players");
        twoPlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.loadTwoPlayer();
            }
        });

        this.add(onePlayerButton);
        this.add(twoPlayerButton);

        this.setVisible(true);
    }
}
