
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
    private double displacement = 0;
    private double displacementIncrease = 20;
    
    public DrawCanvas(int w, int h){
        width = w;
        height = h;
        
    }
    
    @Override
    protected void paintComponent(Graphics g){
        System.out.println(displacement);
        
        Graphics2D g2d = (Graphics2D) g;
        
        RenderingHints rh = new RenderingHints(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
            
        g2d.setRenderingHints(rh);
        
        Rectangle2D.Double rectangle = new Rectangle2D.Double(0,0,width,height);
        g2d.setColor(new Color(100,100,200));
        g2d.fill(rectangle);

        note = new Note(100,displacement,100,100,Color.BLACK);
        
        note.drawNote(g2d);
        
        displacement += displacementIncrease;
        
        
    }
    
    public void RedrawCanvas()
    {
        repaint();
        System.out.println("redrawCanvas");
    }
}
