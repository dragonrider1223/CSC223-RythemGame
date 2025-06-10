
/**
 * Creates a new window for the game
 *
 * @Joshua wolf
 * @version 1.1
 */
import java.util.Timer;
import java.util.TimerTask;
public class GameLoop
{
    int windowSize = 800;
    
    DrawCanvas dc = new DrawCanvas(windowSize,windowSize);
    Player player = new Player(dc);
    GameWindow GameWindow= new GameWindow(windowSize,dc,player);
        
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
        
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run () {
                dc.AddNote();
            }
        }, 0, 1600);
    }

}
