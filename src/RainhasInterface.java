import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * @author pargles
 * @version 1.0
 */

public class RainhasInterface extends JFrame{
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
        Dimension boardSize = new Dimension(300, 300);
        painelTabuleiro = new JPanel();
        painelConf = new JPanel();
        this.getContentPane().add(BorderLayout.EAST,painelTabuleiro);
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
        painelConf.add(labelNivel); painelConf.add(nivel);
        painelConf.add(labelEstados);painelConf.add(estados);
        painelConf.add(labelTempo);painelConf.add(demorou);
        //painelConf.add(labelVazio);

        painelTabuleiro.setLayout(new GridLayout(8, 8));
        painelTabuleiro.setPreferredSize(boardSize);
        //painelTabuleiro.setBounds(0, 0, boardSize.width, boardSize.height);
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
        painelTabuleiro = new JPanel();
        painelTabuleiro.setLayout(new GridLayout(8, 8));
        this.getContentPane().add(BorderLayout.EAST,painelTabuleiro);
        printaTabuleiro();//para limpar a tela
        printaRainhas(solucao.way.get(solucao.way.size()-1).table.table);

    }

    /* metodo que printa o tabuleiro na interface
     * @param void
     * @return void
     */
    public void printaTabuleiro() {
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
      int posicaoRainha;
      for(int i=0; i<solucao.length;i++)
      {
          posicaoRainha = solucao[i];posicaoRainha*=8;posicaoRainha+=i;// *= posiciona na linha e +=i posiciona na coluna
          JLabel peca = new JLabel(new ImageIcon(enderecoImagem));
          JPanel panel = (JPanel) painelTabuleiro.getComponent(posicaoRainha);
          panel.add(peca);
      }
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
                    //solucao = nq.solvehillClimbing();
                    break;
                case Tempera:
                    solucao = nq.solveSimulatedAnnealing();
                    break;
            }
            tempo = (System.currentTimeMillis() - tempoInicio) / 1000;
            imprimeResultados();
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
}