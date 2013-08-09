/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tennis;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Anders Akesson <andyweb.info>
 */
public class Court extends JPanel implements ActionListener {
    private Timer time = new Timer(100, this);
    private JLabel point1, point2; // to show points
    private int p1, p2; // current score
    private int xMax, yMax; // highest x- and y-coordinate
    private int r, x0, y0; // the balls' radius and center
    private int xStep, yStep; // the balls' step length
    private int v, v0 = 10; // the balls' speed
    private int rLeft, rRight, rLength, rStep; // racket length and step
    private JFrame frame = new JFrame("SCORE");

    
    /**
     * Initiates the game
     * @param l1
     * @param l2
     */
    public void init(JLabel l1, JLabel l2) {
        point1 = l1;
        point2 = l2;
        xMax = getSize().width - 1;
        yMax = getSize().height - 1;
        r = yMax / 30; // get the balls radius
        rLength = 3 * r; // get the rackets length
        rStep = r; // get the rackets step
        addKeyListener(kl);
        addComponentListener(cl);
        reset();
        
    }
    
    /**
     * Resets the game
     */
    private void reset() {
        p1 = p2 = 0;
        point1.setText(" 0 ");
        point2.setText(" 0 ");
        xStep = yStep = v = v0 = 10;
        x0 = r + 1;
        y0 = yMax / 2;
        rLeft = rRight = yMax / 2 - rLeft / 2;
        Tennis.buttons[1].setEnabled(false);
        Tennis.buttons[2].setEnabled(false);
    }
    
    /**
     * Starts the game
     */
    public void startGame() {
        time.start();
        Tennis.buttons[1].setEnabled(true);
        Tennis.buttons[2].setEnabled(false);

    }
    
    /**
     * Stops the game
     */
    public void stopGame() {
        time.stop();
        Tennis.buttons[2].setEnabled(true);
        Tennis.buttons[1].setEnabled(false);

    }
    
    /**
     * Starts a new game
     */
    public void newGame() {
        stopGame();
        reset();
        startGame();
        Tennis.buttons[2].setEnabled(false);
        Tennis.buttons[1].setEnabled(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // We get here every 10 seconds, called by the timer
        if((x0 - r) <= 0) { // if the ball is in the left
            if(y0 < rLeft || y0 > (rLeft + rLength)) { // miss?
                Toolkit.getDefaultToolkit().beep();
                Tennis.player1.setPoints(++p2);
                point2.setText(" " + String.valueOf(Tennis.player1.getPoints()) + " ");
                JOptionPane.showMessageDialog(frame,
                    "Point scored for " + Tennis.player1.getName() + "!\nYour score is " + String.valueOf(Tennis.player1.getPoints() + "."));
                if(p1 == 3) {
                    stopGame();
                    JOptionPane.showMessageDialog(frame,
                    Tennis.player1.getName() + " is the winner!");
                    reset();
                }
                v = v0; // return to initial speed
            }
            else { //hit
                v++; // increase speed
            }
            xStep = v; // move to right next time
        
        }
        else if(x0 + r >= xMax) { // is the ball on the right?
            if(y0 < rRight || y0 > rRight+rLength) {
                Toolkit.getDefaultToolkit().beep();
                Tennis.player2.setPoints(++p1);
                point1.setText(" " + String.valueOf(Tennis.player2.getPoints()) + " ");
                JOptionPane.showMessageDialog(frame,
                    "Point scored for " + Tennis.player2.getName() + "!\nYour score is " + String.valueOf(Tennis.player2.getPoints() + "."));
                if(p1 == 3) {
                    stopGame();
                    JOptionPane.showMessageDialog(frame,
                    Tennis.player2.getName() + " is the winner!");
                    reset();
                }
                v = v0; // return to initial speed
            }
            else { //hit
                v++; // increase speed
            }
            xStep = -v; // move to left next time
                
        }
        if((y0 - r) <= 0 || (y0 + r) >= yMax) { // upper or under
            yStep = -yStep; // switch vertically
        }
        x0 += xStep; // move ball horisontally
        y0 += yStep; // move ball vertically
        if(x0 < r) { // is the ball too far to the left?
            x0 = r;
        }
        else if(x0 > (xMax - r)) { // is the ball too far to the right?
            x0 = xMax - r + 1;
        }
        if(y0 < r) { // is the ball too far up?
            y0 = r;
        }
        else if(y0 > yMax - r) { // is the ball too far down?
            y0 = yMax - r + 1;
        }
        repaint();
        
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.red);
        g.fillOval(x0 - r, y0 - r, 2 * r, 2 * r);
        g.setColor(Color.black);
        g.fillRect(0, rLeft, 2, rLength);
        g.fillRect(xMax - 1, rRight, 2, rLength);
        
    }
    
    KeyListener kl = new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == KeyEvent.VK_A) {
                rLeft = Math.max(0, rLeft - rStep);
            }
            else if(e.getKeyCode() == KeyEvent.VK_Z) {
                rLeft = Math.min(yMax - rLength, rLeft + rStep);
            }
            if(e.getKeyCode() == KeyEvent.VK_UP) {
                rRight = Math.max(0, rRight - rStep);
            }
            else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
                rRight = Math.min(yMax - rLength, rRight + rStep);
            }
            else if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                startGame();
            }
        }
    };
    
    ComponentListener cl = new ComponentAdapter() {
        @Override
        public void componentResized(ComponentEvent e) {
            xMax = e.getComponent().getSize().width - 1;
            yMax = e.getComponent().getSize().height - 1;
            e.getComponent().requestFocus();
            repaint();
        }
    };
    
}
