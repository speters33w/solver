package kivaworld;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Objects that can exist in Kiva's world: empty squares, obstacles, pods, dropzones.
 *
 * The valueOf(String) method provided by all enumerations won't work because it uses the enumeration value's name, not its map symbol.
 * Use fromChar(char) to convert a char to a FloorMapObject.
 *
 * @version 20220704.1100
 */
//20220704 added no-break space ( ) and # as valid char representations.
public enum FloorMapObject {
    /**
     * An empty square that Kiva is welcome to tread upon.
     */
    EMPTY("  "),
    /**
     * An obstacle that Kiva cannot walk into/through.
     */
    OBSTACLE("*|-#"),
    /**
     * A pod with inventory that Kiva can pick up. Kiva must be ON this location to take the pod.
     */
    POD("P"),
    /**
     * Location where a pod can be dropped off. Kiva must be ON this location to drop the pod.
     */
    DROP_ZONE("D");

    private static final Map<Character, FloorMapObject> charToEnum = registerCharMappings();
    private final String validRepresentations;

    /**
     * Returns the enum constant of this type with the specified name.
     *
     * @param validCharRepresentations String Valid character representations.
     */
    FloorMapObject(String validCharRepresentations) {
        this.validRepresentations = validCharRepresentations;
    }

    /**
     * Look up a FloorMapObject by char representation.
     *
     * @param symbol valid char representation of the object
     * @return Linked list of FloorMapObjects
     */
    public static Optional<FloorMapObject> fromChar(char symbol) {
        return Optional.ofNullable(charToEnum.get(symbol));
    }

    private static Map<Character, FloorMapObject> registerCharMappings() {
        //todo check upcasting / downcasting mapping in FloorMapObject.registerCharMappings, Map or HashMap?
        Map<Character, FloorMapObject> mapping = new HashMap();
        FloorMapObject[] var1 = values();
        int var2 = var1.length;

        for (FloorMapObject val : var1) {
            for (int i = 0; i < val.validRepresentations.length(); ++i) {
                mapping.put(val.validRepresentations.charAt(i), val);
            }
        }

        return mapping;
    }

    /**
     * Returns a valid char representation for the object.
     * For a FloorMapObject that is representable by more than one possible char,
     *     the first char in the string is returned.
     *
     * @return valid char representation of the object
     */
    public char toChar() {
        return this.validRepresentations.charAt(0);
    }
}

