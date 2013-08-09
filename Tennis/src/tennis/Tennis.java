/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tennis;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Play a nice game of tennis.
 * The program uses a timer to make the ball move across the court.
 * @author Anders Akesson <andyweb.info>
 */
public class Tennis extends JFrame implements ActionListener {
    private Court court = new Court();
    private JLabel point1 = new JLabel("0", JLabel.CENTER);
    private JLabel point2 = new JLabel("0", JLabel.CENTER);
    private JPanel panel = new JPanel();
    protected static JButton[] buttons = new JButton[4];
    private String[] s = {"New Game", "Pause", "Continue", "Exit"};
    protected static Player player1;
    protected static Player player2;
    
    /**
     * Constructor 
     */
    public Tennis() {
        setTitle("Tennis 1.0");
        court.setPreferredSize(new Dimension(550, 450));
        court.setBackground(Color.green);
        point1.setFont(new Font("Verdana", Font.BOLD, 30));
        point2.setFont(new Font("Verdana", Font.BOLD, 30));
        panel.setLayout(new FlowLayout());
        for(int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton();
            buttons[i].setText(s[i]);
            buttons[i].addActionListener(this);
            panel.add(buttons[i]);
        }
        add(court, BorderLayout.CENTER);
        add(point1, BorderLayout.WEST);
        add(point2, BorderLayout.EAST);
        add(panel, BorderLayout.SOUTH);
        pack();
        court.init(point1, point2);
        player1 = new Player();
        player2 = new Player();
        setPlayerName(player1, 1);
        setPlayerName(player2, 2);
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        court.requestFocus();
        if(e.getSource() == buttons[0]) {
            court.newGame();
        }
        else if(e.getSource() == buttons[1]) {
            court.stopGame();
        }
        else if(e.getSource() == buttons[2]) {
            court.startGame();
        }
        else if(e.getSource() == buttons[3]) {
            System.exit(0);
        }
    }
    
    /**
     * Sets the name of the player
     */
    public final void setPlayerName(Player player, int playerNr) {
        //JFrame frame = new JFrame();
        String name;
        name = JOptionPane.showInputDialog(null, "Enter your name: ", 
            "Player " + Integer.toString(playerNr), 1);

           //If a string was returned, say so.
        if ((s != null) && (name.length() > 0)) {
            player.setName(name);
        }
        else {
            player.setName("Anon");
        }
        
    }
  
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Tennis s = new Tennis();
    }

    
}
