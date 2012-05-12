
import java.util.*;


/**
 *
 * @author pargles
 * @version 1.1
 */
public class AmebaSimulada
{
    private int temperaturaMaxima;//limite superior, onde a temperaturaMaxima no laco inicia
    private int fator=1; // usado no laco for para decrementar a temperaturaMaxima, normalmente e 1
    Table atual,proximo,melhorResultado;
    ArrayList<Node> nodosVisitados = new ArrayList<Node>();
    Node nodo,proximoNodo;
    int n = 8,tentaram,ganharamChance;//default
    
    /*
     * metodo construtor que coloca um valor default para a temperaturaMaxima inicial
     */
    public AmebaSimulada(Table vetorInicial)
    {
        temperaturaMaxima = 10000;
        atual = vetorInicial;
        melhorResultado = atual;
    }
    /*
     * metodo construtor que recebe a temperaturaMaxima inicial
     */
    public AmebaSimulada(Table vetorInicial,int temperatura)
    {
        this.temperaturaMaxima = temperatura;
        atual = vetorInicial;
        melhorResultado = atual;

    }

    /* metodo que executa a tempera simulada
     * @param void
     * @return void
     */
    public void execute()
    {
        double delta,probabilidade,randomico;boolean trocou;
        nodo = new Node(0,melhorResultado,null);
        nodosVisitados.add(nodo);
        atual.print();
        System.out.print("conflitos: "+atual.nConf+" ");

        for(int temperatura = temperaturaMaxima; temperatura > 0 && atual.nConf!=0 ;temperatura-=fator)
        {
            proximo = new Table(atual.table); trocou=false;
            aleatoriza(proximo);//gera o proximo nodo a partir da aleatorizacao do nodo atual
            System.out.print("conflitos: "+proximo.nConf+" ");
            proximo.print();
            //nodo = new Node(8,);
            delta = proximo.nConf - atual.nConf;
            probabilidade = Math.exp(-delta/temperatura);
            randomico = Math.random();//gera numero entre 0.0 e 1.0
            System.out.println("delta: "+delta+" <= prob: "+probabilidade+" => randomico: "+randomico);

            if(delta <= 0)//o proximo nodo tem menos conflitos que o atual
            {
                atual = proximo; trocou = true;//houve mudanca de estados
            }
            else //aki da a chance de um nodo com maior conflitos que o atual ser avaliado
            {
                tentaram++;
                if(randomico <= probabilidade)
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
                proximoNodo = new Node(nodo.h+1,atual,nodo);//cria nodo apenas se alguma posicao foi midificada
                nodosVisitados.add(proximoNodo);
            }
            
        }
        System.out.println(ganharamChance+"de "+tentaram+"obtiveram uma chance");
        System.out.println("MELhOR RESULTADO com "+melhorResultado.nConf+" conflitos");
        melhorResultado.print();
    }

    /*metodo utilizado pela tempera simulada, sorteia uma coluna e altera algum valor dessa coluna
    * @param Table t
     * @return void
     */
    public void aleatoriza(Table table)
    {
        Random random = new Random( System.currentTimeMillis());
        int coluna = random.nextInt ( n -1); // gera um numero aleatorio de 0 ate tamTabuleiro (tamTabuleiro e o tamanho do tabuleiro
        int linha = random.nextInt ( n -1);
        table.table[coluna]= linha;
         table.conflicts();//calcula o numero de conflitos para o novo tabuleiro

    }

}
