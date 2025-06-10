
/**
 * Draws the images needed
 *
 * @Joshua wolf
 * @version 1.2
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
    
    private double playerHeight;
    private double playerOffset;
    
    private double noteHeight = 100;
    
    private int score;
    
    public DrawCanvas(int w, int h,double playerh,double playero){
        width = w;
        height = h;
        displacementIncrease = height/120;
        playerHeight = playerh;
        playerOffset = playero;
    }
    
    public void AddNote()
    {
        note = new Note(displacementIncrease,width/2,-playerHeight-playerOffset,noteHeight,Color.BLACK,height,noteList,this);
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

        Rectangle2D.Double playerRec = new Rectangle2D.Double(0,height-playerHeight-playerOffset,width,playerHeight);
        g2d.setColor(Color.WHITE);
        g2d.fill(playerRec);
        
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
        int noteListSize = noteList.size();
        for(int i=0;i<noteListSize;i++){
            if(noteList.get(i).y>height-playerHeight-playerOffset&&noteList.get(i).y+noteHeight<height-playerOffset)
            {
                System.out.println("perfect");
                changeScore(100);
                noteList.get(i).removeNote();
            }else if(noteList.get(i).y>height-playerHeight-playerOffset&&noteList.get(i).y<height-playerOffset)
            {
                System.out.println("late");
                changeScore(50);
                noteList.get(i).removeNote();
            }else if(noteList.get(i).y+noteHeight>height-playerHeight-playerOffset&&noteList.get(i).y+noteHeight<height-playerOffset)
            {
                System.out.println("early");
                changeScore(50);
                noteList.get(i).removeNote();
            }
        }
        if(noteList.size()==noteListSize)
        {
            System.out.println("miss");
            changeScore(-100);
        }
    }
    
    public void changeScore(int scoreChange)
    {
        score+=scoreChange;
        System.out.println(score);
    }
}
