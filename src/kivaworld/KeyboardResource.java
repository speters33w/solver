package kivaworld;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * A simple text scanner that takes keyboard input from a user.
 *
 * @version 20220704.1100
 */
//20220704 includes yes() and close().
public class KeyboardResource {
    private final Scanner scanner;

    /**
     * Scans the "standard" input stream.
     * This stream is already open and ready to supply input data.
     * Typically, this stream corresponds to keyboard input.
     */
    public KeyboardResource() {
        this.scanner = new Scanner(System.in, StandardCharsets.UTF_8.name());
    }

    /**
     * This method returns the current input line, excluding any line separator at the end.
     *
     * @return The line of input scanned.
     */
    public String getLine() {
        return this.scanner.nextLine();
    }

    /**
     * Returns true if input string begins with a Y or y.
     * Y, y, yes, yepper, yup, yak and yo-yo will all return true.
     *
     * @return true if input begins with a Y or y
     */
    public boolean yes(String line){
        return line.toUpperCase().charAt(0) == 'Y';
    }

    /**
     * Closes the Scanner object
     */
    public void close(){
        this.scanner.close();
    }
}
