package env;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import planning.Action;
import planning.Plan;
import planning.PlanGenerator;

public class Env {

	boolean mealReady = false;
	boolean suitUp = false;
	boolean tableReady = false;
	boolean flowersTaken = false;
	boolean waitRenata = false;
	boolean mealTaken = false;

	boolean finished = false;
	boolean ciccioFinish = false;
	
	public final static char TILE = ' ';
	public final static char MEAL = 'M';
	public final static char SUIT = 'S';
	public final static char TABLE = 'T';
	public final static char FLOWERS = 'F';
	public final static char DOOR = 'D';
	public final static char GRASS = 'G';
	public static final char RENATA = 'R';

	public char matrix[][];

	Ciccio ciccio;

	int stepNo = 0;

	/** dopo quanti turni spostare le cose */
	int k = 0;

	/** turni utili prima che renata arrivi */
	int maxTime = 20;

	/** velocita' simulazione */
	int speed = 1;

	/** tipo di ambiente (A,B,C,D) */
	char type = 'A';

	public int posForRoom;

	public int rooms;

	public int doorsPosition[];

	Random rg = new Random();

	PlanGenerator pg;

	Plan currentPlan;

	boolean occupate[];
	
	int perfMeasure;

	public Env(int rooms, int posForRooms) {
		this.rooms = rooms;
		this.posForRoom = posForRooms;
		matrix = new char[rooms][posForRoom];
		doorsPosition = new int[rooms - 1];
		maxTime = 30;
		stepNo = 0;
		perfMeasure = maxTime;
		
		occupate = new boolean[rooms];
		for (int i = 0; i < occupate.length; i++) {
			occupate[i] = false;
		}

		envGeneration();

		int r = Math.abs(rg.nextInt()) % (rooms - 1);
		int p = Math.abs(rg.nextInt()) % posForRoom;
		while (matrix[r][p] != TILE) {
			r = Math.abs(rg.nextInt()) % (rooms - 1);
			p = Math.abs(rg.nextInt()) % posForRoom;
		}
		ciccio = new Ciccio(r, p);
	}

	private void envGeneration() {
		// Inizializzazione
		for (int i = 0; i < rooms - 1; i++)
			for (int j = 0; j < posForRoom; j++)
				matrix[i][j] = TILE;
		// erba
		for (int j = 0; j < posForRoom; j++)
			matrix[rooms - 1][j] = GRASS;

		// generazione posizione suit univoca
		int r = Math.abs(rg.nextInt()) % (rooms - 1);
		int p = Math.abs(rg.nextInt()) % posForRoom;
		while (matrix[r][p] != TILE || occupate[r]) {
			r = Math.abs(rg.nextInt()) % (rooms - 1);
			p = Math.abs(rg.nextInt()) % posForRoom;
		}
		matrix[r][p] = SUIT;
		occupate[r] = true;

		// generazione posizione cena univoca
		r = Math.abs(rg.nextInt()) % (rooms - 1);
		p = Math.abs(rg.nextInt()) % posForRoom;
		while (matrix[r][p] != TILE || occupate[r]) {
			r = Math.abs(rg.nextInt()) % (rooms - 1);
			p = Math.abs(rg.nextInt()) % posForRoom;
		}
		matrix[r][p] = MEAL;
		occupate[r] = true;

		// generazione posizione fiori univoca
		r = Math.abs(rg.nextInt()) % (rooms - 1);
		p = Math.abs(rg.nextInt()) % posForRoom;
		while (matrix[r][p] != TILE || occupate[r]) {
			r = Math.abs(rg.nextInt()) % (rooms - 1);
			p = Math.abs(rg.nextInt()) % posForRoom;
		}
		matrix[r][p] = FLOWERS;
		occupate[r] = true;

		// generazione posizione tavolo univoca
		r = Math.abs(rg.nextInt()) % (rooms - 1);
		p = Math.abs(rg.nextInt()) % posForRoom;
		while (matrix[r][p] != TILE || occupate[r]) {
			r = Math.abs(rg.nextInt()) % (rooms - 1);
			p = Math.abs(rg.nextInt()) % posForRoom;
		}
		matrix[r][p] = TABLE;
		occupate[r] = true;

		// generazione porte
		for (int i = 0; i < rooms - 1; i++) {
			doorsPosition[i] = Math.abs(rg.nextInt()) % posForRoom;
			// System.out.println("Pos doors " + i + " = " + doorsPosition[i]);
		}
	}

	public char get(int i, int j) {
		return matrix[i][j];
	}

