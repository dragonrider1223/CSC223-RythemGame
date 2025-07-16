
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

    private double displacementIncrease;private ArrayList popupList;
    DrawCanvas dc;
    
    private String text;
    
    private int alpha =255;
    private int alphaChange =10;

    Font myFont = new Font("Arial", Font.BOLD, 100);
    
    public TimingPopup(double x,double y,Color color,double windowWidth, String text,DrawCanvas drawCanvas)
    {
        System.out.println("create popup");
        this.x = x;
        this.y = y-50;
        this.color = color;
        this.text = text;
        this.dc = drawCanvas;
    }

    public void drawPopup(Graphics2D g2d)
    {
        
        alpha-=alphaChange;
        if(alpha >= 0){
            g2d.setFont(myFont);
            g2d.setColor(new Color(color.getRed(),color.getGreen(),color.getBlue(),alpha));
            g2d.drawString(text,(float)x,(float)y); 
        }
        //g2d.drawString(text,(float)x,(float)y);  

    }

    public void changeText(String newText,Color newColor)
    {
        alpha = 255;
        this.text = newText;
        this.color = newColor;
    }
}
