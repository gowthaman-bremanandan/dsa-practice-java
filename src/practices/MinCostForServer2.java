package practices;

import java.util.*;

public class MinCostForServer2   {

    public static void main(String[] args) {
        int noOfServer = 5;
        int[][] freeLinks = {{0, 1}, {1, 2}};
        int[][] paidlinks = {{2, 3, 4}, {3, 4, 2}, {0, 4, 10}};

        System.out.println(getMinCost(noOfServer, freeLinks, paidlinks)); // 6
    }

    public static int getMinCost(int n, int[][] freeLinks, int[][] paidLinks) {

        // Graph: node -> list of {neighbor, cost}
        HashMap<Integer, ArrayList<int[]>> graph = new HashMap<>();

        // Build bidirectional graph
        for (int[] e : freeLinks) {
            graph.computeIfAbsent(e[0], k -> new ArrayList<>()).add(new int[]{e[1], 0});
            graph.computeIfAbsent(e[1], k -> new ArrayList<>()).add(new int[]{e[0], 0});
        }

        for (int[] e : paidLinks) {
            graph.computeIfAbsent(e[0], k -> new ArrayList<>()).add(new int[]{e[1], e[2]});
            graph.computeIfAbsent(e[1], k -> new ArrayList<>()).add(new int[]{e[0], e[2]});
        }

        // Min heap → {cost, node}
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));

        boolean[] visited = new boolean[n];

        pq.add(new int[]{0, 0}); // start from node 0

        int totalCost = 0;
        int visitedCount = 0;

        while (!pq.isEmpty()) {

            int[] curr = pq.poll();
            int cost = curr[0];
            int node = curr[1];

            if (visited[node]) continue;

            visited[node] = true;
            totalCost += cost;
            visitedCount++;

            for (int[] nei : graph.getOrDefault(node, new ArrayList<>())) {
                if (!visited[nei[0]]) {
                    pq.add(new int[]{nei[1], nei[0]});
                }
            }
        }

        return visitedCount == n ? totalCost : -1;
    }
}