
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
    
    public Button(int x, int y,int height,GameWindow window,String text,int file,GameLoop gl)
    {
        this.fileSelected = file;
        this.height = height;
        this.gl = gl;
        
        this.window = window;
        
        this.button = new JButton();
        this.button.setText(text);
        this.button.setBounds (x,y,width,height);
        this.button.setFocusable(false);
        this.button.addActionListener(this);
        
        
        this.window.add(button);
        this.window.setLayout(null);
        this.button.setVisible(true);
        
        this.window.repaint();
        this.window.revalidate();
    }
    
    public void actionPerformed(ActionEvent e){
        gl.endLoop(fileSelected); 
        System.out.println("pressed : "+fileSelected);
    }
    
    public void remove()
    {
        window.remove(button);
    }
}
