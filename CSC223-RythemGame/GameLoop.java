
/**
 * Creates a new window for the game
 *
 * @Joshua wolf
 * @version 1
 */
import java.util.Timer;
import java.util.TimerTask;
public class GameLoop
{
    int windowSize = 800;
    
    DrawCanvas dc = new DrawCanvas(windowSize,windowSize);
    GameWindow GameWindow=new GameWindow(windowSize,dc);
        
    public GameLoop()
    {
        // initially creat the window
        GameWindow.createGameWindow();
        
        

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run () {
               dc.RedrawCanvas();
            }
        }, 0, 16);
    }

}
