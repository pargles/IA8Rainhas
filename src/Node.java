/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author gaci15
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
