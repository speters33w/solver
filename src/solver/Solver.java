package solver;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * A Breadth First maze solving class, adapted for use with KivaWorld.
 * <p>
 * Given a solver.Maze() object, solves the map from starting location to pod location to drop zone location,
 * or from start to end if there is no pod in the map.
 *
 * @author StephanPeters (speters33w)
 * @version 20220629.1800
 * @see Maze
 */
public class Solver {

    boolean debugging = false;

    /**
     * 2D Directions array (ordered coordinate integer pairs)
     * {@code [0] = UP, [1] = RIGHT [2] = DOWN [3] = LEFT}
     * Points are in reflected format [row][col] or (y,x).
     */
    static final int[][] DIRECTIONS = facingDirection.directions();

    private enum facingDirection {
        UP(new Point(-1, 0)),
        RIGHT(new Point(0, 1)),
        DOWN(new Point(1, 0)),
        LEFT(new Point(0, -1));

        final Point delta;

        facingDirection(Point delta) {
            this.delta = delta;
        }

        Point getDelta() {
            return this.delta;
        }

        static int[][] directions() {
            int i = -1;
            final int[][] directions = new int[facingDirection.values().length][2];
            for (facingDirection value : facingDirection.values()) {
                i++;
                directions[i][0] = value.getDelta().getX();
                directions[i][1] = value.getDelta().getY();
            }
            return directions;
        }
    }

    static final Map<facingDirection, Point> DIRECTIONSMAP = Stream.of(new Object[][]{ //in reflected format [row][col] or (y,x)
            {facingDirection.UP, facingDirection.UP.getDelta()},
            {facingDirection.RIGHT, facingDirection.RIGHT.getDelta()},
            {facingDirection.DOWN, facingDirection.DOWN.getDelta()},
            {facingDirection.LEFT, facingDirection.LEFT.getDelta()},
    }).collect(Collectors.toMap(data -> (facingDirection) data[0], data -> (Point) data[1]));

    private final List<Point> path = new LinkedList<>();


    /**
     * This is the main entry point for the maze solver.
     *
     * @param maze - a floor map in solver.Maze format
     * @return a List of Points containing the solution.
     */
    public List<Point> solve(Maze maze) {
        List<Point> returnPath = new LinkedList<>();
        //if the map has a pod,
        if (debugging) {
            System.out.println("Pod location: " + maze.getPodLocation());
        }
        if (maze.getPodLocation() != null) {

            // get the path of points from the Kiva to the pod
            List<Point> pathToPod = solver(maze, maze.getInitialKivaLocation()); // Points are reflected (y,x) or [row],[col].
            Collections.reverse(pathToPod);
            // and add the Points to the return list.
            returnPath.addAll(pathToPod);
            if (debugging) {
                System.out.println(pathToPod);
            }
            // In case there is no path to the pod;
            if (pathToPod.isEmpty()) {
                System.out.println("Kiva mission aborted, Kiva can not go to pod location.");
            }

            // Then get the path of points from the pod to the drop zone
            List<Point> pathToDropZone = solver(maze, maze.getDropZoneLocation()); // Points are reflected (y,x) or [row],[col].
            // and add the Points to the return list.
            returnPath.addAll(pathToDropZone);
            if (debugging) {
                System.out.println(pathToDropZone);
            }
            // In case there is no path to the drop zone;
            if (pathToDropZone.isEmpty()) {
                System.out.println("Kiva mission aborted, Kiva can not go to drop zone location.");
            }
        }
        // If the map is a simple start-to-finish maze,
        else {
            // Solve for simple start-to-finish maze,
            maze.setPodLocation(maze.getDropZoneLocation());
            List<Point> pathToPod = solver(maze, maze.getInitialKivaLocation()); // Points are reflected (y,x) or [row],[col].
            Collections.reverse(pathToPod);
            // and send the competed map with the solution to the caller.
            if (debugging) {
                System.out.println(pathToPod);
            }
            returnPath = pathToPod;
        }
        // Send the competed map with the solution to the caller.
        return returnPath;
    }