	public boolean isMealReady() {
		return mealReady;
	}

	public void setMealReady(boolean mealReady) {
		this.mealReady = mealReady;
	}

	public boolean isSuitUp() {
		return suitUp;
	}

	public void setSuitUp(boolean suitUp) {
		this.suitUp = suitUp;
	}

	public boolean isMealTaken() {
		return mealTaken;
	}

	public void setMealTaken(boolean mealTaken) {
		this.mealTaken = mealTaken;
	}

	public boolean isTableReady() {
		return tableReady;
	}

	public void setTableReady(boolean tableReady) {
		this.tableReady = tableReady;
	}

	public boolean isFlowerTaken() {
		return flowersTaken;
	}

	public void setFlowerTaken(boolean flowerTaken) {
		this.flowersTaken = flowerTaken;
	}

	public boolean isWaitRenata() {
		return waitRenata;
	}

	public void setWaitRenata(boolean waitRenata) {
		this.waitRenata = waitRenata;
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

	private void execute(Action a) {
		typeEnvUpdate();
		switch (a) {
		case MOVE_RIGHT:
			ciccio.setJ(ciccio.j + 1);
			break;
		case MOVE_LEFT:
			ciccio.setJ(ciccio.j - 1);
			break;
		case MOVE_DOWN:
			ciccio.setI(ciccio.i + 1);
			break;
		case MOVE_UP:
			ciccio.setI(ciccio.i - 1);
			break;
		case PREPARE_MEAL:
			setMealReady(true);
			break;
		case SUIT_UP:
			setSuitUp(true);
			remove(SUIT);
			break;
		case TAKE_MEAL:
			setMealTaken(true);
			remove(MEAL);
			break;
		case PREPARE_TABLE:
			setTableReady(true);
			break;
		case TAKE_FLOWERS:
			setFlowerTaken(true);
			remove(FLOWERS);
			break;
		case WAIT_RENATA:
			setWaitRenata(true);
			break;

		default:
			break;
		}
	}

	private void typeEnvUpdate() {
		stepNo++;
		switch (type) {
		case 'B':
			updateB();
			break;
		case 'C':
			updateC();
			break;
		case 'D':
			updateD();
			break;
		}
	}

	public void update() {
		if (stepNo == maxTime) {
			matrix[4][doorsPosition[3]] = RENATA;
		}
		if (ciccioFinish) {
			stepNo++;
			return;
		}
		// effettuiamo la prossima azione del piano, se non è vuoto
		// e se il nostro obiettivo non ha cambiato posizione
		if (currentPlan != null && !currentPlan.isEmpty()) {
			if (!myPlanIsStillValid()) {
				currentPlan.reset();
				currentPlan = null;
				System.out.println("Old Plan isn't still valid");
				return;
			}
			Action a = currentPlan.getActions().pop();
			System.out.println(a);
			execute(a);

		} else { // altrimenti creiamolo
			System.out.println("Creating new Plan");
			pg = new PlanGenerator(this);
			System.out.println("Create plan generator");
			String out = pg.executePlan();
			System.out.println("Executed dlv");
			if (out == null)
				ciccioFinish = true;
			if (out != null && !out.isEmpty()) {
				System.out.println(out);
				currentPlan = new Plan(out);
			}
		}
	}

	private void updateB() {
		if (stepNo % k == 0) {
			char obj = chooseRandomObject();
			if (obj == 'E')
				return;
			System.out.println("Sposto " + obj);
			// Sposto nella stessa stanza
			int room = roomOfObject(obj);
			int posInRoom = posOfObject(obj);
			System.out.println("Obj " + room + "," + posInRoom);
			int newPos = genRandomPosition(room);
			System.out.println("OldPos " + posInRoom);
			System.out.println("NewPos " + newPos);
			matrix[room][posInRoom] = Env.TILE;
			matrix[room][newPos] = obj;
		}
	}

	private void updateC() {
		if (stepNo % k == 0) {
			for (char obj : new char[] { 'F', 'S' }) {
				if (rg.nextBoolean()) {
					if (obj == 'F' && isFlowerTaken() || obj == 'S'
							&& isSuitUp())
						continue;
					System.out.println("Sposto " + obj);
					// Sposto nella stessa stanza
					int room = roomOfObject(obj);
					int posInRoom = posOfObject(obj);
					System.out.println("Obj " + room + "," + posInRoom);
					int newPos = genRandomPosition(room);
					System.out.println("OldPos " + posInRoom);
					System.out.println("NewPos " + newPos);
					matrix[room][posInRoom] = Env.TILE;
					matrix[room][newPos] = obj;
				}
			}
		}
	}

	private void updateD() {
		if (stepNo % k == 0) {
			for (char obj : new char[] { 'F', 'S' }) {
				if (rg.nextBoolean()) {
					if ((obj == 'F' && flowersTaken) || (obj == 'S' && suitUp))
						continue;
					System.out.println("Sposto " + obj);
					int oldRoom = roomOfObject(obj);
					int oldPos = posOfObject(obj);

					int randomRoom = rg.nextInt(rooms - 1);
					int randomPos = genRandomPosition(randomRoom);

					System.out
							.println("Obj old pos: " + oldRoom + "," + oldPos);
					System.out.println("Obj new pos: " + randomRoom + ","
							+ randomPos);
					matrix[oldRoom][oldPos] = Env.TILE;
					matrix[randomRoom][randomPos] = obj;
				}
			}
		}
	}

	private int genRandomPosition(int room) {
		// generazione posizione fiori univoca
		int p = Math.abs(rg.nextInt()) % posForRoom;
		while (matrix[room][p] != TILE)
			p = Math.abs(rg.nextInt()) % posForRoom;
		return p;
	}

	public int posOfObject(char obj) {
		for (int i = 0; i < rooms - 1; i++)
			for (int j = 0; j < posForRoom; j++)
				if (matrix[i][j] == obj)
					return j;
		System.err.println("Oggetto senza posizione " + obj);
		return -1;
	}

	public int roomOfObject(char obj) {
		for (int i = 0; i < rooms - 1; i++)
			for (int j = 0; j < posForRoom; j++)
				if (matrix[i][j] == obj)
					return i;
		System.err.println("Oggetto senza stanza " + obj);
		return -1;
	}

	private char chooseRandomObject() {
		Random r = new Random();
		if (r.nextBoolean()) {
			if (suitUp)
				if (flowersTaken)
					return 'E';
				else
					return 'F';
			else
				return 'S';
		} else {
			if (flowersTaken)
				if (suitUp)
					return 'E';
				else
					return 'S';
			else
				return 'F';
		}
	}

	private void remove(char o) {
		for (int i = 0; i < rooms; i++) {
			for (int j = 0; j < posForRoom; j++) {
				if (matrix[i][j] == o)
					matrix[i][j] = TILE;
			}
		}
	}

	private boolean myPlanIsStillValid() {
		// Check meal plan
		if (!mealReady) {
			System.out.println("Check if mealReady plan is valid");
			if (roomOfObject('M') == pg.mealRoom
					&& posOfObject('M') == pg.mealPos)
				return true;
			return false;
		}
		// Check suitUp plan
		else if (!suitUp) {
			System.out.println("Check if suitUp plan is valid");
			if (roomOfObject('S') == pg.smokingRoom
					&& posOfObject('S') == pg.smokingPos)
				return true;
			return false;
		} else if (!mealTaken) {
			System.out.println("Check if mealTaken plan is valid");
			if (roomOfObject('M') == pg.mealRoom
					&& posOfObject('M') == pg.mealPos)
				return true;
			return false;
		} else if (!tableReady) {
			System.out.println("Check if tableReady plan is valid");
			if (roomOfObject('T') == pg.tableRoom
					&& posOfObject('T') == pg.tablePos)
				return true;
			return false;
		} else if (!flowersTaken) {
			if (roomOfObject('F') == pg.flowersRoom
					&& posOfObject('F') == pg.flowersPos)
				return true;
			return false;
		} else
			return true;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean b) {
		this.finished = b;
	}

	public int getCurrentT() {
		return stepNo;
	}

	public int getPM() {
		if (ciccioFinish) 
			return perfMeasure;
		
		if (maxTime > stepNo) {
			perfMeasure = maxTime - stepNo;
		} else {
			if (maxTime + 1 == stepNo) {
				perfMeasure -= isMealReady() ? 0 : 5;
				perfMeasure -= isSuitUp() ? 0 : 5;
				perfMeasure -= isMealTaken() ? 0 : 5;
				perfMeasure -= isTableReady() ? 0 : 5;
				perfMeasure -= isFlowerTaken() ? 0 : 5;
				perfMeasure -= isWaitRenata() ? 0 : 5;
			}
		}
		return perfMeasure;
	}

	public boolean isCiccioFinish() {
		return ciccioFinish;
	}

	public void setCiccioFinish(boolean ciccioFinish) {
		this.ciccioFinish = ciccioFinish;
	}
}
