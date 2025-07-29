
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
    private double noteAmount=25;
    private ArrayList<Note> activeNoteList = new ArrayList<Note>();
    private ArrayList<Note> nonActiveNoteList = new ArrayList<Note>();

    private double displacementIncrease;
    private double playerHeight;
    private double playerOffset;

    private double noteHeight = 50;

    private TimingPopup popup;

    private int score;

    Font myFont = new Font("Arial", Font.BOLD, 100);

    public DrawCanvas(int w, int h,double playerh,double playero){
        width = w;
        height = h;
        playerHeight = playerh;
        playerOffset = playero;
        
        displacementIncrease = (h-playero-(playerh/2)+noteHeight)/120;
        for(int i = 0;i<noteAmount;i++)
        {
            note = new Note(displacementIncrease,width/2,-playerOffset,noteHeight,Color.BLACK,height,activeNoteList,nonActiveNoteList,this,false);
            nonActiveNoteList.add(note);
        }
        popup= new TimingPopup(20,height-playerOffset-playerHeight,Color.BLACK,width,"",this);
    }

    public void AddNote()
    {
        //note = new Note(displacementIncrease,width/2,-playerOffset,noteHeight,Color.BLACK,height,activeNoteList,this);
        Note noteToBeMoved = null;
        for(Note note : nonActiveNoteList)
        {
            if(note!=null)
            {
                noteToBeMoved = note;
            }
        }

        if(noteToBeMoved!=null){
            noteToBeMoved.setActive();
            activeNoteList.add(noteToBeMoved);
            nonActiveNoteList.remove(noteToBeMoved);
        }
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setRenderingHints(rh);

        Rectangle2D.Double rectangle = new Rectangle2D.Double(0,0,width,height);
        g2d.setColor(new Color(100,100,200));
        g2d.fill(rectangle);

        Rectangle2D.Double playerRec = new Rectangle2D.Double(0,height-playerHeight/8*4-playerOffset,width,playerHeight/8);
        g2d.setColor(Color.WHITE);
        g2d.fill(playerRec);

        for(int i=0;i<activeNoteList.size();i++){
            activeNoteList.get(i).drawNote(g2d);
        }

        g2d.setFont(myFont);
        g2d.setColor(Color.GREEN);
        g2d.drawString("Score: "+Integer.toString(score),10, 100); 

        popup.drawPopup(g2d);

    }

    public void RedrawCanvas()
    {
        repaint();
    }

    public void checkNotes()
    {
        int startingNoteListSize = activeNoteList.size();
        int activeNoteListSize = activeNoteList.size();
        double noteY = 0;
        if(activeNoteListSize>0){
            for(int i=0;i<activeNoteListSize;i++){
                if(activeNoteList.get(i)!=null){
                    Note note = activeNoteList.get(i);
                    noteY = note.y;
                    if(noteY+noteHeight>height-playerHeight-playerOffset){
                        if(noteY>height-playerHeight-playerOffset&&noteY<height-playerOffset&&noteY+noteHeight>height-playerOffset)
                        {
                            //System.out.println("late");
                            changeScore(50);
                            popup.changeText("late",Color.ORANGE);
                            note.removeNote();
                            nonActiveNoteList.add(note);
                            activeNoteList.remove(note);
                            activeNoteListSize = activeNoteList.size();
                        }else if(noteY>height-playerHeight-playerOffset&&noteY+noteHeight<height-playerOffset)
                        {
                            //System.out.println("perfect");
                            changeScore(100);
                            popup.changeText("perfect",Color.GREEN);
                            note.removeNote();
                            nonActiveNoteList.add(note);
                            activeNoteList.remove(note);
                            activeNoteListSize = activeNoteList.size();
                        }else  if(noteY+noteHeight<height-playerOffset)
                        {
                            //System.out.println("early");
                            changeScore(50);
                            popup.changeText("early",Color.YELLOW);
                            note.removeNote();
                            nonActiveNoteList.add(note);
                            activeNoteList.remove(note);
                            activeNoteListSize = activeNoteList.size();
                        }
                    }
                }
                noteY = 0;
            }
        }
        if(activeNoteList.size()==startingNoteListSize)
        {
            //System.out.println("miss");
            changeScore(-100);

        }
    }

    public void changeScore(int scoreChange)
    {
        if (score+scoreChange>=0)
            score+=scoreChange;
        else
            score=0;
        if(scoreChange<0)
        {
            popup.changeText("miss",Color.RED);
        }
        //System.out.println(score);
    }
}
