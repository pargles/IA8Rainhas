
/**
 *
 * @author gaci15
 */
import java.util.*;

public class AStarNQueens {

    TreeMap<String, Integer> visited;
    ArrayList<Node> open;
    Table table;
    int n;

    public AStarNQueens(int n, Table t){
        visited = new TreeMap<String, Integer>();
        open = new ArrayList<Node>();
        table = t;
        //System.out.println(table.nConf);
        this.n = n;
    }

    public Solution execute(){
        Node first = new Node(0,table, null);
        setCost(first);
        insertSorted(open, first);
        while (open.size() > 0){
            Node t = open.get(0);
            if (t.table.nConf == 0) {
                Node temp = t;
                ArrayList<Node> way = new ArrayList<Node>();
                while (t.parent != null){
                    way.add(0, t);
                    t = t.parent;
                }
                way.add(0, t);
                Solution solution = new Solution(way,temp.h, visited.size());
                System.out.println("Profundidade da solucao: "+solution.depth);
                System.out.println("Nodos visitados: "+solution.open);
                System.out.println("Caminho da solucao: ");
                for (int i = 0; i < way.size(); i++){
                    printTable(way.get(i).table.table);
                }
                return solution;
            }
            open();   //abre o menos custoso da lista dos abertos
        }
        return null;
    }

    //gera os proximos filhos a partir de um tabuleiro
    private void open(){
        Node t = open.remove(0);
        for (int j = 0; j < n; j++){
            for (int i = 0; i < n; i++){
                //System.out.print("Coluna "+j+" Linha "+i);
                int[] aux = t.table.table.clone();
                aux[j] = i;
                
                //printTable(aux);
                if (!visited.containsKey(KeyGen(aux))){     //se a nova disposição gerada nao foi visitada ainda
                    Table t2 = new Table(aux);
                    Node naux = new Node(t.h+1, t2, t);
                    setCost(naux);
                    insertSorted(open, naux);
                }
            }
        }
    }

    private void printTable(int[] aux){
        System.out.print("[");
        for (int k = 0; k < aux.length; k++){
            System.out.print(aux[k]+",");
        }
        System.out.println("]");
    }

    private void insertSorted(ArrayList<Node> l, Node t){
        visited.put(KeyGen(t.table.table), 0);    //toda vez que um nodod for aberto ele é inserido na lista de visitados (todos os visitados foram abertos em algum momento)
        if (l.size() > 0){
            for (int i = 0; i < l.size(); i++){
                Node atual = l.get(i);
                if (t.cost <= atual.cost){
                    l.add(i, t);
                    return;
                }
            }
        }else l.add(t);
    }

    private String KeyGen(int[] tab){
        String s = "";
        for (int i = 0; i < tab.length; i++){
            s = s + Integer.toString(tab[i]);
        }
        return s;
    }

    private int h(Node n){
        return n.table.nConf;
    }

    private int g(Node n){
        return 0;
    }

    private void setCost(Node n){
        n.cost = h(n) + g(n);
    }
}
