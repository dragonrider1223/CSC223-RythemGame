
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

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

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
    private double noteWidth = 100;

    private int rowAmounts = 3;

    private TimingPopup popup;

    private int score;

    //images
    private BufferedImage playerTriggerImage;

    Font myFont = new Font("Arial", Font.BOLD, 100);

    public DrawCanvas(int w, int h,double playerh,double playero){
        width = w;
        height = h;
        playerHeight = playerh;
        playerOffset = playero;

        displacementIncrease = (h-playero-(playerh/2)+noteHeight/2)/120;
        System.out.println(displacementIncrease);
        for(int i = 0;i<noteAmount;i++)
        {
            note = new Note(displacementIncrease,width/2,-playerOffset,noteWidth,noteHeight,Color.BLACK,height,activeNoteList,nonActiveNoteList,this,false,rowAmounts);
            nonActiveNoteList.add(note);
        }
        popup= new TimingPopup(20,height-playerOffset-playerHeight,Color.BLACK,width,"",this);

        playerTriggerImage = null;
        try {
            playerTriggerImage = ImageIO.read(new File("sprites/player trigger.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void reset()
    {
        changeScore(-999999999);
        nonActiveNoteList.clear();
        activeNoteList.clear();
        for(int i = 0;i<noteAmount;i++)
        {
            note = new Note(displacementIncrease,width/2,-playerOffset,noteWidth,noteHeight,Color.BLACK,height,activeNoteList,nonActiveNoteList,this,false,rowAmounts);
            nonActiveNoteList.add(note);
        }
    }
    
    public void SetLoading(boolean enabled)
    {
        System.out.println("loading new song");
        RedrawCanvas();
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

        int rowWidth = (int)(noteWidth+40);
        for(int i = 0;i<rowAmounts;i++){
            Rectangle2D.Double row = new Rectangle2D.Double(((width/rowAmounts)*i)+(width/rowAmounts)/2-rowWidth/2,0,rowWidth,height);
            if(i%2==0)
                g2d.setColor(new Color(100,204,255));
            else
                g2d.setColor(new Color(100,153,255));
            g2d.fill(row);
        }

        g2d.setColor(Color.WHITE);
        g2d.drawImage(playerTriggerImage,0,(int)(height-playerHeight/1.5-playerOffset),width,(int)(playerHeight/2),this);

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
                            break; 
                        }else if(noteY>height-playerHeight-playerOffset&&noteY+noteHeight<height-playerOffset)
                        {
                            //System.out.println("perfect");
                            changeScore(100);
                            popup.changeText("perfect",Color.GREEN);
                            note.removeNote();
                            nonActiveNoteList.add(note);
                            activeNoteList.remove(note);
                            activeNoteListSize = activeNoteList.size();
                            break; 
                        }else  if(noteY+noteHeight<height-playerOffset)
                        {
                            //System.out.println("early");
                            changeScore(50);
                            popup.changeText("early",Color.YELLOW);
                            note.removeNote();
                            nonActiveNoteList.add(note);
                            activeNoteList.remove(note);
                            activeNoteListSize = activeNoteList.size();
                            break; 
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
