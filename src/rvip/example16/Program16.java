package rvip.example16;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Program16 {
    public static void main(String[] args) {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5);
        long start = System.currentTimeMillis();
        ScheduledFuture scheduledFuture =
                executorService.schedule(() -> {
                            long stop = System.currentTimeMillis();
                            System.out.printf("Time ellapsed: %s %n",
                                    stop - start);
                            System.out.println("Executed!");
                            return "Called!";
                        }, 5, TimeUnit.SECONDS);
        try {
            System.out.printf("Result: %s %n", scheduledFuture.get());
        } catch (InterruptedException | ExecutionException ignored) { }
        executorService.shutdownNow();
    }
}
