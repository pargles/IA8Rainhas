/**
 *
 * @author stephano
 */

import java.util.*;

public class Solution {

    protected ArrayList<Node> way;
    protected int depth;
    protected int open;

    public Solution(ArrayList<Node> l, int h, int o){
        way = l;
        depth = h;
        open = o;
    }

    void showWay() {
        for (int i = 0; i < way.size(); i++)
        {
            System.out.print("conflitos: " + way.get(i).table.nConf + " ");
            way.get(i).table.print();
        }
    }
}
