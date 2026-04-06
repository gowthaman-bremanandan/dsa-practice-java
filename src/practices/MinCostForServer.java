package practices;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

public class MinCostForServer {
    public static void main(String[] args) {
        int noOfServer = 5;
        int[][] freeLinks = new int[][]{{0, 1}, {1, 2}};
        int[][] paidlinks = new int[][]{{2, 3, 4}, {3, 4, 2}, {0, 4, 10}};
        System.out.println(getMinCost(noOfServer, freeLinks, paidlinks));
    }

    public static int getMinCost(int noOfServer, int[][] freeLinks, int[][] paidLinks) {

        PriorityQueue<Integer[]> costOfLinks = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        HashMap<Integer, ArrayList<Integer[]>> serverAndsItsConnections = new HashMap<>();

        for (int i = 0; i < freeLinks.length; i++) {
            int currentServer = freeLinks[i][0];
            int linkedServer = freeLinks[i][1];
            serverAndsItsConnections.putIfAbsent(currentServer, new ArrayList<>());
            serverAndsItsConnections.get(currentServer).add(new Integer[]{linkedServer, 0});
        }

        for (int i = 0; i < paidLinks.length; i++) {
            int currentServer = paidLinks[i][0];
            int linkedServer = paidLinks[i][1];
            int costOfTheLink = paidLinks[i][2];
            serverAndsItsConnections.putIfAbsent(currentServer, new ArrayList<>());
            serverAndsItsConnections.get(currentServer).add(new Integer[]{linkedServer, costOfTheLink});
        }

        costOfLinks.add(new Integer[]{0, 0, 1});
        while (!costOfLinks.isEmpty()) {

            Integer[] currenttLink = costOfLinks.poll();
            int server = currenttLink[0];
            int cost = currenttLink[1];
            int noOfLinks = currenttLink[2];

            if (noOfLinks == noOfServer ) {
                return cost;
            }

            if (noOfLinks > noOfServer)
                continue;

            if (serverAndsItsConnections.containsKey(server)) {
                for (Integer[] link : serverAndsItsConnections.get(server)) {
                    int linkedServer = link[0];
                    int costOfTheLink = link[1];
                        costOfLinks.add(new Integer[]{linkedServer, cost + costOfTheLink, noOfLinks + 1});
                }

            }

        }

        return -1;

    }

}


