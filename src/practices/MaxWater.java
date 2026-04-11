package practices;

public class MaxWater {

    public static void main(String[] args) {
        int[] height = {1, 8, 6, 2, 5, 4, 8, 3, 7};
        System.out.println(maxArea(height));
    }

    private static int maxArea(int[] heights) {
        int maxArea = 0;
        int leftWidth = 0;
        int rightWidth = heights.length - 1;
        while (leftWidth < rightWidth) {
            int height = Math.min(heights[leftWidth], heights[rightWidth]);
            int width = rightWidth - leftWidth;
            int area = height * width;
            maxArea = Math.max(maxArea, area);
            if (leftWidth < rightWidth) {
                leftWidth++;
            } else {
                rightWidth--;
            }
        }
        return maxArea;
    }
}
