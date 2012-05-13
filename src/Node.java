/**
 *
 * @author stephano
 */
public class Node {
        protected int h;  //altura
        protected Table table;    //tabuleiro
        protected Node parent;   //nodo pai
        protected int cost; //custo do nodo = g(n) + h(n)

        public Node(int h, Table t, Node p){
            this.h = h;
            table = t;
            parent = p;
            cost = 0;
        }
}
