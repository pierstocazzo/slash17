package vacuumCleaner;

/**
 * This class implements a single square.
 * 
 */
public class Square{

	public enum Type {
        CLEAN, DIRTY, OBSTACLE, UNKNOWN
    }

    public Type type = Type.CLEAN;

    public Square() {

    }

    /**
     * Set the type of the square
     * 
     * @param type CLEAN, DIRTY, OBSTACLE, UNKNOWN
     */
    public Square(Type type) {
        this.type = type;
    }    

    @Override
    public String toString() {
        return type.toString();
    }
}
