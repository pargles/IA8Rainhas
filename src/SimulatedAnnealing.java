//Simulated Annealing Example
//www.timestocome.com
//
//This program takes the eight queens chess board problem and
//solves it using simulated annealing.
//
//Eight Queens problem:
//How do you place 8 queens on a chess board so that no queen
//can capture any other queen?
//

import java.util.*;
import java.lang.Math.*;

public class SimulatedAnnealing
{

	static int maxLength = 8;		//size of the chess board

	static int currentSolution[] = new int[maxLength];
	static int bestSolution[] = new int[maxLength];
	static int workingSolution[] = new int [maxLength];

	static int workingEnergy;
	static int bestEnergy;
	static int currentEnergy;

	static boolean setRandomNumberGenerator = false;
	static Random random;




	//annealing adjustments
	static double initialTemperature = 30;
	static double finalTemperature = 0.5;
	static double alpha = 0.99;
	static double stepsPerChange = 100;
        static int tentaram=0;
        static int conseguiram=0;

	public static void main ( String[] args )
	{
		//set up random number generator
		random = new Random( System.currentTimeMillis());

		//initialize variables
		int timer = 0;
		int step = 0;
		boolean solution = false;
		boolean useNew = false;
		int accepted = 0;

		double temperature = initialTemperature;

		//set up the initial board setting one queen to a row and do not allow any queens in the same rows as previous queens.
		for ( int i=0; i<maxLength; i++){

			int x = getLargeRandom( maxLength );

			//check that queens are in separate rows
			for ( int j=0; j<i; j++){

				if ( x == workingSolution[j] ){  //we have a row match need a new row

					while ( x == workingSolution[j] ) {	//keep trying till we don't have a match
						x = getLargeRandom( maxLength );
						j=0;                            //check new match against numbers we've already checked
					}
				}
			}

			//set initial values for each array
			workingSolution[i] = x;
			currentSolution[i] = x;
			bestSolution[i] = x;

		}
                System.out.print("vetor sorteado: ");
                for(int i=0;i<maxLength;i++)
                {
                    System.out.print(workingSolution[i]);
                }
                System.out.println();

		//initialize energy
		workingEnergy = computeEnergy ( workingSolution );
		currentEnergy = computeEnergy( currentSolution );
		bestEnergy = computeEnergy ( bestSolution );



		//main loop continue until we reach lowest temperature
		while ( temperature > finalTemperature ){


			accepted = 0;

			//randomizing step
			for ( step = 0; step < stepsPerChange; step++){

				useNew = false;
				tweakSolution ( workingSolution );   //bounce things around a bit on the board
                                System.out.print("vetor TROCADO: ");
                                for(int i=0;i<maxLength;i++)
                                {
                                    System.out.print(workingSolution[i]);
                                }
                                System.out.println();

				workingEnergy = computeEnergy ( workingSolution );

				//if our new solution is better than the one we are using use the new one
				if ( workingEnergy < currentEnergy ){

					useNew = true;

				}else{
					//if workingEnergy more than current try this test
					// P(dE) = exp ( -dE/T )
					// this accepts worse solutions at higher temperatures
					// this allows us to check more solutions at higher temperatures to help
					// avoid local mins.
					double randomTest = getSmallRandom();
					double delta = workingEnergy - currentEnergy;
					double calculation = Math.exp( -delta/temperature );
                                        tentaram++;

					if ( calculation > randomTest ){

						accepted++;
						useNew = true;
                                                conseguiram++;
					}
                                        else
                                        {
                                            System.out.print("");//tentou mas n conseguiu
                                        }
				}


				if ( useNew ){

					//copy working solution to current solution
					useNew = false;
					currentEnergy = workingEnergy;

					for ( int i=0; i < maxLength; i++){
						currentSolution[i] = workingSolution[i];
					}

					//if our new solution is better than the best solution so far - make that one the best solution
					if ( currentEnergy < bestEnergy ){

						bestEnergy = currentEnergy;

						for ( int i=0; i < maxLength; i++){
							bestSolution[i] = currentSolution[i];
						}

					}
				}else{

					workingEnergy = currentEnergy;

					for ( int i=0; i < maxLength; i++){
						workingSolution[i] = currentSolution[i];
					}

				}//if else

			}// for loop

			temperature *= alpha;

			//we have solution no point continuing to loop
			if ( bestEnergy < 1.0 ) {

				solution = true;
				break;
			}

		}// end while loop


		if ( solution ){
			dumpSolution();
		}


	}//end main



	//return a random number between 0 and 1
	static double getSmallRandom()
	{
		return random.nextDouble();
	}


	//return a number between zero and x
	static int getLargeRandom( int maximum )
	{
		return random.nextInt ( maximum );
	}



	//randomly perturn the solution
	static void tweakSolution ( int array[] )
	{
		int tempValue, x, y;

		//pick an x and a y randomly to change
		x = getLargeRandom( maxLength );
		y = getLargeRandom( maxLength );

		//no duplicates
		while ( x == y ) {
			y = getLargeRandom( maxLength );
		}

		//switch x and y
		tempValue = array[x];
		array[x] = array[y];
		array[y] = tempValue;

	}


	//compute energy  ie how many queens are on diagonals with other queens?
	static int computeEnergy ( int array[] )
	{
		int row, column, i, j;
		int board[][] = new int[maxLength][maxLength];
		int conflicts = 0;


		//lets see what we have for conflicts
		//we aleady have set things up so neither horizontal or vertical
		//conflicts can happen.  So we just need to check the diagonals

		//move through each column left to right
		// no one to right of last piece, nothing to check there so go to maxLength -1 only
		for ( i=0;  i<(maxLength-1); i++){

			int x = 0;
			for ( j=(i+1); j<maxLength; j++){
				x++;
				if ( (array[i] - x) == array[j] ){ conflicts++;  }
				if ( (array[i] + x) == array[j] ){ conflicts++;  }

			}

		}//move through each column

		return conflicts;
	}




	//randomly initialize a solution
	static void initializeSolution ( int array[] )
	{
		int i;

		for ( i=0; i<maxLength; i++ ){
			array[i] = i;
		}


		for ( i=0; i<maxLength; i++ ){
			tweakSolution( array );
		}


	}

	//dump solution out to user
	static void dumpSolution ( )
	{
		char board[][] = new char[maxLength][maxLength];
		int x, y;

		//clear out the board
		for ( x=0; x<maxLength; x++ ){
			for ( y=0; y<maxLength; y++ ){
				board[x][y] = ' ';
			}
		}


		//put solution on the board
		for ( x=0; x<maxLength; x++){
			board[x][bestSolution[x]] = 'Q';
		}


		//print the board out to the user
		System.out.println ( "\n Energy: " + bestEnergy );

		System.out.println ( "\n\n" );
		for ( y=0; y<maxLength; y++ ){
			for ( x=0; x<maxLength; x++ ){
				if ( board[x][y] == 'Q') { System.out.print( "Q" ); }
				else { System.out.print (". "); }
			}
			System.out.println();
		}
		System.out.println ( "\n" );
                System.out.println("tentaram: "+tentaram+ "conseguiram: "+conseguiram);


	}


}//end public class SimualatedAnnealing