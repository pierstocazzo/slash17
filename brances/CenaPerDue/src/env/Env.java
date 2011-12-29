package env;

public class Env {

	boolean meal_ready = false;
	boolean suit_up = false;
	boolean table_ready = false;
	boolean flower_taken = false;
	boolean entrance = false;
	
	public final static char TILE = ' ';
	public final static char MEAL = 'M';
	public final static char SUIT = 'S';
	public final static char TABLE = 'T';
	public final static char FLOWERS = 'F';
	public final static char DOOR = 'D';
	public final static char GRASS = 'G';
	
	char matrix[][];
	
	Ciccio ciccio;
	
	/** dopo quanti turni spostare le cose */
	int k = 0;

	/** turni utili prima che renata arrivi */
	int maxTime = 20;
	
	/** velocità simulazione */
	int speed = 1;
	
	/** tipo di ambiente (A,B,C,D) */
	char type = 'A';
	
	public Env() {
		matrix = new char[5][8];
		envGeneration();
		ciccio = new Ciccio(0, 0);
	}

	private void envGeneration() {
		//TODO
		for (int i=0; i<4; i++) {
			for (int j=0; j<8; j++) {
				matrix[i][j] = TILE;
			}
		}
		for (int j=0; j<8; j++) {
			matrix[4][j] = GRASS;
		}
		matrix[0][3] = SUIT;
		matrix[1][0] = MEAL;
		matrix[2][4] = FLOWERS;
		matrix[3][2] = TABLE;
	}
	
	public char get(int i, int j) {
		return matrix[i][j];
	}

	public boolean isMeal_ready() {
		return meal_ready;
	}

	public void setMeal_ready(boolean meal_ready) {
		this.meal_ready = meal_ready;
	}

	public boolean isSuit_up() {
		return suit_up;
	}

	public void setSuit_up(boolean suit_up) {
		this.suit_up = suit_up;
	}

	public boolean isTable_ready() {
		return table_ready;
	}

	public void setTable_ready(boolean table_ready) {
		this.table_ready = table_ready;
	}

	public boolean isFlower_taken() {
		return flower_taken;
	}

	public void setFlower_taken(boolean flower_taken) {
		this.flower_taken = flower_taken;
	}

	public boolean isEntrance() {
		return entrance;
	}

	public void setEntrance(boolean entrance) {
		this.entrance = entrance;
	}

	public int player_i() {
		return ciccio.getI();
	}

	public int player_j() {
		return ciccio.getJ();
	}

	public int getK() {
		return k;
	}

	public void setK(int k) {
		this.k = k;
	}

	public int getMaxTime() {
		return maxTime;
	}

	public void setMaxTime(int maxTime) {
		this.maxTime = maxTime;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public char getType() {
		return type;
	}

	public void setType(char type) {
		this.type = type;
	}
}
