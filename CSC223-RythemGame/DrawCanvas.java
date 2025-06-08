
/**
 * Write a description of class DrawCanvas here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
public class DrawCanvas extends JComponent
{
    private int width;
    private int height;
    private Note note;
    
    public DrawCanvas(int w, int h){
        width = w;
        height = h;
        note = new Note(100,100,100,100,Color.BLACK);
    }
    
    @Override
    protected void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        
        RenderingHints rh = new RenderingHints(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
            
        g2d.setRenderingHints(rh);
        
        Rectangle2D.Double rectangle = new Rectangle2D.Double(0,0,width,height);
        g2d.setColor(new Color(100,100,200));
        g2d.fill(rectangle);
        
        note.drawNote(g2d);
    }

}
