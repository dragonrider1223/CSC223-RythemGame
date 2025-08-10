
/**
 * Write a description of class MusicToTextFile here.
 *
 * Joshua wolf
 * v2
 */
import java.io.BufferedWriter;
import java.io.FileWriter;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
public class MusicToTextFile
{   
    String songPath = "Songs/";

    int frameSize = 512;
    DrawCanvas dc;
    
    //converts the song wav into a txt file using its energy volume at each sample
    public void convertSong(String songName,String textFilePath,DrawCanvas dc) throws Exception
    {
        this.dc = dc;
        dc.SetLoading(true);
        
        File audioFile = new File(songPath+songName+".wav");
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
        AudioFormat format = audioInputStream.getFormat();

        long previousTimeStamp = 0;
        
        int bytesPerFrame = format.getFrameSize();
        int sampleRate = (int) format.getSampleRate();
        byte[] buffer = new byte[frameSize];

        int bytesRead;
        int frameIndex = 0;

        double previosSampleEnergy = 0;
        double energyThreshold = 3.4;
        double energySensetivity = 0.00092;

        new FileWriter(textFilePath+songName+".txt", false).close();
        try {
            File newFile = new File(textFilePath+songName+".txt");
            if (!newFile.createNewFile()) {System.out.println("File created: " + newFile.getName());}      
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        //adds a note to the start of each song
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(textFilePath+songName+".txt", true))){
            writer.write("1");
            writer.newLine();
        }catch(IOException e){}
        
        int previosMsTime = 0;
        
        //while the song has
        while ((bytesRead = audioInputStream.read(buffer))!=-1)
        {
            double energy = 0;
            for(int i=0;i<bytesRead; i+=2)
            {
                int low = buffer[i] & 0xff;
                int high = buffer[i + 1];
                int sample = (high << 8) | low;

                double normalizedSample = sample / 32768.0;
                energy+= normalizedSample*normalizedSample;
            }
            energy = energy / (bytesRead / 2);
            
            //checks if the energy difference between samples is large enough and notes the millesecond time between the previous note
            if (energy > previosSampleEnergy * energyThreshold && (energy - previosSampleEnergy) > energySensetivity) {
                long timestampMs = (long) ((frameIndex * frameSize / bytesPerFrame) * 1000.0 / sampleRate)-previousTimeStamp;
                previousTimeStamp += timestampMs;
                
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(textFilePath+songName+".txt", true))) {
                    if (timestampMs > 100)
                    {
                        writer.write( String.valueOf(timestampMs+previosMsTime));
                        previosMsTime = 0;
                        writer.newLine();
                    }else
                        previosMsTime += timestampMs;
                    
                } catch (IOException e) {}
            }
            previosSampleEnergy = energy;
            frameIndex++;
        }
        dc.SetLoading(false);

    }   
}
