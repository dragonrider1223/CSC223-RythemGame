
/**
 * Creates a new window for the game
 *
 * @Joshua wolf
 * @version 1.2
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JButton;




public class GameWindow extends JFrame 
{
    int wSize;
    DrawCanvas dc;
    Player player;
    
    public GameWindow(int windowSize, DrawCanvas DrawCanvas, Player thePlayer)
    {
        wSize = windowSize;
        dc = DrawCanvas;
        player  = thePlayer;
    }
    
    //creates window for the game
    public void createGameWindow()
    {
        //sets window name
        setTitle("Music game");
        
        // draws the canvas with the window size
        //DrawCanvas dc = new DrawCanvas(wSize,wSize);
        
        //makes the window
        this.getContentPane().setPreferredSize(new Dimension(wSize,wSize));
        this.setResizable(false);
        this.toFront();
        this.setVisible(true);
        
        this.addKeyListener(player);
        this.add(dc);
        
        //pack this
        this.pack();
        
        //end on close
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    

    
}
