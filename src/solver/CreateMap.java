package solver;

import kivaworld.FloorMap;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

/**
 * Creates a Kiva FloorMap
 * This can be a random map, or a default map as a string or FloorMap
 *
 * @author StephanPeters (speters33w)
 * @version 20220704.1100
 */
public class CreateMap {
    private final facingDirection[] directions = facingDirection.values();
    Random random = new Random();
    private final LinkedList<Point> obstacles = new LinkedList<>();

    enum facingDirection {
        UP(new Point(0, -1)),
        RIGHT(new Point(1, 0)),
        DOWN(new Point(0, 1)),
        LEFT(new Point(-1, 0));

        final Point delta;

        facingDirection(Point delta) {
            this.delta = delta;
        }
    }

    /**
     * public static default floor map, returns as a String
     * Can be accessed to print the default map, or whatever.
     *
     * @return default map as a string
     */
    public static String defaultMapString() {
        return ""
                + "-------------\n"
                + "        P   *\n"
                + "   **       *\n"
                + "   **       *\n"
                + "  K       D *\n"
                + " * * * * * **\n"
                + "-------------\n";
    }

    /**
     * Create a random map (String) that can be used with FloorMap
     *
     * @param mapWidth  Width (x, col) of the map.
     * @param mapHeight Height (y, row) of the map.
     * @return The generated map in String format.
     */
    public String randomMapString(int mapWidth, int mapHeight) {
        Point pod = new Point();
        Point kiva = new Point();
        Point drop = new Point();

        //Create PKD (Philip K. Dick) Points and ensure none are the same.
        do {
            pod.move(random.nextInt(mapWidth - 2) + 1, random.nextInt(mapHeight - 3) + 1);
            kiva.move(random.nextInt(mapWidth - 2) + 1, random.nextInt(mapHeight - 3) + 1);
            drop.move(random.nextInt(mapWidth - 2) + 1, random.nextInt(mapHeight - 3) + 1);
        } while (pod.equals(kiva) || drop.equals(pod) || kiva.equals(drop));

           // Create obstacles over a random % from 15 to 20% of usable map area
            for (int obstaclesLeft = ((mapWidth - 2) * (mapHeight - 2) * (random.nextInt(10) + 15)) / 100; obstaclesLeft > 0; ) {

                // Randomly decide in which direction to build an obstacle wall.
                facingDirection direction = directions[random.nextInt(directions.length)];
                int obstacleLength = 1;

                // Randomly decide how long the wall will be.
                if (direction == facingDirection.UP || direction == facingDirection.DOWN) {
                    obstacleLength = random.nextInt(mapHeight - 2) + 1;
                }
                if (direction == facingDirection.LEFT || direction == facingDirection.RIGHT) {
                    obstacleLength = random.nextInt(mapWidth - 2) + 1;
                }

                // Create anchor Point for wall and add it to obstacles
                Point obstacle = new Point(random.nextInt(mapHeight - 2) + 1, random.nextInt(mapWidth - 2) + 1);
                obstacles.add(obstacle);
                obstaclesLeft--;

                // Create an obstacle wall from the anchor point
                if (obstacleLength > 1) {
                    for (int i = 1; i < obstacleLength; i++) {
                        // Create the next Point in the wall in the current facing direction
                        obstacle = obstacle.moveBy(direction.delta);
                        // Add the new Point to the wall if it is within the map walls
                        if ((obstacle.getX() >= 0) && (obstacle.getX() < mapWidth)
                                && (obstacle.getY() > 0) && (obstacle.getY() < mapHeight)) {
                            obstacles.add(obstacle);
                            obstaclesLeft--;
                        }
                    }
                }
            }

        // Create the basic map frame
        System.out.println("Width = " + mapWidth + " Height = " + mapHeight);

        StringBuilder mapFloor = new StringBuilder();
        for (int row = 0; row < mapHeight; row++) {
            for (int col = 0; col < mapWidth; col++) {
                if (row == 0 || row == mapHeight - 1) {
                    if (col == mapWidth - 1) {
                        mapFloor.append("-\n");
                    } else {
                        mapFloor.append("-");
                    }
                } else if (col == 0) {
                    mapFloor.append("|");
                } else if (col == mapWidth - 1) {
                    mapFloor.append("|\n");
                } else {
                    mapFloor.append(" ");
                }

                // Insert obstacles
                for (int i = 0; i < obstacles.size() - 1; i++) {
                    if (row == obstacles.get(i).getX() + 1 && col == obstacles.get(i).getY()) {
                        if (row != mapHeight - 1) {
                            if (mapFloor.charAt(mapFloor.length() - 1) != '\n') {
                                mapFloor.replace(mapFloor.length() - 1, mapFloor.length(), "*");
                            }
                        }
                        obstacles.remove(i);
                    }
                }

                // Insert the PKD into the map
                if (row == pod.getY() + 1 && col == pod.getX()) {
                    mapFloor.replace(mapFloor.length() - 1, mapFloor.length(), "P");
                }
                if (row == kiva.getY() + 1 && col == kiva.getX()) {
                    mapFloor.replace(mapFloor.length() - 1, mapFloor.length(), "K");
                }
                if (row == drop.getY() + 1 && col == drop.getX()) {
                    mapFloor.replace(mapFloor.length() - 1, mapFloor.length(), "D");
                }
            }
        }

        return String.valueOf(mapFloor);
    }

    /**
     * Create a random map (String) that can be used with FloorMap
     *
     * @return The generated map in String format.
     */
    public String randomMapString() {
        String map;
        Solver solver;
        do {
            solver = new Solver();
            int mapWidth = random.nextInt(15) + 10;
            int mapHeight = random.nextInt(5) + 10;
            map = randomMapString(mapWidth, mapHeight);
            Maze floormap = new Maze(map);
            solver.solve(floormap);
        } while (solver.unsolvable);
        return map;
    }

    // The following two FloorMap methods should be commented out for package independence from the ATA KivaWorld project.

    /**
     * Returns a default map as a KivaWorld FloorMap
     *
     * @return default map as a FloorMap
     */
    public FloorMap defaultMap() {
        return new FloorMap(defaultMapString());
    }

    /**
     * Returns a random map as a KivaWorld FloorMap
     *
     * @return random map as a FloorMap
     */
    public FloorMap randomMap() {
        return new FloorMap(randomMapString());
    }

    /**
     * Opens a JFileChooser save dialog and allows the user to save a String to a file.
     *
     * @param map String to be saved to the file
     * @return The local file name of the saved file
     */
    public String saveFile(String map) {
        try {
            File path;
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("FloorMap Files", "txt", "map", "maz", "maze", "fm", "FloorMap");
            fileChooser.setFileFilter(filter);
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
            fileChooser.setSelectedFile(new File("random_floor_map.txt"));
            int option = fileChooser.showSaveDialog(null);
            fileChooser.setVisible(true);
            if (option == JFileChooser.APPROVE_OPTION) {
                path = new File(fileChooser.getSelectedFile().getAbsolutePath());
                String name = path.toString();
                FileWriter fileWriter = new FileWriter(path);
                fileWriter.write(map);
                fileWriter.flush();
                fileWriter.close();
                System.out.println(name + " saved.");
                return name;
            } else {
                System.out.println("Save canceled");
                return "";
            }
        } catch (IOException e) {
            System.out.println("IO Error. Save canceled");
            e.printStackTrace();
        }
        return "";
    }

    public static void main(String[] args) {
        CreateMap kivaCreateMap = new CreateMap();
        String map = kivaCreateMap.randomMapString();
        System.out.println(map);
        //String floorMapFileName = kivaCreateMap.saveFile(map);
    }
}
