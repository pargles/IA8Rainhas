
import javax.swing.JFrame;

/**
 *
 * @authors Stephano and pargles
 */
public class Main {
    public static void main(String[] args) throws InterruptedException{
        int[] tab = {6,1,5,2,3,4,2,2};
        //Table t= new Table(tab);
        //System.out.println("CONFLITOS INICIAIS: "+t.nConf);
        //int[] tab = {4,6,0,3,1,7,5,2};
        //NQueens nq = new NQueens();
        //nq.solveSimulatedAnnealing();
        
        RainhasInterface rainhas = new RainhasInterface();
        rainhas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        rainhas.pack();
        rainhas.setResizable(false);
        //frame.setLocationRelativeTo( null );
        rainhas.setVisible(true);
        //NQueens nq = new NQueens(algoritmo);
        //nq.executa();*/
    }
}
