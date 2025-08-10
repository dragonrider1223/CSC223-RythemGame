
/**
 * creates and destroys note that moves down at a constant rate
 *
 * @Joshua wolf
 * @version 1.2
 */
import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Note
{
    private int rowAmounts;
    
    public double x;
    public double y;
    private double width;
    private double height;
    private Color color;
    
    private double displacementIncrease;

    private double deathDistance;

    private boolean isActive;

    private ArrayList activeNoteList;
    private ArrayList nonActiveNoteList;
    DrawCanvas dc;
    
    private BufferedImage noteImage;

    //sets up all variables of the note
    public Note(double speed,double x,double y,double width,double height,Color color,double windowHeight, ArrayList list, ArrayList listNonActive, DrawCanvas drawCanvas,boolean isActive, int ra)
    {
        this.rowAmounts = ra;
        this.x = (windowHeight/rowAmounts)*(int)(Math.random()*rowAmounts)+(((windowHeight/rowAmounts)/2)-width/2);// 
        this.y = y-height;
        this.width = width;
        this.height = height;
        this.color = color;
        this.displacementIncrease = speed;
        this.deathDistance = windowHeight+height*2;//increase this by height*2 to prevent flashing of the other notes
        this.activeNoteList = list;
        this.nonActiveNoteList = listNonActive;
        this.dc = drawCanvas;
        this.isActive = isActive;
        
        noteImage = null;
        try {
            noteImage = ImageIO.read(new File("sprites/note.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //draws the note to the canvas in draw canvas
    public void drawNote(Graphics2D g2d)
    {
        if(this.isActive){
            g2d.drawImage(noteImage,(int)this.x,(int)(this.y+=displacementIncrease),(int)this.width,(int)this.height,this.color,dc);

            if(this.y>deathDistance)
            {
                dc.changeScore(-100);
                activeNoteList.remove(this);
                nonActiveNoteList.add(this);
                removeNote();
            }
        }
    }

    //removes the note and makes it not visible and resets its height for the object pooling
    public void removeNote()
    {
        isActive = false;
        this.y=0-height;
    }
    
    //activates the note
    public void setActive()
    {
        isActive = true;
        
    }
}
