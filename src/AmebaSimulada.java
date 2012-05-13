
import java.util.*;


/**
 *
 * @author pargles
 * @version 1.2
 */
public class AmebaSimulada
{
    private int temperaturaMaxima;//limite superior, onde a temperaturaMaxima no laco inicia
    private int fator=1; // usado no laco for para decrementar a temperaturaMaxima, normalmente e 1
    Table atual,proximo,melhorResultado;
    ArrayList<Node> nodosVisitados = new ArrayList<Node>();
    Node nodo,proximoNodo;
    int n,tentaram,ganharamChance;
    static Random random;
    
    /*
     * metodo construtor que recebe a quantidade de rainhas e o vetor inicial
     */
    public AmebaSimulada(int rainhas,Table vetorInicial)
    {
        n = rainhas;
        atual = vetorInicial;
        melhorResultado = atual;
        random = new Random( System.currentTimeMillis());

    }

    /* metodo que executa a tempera simulada e
     * retorna uma lista com os passos para a solucao
     * @param void
     * @return Solution execute
     */
     public Solution execute()
    {
       double delta,probabilidade,randomico;boolean trocou;
       Node nodoSeguinte;
       Node nodoAtual = new Node(0,atual, null); nodosVisitados.add(nodoAtual);
       System.out.println("conflitos: "+atual.nConf+" ");
        atual.print();
        System.out.println("");
        
        int temperatura;
        for(temperatura = temperaturaMaxima; temperatura > 0 && atual.nConf!=0 ;temperatura-=fator)
        {
            proximo = new Table(atual.table); trocou=false;
            aleatoriza(proximo);//gera o proximo nodo a partir da aleatorizacao do nodo atual
            System.out.print("conflitos: "+proximo.nConf+" ");
            proximo.print();
            System.out.println("");
            //nodo = new Node(8,);
            delta = proximo.nConf - atual.nConf;
            probabilidade = Math.exp(-delta/temperatura);
            randomico = Math.random();//gera numero entre 0.0 e 1.0
            
            if(delta <= 0)//o proximo nodo tem menos conflitos que o atual
            {
                atual = proximo; trocou = true;//houve mudanca de estados
            }
            else //aki da a chance de um nodo com maior conflitos que o atual ser avaliado
            {
                tentaram++;
                if(randomico < probabilidade)
                {
                    atual = proximo;trocou=true;
                    ganharamChance++;
                }
            }
            if(trocou)//se houve alguma modificacao no nodo tual
            {
                if(melhorResultado.nConf > atual.nConf)
                {
                    melhorResultado = atual;
                }
                nodoSeguinte = new Node(nodoAtual.h+1,atual, nodoAtual);
                nodosVisitados.add(nodoSeguinte);
                nodoAtual = nodoSeguinte;
                
            }
            
        }
        System.out.println(ganharamChance+" de "+tentaram+" obtiveram uma chance");
        System.out.println("MELhOR RESULTADO com "+melhorResultado.nConf+" conflitos");
        Solution listaSolucao = new Solution(nodosVisitados,nodosVisitados.size(),nodosVisitados.size());
        melhorResultado.print();
        return listaSolucao;
    }

    /*metodo utilizado pela tempera simulada, sorteia uma coluna e altera algum valor dessa coluna
    * @param Table t
     * @return void
     */
    public void aleatoriza(Table table)
    {
        int linha = random.nextInt ( n -1); // gera um numero aleatorio de 0 ate tamTabuleiro (tamTabuleiro e o tamanho do tabuleiro
        int coluna = random.nextInt ( n -1);
        table.table[coluna]=linha;

    }

    /* metodo para setar a temperatura
     * @param int temperatura
     * @return void
     */
    public void setTemperatura(int temperatura)
    {
        temperaturaMaxima = temperatura;
    }

}
