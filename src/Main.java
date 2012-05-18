
import javax.swing.JFrame;

/**
 *
 * @authors Stephano and pargles
 */
public class Main {
    public static void main(String[] args) throws InterruptedException{
        RainhasInterface rainhas = new RainhasInterface();
        rainhas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        rainhas.pack();
        rainhas.setResizable(false);
        //frame.setLocationRelativeTo( null );
        rainhas.setVisible(true);
    }
}
