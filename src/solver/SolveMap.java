package solver;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.List;

public class SolveMap {
    private static boolean kivaCommands = false;

    /**
     * If parameter is set to true, prints Kiva commands to console after solving maze.
     *
     * @param printKivaCommands boolean true = print Kiva commands to console after solving maze.
     */
    public void setKivaCommands(boolean printKivaCommands){
        kivaCommands = printKivaCommands;
    }

    /**
     * Opens a GUI file selector dialog in the source directory where the user can select a Maze or FloorMap file.
     * Restricts extensions to text files (*.txt).
     *
     * @return java.io.file the selected file.
     *
     * @throws NullPointerException (in main) if user cancels the file open operation.
     */
    public static File selectFile(){
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("FloorMap Files", "txt", "map", "maz", "maze", "fm", "FloorMap");
        fileChooser.setFileFilter(filter);
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        fileChooser.showDialog(null,"Select");
        fileChooser.setVisible(true);
        return fileChooser.getSelectedFile();
    }

    /**
     * Solves a selected map from a File.
     * @param file The file with the map to be solved.
     */
    public static String solve(File file) {
        Maze maze = new Maze(file);
        Solver solver = new Solver();
        List<Point> path = solver.solve(maze);
        maze.printPath(path);
        String commands = solver.constructKivaCommands(path);
        if (kivaCommands) {
            System.out.println("Kiva Commands:\n" + commands);
        }
        maze.reset();
        return commands;
    }

    public static void main(String[] args){
        solve(selectFile());
    }
}

