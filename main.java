package main_pack;

import java.io.File;     
import java.io.FileNotFoundException;  
import java.util.Scanner;
import java.util.Arrays;

public class main {
	public static void main(String[] args) {
        try {
            File file = new File("test.txt");
            Scanner scanner = new Scanner(file);

            int set = getNextInt(scanner); // how many matrix being tested

            for (int curSet = 0; curSet < set; curSet++) {
                int numColors = getNextInt(scanner); // current number of colors allowed
                int size = getNextInt(scanner); // current size of matrix

                int[][] matrix = new int[size][size];
                for (int i = 0; i < size; i++) {
                    String line = getNextLine(scanner);
                    if (!line.isEmpty()) {
                        String[] row = line.split("\\s+");
                        for (int j = 0; j < size; j++) {
                            matrix[i][j] = Integer.parseInt(row[j]);
                        }
                    }
                }

                System.out.println("Set: " + (curSet + 1));
                GraphColoring graphColoring = new GraphColoring(matrix);
				graphColoring.graphColoring(numColors);

                System.out.println();
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("file not found error");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("there was a number formating error in " + e.getMessage());
        }
    }

    private static int getNextInt(Scanner scanner) {
        String line;
        while (scanner.hasNextLine()) {
            line = scanner.nextLine().trim();
            if (!line.isEmpty()) {
                return Integer.parseInt(line);
            }
        }
        return -1;
    }

    private static String getNextLine(Scanner scanner) {
        String line;
        while (scanner.hasNextLine()) {
            line = scanner.nextLine().trim();
            if (!line.isEmpty()) {
                return line;
            }
        }
        return "";
    }
}
