
/**
 * creates and destroys note that moves down at a constant rate
 *
 * @Joshua wolf
 * @version 1.2
 */
import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;

public class TimingPopup
{
    public double x;
    public double y;
    private double fontSize = 100;
    private Color color;

    private double displacementIncrease;
    
    private ArrayList popupList;
    DrawCanvas dc;
    
    private String text;
    
    private int alpha =255;
    private int alphaChange =10;

    Font myFont = new Font("Arial", Font.BOLD, 100);
    
    public TimingPopup(double x,double y,Color color,double windowWidth, ArrayList list, String text,DrawCanvas drawCanvas)
    {
        this.x = x;
        this.y = y-50;
        this.color = color;
        this.popupList = list;
        this.text = text;
        this.dc = drawCanvas;
    }

    public void drawPopup(Graphics2D g2d)
    {
        g2d.setFont(myFont);
        alpha-=alphaChange;
        if(alpha >= 0)
            g2d.setColor(new Color(color.getRed(),color.getGreen(),color.getBlue(),alpha));
        else{
            g2d.setColor(new Color(color.getRed(),color.getGreen(),color.getBlue(),0));
            removePopup();
        }
        g2d.drawString(text,(float)x,(float)y);  

    }
    
    public void removePopup()
    {
        popupList.remove(this);
    }
}