    /**
     * The main solver method.
     *
     * @param maze          - a floor map in solver.Maze format.
     * @param startLocation - the starting location point.
     * @return a List of Points containing the solution.
     */
    public List<Point> solver(Maze maze, Point startLocation) {
        LinkedList<Point> nextToVisit = new LinkedList<>();
        path.clear();
        nextToVisit.add(startLocation);

        while (!nextToVisit.isEmpty()) {
            Point currentPoint = nextToVisit.remove();

            if (!maze.isValidLocation(currentPoint.getX(), currentPoint.getY()) || maze.isExplored(currentPoint.getX(), currentPoint.getY())) {
                continue;
            }

            if (maze.isObstacle(currentPoint.getX(), currentPoint.getY())) {
                maze.setVisited(currentPoint.getX(), currentPoint.getY(), true);
                continue;
            }

            if (maze.isPodLocation(currentPoint.getX(), currentPoint.getY())) {
                maze.reset();
                return backtrackPath(currentPoint);
            }


            //todo eliminate 2D integer array DIRECTIONS and use a point array from facingDirection enum?
            for (int[] direction : DIRECTIONS) {
                Point coordinate = new Point(currentPoint.getX() + direction[0], currentPoint.getY() + direction[1], currentPoint);
                nextToVisit.add(coordinate);
                maze.setVisited(currentPoint.getX(), currentPoint.getY(), true);
            }
        }

        return Collections.emptyList();
    }

    /**
     * The Point path creator method.
     *
     * @param currentPoint the current location of the search (y,x).
     * @return - a List of Points containing the path.
     */
    private List<Point> backtrackPath(Point currentPoint) {
        Point iteration = currentPoint;

        while (iteration != null) {
            path.add(iteration);
            iteration = iteration.reference;
        }
        return path;
    }

    /**
     * Creates a set of solution commands specifically for the ATA KivaWorld project.
     *
     * @param path The solution path returned by Solver.
     * @return String with the Kiva commands.
     */
    public String constructKivaCommands(List<Point> path) {
        // initialize method-scope variables
        StringBuilder commands = new StringBuilder();
        facingDirection direction = facingDirection.UP;
        facingDirection directionDesired = facingDirection.UP;
        Point delta = new Point();

        // Iterate over solved path to determine direction of travel.
        for (int i = 0; i < path.size() - 1; i++) {
            Point p = path.get(i);
            Point q = path.get(i + 1);
            delta = delta.getDelta(p, q);
            if (delta.equals(new Point())) { // If delta is (0,0), pod location
                commands.append("T");
            }
            if (DIRECTIONSMAP.get(direction).equals(delta)) {
                commands.append("F");
            }
            switch (delta.getX()) {
                case -1:
                    directionDesired = facingDirection.UP;
                    if (direction == facingDirection.RIGHT) {
                        commands.append("LF");
                    }
                    if (direction == facingDirection.DOWN) {
                        commands.append("LLF");
                    }
                    if (direction == facingDirection.LEFT) {
                        commands.append("RF");
                    }
                    break;
                case 1:
                    directionDesired = facingDirection.DOWN;
                    if (direction == facingDirection.RIGHT) {
                        commands.append("RF");
                    }
                    if (direction == facingDirection.UP) {
                        commands.append("RRF");
                    }
                    if (direction == facingDirection.LEFT) {
                        commands.append("LF");
                    }
                    break;
            }
            switch (delta.getY()) {
                case -1:
                    directionDesired = facingDirection.LEFT;
                    if (direction == facingDirection.UP) {
                        commands.append("LF");
                    }
                    if (direction == facingDirection.RIGHT) {
                        commands.append("LLF");
                    }
                    if (direction == facingDirection.DOWN) {
                        commands.append("RF");
                    }
                    break;
                case 1:
                    directionDesired = facingDirection.RIGHT;
                    if (direction == facingDirection.UP) {
                        commands.append("RF");
                    }
                    if (direction == facingDirection.LEFT) {
                        commands.append("RRF");
                    }
                    if (direction == facingDirection.DOWN) {
                        commands.append("LF");
                    }
                    break;
            }
            direction = directionDesired;
            if (i == path.size() - 2) {
                commands.append("D");
            }
        }
        return commands.toString();
    }
}
