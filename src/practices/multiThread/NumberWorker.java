package practices.multiThread;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

public class NumberWorker implements Runnable {

    private final List<Integer> numbers;

    private final AtomicInteger index;

    private final Function<Integer, Integer> logic;

    // each thread can process only 2 numbers
    private static final int MAX_PROCESS_COUNT = 2;

    public NumberWorker(
            List<Integer> numbers,
            AtomicInteger index,
            Function<Integer, Integer> logic
    ) {

        this.numbers = numbers;
        this.index = index;
        this.logic = logic;
    }

    @Override
    public void run() {

        // thread-local processed count
        int processedCount = 0;

        while (true) {

            /*
                if thread already processed 2 numbers,
                exit thread
             */
            if (processedCount >= MAX_PROCESS_COUNT) {

                System.out.println(
                        Thread.currentThread().getName()
                                + " reached max limit. Exiting..."
                );

                return;
            }

            int currentIndex =
                    index.getAndIncrement();

            /*
                no more numbers available
             */
            if (currentIndex >= numbers.size()) {

                System.out.println(
                        Thread.currentThread().getName()
                                + " no more numbers left."
                );

                return;
            }

            int number =
                    numbers.get(currentIndex);

            Integer result =
                    logic.apply(number);

            System.out.println(
                    Thread.currentThread().getName()
                            + " processed -> "
                            + result
            );

            // increment thread-specific count
            processedCount++;

            /*
                wait 5 seconds before taking next number
             */
            try {

                System.out.println(
                        Thread.currentThread().getName()
                                + " waiting for 5 seconds..."
                );

                Thread.sleep(5000);

            } catch (InterruptedException e) {

                System.out.println(
                        Thread.currentThread().getName()
                                + " interrupted."
                );

                Thread.currentThread().interrupt();

                return;
            }
        }
    }
}