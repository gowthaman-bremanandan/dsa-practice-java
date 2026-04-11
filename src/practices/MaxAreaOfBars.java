package practices;

public class MaxAreaOfBars {

    public static void main(String[] args) {
        int[] heights = {2, 1, 5, 6, 2, 3};
        System.out.println(findTheMaxArea(heights));
    }

    private static int findTheMaxArea(int[] heights) {
        if (heights.length == 0) {
            return 0;
        }
        int maxArea = heights[0];
        int startingBarPosition = 0;
        int currentbarPosition = 1;
        int maxBarPosition = 0;
        int maxBarSize = heights[0];
        while (currentbarPosition < heights.length) {
            int currentBarSize = heights[currentbarPosition];
            if (maxBarPosition > currentbarPosition) {
                startingBarPosition = maxBarPosition;
            }
            int startBarSize = heights[startingBarPosition];
            int width = currentBarSize - startBarSize + 1;
            int length = Math.min(startBarSize, currentBarSize);
            int area = width * length;
            maxArea = Math.max(maxArea, area);
            if (maxBarSize < currentBarSize) {
                maxBarPosition = currentbarPosition;
                maxBarSize = currentBarSize;
            }
            currentbarPosition++;
        }
        return maxArea;
    }
}
