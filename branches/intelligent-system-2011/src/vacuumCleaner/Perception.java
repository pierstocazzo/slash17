package vacuumCleaner;

/**
 * A class for things an agent can perceives. 
 * @see Floor
 *
 */

public class Perception {
	
	Floor floor;

	/**
	 * 
	 * @param perceivedFloor an scenario of clean and dirty tiles and/or obstacles
	 */
	public Perception(Floor perceivedFloor) {
		this.floor = perceivedFloor;
	}
}
