
/**
 * changes the text of the popup telling you how well you did
 *
 * @Joshua wolf
 * @version 1.3
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
    
    //sets up necesary variables
    public TimingPopup(double x,double y,Color color,double windowWidth, String text,DrawCanvas drawCanvas)
    {
        this.x = x;
        this.y = y-50;
        this.color = color;
        this.text = text;
        this.dc = drawCanvas;
    }

    //draws the popup with a ever decreasing alpha to make it fade out
    public void drawPopup(Graphics2D g2d)
    {
        
        alpha-=alphaChange;
        if(alpha >= 0){
            g2d.setFont(myFont);
            g2d.setColor(new Color(color.getRed(),color.getGreen(),color.getBlue(),alpha));
            g2d.drawString(text,(float)x,(float)y); 
        }
    }

    //gets called to change the text and color of the popup and makes its alpha fully visible again
    public void changeText(String newText,Color newColor)
    {
        alpha = 255;
        this.text = newText;
        this.color = newColor;
    }
}
