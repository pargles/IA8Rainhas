import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * @author pargles
 * @version 1.0
 */

public class RainhasInterface extends JFrame{
  private JPanel painelTabuleiro,painelConf;
  private JButton iniciar;
  private JLabel labelEncosta,labelTempera;
  private JComboBox listaAlgoritmos = new JComboBox();//para colocar os algoritmos
  private String tipoBusca;
  private String diretorio = System.getProperty("user.dir");
  private String enderecoImagem = diretorio+ "/white.png";
  private JTextField temperatura,reestarts;
  private NQueens nq;

  /*
   * metodo construtor da classe
   */
  public RainhasInterface()
  {
      nq = new NQueens();
      iniciaComponentes();
      printaRainhas(nq.table.table);
  }

  /* metodo que inicia todos objetos
   * que a interface vai conter
   * @param void
   * @return void
   */
    public void iniciaComponentes()
    {
        Dimension boardSize = new Dimension(600, 600);
        painelTabuleiro = new JPanel();
        painelConf = new JPanel();
        add(painelTabuleiro);

        iniciar = new JButton("Iniciar");
        iniciar.addActionListener(new botaoIniciar());

        listaAlgoritmos.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"AStar", "Encosta", "Tempera"}));
        listaAlgoritmos.addActionListener(new selecionaAlgoritmo());

        labelTempera = new JLabel("temperatura:");
        labelEncosta = new JLabel("reestarts: ");

        temperatura = new JTextField();
        temperatura.setText("10000");temperatura.setEnabled(false); 
        reestarts = new JTextField();
        reestarts.setText("50"); reestarts.setEnabled(false);

        painelConf.setLayout(new BoxLayout(painelConf, BoxLayout.Y_AXIS));
        painelConf.add(listaAlgoritmos);
        painelConf.add(labelEncosta);
        painelConf.add(reestarts);
        painelConf.add(labelTempera);
        painelConf.add(temperatura);
        painelConf.add(iniciar);

        this.getContentPane().add(BorderLayout.WEST, painelConf);
        painelTabuleiro.setLayout(new GridLayout(8, 8));
        painelTabuleiro.setPreferredSize(boardSize);
        //painelTabuleiro.setBounds(0, 0, boardSize.width, boardSize.height);
        printaTabuleiro();
}

  /* metodo que printa o tabuleiro na interface
   * @param void
   * @return void
   */
  public void printaTabuleiro()
  {
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