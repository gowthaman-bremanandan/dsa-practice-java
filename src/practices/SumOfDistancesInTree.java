package practices;

import java.util.*;

public class SumOfDistancesInTree {

    public static void main(String[] args) {

        int n = 6;


        int[][] edges = {
                {0, 1},
                {0, 2},
                {2, 3},
                {2, 4},
                {2, 5}
        };

        int[] result = sumOfDistancesInTree(n, edges);

        // Print output
        System.out.println(Arrays.toString(result));
    }

    // 🔴 Implement your logic here
    public static int[] sumOfDistancesInTree(int n, int[][] edges) {
        int[] ans = new int[n];
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }
        int[] count = new int[n];
        Arrays.fill(count, 1);
        dfs(0, -1, graph, count, ans);
        dfs2(0, -1, graph, count, ans);

        return ans;
    }

    private static void dfs2(int i, int i1, List<List<Integer>> graph, int[] count, int[] ans) {
        for (int j : graph.get(i)) {
            if (j == i1) {
                continue;
            }
            ans[j] = ans[i] - count[j] + (count.length - count[j]);
            dfs2(j, i, graph, count, ans);
        }
    }

    private static void dfs(int i, int i1, List<List<Integer>> graph, int[] count, int[] ans) {
        for (int j : graph.get(i)) {
            if (j == i1) {
                continue;
            }
            dfs(j, i, graph, count, ans);
            count[i] += count[j];

            ans[i] += ans[j] + count[j];
        }
    }
}