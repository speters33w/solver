package kivaworld;

import solver.Point;
//import edu.duke.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * FloorMap represents Kiva's environment, including pod pickup/drop-off points, obstacles, and Kiva's starting location.
 * It allows identifying what, if anything, exists at a given location, as well as rendering the entire map to a String.
 *
 * FloorMap's coordinate system is zero-indexed (first row and first column are both 0), and the origin, (0,0), is in the upper left.
 * The Y coordinate of a Points increases as you traverse down, and X increases as you move right.
 * Note that this corresponds to the layout of the floor inside the String representation passed into constructor.
 *
 * Each location on the FloorMap is represented by a Point, with the first coordinate--X--representing the column,
 * and the second coordinate--Y--representing the row.
 * For example, the floor location on the far left column three rows from the top would be located at (X,Y) == (0, 2).
 *
 * In the text/String representation of the floor map, each character in the String represents one square on the map,
 * and the following symbols are used:
 *
 *     <ul>
 *     <li>K represents starting point of the robot, which is initially facing upward on the map
 *         (Kiva's first forward move would end up one square higher than this location if Kiva doesn't turn left or right first).</li>
 *     <li>P represents starting point of the pod. Kiva must be ON this location to pick up the pod.</li>
 *     <li>D represents where the pod should be dropped</li>
 *     <li>* - | (a vertical bar) all represent objects and barriers that the robot cannot pass through.</li>
 *     </ul>
 *
 * Note that the floor map contains blank locations (where you have SPACE characters - ' '), so spaces will be considered part of the map.
 * If you have a few spaces at the end of lines or on blank lines of their own, you may get InvalidMapLayoutException.
 * Be sure not to have trailing whitespace on your maps rows or on blank lines!
 *
 * See the sample floor map text file in sample_floor_map1.txt in your BlueJ project directory
 *
 * @see FloorMapObject
 */
public class FloorMap {
    private static final char KIVA_REPRESENTATION = 'K';
    private final char[][] map;
    private Point initialKivaLocation;
    private Point podLocation;
    private Point dropZoneLocation;

    /**
     * Construct a FloorMap from a String containing the floor layout, including Kiva's initial location.
     *
     * @param inputMap String FloorMap
     */
    public FloorMap(String inputMap) {
        this.map = this.populateMap(inputMap);
    }

    /**
     * Given a Point location in the map, returns the corresponding FloorMapObject.
     *
     * @param location The Point representing the location to look in for an object,
     *                 which must be a valid location within the FloorMap.
     * @return the FloorMapObject at the given location (including EMPTY).
     *
     * @see FloorMapObject
     * @see Point
     */
    public FloorMapObject getObjectAtLocation(Point location) {
        if (location.getX() < 0) {
            throw new InvalidFloorMapLocationException(String.format("Cannot access a negative column: %d", location.getX()));
        } else if (location.getX() > this.map[0].length - 1) {
            throw new InvalidFloorMapLocationException(String.format("Cannot access beyond max column (%d): %d", this.map[0].length - 1, location.getX()));
        } else if (location.getY() < 0) {
            throw new InvalidFloorMapLocationException(String.format("Cannot access a negative row: %d", location.getY()));
        } else if (location.getY() > this.map.length - 1) {
            throw new InvalidFloorMapLocationException
                    (String.format("Cannot access beyond max row (%d): %d",
                            this.map.length - 1, location.getY()));
        } else {
            Optional<FloorMapObject> obj = FloorMapObject.fromChar(this.map[location.getY()][location.getX()]);
            return obj.orElseThrow(() ->
                    new InvalidMapLayoutException
                            (String.format("Unrecognized MapFloorObject char representation at (%d, %d): '%c'",
                            location.getX(), location.getY(), this.map[location.getY()][location.getX()])));
        }
    }

    /**
     * Returns Kiva's initial location.
     *
     * @return Point representing Kiva's initial location.
     *
     * @see Point
     */
    public Point getInitialKivaLocation() {
        return new Point(this.initialKivaLocation.getX(), this.initialKivaLocation.getY());
    }

    /**
     * Returns pod's location.
     *
     * @return Point representing the pod's location.
     *
     * @see Point
     */
    public Point getPodLocation() {
        return new Point(this.podLocation.getX(), this.podLocation.getY());
    }

    /**
     * Returns drop zone's location.
     *
     * @return Point representing the drop zone's location.
     *
     * @see Point
     */
    public Point getDropZoneLocation() {
        return new Point(this.dropZoneLocation.getX(), this.dropZoneLocation.getY());
    }

    /**
     * Returns minimum valid column number (y) that can be accessed by a call to getObjectAtLocation.
     * Requesting a location with column number less than this will result in InvalidMapLocationException.
     *
     * @return integer minimum valid column number (y)
     *
     * @see InvalidMapLayoutException
     */
    public int getMinColNum() {
        return 0;
    }

    /**
     * Returns maximum valid column number (y) that can be accessed by a call to getObjectAtLocation.
     * Requesting a location with column number greater than this will result in InvalidMapLocationException.
     *
     * @return integer maximum valid column number (y)
     *
     * @see InvalidMapLayoutException
     */
    public int getMaxColNum() {
        return this.map[0].length - 1;
    }

