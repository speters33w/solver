package kivaworld;

/**
 * Thrown when an invalid map is given (e.g. missing Kiva, Pod or DropZone; contains invalid characters).
 */
public class InvalidMapLayoutException extends RuntimeException {

    /**
     * Thrown when encountering a floor map that has too many or too few of particular FloorMapObjects.
     *
     * @param message String message to be displayed on console.
     */
    public InvalidMapLayoutException(String message) {
        super(message);
    }
}

