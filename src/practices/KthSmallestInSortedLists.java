package practices;

import java.util.*;

class Node {
    int value;
    int listIndex;
    int elementIndex;

    Node(int value, int listIndex, int elementIndex) {
        this.value = value;
        this.listIndex = listIndex;
        this.elementIndex = elementIndex;
    }
}

public class KthSmallestInSortedLists {

    public static void main(String[] args) {
        List<List<Integer>> lists = Arrays.asList(
                Arrays.asList(1,5,9),
                Arrays.asList(2,6,10),
                Arrays.asList(3,7,11)
        );

        int k = 5;
        System.out.println(findKthSmallest(lists, k)); // 6
    }

    public static int findKthSmallest(List<List<Integer>> lists, int k) {

        PriorityQueue<Node> pq =
                new PriorityQueue<>(Comparator.comparingInt(n -> n.value));

        // Step 1: add first element of each list
        for (int i = 0; i < lists.size(); i++) {
            pq.offer(new Node(lists.get(i).get(0), i, 0));
        }

        int count = 0;

        while (!pq.isEmpty()) {
            Node curr = pq.poll();
            count++;

            if (count == k) return curr.value;

            // push next element from same list
            if (curr.elementIndex + 1 < lists.get(curr.listIndex).size()) {
                pq.offer(new Node(
                        lists.get(curr.listIndex).get(curr.elementIndex + 1),
                        curr.listIndex,
                        curr.elementIndex + 1
                ));
            }
        }

        return -1;
    }
}