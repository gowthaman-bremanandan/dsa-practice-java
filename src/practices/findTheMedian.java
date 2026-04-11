package practices;

public class findTheMedian {

    public static void main(String[] args){
        int[] arr1 = new int[]{1, 3};
        int[] arr2 = new int[]{2};
        System.out.println(findMedianSortedArrays(arr1, arr2));
    }

    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {

        int m = nums1.length;
        int n = nums2.length;

        int total = m + n;
        int mid = total / 2;

        int i = 0, j = 0;
        int prev = 0, curr = 0;

        for (int k = 0; k <= mid; k++) {

            prev = curr;

            if (i < m && (j >= n || nums1[i] <= nums2[j])) {
                curr = nums1[i];
                i++;
            } else {
                curr = nums2[j];
                j++;
            }
        }

        // If even length → average of middle two
        if (total % 2 == 0) {
            return (prev + curr) / 2.0;
        }

        // If odd → middle element
        return curr;
    }
}
