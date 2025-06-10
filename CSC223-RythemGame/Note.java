
/**
 * Creates a new window for the game
 *
 * @Joshua wolf
 * @version 1.1
 */
import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;

public class Note
{
    public double x;
    public double y;
    private double width = 200;
    private double height = 100;
    private Color color;

    private double displacementIncrease;
    
    private double deathDistance;
    
    private ArrayList noteList;
    
    public Note(double speed,double x,double y,Color color,double windowHeight, ArrayList list)
    {
        this.x = x-width/2;
        this.y = y-height;
        this.color = color;
        this.displacementIncrease = speed;
        this.deathDistance = windowHeight;
        this.noteList = list;
    }

    public void drawNote(Graphics2D g2d)
    {
        Ellipse2D.Double ellipse = new Ellipse2D.Double(x,y+=displacementIncrease,width,height);
        g2d.setColor(color);
        g2d.fill(ellipse);
        
        if(this.y>deathDistance)
            removeNote();
        

    }
    
    public void removeNote()
    {
        noteList.remove(this);
    }
}
