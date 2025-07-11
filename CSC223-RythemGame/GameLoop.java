
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
    private double playerHeight = 150;
    private double playerOffset = 100;

    DrawCanvas dc = new DrawCanvas(windowSize,windowSize,playerHeight,playerOffset);
    Player player = new Player(dc);
    GameWindow GameWindow= new GameWindow(windowSize,dc,player);
    MusicToTextFile converter= new MusicToTextFile();

    private long noteTimer = 1000;
    private int noteIndex;
    private ArrayList<Integer> noteList= new ArrayList<Integer>() ;

    private ArrayList<String> fileList = new ArrayList<String>();
    private String filePath = "SongTextFiles/";
    private String fileName;

    public GameLoop()
    {
        Scanner input = new Scanner(System.in);
        
        File songFolder = new File("Songs/");
        File[] songFiles = songFolder.listFiles();
        try
        {
            converter.convertSong("metronome",filePath);
            converter.convertSong("AlienExtermination",filePath);
            converter.convertSong("Toby Fox - Megalovania",filePath);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        File folder = new File(filePath);
        File[] files = folder.listFiles();
        for(File file : files)
        {
            if(file.isFile() && file.getName().endsWith(".txt"))
            {
                fileList.add(file.getName());
                System.out.println(file.getName());
            }
        }
        boolean loop = true;
        int fileSelected = 0;
        while(loop)
        {
            System.out.println("what song would you like to play?");
            for(int i = 0;i<fileList.size();i++)
            {
                System.out.println((i+1)+". "+fileList.get(i));
            }
            String temp = input.nextLine();
            fileSelected = 0;
            try{
                fileSelected = Integer.parseInt(temp);
            }catch(NumberFormatException e){
                System.out.println("that is not an int");
            }

            if(fileSelected>0&&fileSelected<fileList.size()+1)
            {
                fileName = fileList.get(fileSelected-1);
                loop = false;
            }else{System.out.println("Invalid input please try again");}

        }
        // initially creat the window
        GameWindow.createGameWindow();

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

        NoteSpawnTimer();
        
        try
        {
            playSong("Songs/"+fileName.replace(".txt",".wav"));
        }
        catch (java.io.IOException ioe)
        {
            ioe.printStackTrace();
        }
    }
    
    public void playSong(String songFile) throws java.io.IOException {
         File audioFile = new File(songFile);
        try
        {
            Thread.sleep(1450);
        }
        catch (InterruptedException ie)
        {
            ie.printStackTrace();
        }
        try
        {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            try
            {
                Clip clip = AudioSystem.getClip();
                clip.open(audioStream);
                clip.start();
            }
            catch (LineUnavailableException lue)
            {
                lue.printStackTrace();
            }
        }
        catch (UnsupportedAudioFileException uafe)
        {
            uafe.printStackTrace();
        }

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
