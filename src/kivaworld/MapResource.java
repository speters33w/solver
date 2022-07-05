package kivaworld;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Utility to open and read Kiva map files.
 *
 * @author StephanPeters (speters33w)
 * @version 20220704.2330
 * @see FloorMap
 */
public class MapResource {

    /**
     * Opens a GUI file selector dialog in the source directory where the user can select a Maze or FloorMap file.
     * Restricts extensions to text files (*.txt).
     *
     * @return java.io.file the selected file.
     *
     * @throws NullPointerException if user cancels the file open operation.
     */
    public static String getFileName(){
        System.out.println("Please select a map file.");
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("FloorMap Files", "txt", "map", "maz", "maze", "fm", "FloorMap");
        fileChooser.setFileFilter(filter);
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        fileChooser.showDialog(null,"Select");
        fileChooser.setVisible(true);
        return fileChooser.getName(fileChooser.getSelectedFile());
    }

    /**
     * Returns a java.io.File resource given a filename.
     * @param fileName The filename of the file.
     * @return the file.
     */
    public static File getFile(String fileName){
        return new File(fileName);
    }

    /**
     * Opens a GUI file selector dialog in the source directory where the user can select a Maze or FloorMap file.
     * Restricts extensions to text files (*.txt).
     *
     * @return java.io.file the selected file.
     *
     * @throws NullPointerException (in main) if user cancels the file open operation.
     */
    public static File selectMap(){
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("FloorMap Files", "txt", "map", "maz", "maze", "fm", "FloorMap");
        fileChooser.setFileFilter(filter);
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        fileChooser.showDialog(null,"Select");
        fileChooser.setVisible(true);
        return fileChooser.getSelectedFile();
    }

    /**
     * Reads a map File and returns the map as a String.
     *
     * @param map the map File to be read.
     * @return the map File as a String.
     *
     */
    public static String getMapString(File map) {
        StringBuilder mapString = new StringBuilder();
        try (Scanner input = new Scanner(map)) {
            while (input.hasNextLine()) {
                mapString.append(input.nextLine()).append("\n");
            }
            return mapString.toString();
        } catch (IOException e) {
            System.out.println("File not found or other IO error, using default map.");
            return ""
                    + "-------------\n"
                    + "        P   *\n"
                    + "   **       *\n"
                    + "   **       *\n"
                    + "  K       D *\n"
                    + " * * * * * **\n"
                    + "-------------\n";
        }
    }

    /**
     * Opens a JFileChooser save dialog and allows the user to save a String to a file.
     *
     * @param map String to be saved to the file
     */
    public static String saveMap(String map) {
        try {
            File path;
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("FloorMap Files", "txt", "map", "maz", "maze", "fm", "FloorMap");
            fileChooser.setFileFilter(filter);
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
            fileChooser.setSelectedFile(new File("kiva_floor_map.txt"));
            int option = fileChooser.showSaveDialog(null);
            fileChooser.setVisible(true);
            if (option == JFileChooser.APPROVE_OPTION) {
                path = new File(fileChooser.getSelectedFile().getAbsolutePath());
                String name = path.getName();
                FileWriter fileWriter = new FileWriter(path);
                fileWriter.write(map);
                fileWriter.flush();
                fileWriter.close();
                System.out.println("File saved.");
                return name;
            } else {
                System.out.println("Save canceled.");
                return "";
            }
        } catch (IOException e) {
            System.out.println("IO Error. Save canceled.");
            e.printStackTrace();
        }
        return "";
    }
}
