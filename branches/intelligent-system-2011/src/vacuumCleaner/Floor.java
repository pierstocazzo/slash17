package vacuumCleaner;

import java.io.Serializable;
import java.util.Collections;

import java.util.LinkedList;
import java.util.Random;
/**
 *  This class implement a Floor built of a Square matrix where the agent acts.
 * 
 * @see Square	 
 *
 */
public class Floor implements Serializable {

	private static final long	serialVersionUID	= -2524877175083650036L;

	public int length;

	public int width;

	public int initialDirt;

	private Square [][] floor;
	/**
	 * 
	 * @param length		length of the floor
	 * @param width			width of the floor
	 * @param squareType	the square type (Clean, Dirty, Obstacle, Unknown)
	 */
	public Floor(int length, int width, Square.Type squareType){

		this.length = length;
		this.width = width;
		this.floor = new Square[length][width];
		for (int i = 0; i < length; i++)
			for (int j = 0; j < width; j++)
				this.floor[i][j] = new Square(squareType);
	}

	/**
	 * Generates randomly a floor scenario
	 * 
	 * @param numDirtySquares	quantity of dirty squares
	 * @param numObstacles		quantity of obstacles
	 */
	public void generateObject(int numDirtySquares, int numObstacles){

		class Cell {
			int x, y;
			public Cell(int x, int y){
				this.x = x;
				this.y = y;
			}
		}

		LinkedList<Cell> cells = new LinkedList<Cell>();

		for (int i = 0; i < length; i++)
			for (int j = 0; j < width; j++)
				if(floor[i][j].type == Square.Type.CLEAN)
					cells.add(new Cell(i, j));

		Collections.shuffle(cells);
		Random randomGen = new Random();
		int size = cells.size();

		if (numDirtySquares + numObstacles > size){
			numDirtySquares = ( size * numDirtySquares ) / (numDirtySquares + numObstacles);
			numObstacles = size - numDirtySquares;
		}

		for (int i = 0; i < numDirtySquares; i++) {
			int random = Math.abs(randomGen.nextInt()) % cells.size();
			Cell myCell = cells.remove(random);
			floor[myCell.x][myCell.y].type = Square.Type.DIRTY;
		}

		for (int i = 0; i < numObstacles; i++) {
			int random = Math.abs(randomGen.nextInt()) % cells.size();
			Cell myCell = cells.remove(random);
			floor[myCell.x][myCell.y].type = Square.Type.OBSTACLE;
		}
	}
	/**
	 * 
	 * @return the current number of clean squares.
	 */
	public int squaresNowCleaned(){
		int cleanedSquare = 0;
		for (int i = 0; i < length; i++)
			for (int j = 0; j < width; j++)
				if(get(i,j) == Square.Type.CLEAN)
					cleanedSquare++;
		return cleanedSquare;
	}

	public void clear(){
		for (int i = 0; i < length; i++)
			for (int j = 0; j < width; j++)
				floor[i][j].type = Square.Type.CLEAN;
	}

	public void load(Floor floor){
		this.floor = floor.floor;
		this.width = floor.width;
		this.length = floor.length;
	}

	public int dirtySquares(){
		int dirtySquare = 0;
		for (int i = 0; i < length; i++)
			for (int j = 0; j < width; j++)
				if(get(i,j) == Square.Type.DIRTY)
					dirtySquare++;
		return dirtySquare;
	}

	/**
	 * Confirm if there is an obstacle in the position
	 * 
	 * @param i the row position of the square
	 * @param j the column position of the square
	 * 
	 * @return TRUE if there is an obstacle.
	 */
	public boolean obstacle(int i, int j) {
		return get(i,j) == Square.Type.OBSTACLE;
	}

	/**
	 * 
	 * @param i the row position of the square
	 * @param j the column position of the square
	 * @return the type of the square
	 */
	public Square.Type get(int i, int j){
		if(i<0 || j<0 || i>=length || j>=width)
			return Square.Type.UNKNOWN;
		return this.floor[i][j].type;
	}

	/**
	 * Set the state of a square in the position (i,j).
	 * 
	 * @param i		the square's row
	 * @param j 	the square's column
	 * @param st	the square's state
	 */
	public void set(int i, int j, Square.Type st){
		if(i<0 || j<0 || i>=length || j>=width)
			return;
		this.floor[i][j].type = st;
	}

	public int getSparsity() {
		if(dirtySquares() == 0)
			return 0;
		int minI = length-1, minJ = width-1, maxI = 0, maxJ = 0;
		for (int i = 0; i < length; i++)
			for (int j = 0; j < width; j++)
				if(get(i,j) == Square.Type.DIRTY){
					if(i<minI)
						minI = i;
					if(j<minJ)
						minJ = j;
					if(i>maxI)
						maxI = i;
					if(j>maxJ)
						maxJ = j;
				}
		return (maxI-minI)*(maxJ-minJ);
	}
}
