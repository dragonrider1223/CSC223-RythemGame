
/**
 * Creates a new window for the game
 *
 * @Joshua wolf
 * @version 1
 */
import java.awt.*;
import java.awt.geom.*;

public class Note
{
    private double x;
    private double y;
    private double width;
    private double height;
    private Color color;
    
    public Note(double x,double y,double width,double height,Color color)
    {
               this.x = x;
               this.y = y;
               this.width = width;
               this.height = height;
               this.color = color;
    }

    public void drawNote(Graphics2D g2d)
    {
        Ellipse2D.Double ellipse = new Ellipse2D.Double(x,y,width,height);
        g2d.setColor(color);
        g2d.fill(ellipse);
    }
}
