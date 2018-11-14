package rvip.example26;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

class SomeThing implements Runnable {
    @Override
    public void run() {
        try {
            Thread.sleep(1_000);
        } catch (InterruptedException ignored) { }
    }
}
public class Program26 {
    public static void main(String[] args) throws InterruptedException {
        int mBytes = 1024 * 1024;
        ThreadPoolExecutor executorService =
                (ThreadPoolExecutor) Executors.newFixedThreadPool(8);
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            executorService.execute(new SomeThing());
            if (executorService.getQueue().size() % 1_000_000 != 0) {
                continue;
            }
            System.out.printf("Queue size is: %s; Heap size is: %s Mb; " +
                            "Max heap size is: %s Mb %n",
                    executorService.getQueue().size(),
                    Runtime.getRuntime().totalMemory() / mBytes,
                    Runtime.getRuntime().maxMemory() / mBytes);
        }
        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
    }
}
