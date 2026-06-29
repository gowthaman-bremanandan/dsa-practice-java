package practices.multiThread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

public class NumberProcessor {

    private final List<Integer> numbers;

    // shared index
    private final AtomicInteger index = new AtomicInteger(0);

    // thread pool
    ThreadPoolExecutor executorService = new ThreadPoolExecutor(3, 10, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(2));

    public NumberProcessor(List<Integer> numbers) {

        this.numbers = numbers;
    }

    public void start(int workerCount, Function<Integer, Integer> logic) {

        List<Future<?>> futures = new ArrayList<>();

        for (int i = 0; i < workerCount; i++) {

            NumberWorker worker = new NumberWorker(numbers, index, logic);

            Future<?> future = executorService.submit(worker);

            futures.add(future);
        }

        waitForCompletion(futures);

        shutdown();
    }

    private void waitForCompletion(List<Future<?>> futures) {

        for (Future<?> future : futures) {

            try {

                future.get();

            } catch (Exception e) {

                e.printStackTrace();
            }
        }
    }

    private void shutdown() {

        executorService.shutdown();

        try {

            if (!executorService.awaitTermination(1, TimeUnit.MINUTES)) {

                executorService.shutdownNow();
            }

        } catch (InterruptedException e) {

            executorService.shutdownNow();

            Thread.currentThread().interrupt();
        }
    }
}