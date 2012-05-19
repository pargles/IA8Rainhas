
import java.util.*;


/**
 *
 * @author pargles
 * @version 3.2
 */
public class SimulatedAnnealing
{
    private int temperaturaMaxima;//limite superior, onde a temperaturaMaxima no laco inicia
    private int fator=1; // usado no laco for para decrementar a temperaturaMaxima, normalmente e 1
    Table principal,atual,proximo,melhorResultado;
    ArrayList<Node> nodosVisitados,caminho;
    ArrayList<Table> vizinhos;
    Node nodoAtual,proximoNodo,melhorNodo;
    int n,tentaram,ganharamChance;
    static Random random;

    
    /*
     * metodo construtor que recebe a quantidade de rainhas e o vetor inicial
     */
    public SimulatedAnnealing(int rainhas,Table vetorInicial)
    {
        n = rainhas;
        principal = new Table(vetorInicial.table.clone());
        melhorResultado = principal;
        random = new Random( System.currentTimeMillis());
        nodoAtual = new Node(0,principal, null); // cria um nodo com altura 0
        nodosVisitados = new ArrayList<Node>(); //instancia o array de nodos
        vizinhos = new ArrayList<Table>(); //instancia o array de nodos
        nodosVisitados.add(nodoAtual);//insere a solucao sorteada na lista
        caminho = new ArrayList<Node>();//instancia da lista que contera o caminho ate a solucao
        caminho.add(nodoAtual);//insere primeiro nodo na lista do caminho

    }

    /* metodo que executa a tempera simulada e
     * retorna uma lista com os passos para a solucao
     * @param void
     * @return Solution execute
     */
     public Solution execute()
    {
       double delta,probabilidade,randomico;
       boolean trocou;
       int temperatura;
       atual = new Table(principal.table.clone());
      
        for(temperatura = temperaturaMaxima; temperatura > 0 && atual.nConf!=0 ;temperatura-=fator)
        {
            neighboors(atual);
            proximo = new Table(vizinhoAleatorio().table.clone());
            vizinhos.clear();
            trocou=false;
            delta = proximo.nConf - atual.nConf;
            probabilidade = Math.exp(-delta/temperatura)-0.5;
            randomico = Math.random();//gera numero entre 0.0 e 1.0
            
            if(delta < 0)//o proximo nodo tem menos conflitos que o atual
            {
                atual = proximo;
                trocou = true;//houve mudanca de estados
            }
            else //aki da a chance de um nodo com maior conflitos que o atual ser avaliado
            {
                tentaram++;
                if(randomico < probabilidade)
                {
                    atual = proximo;
                    trocou=true;
                    ganharamChance++;
                }
            }
            if (trocou)//se houve alguma modificacao no nodo atual
            {

                if (melhorResultado.nConf > atual.nConf) {
                    melhorResultado = atual;
                    //altura,     tabela,  Nodopai
                    Node nodoAux = new Node(nodoAtual.h + 1, atual, nodoAtual);
                    caminho.add(nodoAux);
                }
            }
            //altura,     tabela,  Nodopai
            proximoNodo = new Node(nodoAtual.h + 1, atual, nodoAtual);
            nodosVisitados.add(proximoNodo);
            nodoAtual = proximoNodo;
        }
        System.out.println(ganharamChance+" de "+tentaram+" obtiveram uma chance");
        //melhorResultado.print();
        return caminhoSolucao();
    }

    /* metodo para setar a temperatura
     * @param int temperatura
     * @return void
     */
    public void setTemperatura(int temperatura)
    {
        temperaturaMaxima = temperatura;
    }

    /* metodo que gera uma lista contedo os vizinhos do nodo atual
     * @param void
     * @return void
     */  
    private void neighboors(Table t){
        for (int j = 0; j < n; j++) {
            for (int i = 0; i < n; i++) {
                int[] aux = t.table.clone();
                aux[j] = i;
                Table t2 = new Table(aux);
                vizinhos.add(t2);
            }
        }
    }

    /*  metodo que seleciona um vizinho aleatoriamente
     * e retorna a disposicao do tabuleiro desse vizinho
     * @param void
     * @ return Table vizinhoAleatorio
     */
    public Table vizinhoAleatorio()
    {
         int posicao = random.nextInt (vizinhos.size()-1);
         return vizinhos.get(posicao);

    }

    /* metodo que gera o caminho da classe Solution e retorna esse caminho
     * esse metodo tambem printa o caminho e outras informacoes
     * @param void
     * @return Solution caminhoSolucao
     */
    public Solution caminhoSolucao()
    {                              //lista de nodos , depht (h)      , abertos (open)
        Solution listaSolucao = new Solution(caminho,caminho.size(),nodosVisitados.size()-1);
        System.out.println("Profundidade da solucao: " + listaSolucao.depth);
        System.out.println("Nodos visitados: " + listaSolucao.open);
        System.out.println("Caminho da solucao: ");
        listaSolucao.showWay();
        return listaSolucao;
    }

}
