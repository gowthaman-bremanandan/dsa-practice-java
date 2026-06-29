package practices;

import java.util.LinkedList;
import java.util.Queue;

public class FindShortestPathFromObstrucle {

    static class State {
        int row;
        int col;
        int remainingK;
        int steps;

        State(int row, int col, int remainingK, int steps) {
            this.row = row;
            this.col = col;
            this.remainingK = remainingK;
            this.steps = steps;
        }
    }

    public static void main(String[] args) {

        int[][] grid = new int[][]{
                {0, 1, 0},
                {2, 1, 0},
                {0, 0, 3}
        };

        int k = 1;

        System.out.println(shortestPath(grid, k)); // Output: 4
    }

    private static int shortestPath(int[][] grid, int k) {

        int m = grid.length;
        int n = grid[0].length;

        int startRow = -1, startCol = -1;

        // Find starting point (2)
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 2) {
                    startRow = i;
                    startCol = j;
                    break;
                }
            }
        }

        if (startRow == -1) return -1;

        // visited[row][col][remainingK]
        boolean[][][] visited = new boolean[m][n][k + 1];

        Queue<State> queue = new LinkedList<>();
        queue.offer(new State(startRow, startCol, k, 0));
        visited[startRow][startCol][k] = true;

        int[][] directions = {
                {0, 1},   // right
                {0, -1},  // left
                {1, 0},   // down
                {-1, 0}   // up
        };

        while (!queue.isEmpty()) {

            State current = queue.poll();

            int row = current.row;
            int col = current.col;
            int remainingK = current.remainingK;
            int steps = current.steps;

            // Target found
            if (grid[row][col] == 3) {
                return steps;
            }

            for (int[] dir : directions) {

                int newRow = row + dir[0];
                int newCol = col + dir[1];

                // Boundary check
                if (newRow < 0 || newRow >= m || newCol < 0 || newCol >= n) {
                    continue;
                }

                int newK = remainingK;

                // If obstacle, use one removal
                if (grid[newRow][newCol] == 1) {
                    if (remainingK == 0) continue;
                    newK = remainingK - 1;
                }

                // If not visited with this state
                if (!visited[newRow][newCol][newK]) {
                    visited[newRow][newCol][newK] = true;
                    queue.offer(new State(newRow, newCol, newK, steps + 1));
                }
            }
        }

        return -1; // no path
    }
}