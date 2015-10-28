package View;

import Controller.Controller;
import Game.*;
import Model.GameModel;
import Model.Manual;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Created by raph on 26/10/15.
 */
public class TrayPannel extends JPanel {

    private Tray tray;
    private GameModel gameModel;

    private int cellSize=50;
    int lineWidth = 10;

    Color trayLine = Color.BLACK;
    Color emptyCell = Color.WHITE;
    Color blackPawn = Color.ORANGE;
    Color whitePawn = Color.CYAN;

    Point mousePosition = null;

    // states
    Boolean tryBridge = false;
    Cell cell1 = null;
    Cell cell2 = null;
    private Color bridgeColor = Color.GRAY;


    public TrayPannel(Controller controller, int size){
        this.tray = controller.getGameModel().getTray();
        this.gameModel = controller.getGameModel();
        this.setSize(size, size);

        cellSize = size / tray.getSize();

        this.addMouseMotionListener(new MouseMotionListener() {

            private void updateMousePosition(){
                if (mousePosition != null){
                    mousePosition.setLocation(getMousePosition().getX(), getMousePosition().y);
                } else {
                    mousePosition = new Point(getX(), getY());
                }
                repaint();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (!tryBridge){
                    cell1 = tray.getCell(getLine(), getColumn());
                    if (cell1 != null && cell1.getPawn() != null)
                        tryBridge = true;
                }
                updateMousePosition();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                updateMousePosition();
            }
        });

        this.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {

                gameModel.currentPlayerPlacePawns(getLine(), getColumn());
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (cell1 != null && tryBridge){

                    cell2 = tray.getCell(getLine(), getColumn());

                    if (cell2 != null){
                        gameModel.currentPlayerPlaceBridge(cell1, cell2);
                        cell1 = null;
                        cell2 = null;
                        repaint();
                    }
                }

                tryBridge = false;
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }

        });
    }


    @Override
    protected void paintComponent(Graphics g) {

        displayTray(g);

        displayMouseCursor(g);
    }

    private void displayTray(Graphics g){

        for (int line = 0 ; line < tray.getSize() ; line++){

            for (int column = 0 ; column < tray.getSize(); column++){
               displayCell(column, line, g);
            }
        }

        for (Bridge bridge : this.tray.getBridgeList()){
            displayBridge(bridge, g);
        }
    }

    private void displayCell(int x, int y, Graphics g) {
        Cell cell = this.tray.getCell(y, x);

        if (cell != null) {

            x *= cellSize;
            y *= cellSize;

            g.setColor(trayLine);


            g.fillRect(x, y, cellSize, cellSize);

            if (cell.isTaken()) {
                if (cell.getPawn().getColor() == Game.Color.White)
                    g.setColor(whitePawn);
                else
                    g.setColor(blackPawn);
            } else {
                g.setColor(emptyCell);
            }

            g.fillRect(x + lineWidth / 2, y + lineWidth / 2, cellSize - lineWidth, cellSize - lineWidth);
        }
    }

    private void displayBridge(Bridge bridge, Graphics g){
        if (bridge != null) {
            Cell cell1 = bridge.getBase1().getCell();
            Cell cell2 = bridge.getBase2().getCell();

            if (cell1 != null && cell2 != null){


                int x1 = cell1.getColumn() * cellSize + (cellSize/3);
                int x2 = cell2.getColumn() * cellSize + (cellSize/2);
                int y1 = cell1.getLine() * cellSize + (cellSize/2);
                int y2 = cell2.getLine() * cellSize + (cellSize/2);

                g.setColor(bridgeColor);
                Polygon polygon = new Polygon();
                polygon.addPoint(x1, y1);

                if (y1 != y2) {

                    polygon.addPoint(x1 + cellSize / 3, y1);

                    polygon.addPoint(x2 + cellSize / 3, y2);
                }
                else {
                    polygon.addPoint(x1 , y1 + cellSize / 3);
                    polygon.addPoint(x2 , y2 + cellSize / 3);

                }
                polygon.addPoint(x2, y2);
                g.fillPolygon(polygon);
            }

        }
    }

    private void displayMouseCursor(Graphics g){

        if (mousePosition != null && gameModel != null
                &&  gameModel.getCurrentPlayer() != null
                && gameModel.getCurrentPlayer().getColor() != null
                && gameModel.getCurrentPlayer() instanceof Manual){
            if (tryBridge){
                g.setColor(bridgeColor);
            } else if ( tray.getCell(mousePosition.y/cellSize, mousePosition.x/cellSize).pawnAllowedHere(gameModel.getCurrentPlayer().getColor())){
                g.setColor(Color.green);
            }
            else g.setColor(Color.red);
            g.fillOval(mousePosition.x - 5, mousePosition.y - 5, 10, 10);
        }
    }



    private int getLine(){
        return getMousePosition().y/cellSize;
    }

    private int getColumn(){
        return getMousePosition().x/cellSize;
    }




}
