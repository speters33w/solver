package kivaworld;

/**
 * Thrown when a request is made to get an object located off the current FloorMap.
 */
public class InvalidFloorMapLocationException extends RuntimeException {
    private static final long serialVersionUID = 3490513234138208627L;

    /**\
     * Thrown when attempting to access a location outside of the floor.
     *
     * @param message What went wrong and in which location.
     */
    public InvalidFloorMapLocationException(String message) {
        super(message);
    }
}

