package binarySearch.medianOfTwoSorted;

public class MedianOfTwoSortedArrays {

    public static void main(String[] args) {

        System.out.println(findMedianSortedArrays(
                new int[]{1, 2, 3, 4},
                new int[]{5, 6, 7}
        )); // 4.0

        System.out.println(findMedianSortedArrays(
                new int[]{1, 3},
                new int[]{2, 4}
        )); // 2.5

        System.out.println(findMedianSortedArrays(
                new int[]{},
                new int[]{1}
        )); // 1.0
    }

    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {

        // Ensure nums1 is the smaller array
        if (nums1.length > nums2.length) {
            return findMedianSortedArrays(nums2, nums1);
        }

        int m = nums1.length;
        int n = nums2.length;

        int left = 0;
        int right = m;

        int totalLeft = (m + n + 1) / 2;

        while (left <= right) {

            int partitionX = left + (right - left) / 2;
            int partitionY = totalLeft - partitionX;

            int maxLeftX = (partitionX == 0) ? Integer.MIN_VALUE : nums1[partitionX - 1];
            int minRightX = (partitionX == m) ? Integer.MAX_VALUE : nums1[partitionX];

            int maxLeftY = (partitionY == 0) ? Integer.MIN_VALUE : nums2[partitionY - 1];
            int minRightY = (partitionY == n) ? Integer.MAX_VALUE : nums2[partitionY];

            // Correct partition found
            if (maxLeftX <= minRightY && maxLeftY <= minRightX) {

                if ((m + n) % 2 == 0) {
                    return (Math.max(maxLeftX, maxLeftY) +
                            Math.min(minRightX, minRightY)) / 2.0;
                } else {
                    return Math.max(maxLeftX, maxLeftY);
                }
            }
            // Move partitionX left
            else if (maxLeftX > minRightY) {
                right = partitionX - 1;
            }
            // Move partitionX right
            else {
                left = partitionX + 1;
            }
        }

        // This should never be reached if input arrays are sorted
        throw new IllegalArgumentException("Input arrays are not sorted");
    }
}
