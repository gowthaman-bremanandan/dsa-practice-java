package practices;

public class MinTheMaxLoad {

    public static void main(String[] args) {

//         Test Case 1 (k = 2)
        int[] workLoads1 = {1, 2, 3, 4, 5};
        int numWorkers1 = 2;
        System.out.println("Output: " + findMinMaxLoad(workLoads1, numWorkers1) + " Expected: 9");

        // Test Case 2 (k = 3)
        int[] workLoads2 = {1, 2, 3, 4, 5};
        int numWorkers2 = 3;
        System.out.println("Output: " + findMinMaxLoad(workLoads2, numWorkers2) + " Expected: 6");
        // Split: [1,2,3]=6, [4]=4, [5]=5 → max = 6

        // Test Case 3 (k = 4)
        int[] workLoads3 = {1, 2, 3, 4, 5};
        int numWorkers3 = 4;
        System.out.println("Output: " + findMinMaxLoad(workLoads3, numWorkers3) + " Expected: 5");

        // Test Case 4 (k = 3)
        int[] workLoads4 = {10, 20, 30, 40};
        int numWorkers4 = 3;
        System.out.println("Output: " + findMinMaxLoad(workLoads4, numWorkers4) + " Expected: 40");
        // Split: [10,20]=30, [30]=30, [40]=40 → max = 40

        // Test Case 5 (k = 4)
        int[] workLoads5 = {7, 2, 5, 10, 8};
        int numWorkers5 = 4;
        System.out.println("Output: " + findMinMaxLoad(workLoads5, numWorkers5) + " Expected: 10");
        // Split: [7,2]=9, [5]=5, [10]=10, [8]=8 → max = 10

        int[] workLoads6 = {1, 100, 1, 1, 1};
        int numWorkers6 = 2;
        System.out.println("Output: " + findMinMaxLoad(workLoads6, numWorkers6) + " Expected: 101");

        int[] workLoads7 = {5, 5, 5, 5, 5};
        int numWorkers7 = 3;
        System.out.println("Output: " + findMinMaxLoad(workLoads7, numWorkers7) + " Expected: 10");

        int[] workLoads8 = {1, 2, 3, 100};
        int numWorkers8 = 2;
        System.out.println("Output: " + findMinMaxLoad(workLoads8, numWorkers8) + " Expected: 100");

        int[] workLoads9 = {10, 20, 30, 40, 50};
        int numWorkers9 = 3;
        System.out.println("Output: " + findMinMaxLoad(workLoads9, numWorkers9) + " Expected: 60");
    }

//    private static int findMinMaxLoad(int[] workLoads, int numWorkers) {
//        return findMinMaxLoad(workLoads, numWorkers, 0, workLoads.length - 1);
//    }
//
//    private static int findMinMaxLoad(int[] workLoads, int numWorkers, int left, int right) {
//        int maxminLoad = Integer.MAX_VALUE;
//        int leftForMidCalculation = left;
//        int rightForMidCalculation = right;
//        while (leftForMidCalculation < rightForMidCalculation) {
//            int mid = leftForMidCalculation + (rightForMidCalculation - leftForMidCalculation) / numWorkers;
//            int rightLoadAggregiation = 0;
//            int leftLoadAggregiation = 0;
//            for (int i = left; i <= right; i++) {
//                if (i <= mid) {
//                    leftLoadAggregiation += workLoads[i];
//                } else {
//                    if (numWorkers <= 2) {
//                        rightLoadAggregiation += workLoads[i];
//                    } else {
//                        rightLoadAggregiation = findMinMaxLoad(workLoads, numWorkers - 1, mid + 1, right);
//                    }
//                }
//            }
//            int currentMaxLoad = Math.max(leftLoadAggregiation, rightLoadAggregiation);
//            if (maxminLoad > currentMaxLoad) {
//                maxminLoad = currentMaxLoad;
//                rightForMidCalculation--;
//            } else {
//                leftForMidCalculation++;
//            }
//        }
//        return maxminLoad;
//    }

    private static int findMinMaxLoad(int[] workLoads, int numWorkers) {

        int left = 0;
        int right = 0;

        // same variables idea, but now used correctly
        for (int load : workLoads) {
            left = Math.max(left, load); // minimum possible max load
            right += load; // maximum possible max load
        }

        int maxminLoad = right;

        while (left <= right) {

            int mid = left + (right - left) / 2;

            // check if this mid (max load) is feasible
            if (isFeasible(workLoads, numWorkers, mid)) {
                maxminLoad = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return maxminLoad;
    }

    private static boolean isFeasible(int[] workLoads, int numWorkers, int maxAllowedLoad) {

        int workersRequired = 1;
        int currentLoad = 0;

        for (int load : workLoads) {

            if (currentLoad + load > maxAllowedLoad) {
                workersRequired++;
                currentLoad = load;

                if (workersRequired > numWorkers) {
                    return false;
                }
            } else {
                currentLoad += load;
            }
        }

        return true;
    }
}
