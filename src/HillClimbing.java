
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;

/**
 *
 * @author pargles
 */

public class HillClimbing {
    private int reestarts;//quantidade de reestarts
    Table atual,proximo,melhorResultado;
    ArrayList<Node> nodosVisitados;
    ArrayList<Node> caminho;
    Node nodoAtual,proximoNodo,melhorNodo;
    static Random random;
    int n;

    //metodo sobrescrito para dizer oque comparar para inserir um nodo
    Comparator<Node> comparator = new Comparator<Node>()
    {    public int compare(Node n, Node n1) {
            if(n1 == null){ return -1;} // n e menor
            return n.table.nConf<=n1.table.nConf ? -1:1; // -1 coloca o menor valor no inicio da lista de prioridade
            //compara o numero de conflitos
        }
    };

    PriorityQueue<Node> vizinhos;//filfa de prioridade de nodos




    /*
     * metodo construtor que recebe a quantidade de rainhas e o vetor inicial
     */
    public HillClimbing(int rainhas,Table vetorInicial)
    {
        n = rainhas;
        atual = new Table(vetorInicial.table.clone());
        melhorResultado = atual;
        random = new Random( System.currentTimeMillis());
        nodoAtual = new Node(0,atual, null); // cria um nodo com altura 0
        nodosVisitados = new ArrayList<Node>(); //instancia o array de nodos
        caminho = new ArrayList<Node>(); //instancia o array de nodos
        nodosVisitados.add(nodoAtual);//insere a solucao sorteada na lista
        caminho.add(nodoAtual);
        vizinhos = new PriorityQueue<Node>(10, comparator);

    }

    /* metodo que executa a subida da encosta
     * retorna uma lista com os passos para a solucao
     * @param void
     * @return Solution execute
     */
    public Solution execute()
    {
        neighboors(nodoAtual);
        melhorNodo = vizinhos.peek();//peek nao remove elemento enquanto pool remove
        nodoAtual = melhorNodo;
        for(int i= 0;i< reestarts && atual.nConf!=0;i++)
        {
            proximo = new Table(); //altura,     tabela,  Nodopai
            proximoNodo = new Node(nodoAtual.h + 1, proximo, nodoAtual);
            neighboors(proximoNodo);
            nodoAtual = vizinhos.peek();//peek nao remove elemento enquanto pool remove
            if(nodoAtual.table.nConf < melhorNodo.table.nConf)
            {
                melhorNodo = nodoAtual;
                caminho.add(melhorNodo);
            }
            nodoAtual = proximoNodo;
        }
        return caminhoSolucao();
    }

    /* metodo para setar a quantidade de reestars
     * @param int reestars
     * @return void
     */
    public void setReestarts(int reestars)
    {
        this.reestarts = reestars;
    }

    /* metodo que gera uma lista contedo os vizinhos do nodo atual
     * @param void
     * @return void
     */
    private void neighboors(Node no) {
        for (int j = 0; j < n; j++) {
            for (int i = 0; i < n; i++) {
                int[] aux = no.table.table.clone();
                aux[j] = i;
                Table t2 = new Table(aux);
                                            //altura,     tabela,  Nodopai
                proximoNodo = new Node(nodoAtual.h, t2, nodoAtual);
                vizinhos.add(proximoNodo);
                nodosVisitados.add(proximoNodo);
                nodoAtual = proximoNodo;
                //vizinhos.add(t2);
            }
        }
    }

    /* metodo que gera o caminho da classe Solution e retorna esse caminho
     * esse metodo tambem printa o caminho e outras informacoes
     * @param void
     * @return Solution caminhoSolucao
     */
    public Solution caminhoSolucao()
    {                              //lista de nodos , depht (h)      , abertos (open)
        Solution listaSolucao = new Solution(caminho,caminho.size(),nodosVisitados.size());
        System.out.println("Profundidade da solucao: " + listaSolucao.depth);
        System.out.println("Nodos visitados: " + listaSolucao.open);
        System.out.println("Caminho da solucao: ");
        listaSolucao.showWay();
        return listaSolucao;
    }

}
