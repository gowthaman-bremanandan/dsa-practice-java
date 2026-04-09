package practices;

import java.util.*;

public class TaskSchedulerTest {

    static class Task {
        String name;
        int duration;
        int deadline;

        Task(String name, int duration, int deadline) {
            this.name = name;
            this.duration = duration;
            this.deadline = deadline;
        }
    }

    public static void main(String[] args) {

        // Tasks: A(dur=2, dl=5), B(dur=3, dl=6), C(dur=1, dl=4)
        Map<String, Task> tasks = new HashMap<>();
        tasks.put("A", new Task("A", 2, 5));
        tasks.put("B", new Task("B", 3, 6));
        tasks.put("C", new Task("C", 1, 4));

        // Dependencies: C -> A -> B
        Map<String, List<String>> graph = new HashMap<>();
        graph.put("C", Arrays.asList("A"));
        graph.put("A", Arrays.asList("B"));
        graph.put("B", new ArrayList<>());

        int missed = scheduleTasks(tasks, graph);

        System.out.println("Missed deadlines: " + missed); // Expected: 0
    }

    private static int scheduleTasks(Map<String, Task> tasks,
                                     Map<String, List<String>> graph) {

        // Step 1: Calculate in-degree
        Map<String, Integer> inDegree = new HashMap<>();
        for (String task : tasks.keySet()) {
            inDegree.put(task, 0);
        }

        for (String u : graph.keySet()) {
            for (String v : graph.get(u)) {
                inDegree.put(v, inDegree.get(v) + 1);
            }
        }

        // Step 2: Topological Sort (Kahn’s Algorithm)
        Queue<String> queue = new LinkedList<>();

        for (String task : inDegree.keySet()) {
            if (inDegree.get(task) == 0) {
                queue.offer(task);
            }
        }

        List<String> topoOrder = new ArrayList<>();

        while (!queue.isEmpty()) {
            String current = queue.poll();
            topoOrder.add(current);

            for (String neighbor : graph.getOrDefault(current, new ArrayList<>())) {
                inDegree.put(neighbor, inDegree.get(neighbor) - 1);

                if (inDegree.get(neighbor) == 0) {
                    queue.offer(neighbor);
                }
            }
        }

        // Step 3: Cycle check
        if (topoOrder.size() != tasks.size()) {
            return -1; // cycle exists
        }

        // Step 4: Execute tasks & count deadline misses
        int currentTime = 0;
        int missed = 0;

        for (String taskName : topoOrder) {
            Task task = tasks.get(taskName);

            currentTime += task.duration;

            if (currentTime > task.deadline) {
                missed++;
            }
        }

        return missed;
    }
}