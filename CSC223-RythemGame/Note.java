
/**
 * creates and destroys note that moves down at a constant rate
 *
 * @Joshua wolf
 * @version 1.2
 */
import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;

public class Note
{
    public double x;
    public double y;
    private double width = 200;
    private double height;
    private Color color;

    private double displacementIncrease;
    
    private double deathDistance;
    
    private ArrayList noteList;
    DrawCanvas dc;
    
    public Note(double speed,double x,double y,double height,Color color,double windowHeight, ArrayList list, DrawCanvas drawCanvas)
    {
        this.x = 25+Math.random()*((windowHeight-width)-25);// the -25 and +25 are for the offset from either edge
        this.y = y-height;
        this.height = height;
        this.color = color;
        this.displacementIncrease = speed;
        this.deathDistance = windowHeight+height*2;//increase this by height*2 to prevent flashing of the other notes
        this.noteList = list;
        this.dc = drawCanvas;
    }

    public void drawNote(Graphics2D g2d)
    {
        Rectangle2D.Double note = new Rectangle2D.Double(x,y+=displacementIncrease,width,height);
        g2d.setColor(color);
        g2d.fill(note);
        
        if(this.y>deathDistance)
        {
            dc.changeScore(-100);
            removeNote();
        }

    }
    
    public void removeNote()
    {
        noteList.remove(this);
    }
}
