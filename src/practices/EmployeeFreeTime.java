package practices;

import java.util.*;

class Interval {
    int start, end;

    Interval(int s, int e) {
        start = s;
        end = e;
    }
}

public class EmployeeFreeTime {

    public static void main(String[] args) {

        List<List<Interval>> schedule = new ArrayList<>();

        schedule.add(Arrays.asList(new Interval(1, 3), new Interval(6, 7)));
        schedule.add(Arrays.asList(new Interval(2, 4)));
        schedule.add(Arrays.asList(new Interval(5, 6)));

        List<Interval> result = findEmployeeFreeTime(schedule);

        for (Interval i : result) {
            System.out.println("[" + i.start + "," + i.end + "]");
        }
    }

    public static List<Interval> findEmployeeFreeTime(List<List<Interval>> schedule) {

        List<Interval> allIntervals = new ArrayList<>();

        // Step 1: Flatten
        for (List<Interval> emp : schedule) {
            allIntervals.addAll(emp);
        }

        // Step 2: Sort
        allIntervals.sort((a, b) -> a.start - b.start);

        // Step 3: Merge
        List<Interval> merged = new ArrayList<>();
        Interval prev = allIntervals.get(0);

        for (int i = 1; i < allIntervals.size(); i++) {
            Interval curr = allIntervals.get(i);

            if (curr.start <= prev.end) {
                prev.end = Math.max(prev.end, curr.end);
            } else {
                merged.add(prev);
                prev = curr;
            }
        }
        merged.add(prev);

        // Step 4: Find gaps
        List<Interval> freeTime = new ArrayList<>();

        for (int i = 1; i < merged.size(); i++) {
            int start = merged.get(i - 1).end;
            int end = merged.get(i).start;

            if (start < end) {
                freeTime.add(new Interval(start, end));
            }
        }

        return freeTime;
    }
}