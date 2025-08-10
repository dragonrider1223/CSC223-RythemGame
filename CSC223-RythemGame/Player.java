
/**
 * this catches players inputs and passes them on to the check note function in the drawCanvas class
 *
 * @Joshua wolf
 * @version 1.2
 */
import java.awt.event.*;
import java.awt.event.KeyEvent;

public class Player implements KeyListener
{
    DrawCanvas dc;
    boolean released;
    
    //sets up the connection with the drawCanvas class
    public Player(DrawCanvas drawCanvas)
    {
        dc = drawCanvas;
        released = true;
    }
    
    public void keyTyped(KeyEvent e){}  
    //if key released sets released to true
    public void keyReleased(KeyEvent e)
    {
        released = true;
    }  
    
    //when key pressed calls check notes and sets released to false to prevent button holding
    public void keyPressed(KeyEvent e){
        if(released){
            released = false;
            checkNotes();
        }
    }  
    
    //checks the notes if they are in the correct height
    public void checkNotes()
    {
        dc.checkNotes();
    }
}
