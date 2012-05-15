import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 * @author pargles
 * @version 3.2
 */

public class RainhasInterface extends JFrame implements Observer{
  enum heuristica{AStar, Encosta,Tempera;}
  private JPanel painelTabuleiro,painelConf;
  private JButton iniciar;
  private JLabel labelEncosta,labelTempera,labelNivel,nivel,labelEstados,estados,labelTempo,demorou,labelVazio;
  private JComboBox listaAlgoritmos = new JComboBox();//para colocar os algoritmos
  private String tipoBusca ="AStar";//default
  private String diretorio = System.getProperty("user.dir");
  private String enderecoImagem = diretorio+ "/white.png";
  private JTextField temperatura,reestarts;
  private NQueens nq;
  private Solution solucao;//recebe a lista de nodos abertos e profundidade etc
  public long tempo;//armazena tempo que demorou para calcular em segundos
  private Thread processo;

  /*
   * metodo construtor da classe
   */
  public RainhasInterface()
  {
      nq = new NQueens();
      iniciaComponentes();
      printaRainhas(nq.table.table);//tabuleiro inicial, sorteado
  }

  /* metodo que inicia todos objetos
   * que a interface vai conter
   * @param void
   * @return void
   */
    public void iniciaComponentes()
    {
        
        
        painelConf = new JPanel();
        this.getContentPane().add(BorderLayout.WEST,painelConf);
        painelConf.setLayout(new GridLayout(0, 1));
        //add(painelTabuleiro);

        iniciar = new JButton("Iniciar");
        iniciar.addActionListener(new botaoIniciar());

        listaAlgoritmos.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"AStar", "Encosta", "Tempera"}));
        listaAlgoritmos.addActionListener(new selecionaAlgoritmo());

        labelTempera = new JLabel("temperatura:");
        labelEncosta = new JLabel("reestarts: ");
        labelNivel= new JLabel("nivel: ");nivel= new JLabel("");
        labelEstados= new JLabel("estados: ");estados= new JLabel("");
        labelTempo = new JLabel("tempo: ");demorou =new JLabel("");
        labelVazio = new JLabel("\n");

        temperatura = new JTextField();
        temperatura.setText("10000");temperatura.setEnabled(false); 
        reestarts = new JTextField();
        reestarts.setText("50"); reestarts.setEnabled(false);

        painelConf.add(listaAlgoritmos);
        painelConf.add(labelEncosta);
        painelConf.add(reestarts);
        painelConf.add(labelTempera);
        painelConf.add(temperatura);
        painelConf.add(labelVazio);
        painelConf.add(iniciar);
        painelConf.add(labelNivel);
        painelConf.add(nivel);
        painelConf.add(labelEstados);
        painelConf.add(estados);
        painelConf.add(labelTempo);
        painelConf.add(demorou);
        //painelConf.add(labelVazio);

        printaTabuleiro();
}
    
    /* Metodo que imprime na iterface os resultados de tempo
     * nivel e quantidade de estados visitados
     * @param void
     * @return void
     */
    public void imprimeResultados() {
        nivel.setText(""+solucao.depth);
        estados.setText(""+solucao.open);//open e igual a way.size
        demorou.setText("" + tempo + " s");
        //printaTabuleiro();//para limpar a tela
        printaRainhas(solucao.way.get(solucao.way.size()-1).table.table);

    }

    /* metodo que printa o tabuleiro na interface
     * @param void
     * @return void
     */
    public void printaTabuleiro() {
        
        Dimension boardSize = new Dimension(300, 300);
        painelTabuleiro = new JPanel();
        painelTabuleiro.setLayout(new GridLayout(8, 8));
        painelTabuleiro.setPreferredSize(boardSize);
        this.getContentPane().add(BorderLayout.EAST,painelTabuleiro);
        for (int i = 0; i < 64; i++) {
            JPanel square = new JPanel(new BorderLayout());
            painelTabuleiro.add(square);
            int row = (i / 8) % 2;// a cada 8 coluna o resto da divisao deve dar zero para inciar outra linha
            if (row == 0) {//se iniciou outra linha verifica se a peca deve ser branca ou preta
                square.setBackground(i % 2 == 0 ? Color.white : Color.black);
            } else {
                square.setBackground(i % 2 == 0 ? Color.black : Color.white);
            }
        }
    }

  /* metodo que recebe um vetor com a solucao e printa ele na interface
   * @param int[] solucao
   * @return void
   */
  public void printaRainhas(int[] solucao)
  {
      painelTabuleiro.removeAll();
      remove(painelTabuleiro);
       printaTabuleiro();//para limpar a tela
      int posicaoRainha;
      for(int i=0; i<solucao.length;i++)
      {
          posicaoRainha = solucao[i];posicaoRainha*=8;posicaoRainha+=i;// *= posiciona na linha e +=i posiciona na coluna
          JLabel peca = new JLabel(new ImageIcon(enderecoImagem));
          JPanel panel = (JPanel) painelTabuleiro.getComponent(posicaoRainha);
          panel.add(peca);
      }
      add(painelTabuleiro);
      validate();
      repaint();
  }

    /* evento que cuida o botao iniciar
     * @param void
     * @return void
     */
    public class botaoIniciar implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            iniciar.setEnabled(false);
            long tempoInicio = System.currentTimeMillis();
            switch (heuristica.valueOf(tipoBusca)) {
                case AStar:
                    solucao = nq.solveAStar();
                    break;
                case Encosta:
                    //nq.tempera.setReestarts(Integer.parseInt( reestarts.getText();)));
                    //solucao = nq.solvehillClimbing();
                    break;
                case Tempera:
                    nq.tempera.setTemperatura(Integer.parseInt(temperatura.getText()));
                    solucao = nq.solveSimulatedAnnealing();
                    break;
            }
            tempo = (System.currentTimeMillis() - tempoInicio) / 1000;
            nivel.setText("" + solucao.depth);
            estados.setText("" + solucao.open);//open e igual a way.size
            demorou.setText("" + tempo + " s");
            executaProcesso();
            processo = null;//pronto para outro processo
            //imprimeResultados();
            iniciar.setEnabled(true);

        }
    }

     /* evento que cuida da caixa para selecionar o nome
      * do algoritmo a ser executado
     * @param void
     * @return void
     */
    public class selecionaAlgoritmo implements ActionListener {

        public void actionPerformed(ActionEvent ae) {
            tipoBusca = (String) listaAlgoritmos.getSelectedItem();
            temperatura.setEnabled(false); //desativa a caixa de testo da temperatura
            reestarts.setEnabled(false);//desativa a caixa de texto dos reestarts
            if(tipoBusca.compareTo("Encosta")==0)
            {
                reestarts.setEnabled(true);
            }
            if(tipoBusca.compareTo("Tempera")==0)
            {
                temperatura.setEnabled(true);
            }
        }
    }

    /**
     * Atualiza a tela
     * @see java.util.Observerupdate(java.util.Observable, java.lang.Object)
     * @param o Objeto que sofreu uma atualização
     * @param arg Argumento passado pelo objeto para seus observadores
     */
    public void update(Observable o, Object arg) {
        //se nao for um boolean, ou seja se nao terminou o processo
        if (!(arg instanceof Boolean)) {
            int[] temp = (int[]) arg;
            printaRainhas(temp);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(RainhasInterface.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void executaProcesso() {
        if (processo == null)
        { //Instancia a thread SE não existir uma
            processo = new Thread(new printSolution(this));
            processo.start();
        } 
        else
        {
            System.out.println("O processo ainda está em execução");
        }
    }

    public class printSolution extends Observable implements Runnable {

        public printSolution(Observer observador) {
            addObserver(observador);
        }

        public void run() {
            //if (oito.way.size() > 0){
            int i = 0;
            int[] sol = solucao.way.get(solucao.way.size()-1).table.table;
            int[] temp = nq.table.table;//recebe o vetor incial
            //cada posicao dele, a cada passagem do laco, sera subsituida pela posicao final

            for (i=0; i < temp.length; i++) {
                temp[i] = sol[i];//cada volta do laco uma posicao do incial e trocada pelo final
                notifyObservers(temp);
                setChanged();
                //Notifica o processamento a cada 1 iteração
            }
            //Notifica fim do processo
            notifyObservers(new Boolean(true));
            setChanged();
            //}

        }
    }
}