    /**
     * Returns minimum valid row number (x) that can be accessed by a call to getObjectAtLocation.
     * Requesting a location with row number less than this will result in InvalidMapLocationException.
     *
     * @return integer minimum valid row number (x)
     *
     * @see InvalidMapLayoutException
     */
    public int getMinRowNum() {
        return 0;
    }

    /**
     * Returns maximum valid row number (x) that can be accessed by a call to getObjectAtLocation.
     * Requesting a location with row number greater than this will result in InvalidMapLocationException.
     *
     * @return integer maximum valid row number (x)
     *
     * @see InvalidMapLayoutException
     */
    public int getMaxRowNum() {
        return this.map.length - 1;
    }

    /**
     * Return pretty String representation of the FloorMap.
     *
     * Overrides toString in class java.lang.Object
     */
    public String toString() {
        StringBuilder mapStr = new StringBuilder();

        for(int y = 0; y < this.map.length; ++y) {
            for(int x = 0; x < this.map[y].length; ++x) {
                if (x == this.initialKivaLocation.getX() && y == this.initialKivaLocation.getY()) {
                    mapStr.append('K');
                } else {
                    mapStr.append(this.map[y][x]);
                }
            }

            if (y < this.map.length - 1) {
                mapStr.append(System.lineSeparator());
            }
        }

        return mapStr.toString();
    }

    private char[][] populateMap(String inputMap) {
        boolean foundKiva = false;
        boolean foundPod = false;
        boolean foundDropZone = false;
        List<String> rowList = this.readAndValidateMapRows(inputMap);
        char[][] newMap = new char[rowList.size()][];

        for(int rowNum = 0; rowNum < rowList.size(); ++rowNum) {
            String line = rowList.get(rowNum);
            newMap[rowNum] = new char[line.length()];

            for(int colNum = 0; colNum < newMap[rowNum].length; ++colNum) {
                char mapChar = line.charAt(colNum);
                if ('K' == mapChar) {
                    foundKiva = this.foundKivaLocation(colNum, rowNum, foundKiva);
                    mapChar = FloorMapObject.EMPTY.toChar();
                } else {
                    Optional<FloorMapObject> mapObjectOptional = FloorMapObject.fromChar(mapChar);
                    if (!mapObjectOptional.isPresent()) {
                        throw new InvalidMapLayoutException(String.format("Unrecognized MapFloorObject char representation at (%d, %d): '%c'", colNum, rowNum, mapChar));
                    }

                    FloorMapObject mapObj = mapObjectOptional.get();
                    if (FloorMapObject.POD == mapObj) {
                        foundPod = this.foundPodLocation(colNum, rowNum, foundPod);
                    } else if (FloorMapObject.DROP_ZONE == mapObj) {
                        foundDropZone = this.foundDropZoneLocation(colNum, rowNum, foundDropZone);
                    }
                }

                newMap[rowNum][colNum] = mapChar;
            }
        }

        if (!foundKiva) {
            throw new InvalidMapLayoutException("Didn't find a Kiva!");
        } else if (!foundPod) {
            throw new InvalidMapLayoutException("Didn't find a Pod!");
        } else if (!foundDropZone) {
            throw new InvalidMapLayoutException("Didn't find a Drop Zone!");
        } else {
            return newMap;
        }
    }

    private List<String> readAndValidateMapRows(String inputMap) {
        int rowLength = 0;
        int rowNum = -1;
        //todo check upcasting / downcasting rowlist in readAndValidateMapRows, List or ArrayList?
        List<String> rowList = new ArrayList();
        String[] var5 = inputMap.split("\r?\n");
        int var6 = var5.length;

        for (String line : var5) {
            ++rowNum;
            if (rowNum == 0) {
                rowLength = line.length();
            } else if (line.length() != rowLength) {
                throw new InvalidMapLayoutException(String.format("Previously encountered row(s) of length %d, but row %d is of length %d", rowLength, rowNum, line.length()));
            }

            rowList.add(line);
        }

        return rowList;
    }

    private boolean foundKivaLocation(int colNum, int rowNum, boolean foundKivaPreviously) {
        if (foundKivaPreviously) {
            throw new InvalidMapLayoutException(String.format("Found a second Kiva at (%d, %d)", colNum, rowNum));
        } else {
            this.initialKivaLocation = new Point(colNum, rowNum);
            return true;
        }
    }

    private boolean foundPodLocation(int colNum, int rowNum, boolean foundPodPreviously) {
        if (foundPodPreviously) {
            throw new InvalidMapLayoutException(String.format("Found a second POD at (%d, %d)", colNum, rowNum));
        } else {
            this.podLocation = new Point(colNum, rowNum);
            return true;
        }
    }

    private boolean foundDropZoneLocation(int colNum, int rowNum, boolean foundDropZonePreviously) {
        if (foundDropZonePreviously) {
            throw new InvalidMapLayoutException(String.format("Found a second DROP_ZONE at (%d, %d)", colNum, rowNum));
        } else {
            this.dropZoneLocation = new Point(colNum, rowNum);
            return true;
        }
    }
}
