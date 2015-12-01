package fpt.com.component.view;

/**
 * ID-Generator
 *
 * @author Tobias
 */
public class IDGenerator {

    /**
     * Maximum ID
     */
    private static long IDmax = 999999;

    private static IDGenerator instance = null;

    /**
     * Current id
     */
    private long id = 1;

    /**
     * Constructor
     * <p/>
     * This method is protected to prevent multiple
     * instances of class
     */
    private IDGenerator() {
        // No implementation needed
    }

    /**
     * Get instance of class
     *
     * @return
     */
    public static IDGenerator getInstance() {
        // Check for already existing instance
        if (instance == null) {
            instance = new IDGenerator();
        }

        return instance;
    }

    /**
     * Generate an id
     *
     * @return
     * @throws IDOverflow
     */
    public long getId() throws IDOverflow {
        if (this.id == IDmax) {
            throw new IDOverflow();
        }
        return id++;
    }
    
    /**
     * 
     */
    public void setId(long id) {
    	this.id = id;
    }

    /**
     * IDOverflow-Exception
     */
    public class IDOverflow
            extends Exception {

        public void idHigh() {
            System.err.print("ID ist zu hoch.");
        }
    }

}
