package practices;

import java.util.Arrays;
import java.util.List;

public class MultiThreading {

    private static int index = 0;
    private static final Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {

        List<Integer> numbers =
                Arrays.asList(23, 22,65, 42, 88, 22, 11, 8, 15, 16, 100, 4,3);

        Runnable task = () -> {

            while (true) {

                synchronized (lock) {

                    // stop condition
                    if (index >= numbers.size()) {
                        break;
                    }

                    int num = numbers.get(index);

                    // print only even numbers
                    if (num % 2 == 0) {

                        System.out.println(
                                Thread.currentThread().getName()
                                        + " -> " + num
                        );
                    }

                    index++;

                }

                // simulate thread switching
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };

        // Create multiple threads
        Thread t1 = new Thread(task, "Thread-1");
        Thread t2 = new Thread(task, "Thread-2");
        Thread t3 = new Thread(task, "Thread-3");
        Thread t4 = new Thread(task, "Thread-4");
        Thread t5 = new Thread(task, "Thread-5");

        // Start threads
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();

        // Wait for completion
        t1.join();
        t2.join();
        t3.join();
        t4.join();
        t5.join();

        System.out.println("Completed");
    }
}