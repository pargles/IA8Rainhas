/**
 *
 * @author Stephano
 */

import java.util.*;

public class NQueens {

    protected Table table; //representacao do tabuleiro inicial
    int n;  //tamanho do tabuleiro
    protected AStarNQueens aStar;
    //protected AmebadoParglesSimulada parglesAmeba;
    //protected HillClimbing hillClimbing;

    public NQueens(){
        n = 8;
        table = new Table();
        aStar = new AStarNQueens(n, table);
    }

    public NQueens(int[] table){
        this.n = table.length;
        this.table = new Table(table);
        aStar = new AStarNQueens(n, this.table);
    }

    public Solution solveAStar(){
        return aStar.execute();
    }

    /*public Solution solveSimulatedAnealing(){
        return parglesAmeba.execute();
    }

    public Solution hillClimbing(){
        return hillClimbing.execute();
    }*/
    
}
