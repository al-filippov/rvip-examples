package rvip.example17;

import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Program17 {
    public static void main(String[] args) {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        ThreadLocal<Integer> count = new ThreadLocal<>();
        long start = System.currentTimeMillis();
        executorService.scheduleWithFixedDelay(() -> {
                    count.set(Optional.ofNullable(count.get()).orElse(0) + 1);
                    System.out.printf("Executed %s times; Time ellapsed: %s %n",
                            count.get(), System.currentTimeMillis() - start);
                    if (count.get() == 5) {
                        executorService.shutdownNow();
                        System.out.println("Stop");
                    }
                }, 100, 1500, TimeUnit.MILLISECONDS);
    }
}
