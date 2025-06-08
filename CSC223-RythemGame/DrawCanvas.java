
/**
 * Write a description of class DrawCanvas here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
public class DrawCanvas extends JPanel
{
    private int width;
    private int height;
    private Note note;
    private double displacement = -1000;
    private double displacementIncrease;
    
    public DrawCanvas(int w, int h){
        width = w;
        height = h;
        displacementIncrease = height/60;
    }
    
    
    
    @Override
    protected void paintComponent(Graphics g){

        System.out.println("Paint: "+displacement);
        
        Graphics2D g2d = (Graphics2D) g;
        
        RenderingHints rh = new RenderingHints(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
            
        g2d.setRenderingHints(rh);
        
        Rectangle2D.Double rectangle = new Rectangle2D.Double(0,0,width,height);
        g2d.setColor(new Color(100,100,200));
        g2d.fill(rectangle);

        //note = new Note(100,displacement,100,100,Color.BLACK);
        Ellipse2D.Double ellipse = new Ellipse2D.Double(100,displacement,100,100);
        g2d.setColor(Color.BLACK);
        g2d.fill(ellipse);
        
        //note.drawNote(g2d);
        
                
        
    }
    
    public void RedrawCanvas()
    {
        displacement += displacementIncrease;

        repaint();
        System.out.println("redrawCanvas");
    }
}
