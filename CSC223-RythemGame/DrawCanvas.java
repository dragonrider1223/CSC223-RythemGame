
/**
 * Creates a new window for the game
 *
 * @Joshua wolf
 * @version 1.1
 */
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.util.ArrayList;
public class DrawCanvas extends JPanel
{
    private int width;
    private int height;
    private Note note;
    private ArrayList<Note> noteList = new ArrayList<Note>();
    
    private double displacementIncrease;
    
    public DrawCanvas(int w, int h){
        width = w;
        height = h;
        displacementIncrease = height/60;
    }
    
    public void AddNote()
    {
        note = new Note(displacementIncrease,width/2,-200,Color.BLACK,height,noteList);
        noteList.add(note);
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

        for(int i=0;i<noteList.size();i++){
            noteList.get(i).drawNote(g2d);
        }
        
                
        
    }
    
    public void RedrawCanvas()
    {
        repaint();
    }
    
    public void checkNotes()
    {
        for(int i=0;i<noteList.size();i++){
            if(noteList.get(i).y>height - 100)
            {
                noteList.get(i).removeNote();
            }
        }
    }
}
