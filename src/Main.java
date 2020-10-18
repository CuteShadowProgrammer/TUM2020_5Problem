import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * III Saint Petersburg tournament of young mathematicians – 2020.
 * 5 Problem - "Прятки с котом".
 * <p>
 * Main class of Program.
 * <p>
 * Made by Krasilnikov Alexandr 8M 2020.
 */
public class Main {

    /**
     * Main method of program.
     * Command structure: ('show'/'save'/'print'/res') + ' ' + ('l' or 'line'/ 'c' or 'circle') + ' ' + number.
     *
     * @param args - not used java compiler arguments.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input command as ('show'/'save'/'print'/res') + ' ' + ('l' or 'line'/ 'c' or 'circle') + ' ' + number:");
        String command = scanner.next(), type = scanner.next();
        int number = scanner.nextInt();
        switch (type) {
            case "line":
            case "l":
            switch (command) {
                case "show":
                    LineTool.showTableOnScreenFor(number);
                    break;
                case "save":
                    LineTool.saveTableAsFileFor(number);
                    break;
                case "print":
                    LineTool.printTableFor(number);
                    break;
                case "res":
                    System.out.println(LineTool.resultFor(number));
                    break;
                default:
                    System.err.println("Command not supported: \"" + command + "\"!");
                    break;
            }
            break;
            case "circle":
            case "c":
                switch (command) {
                    case "show":
                        CircleTool.showTableOnScreenFor(number);
                        break;
                    case "save":
                        CircleTool.saveTableAsFileFor(number);
                        break;
                    case "print":
                        CircleTool.printTableFor(number);
                        break;
                    case "res":
                        System.out.println(CircleTool.resultFor(number));
                        break;
                    default:
                        System.err.println("Command not supported: \"" + command + "\"!");
                        break;
                }
                break;
            default:
                System.err.println("Type not supported: \"" + command + "\"!");
                break;
        }
    }

    /**
     * File of "Green cross.jpg".
     **/
    public static final File GreenCrossJPEG = new File("data/Green cross.jpg");

    /**
     * File of "Red cross.jpg".
     **/
    public static final File RedCrossJPEG = new File("data/Red cross.jpg");

    /**
     * File of "Cell.jpg".
     **/
    public static final File CellJPEG = new File("data/Cell.jpg");

    /**
     * Image of "Green cross.jpg".
     **/
    public static final BufferedImage GreenCross = importImageFromFile(GreenCrossJPEG);

    /**
     * Image of "Red cross.jpg".
     **/
    public static final BufferedImage RedCross = importImageFromFile(RedCrossJPEG);

    /**
     * Image of "Cell.jpg".
     **/
    public static final BufferedImage Cell = importImageFromFile(CellJPEG);

    /**
     * Max size of one cell`s side.
     **/
    public static final int MAX_CELL_SIZE = 50;

    /**
     * '0' in table is simple cell, maybe empty.
     * 'V' in table is cell with green cross, which was chosen by Lera, and where cat can't be after step.
     * 'X' in table is cell with red cross, where cat can't be after step.
     **/
    public static final int V = 1, X = 2;

    /**
     * @param val - value in table, such as 'V', 'X' and zero.
     * @return name of value as String.
     */
    public static String nameOf(int val) {
        if (val == V) return "V";
        if (val == X) return "x";
        return " ";
    }

    /**
     * @param file to import from.
     * @return imported image from file.
     */
    public static BufferedImage importImageFromFile(File file) {
        try {
            return ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Cant import image from file: " + file);
        }
    }

    /**
     * @param image to export from.
     * @param file  to export to.
     */
    public static void exportImageToFile(BufferedImage image, File file) {
        try {
            ImageIO.write(image, "jpg", file);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Cant export image to file: " + file);
        }
    }

    /**
     * Showing image on screen.
     *
     * @param image to show on screen.
     * @param name  of new frame.
     */
    public static void showOnScreen(BufferedImage image, String name) {
        JFrame frame = new JFrame(name);
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(new JLabel(new ImageIcon(image)));
        frame.add(mainPanel);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }
}
