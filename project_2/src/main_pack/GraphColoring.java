package main_pack;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class GraphColoring {
    private int V;  // Number of vertices
    private int[][] graph;
    private int[] color;
    private List<int[]> allSolutions;

    public GraphColoring(int[][] graph) {
        V = graph.length;
        this.graph = graph;
        color = new int[V];
        allSolutions = new ArrayList<>();
    }

    private boolean isSafe(int v, int c) {
        for (int i = 0; i < V; i++) {
            if (graph[v][i] == 1 && color[i] == c)
                return false;
        }
        return true;
    }

    private void graphColoringUtil(int m, int v) {
        if (v == V) {
            boolean[] usedColors = new boolean[m + 1];
            for (int i = 0; i < V; i++) {
                usedColors[color[i]] = true;
            }
            boolean allColorsUsed = true;
            for (int i = 1; i <= m; i++) {
                if (!usedColors[i]) {
                    allColorsUsed = false;
                    break;
                }
            }
            if (allColorsUsed) {
                allSolutions.add(color.clone());
            }
            return;
        }

        for (int c = 1; c <= m; c++) {
            if (isSafe(v, c)) {
                color[v] = c;
                graphColoringUtil(m, v + 1);
                color[v] = 0; // Backtrack
            }
        }
    }

    private double calculateStandardDeviation(int[] colorCount, int m) {
        double sum = 0;
        for (int i = 1; i <= m; i++) {
            sum += colorCount[i];
        }
        double mean = sum / m;

        double variance = 0;
        for (int i = 1; i <= m; i++) {
            variance += Math.pow(colorCount[i] - mean, 2);
        }

        return Math.sqrt(variance / m);
    }

    private boolean selectMostBalancedColoring(int m) {
        double minStdDev = Double.MAX_VALUE;
        int[] bestSolution = null;

        for (int[] solution : allSolutions) {
            int[] colorCount = new int[m + 1];
            for (int c : solution) {
                colorCount[c]++;
            }

            double stdDev = calculateStandardDeviation(colorCount, m);
            if (stdDev < minStdDev) {
                minStdDev = stdDev;
                bestSolution = solution;
            }
        }

        if (bestSolution != null) {
            System.arraycopy(bestSolution, 0, color, 0, V);
            return true;
        } else if (!allSolutions.isEmpty()) {
            System.arraycopy(allSolutions.get(0), 0, color, 0, V);
            return true;
        }

        return false;
    }

    public boolean graphColoring(int m) {
        graphColoringUtil(m, 0);

        if (selectMostBalancedColoring(m)) {
            printSolution(color, m);
            return true;
        } else {
            System.out.println("no such a sequence exists");
            return false;
        }
    }


    private void printSolution(int[] solution, int m) {
        String[] colorNames = new String[m + 1];
        
        for (int i = 1; i <= m; i++) {
            switch (i) {
                case 1: colorNames[i] = "Red"; break;
                case 2: colorNames[i] = "Green"; break;
                case 3: colorNames[i] = "Blue"; break;
                case 4: colorNames[i] = "Yellow"; break;
                case 5: colorNames[i] = "Purple"; break;
                case 6: colorNames[i] = "Orange"; break;
                default: colorNames[i] = "Color: " + i; break;
            }
        }

        Map<Integer, List<Integer>> colorDistribution = new HashMap<>();
        for (int i = 0; i < solution.length; i++) {
            colorDistribution.computeIfAbsent(solution[i], k -> new ArrayList<>()).add(i + 1);
        }

        System.out.print("(");
        for (int i = 1; i <= m; i++) {
            System.out.print(colorDistribution.getOrDefault(i, new ArrayList<>()).size());
            if (i < m) System.out.print(", ");
        }
        System.out.println(")");

        for (int i = 1; i <= m; i++) {
            System.out.print(colorNames[i] + ": ");
            List<Integer> vertices = colorDistribution.getOrDefault(i, new ArrayList<>());
            for (int vertex : vertices) {
                System.out.print(vertex + " ");
            }
            System.out.println();
        }
    }
}
