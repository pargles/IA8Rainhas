/**
 *
 * @author Stephano
 */
public class Table {
    protected int[] table; //tabuleiro
    protected int nConf;  //numero de conflitos do tabuleiro
    protected int n;  //tamanho do tabuleiro

    //gera um tabuleiro randomico de tamanho 8
    public Table(){
        n = 8;
        table = new int[8];
        for (int i = 0; i < n; i++){
            table[i] = (int)(Math.random()*(n-1));
        }
        nConf = conflicts();
        //print();
    }

    public Table(int[] t){
        this.n = t.length;
        table = t;
        nConf = conflicts();
    }

    //calcula e retorna o numero de conflitos do tabuleiro
    public int conflicts(){
        int conf = 0;
        int atual;
        int ant;
        int i,j;
        for ( i = 0; i < n; i++){
            ant = table[i];
            for (j = i + 1; j < n; j++){
                atual = table[j];
                int dif = j - i; //diferenca em linhas/colunas do atual em relacao ao anterior
                if ((atual == ant) || (atual == ant + dif) || (atual == ant - dif)){
                    conf ++;
                }
            }
        }
        return conf;
    }

    public static int conflicts(Table t){
        int conf = 0;
        int atual;
        int ant;
        int i,j;
        int n = t.n;
        int[] table = t.table;
        for ( i = 0; i < n; i++){
            ant = table[i];
            for (j = i + 1; j < n; j++){
                atual = table[j];
                int dif = j - i; //diferenca em linhas/colunas do atual em relacao ao anterior
                if ((atual == ant) || (atual == ant + dif) || (atual == ant - dif)){
                    conf ++;
                }
            }
        }
        return conf;
    }

    public void print(){
        System.out.print("[");
        for (int i = 0; i < table.length; i++){
            System.out.print(table[i]+",");
        }
    }
}
