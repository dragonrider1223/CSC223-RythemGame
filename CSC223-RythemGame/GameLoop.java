
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

import javax.sound.sampled.*;
public class GameLoop
{
    int windowSize = 800;
    private double playerHeight = 100;
    private double playerOffset = 100;

    DrawCanvas dc = new DrawCanvas(windowSize,windowSize,playerHeight,playerOffset);
    Player player = new Player(dc);
    GameWindow GameWindow= new GameWindow(windowSize,dc,player);
    MusicToTextFile converter= new MusicToTextFile();

    private long noteTimer = 1000;
    private int noteIndex;
    private ArrayList<Integer> noteList= new ArrayList<Integer>() ;

    private ArrayList<String> songList = new ArrayList<String>();
    private ArrayList<String> fileList = new ArrayList<String>();
    private String filePath = "SongTextFiles/";
    private String fileName;

    private ArrayList<Button> buttons = new ArrayList<Button>();

    private boolean loop = true;

    public GameLoop()
    {
        Scanner input = new Scanner(System.in);

        // initially creat the window
        GameWindow.createGameWindow();

        File songFolder = new File("Songs/");
        File[] songFiles = songFolder.listFiles();
        for(File file : songFiles)
        {
            if(file.isFile() && file.getName().endsWith(".wav"))
            {
                songList.add(file.getName());
            }
        }
        File folder = new File(filePath);
        File[] files = folder.listFiles();

        for(File file : files)
        {
            if(file.isFile() && file.getName().endsWith(".txt"))
            {
                int fileToRemove = -1;
                for(int i =0;i<songList.size();i++){
                    if(songList.get(i).replace(".wav","").equals(file.getName().replace(".txt","")))
                    {
                        fileToRemove=i;
                    }
                }

                if(fileToRemove>=0)
                    songList.remove(fileToRemove);

                fileToRemove = -1;
                fileList.add(file.getName());
            }
        }

        for(int i = 0;i<songList.size();i++){
            try
            {
                converter.convertSong(songList.get(i).replace(".wav",""),filePath);
                fileList.add(songList.get(i).replace(".wav",".txt"));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        int fileSelected = 0;
        int buttonHeight;
        int buttonX = 20;
        int index = 0;

        System.out.println("what song would you like to play?");
        System.out.println();
        for(int i = 0;i<fileList.size();i++)
        {
            buttonHeight = (100+20)*index+20;
            if(buttonHeight+100 > windowSize){
                buttonHeight = 20;
                index = 0;
                buttonX += 250+20;
            }
            System.out.println((i+1)+". "+fileList.get(i).replace(".txt",""));
            buttons.add(new Button(buttonX, buttonHeight,100,GameWindow,(i+1)+". "+fileList.get(i).replace(".txt",""),i,this));
            index++;
        }

        
        //this is here to make sure the timer and the notes dont desync instead of it being in a later function
        while(loop){
            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException ie)
            {
                ie.printStackTrace();
            }
        }

        try {
            File file = new File(filePath+fileName);
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

        try
        {
            playSong("Songs/"+fileName.replace(".txt",".wav"));
        }
        catch (java.io.IOException ioe)
        {
            ioe.printStackTrace();
        }
    }

    public void endLoop(int chosenSong)
    {
        for(int i = 0;i<buttons.size();i++)
            buttons.get(i).remove();
        fileName = fileList.get(chosenSong);
        loop = false;
    }

    public void playSong(String songFile) throws java.io.IOException {
        File audioFile = new File(songFile);
        Clip song = loadSong(songFile);
        if( song.isRunning() ) song.stop();

        NoteSpawnTimer();
        try
        {
            Thread.sleep(100*16);
        }
        catch (InterruptedException ie)
        {
            ie.printStackTrace();
        }
        song.setFramePosition( 0 );
        song.start();

    }

    private Clip loadSong(String songFile)
    {
        Clip in = null;
        try
        {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream( getClass().getResource( songFile ) );
            in = AudioSystem.getClip();
            in.open( audioIn );
        }catch( Exception e )
        {
            e.printStackTrace();
        }
        return(in);
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
                    //System.out.println("time until next note : "+noteTimer);
                    NoteSpawnTimer();

                }
            };
        if(noteIndex<noteList.size())
            timer.schedule(task,noteTimer,noteTimer);

    }
}
