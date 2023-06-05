/**
 * Represents a simple plate only defined from a unique ID.
 */
public class Plate {
    private int ID;

    /**
     * Instansiation of a plate with the ID as input parameter.
     * 
     * @param ID Unique value for a plate.
     */
    public Plate(int ID) {
        this.ID = ID;
    }

    /**
     * Returns the ID value of a plate.
     * 
     * @return ID value of the plate.
     */
    public int getID() {
        return ID;
    }
}