/**
 *
 * @author Stephano
 */

import java.util.*;

public class NQueens {

    protected Table table; //representacao do tabuleiro inicial
    int n;  //tamanho do tabuleiro
    protected AStarNQueens aStar;
    protected SimulatedAnnealing tempera;
    protected HillClimbing encosta;

    public NQueens(){
        n = 8;
        table = new Table();
        aStar = new AStarNQueens(n, table);
        tempera = new SimulatedAnnealing(n,table);
        encosta = new HillClimbing(n, table);
    }

    public NQueens(int[] table){
        this.n = table.length;
        this.table = new Table(table);
        aStar = new AStarNQueens(n, this.table);
        tempera = new SimulatedAnnealing(n,this.table);
        encosta = new HillClimbing(n, this.table);
    }

    public Solution solveAStar(){
        return aStar.execute();
    }

    public Solution solveSimulatedAnnealing(){
        return tempera.execute();
    }

    public Solution solveHillClimbing(){
        return encosta.execute();
    }
    
}
