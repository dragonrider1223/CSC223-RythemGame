
/**
 * Creates handles all of the timigns of frames and is the starting class
 *
 * @Joshua wolf
 * @version 1.2
 */
import java.util.Timer;
import java.util.TimerTask;

import java.io.File; 
import java.io.FileNotFoundException;  
import java.util.Scanner; 

import java.util.ArrayList;
public class GameLoop
{
    int windowSize = 800;
    private double playerHeight = 150;
    private double playerOffset = 100;
    
    DrawCanvas dc = new DrawCanvas(windowSize,windowSize,playerHeight,playerOffset);
    Player player = new Player(dc);
    GameWindow GameWindow= new GameWindow(windowSize,dc,player);
    
    private long noteTimer = 1000;
    private int noteIndex;
    private ArrayList<Integer> noteList= new ArrayList<Integer>() ;
    
    private String filePath = "test.txt";
        
    public GameLoop()
    {
        // initially creat the window
        GameWindow.createGameWindow();
        
        try {
          File file = new File("test.txt");
          Scanner myReader = new Scanner(file);
          while (myReader.hasNextLine()) {
              noteList.add(Integer.parseInt(myReader.nextLine()));
          }
          myReader.close();
        } catch (FileNotFoundException e) {
          System.out.println("An error occurred.");
          e.printStackTrace();
        }
        noteTimer = noteList.get(0);
        
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run () {
                dc.RedrawCanvas();
            } 
        }, 0, 16);
        
        NoteSpawnTimer();
    }

    private void NoteSpawnTimer()
    {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run () {
                dc.AddNote();
                noteIndex++;
                if(noteIndex<noteList.size())
                    noteTimer = noteList.get(noteIndex);
                timer.cancel();
                System.out.println("time until next note : "+noteTimer);
                NoteSpawnTimer();
                
                
            }
        };
        if(noteIndex<noteList.size())
            timer.schedule(task,noteTimer,noteTimer);
        
    }
}
