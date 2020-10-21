import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * III Saint Petersburg tournament of young mathematicians – 2020.
 * 5 Problem - "Прятки с котом".
 * <p>
 * Class for 3 item of problem (boxes are arranged as circle).
 * <p>
 * Made by Krasilnikov Alexandr 8M 2020.
 */
public class CircleTool extends Main {

    /**
     * @param n - number of boxes.
     * @return number of steps with this number of boxes.
     */
    public static int resultFor(int n) {
        if (n < 3) throw new IllegalArgumentException("N must be not less than 3: " + n);
        return n == 3 || n == 4 ? 2 : n % 2 == 0 ? n - 1 : n;
    }

    /**
     * @param n - number of boxes.
     * @return table - step by step algorithm realization as two-dimensional array, where first and last boxes are connected.
     */
    public static int[][] createTableFor(int n) {
        if (n < 3) throw new IllegalArgumentException("N must be not less than 3: " + n);
        if (n == 3) return new int[][]{{V, 0, V}, {V, X, V}};
        if (n == 4) return new int[][]{{V, 0, V, 0}, {V, X, V, X}};
        int[][] res = new int[resultFor(n)][n];
        res[0][0] = V;
        res[0][n - 2] = V;
        res[1][0] = V;
        res[1][n - 2] = V;
        res[1][n - 1] = X;
        if (n % 2 == 0) {
            for (int i = 1; i < n / 2 - 1; i++) {
                res[1 + i][i] = V;
                for (int j = i - 2; j >= 0; j -= 2) res[1 + i][j] = X;
                res[1 + i][n - 2 - i] = V;
                for (int j = n - i; j < n; j += 2) res[1 + i][j] = X;
            }
            for (int i = 0; i < n / 2 - 1; i++) {
                res[n / 2 + i][n / 2 - i - 2] = V;
                for (int j = n / 2 - i - 3; j >= 0; j -= 2) res[n / 2 + i][j] = X;
                res[n / 2 + i][n / 2 + i] = V;
                for (int j = n / 2 + i + 1; j < n; j += 2) res[n / 2 + i][j] = X;
                for (int j = n / 2 - i - 1; j < n / 2 + i; j++) res[n / 2 + i][j] = X;
            }
        } else {
            for (int i = 1; i < n / 2; i++) {
                res[1 + i][i] = V;
                for (int j = i - 2; j >= 0; j -= 2) res[1 + i][j] = X;
                res[1 + i][n - 2 - i] = V;
                for (int j = n - i; j < n; j += 2) res[1 + i][j] = X;
            }
            for (int i = 0; i < n / 2; i++) {
                res[n / 2 + i + 1][n / 2 - i - 1] = V;
                for (int j = n / 2 - i - 2; j >= 0; j -= 2) res[n / 2 + i + 1][j] = X;
                res[n / 2 + i + 1][n / 2 + i] = V;
                for (int j = n / 2 + i + 1; j < n; j += 2) res[n / 2 + i + 1][j] = X;
                for (int j = n / 2 - i; j < n / 2 + i; j++) res[n / 2 + i + 1][j] = X;
            }
        }
        return res;
    }

    /**
     * @param n - number of boxes.
     * @return created table, compressed in string.
     */
    public static String tableAsStringFor(int n) {
        StringBuilder res = new StringBuilder();
        int[][] table = createTableFor(n);
        for (int[] ints : table) {
            res.append("*|");
            for (int j = 0; j < n; j++) res.append(nameOf(ints[j])).append("|");
            res.append("*\n");
        }
        return res.toString();
    }

    /**
     * Printing an answer to System.out
     *
     * @param n - number of boxes.
     */
    public static void printTableFor(int n) {
        System.out.println(tableAsStringFor(n));
    }

    /**
     * @param n - number of boxes.
     * @return image, where the picture was drawn.
     */
    public static BufferedImage imageOfTableFor(int n) {
        int[][] table = createTableFor(n);
        int c = Math.min(MAX_CELL_SIZE, Math.max(1000 / table.length, 1)); // c - current cell size.
        int s = c / 10, m = table.length; // s - indent from edges of cell.
        int b = Math.max(1, s), v; // b - size of blue square.
        int width = n * c, height = m * c;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        g.setColor(Color.BLUE);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int x = j * c, y = i * c;
                g.drawImage(Cell, x, y, c, c, null);
                if (table[i][j] == V) g.drawImage(GreenCross, x + s, y + s, c - s * 2, c - s * 2, null);
                if (table[i][j] == X) g.drawImage(RedCross, x + s, y + s, c - s * 2, c - s * 2, null);
            }
            g.fillRect(0, v = i * c + c / 2 - b, 2 * b, 2 * b);
            g.fillRect(c * n - 2 * b, v, 2 * b, 2 * b);
        }
        return image;
    }

    /**
     * @param n - number of boxes.
     * @return created name for n.
     */
    public static String newNameFor(int n) {
        return "Table of circle for " + n + " (" + n + "x" + resultFor(n) + ")";
    }

    /**
     * Showing image of table on screen.
     *
     * @param n - number of boxes.
     */
    public static void showTableOnScreenFor(int n) {
        showOnScreen(imageOfTableFor(n), newNameFor(n));
    }

    /**
     * Showing image of table, loaded from directory "res".
     *
     * @param n - number of boxes.
     */
    public static void showTableFromResFor(int n) {
        File file = new File("res/" + newNameFor(n) + ".jpg");
        if (file.exists()) showOnScreen(importImageFromFile(file), newNameFor(n));
    }

    /**
     * Saving image of table in file located in directory "res".
     *
     * @param n - number of boxes.
     */
    public static void saveTableAsFileFor(int n) {
        BufferedImage image = imageOfTableFor(n);
        File file = new File("res/" + newNameFor(n) + ".jpg");
        try {
            if (file.createNewFile()) exportImageToFile(image, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param n - number of boxes.
     */
    public static void showAndSaveFor(int n) {
        showTableFromResFor(n);
        saveTableAsFileFor(n);
    }

}
