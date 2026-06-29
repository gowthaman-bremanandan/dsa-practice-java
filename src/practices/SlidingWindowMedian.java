package practices;

import java.util.*;

public class SlidingWindowMedian {

    public double[] medianSlidingWindow(int[] nums, int k) {
        List<Double> result = new ArrayList<>();

        // MaxHeap (left) and MinHeap (right)
        PriorityQueue<Integer> small = new PriorityQueue<>(Collections.reverseOrder());
        PriorityQueue<Integer> large = new PriorityQueue<>();

        // Lazy deletion map
        Map<Integer, Integer> delayed = new HashMap<>();

        int smallSize = 0, largeSize = 0;

        for (int i = 0; i < nums.length; i++) {

            // 1. Add element
            if (small.isEmpty() || nums[i] <= small.peek()) {
                small.offer(nums[i]);
                smallSize++;
            } else {
                large.offer(nums[i]);
                largeSize++;
            }

            // 2. Balance heaps
            balanceHeaps(small, large);
            smallSize = small.size();
            largeSize = large.size();

            // 3. Remove element outside window
            if (i >= k) {
                int out = nums[i - k];
                delayed.put(out, delayed.getOrDefault(out, 0) + 1);

                if (out <= small.peek()) {
                    smallSize--;
                    if (out == small.peek()) prune(small, delayed);
                } else {
                    largeSize--;
                    if (!large.isEmpty() && out == large.peek()) prune(large, delayed);
                }

                balanceHeaps(small, large);
            }

            // 4. Get median
            if (i >= k - 1) {
                if (k % 2 == 1) {
                    result.add((double) small.peek());
                } else {
                    result.add(((double) small.peek() + large.peek()) / 2.0);
                }
            }
        }

        // Convert to array
        double[] ans = new double[result.size()];
        for (int i = 0; i < result.size(); i++) {
            ans[i] = result.get(i);
        }
        return ans;
    }

    private void balanceHeaps(PriorityQueue<Integer> small, PriorityQueue<Integer> large) {
        if (small.size() > large.size() + 1) {
            large.offer(small.poll());
        } else if (small.size() < large.size()) {
            small.offer(large.poll());
        }
    }

    private void prune(PriorityQueue<Integer> heap, Map<Integer, Integer> delayed) {
        while (!heap.isEmpty()) {
            int num = heap.peek();
            if (delayed.containsKey(num)) {
                delayed.put(num, delayed.get(num) - 1);
                if (delayed.get(num) == 0) {
                    delayed.remove(num);
                }
                heap.poll();
            } else {
                break;
            }
        }
    }

    public static void main(String[] args) {
        SlidingWindowMedian obj = new SlidingWindowMedian();
        int[] nums = {1,3,-1,-3,5,3,6,7};
        int k = 3;

        double[] result = obj.medianSlidingWindow(nums, k);
        System.out.println(Arrays.toString(result));
    }
}