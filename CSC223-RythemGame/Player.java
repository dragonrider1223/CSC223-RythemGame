
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
    
    
    
    public Player(DrawCanvas drawCanvas)
    {
        dc = drawCanvas;
        
    }
    
    public void keyTyped(KeyEvent e){}  
    public void keyReleased(KeyEvent e){}  
    public void keyPressed(KeyEvent e){
        //System.out.println("You pressed key char: "+e.getKeyChar());
        checkNotes();
    }  
    
    public void checkNotes()
    {
        dc.checkNotes();
    }
}
