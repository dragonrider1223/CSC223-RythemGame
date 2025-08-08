
/**
 * This class handels all buttons in the game
 *
 * @Joshua wolf
 */
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.event.*;

public class Button extends JFrame implements ActionListener
{
    private int width = 250;
    private int height;
    private int wScale;
    private JButton button;
    private GameLoop gl;
    private int fileSelected;
    GameWindow window;
    private boolean levelButton;
    private DrawCanvas dc;

    public Button(int x, int y,int height,GameWindow window,String text,int file,GameLoop gl,DrawCanvas dc,boolean levelButton)
    {
        this.fileSelected = file;
        this.height = height;
        this.gl = gl;
        
        this.dc = dc;

        this.levelButton = levelButton;

        this.window = window;

        this.button = new JButton();
        this.button.setText(text);
        this.button.setBounds (x,y,width,height);
        this.button.setFocusable(false);
        this.button.addActionListener(this);

        this.dc.add(button);
        this.dc.setLayout(null);
        this.button.setVisible(true);

        this.dc.repaint();
        this.dc.revalidate();
    }

    public void actionPerformed(ActionEvent e){
        if (levelButton) {
            gl.endLoop(fileSelected);
            System.out.println("pressed : " + fileSelected);
        } else {
            new Thread(new Runnable() {
                    @Override
                    public void run() {
                        gl.restartGame();
                    }
                }).start();
        }
    }

    public void remove()
    {
        dc.remove(button);
    }
}
