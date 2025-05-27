
/**
 * Creates a new window for the game
 *
 * @Joshua wolf
 * @version 1
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JButton;

import java.util.Scanner;

public class GameWindow extends JFrame
{
    //sets windowsize
    int wSize = 800;
    
    //creates window for the game
    public GameWindow()
    {
        //sets window name
        setTitle("temp");
        
        //makes the window
        this.getContentPane().setPreferredSize(new Dimension(wSize,wSize));
        this.toFront();
        this.setVisible(true);
        
        //pack this
        this.pack();
        
        //end on close
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

}
