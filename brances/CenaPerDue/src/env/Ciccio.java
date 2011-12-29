package env;

public class Ciccio {

	/** posizione */
	int i, j;
	
	boolean withFlowers = false;
	boolean withSmoking = false;
	boolean withMeal = false;
	
	public Ciccio(int i, int j) {
		this.i = i;
		this.j = j;
	}

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	public int getJ() {
		return j;
	}

	public void setJ(int j) {
		this.j = j;
	}

	public boolean isWithFlowers() {
		return withFlowers;
	}

	public void setWithFlowers(boolean withFlowers) {
		this.withFlowers = withFlowers;
	}

	public boolean isWithSmoking() {
		return withSmoking;
	}

	public void setWithSmoking(boolean withSmoking) {
		this.withSmoking = withSmoking;
	}

	public boolean isWithMeal() {
		return withMeal;
	}

	public void setWithMeal(boolean withMeal) {
		this.withMeal = withMeal;
	}
}
