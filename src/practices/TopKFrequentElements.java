package practices;

import greedy.handOfStraights.HandOfStraights;

import java.util.*;

public class TopKFrequentElements {

    public static void main(String[] args) {
        int[] numbers = new int[]{4,4,4,6,6,7,7,7,7};
        System.out.println(topKFrequent(numbers, 2));
    }

    private static List<Integer> topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> freqMap = new HashMap<>();

        for (int num : nums) {
            freqMap.put(num, freqMap.getOrDefault(num, 0) + 1);
        }

        PriorityQueue<Integer> minHeap =
                new PriorityQueue<>((a, b) -> freqMap.get(a) - freqMap.get(b));

        for (int num : freqMap.keySet()) {
            minHeap.offer(num);
            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }

        return new ArrayList<>(minHeap);
    }
}
