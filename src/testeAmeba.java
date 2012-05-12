/**
 *
 * @author riyad
 */

import java.util.Random;

class Queen
{
	int indexOfX, indexOfY;

	public Queen()
	{

	}

	public Queen(int indexOfX, int indexOfY)
	{
		this.indexOfX = indexOfX;
		this.indexOfY = indexOfY;
	}

	public void setIndexOfX(int indexOfX)
	{
		this.indexOfX = indexOfX;
	}

	public void setIndexOfY(int indexOfY)
	{
		this.indexOfY = indexOfY;
	}

	public int getIndexOfX()
	{
		return indexOfX;
	}

	public int getIndexOfY()
	{
		return indexOfY;
	}
}

class State
{
	int boardSize;
	int cost;
	Queen q[];
	Random randomGenerator = new Random();

	public State(int boardSize)
	{
        int i;
		this.boardSize = boardSize;
		q = new Queen[boardSize];

		for(i=0; i<boardSize; i++)
		{
			q[i] = new Queen(i,  randomGenerator.nextInt(boardSize));
		}

		cost = 0;
	}

	public State(int boardSize, Queen q[])
	{
		this.boardSize = boardSize;
		this.q = q;
		cost = 0;
	}

	public State getNextState()
	{
        int i;
		Queen nextStateQueen[] = new Queen[boardSize];

		int rand = randomGenerator.nextInt(boardSize);

		for(i=0; i<boardSize; i++)
		{
			nextStateQueen[i] = new Queen( q[i].getIndexOfX(), q[i].getIndexOfY());

			if(rand == i)
			{
				int temp = randomGenerator.nextInt(boardSize) ;

				while(temp == q[i].getIndexOfY())
				{
					temp = randomGenerator.nextInt(boardSize);
				}

				nextStateQueen[i] = new Queen(q[i].getIndexOfX(), temp);
			}
		}


		return new State(boardSize, nextStateQueen);
	}

	public void calculateCost()
	{
        int i, j;
		cost = 0;

		for(i=0; i < boardSize; i++)
		{
			for(j=0; j < boardSize; j++)
			{
				if(
					q[i].getIndexOfX() == q[j].getIndexOfX() || q[i].getIndexOfY() == q[j].getIndexOfY() ||
					(q[i].getIndexOfX() - q[j].getIndexOfX() == q[i].getIndexOfY() - q[j].getIndexOfY()) ||
					(q[i].getIndexOfX() - q[j].getIndexOfX() == q[j].getIndexOfY() - q[i].getIndexOfY())
				)
				{
					cost++;
				}
			}
		}

		cost = cost / 2;

        //System.out.println(cost);
	}

	public int getCost()
	{
        calculateCost();
		return cost;
	}

    public Queen[] getQueens()
    {
        return q;
    }
}


class NQueen
{
	private int boardSize;
	private State currentState, nextState;

	public NQueen(int boardSize)
	{
		this.boardSize = boardSize;

		currentState = new State(boardSize);
	}

	public void solve()
	{
		double temperature;
		double delta;
		double probability;
		double rand;

        for( temperature = 10000; temperature > 0 && (currentState.getCost()!= 0) ; temperature--)
        {
			nextState = currentState.getNextState();
			delta = currentState.getCost() - nextState.getCost();
			probability = Math.exp(delta / temperature);
			rand = Math.random();

			if(delta > 0)
			{
				currentState = nextState;
			}
			else if(rand <= probability)
			{
				currentState = nextState;
			}
		}
	}

    public void show()
    {
        int temp = 0;
        Queen q[] = currentState.getQueens();
        boolean queen = false;
        System.out.println();

		for (int i = 0; i < boardSize; i++)
		{
			for (int j = 0; j < boardSize; j++)
			{
				for (int k = 0; k < boardSize; k++)
				{
					if (i == q[k].getIndexOfX() && j == q[k].getIndexOfY())
					{
						queen = true;
                        temp = k;
						break;

					}
				}

				if (queen)
				{
					System.out.print(""+temp);
					queen = false;
				}

				else
				{
					System.out.print("*");
				}
			}

			System.out.println();
        }
    }
}
/**
 *
 * @author riyad
 */
public class testeAmeba
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        NQueen nq = new NQueen(8);

        nq.solve();
        nq.show();
        // TODO code application logic here
    }

}