
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
    public double x;
    public double y;
    private double width = 100;
    private double height;
    private Color color;
    
    private double displacementIncrease;

    private double deathDistance;

    private boolean isActive;

    private ArrayList activeNoteList;
    private ArrayList nonActiveNoteList;
    DrawCanvas dc;
    
    private BufferedImage noteImage;

    public Note(double speed,double x,double y,double height,Color color,double windowHeight, ArrayList list, ArrayList listNonActive, DrawCanvas drawCanvas,boolean isActive)
    {
        this.x = 25+Math.random()*((windowHeight-width)-25);// the -25 and +25 are for the offset from either edge
        this.y = y-height;
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

    public void removeNote()
    {
        isActive = false;
        this.y=0-height;
    }
    
    public void setActive()
    {
        isActive = true;
        
    }
}
