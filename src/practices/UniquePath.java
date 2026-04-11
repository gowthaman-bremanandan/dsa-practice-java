package practices;

public class UniquePath {

    public static boolean isEndReached = false;

    public static void main(String[] args) {
        int[][] paths = new int[][]{{1, 3, -1}, {2, 1, 4}, {-1, 2, 1}};
        System.out.println(findThePathLength(paths));
    }

    private static int findThePathLength(int[][] paths) {
        isEndReached = false;
        int rows = paths.length;
        int cols = paths[0].length;
        if (paths[rows - 1][cols - 1] == -1) {
            return -1;
        }
        int pathLength = findTheMinLength(paths, 0, 0);
        return isEndReached ? pathLength : -1;
    }

    private static int findTheMinLength(int[][] paths, int row, int column) {
        int rows = paths.length;
        int cols = paths[0].length;
        if (row == rows - 1 && column == cols - 1) {
            isEndReached = true;
            return paths[row][column];
        }
        if (row >= rows || column >= cols) {
            return Integer.MAX_VALUE;
        }
        if (paths[row][column] == -1) {
            return Integer.MAX_VALUE;
        }
        int pathLength = paths[row][column];
        int pathToright = Math.abs(findTheMinLength(paths, row, column + 1));
        int pathToDown = Math.abs(findTheMinLength(paths, row + 1, column));
        System.out.println("for row & Column " + row + " " + column + " path to right " + pathToright + " path to down " + pathToDown);
        return Math.min(pathToDown, pathToright) + pathLength;
    }
}
