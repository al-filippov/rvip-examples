package rvip.example13;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Counter {
    private int count;
    public synchronized void increment() { count++; }
    public int getCount() { return count; }
}
class SomeThing implements Runnable {
    private Counter counter;
    public SomeThing(Counter counter) { this.counter = counter; }
    public void run() {
        for (int i = 0; i < 100_000_000; i++) {
            counter.increment();
        }
    }
}
public class Program13 {
    public static void main(String[] args) {
        Counter counter = new Counter();
        ExecutorService executorService = Executors.newCachedThreadPool();
        long start = System.currentTimeMillis();
        executorService.execute(new Thread(new SomeThing(counter)));
        executorService.execute(new Thread(new SomeThing(counter)));
        try {
            executorService.shutdown();
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
        }
        catch (InterruptedException e) {
            System.err.println("tasks interrupted");
        }
        finally {
            if (!executorService.isTerminated()) {
                System.err.println("cancel non-finished tasks");
            }
            executorService.shutdownNow();
            System.out.println("shutdown finished");
        }
        long stop = System.currentTimeMillis();
        System.out.printf("Value: %s; Time: %s ms; %n",
                counter.getCount(), stop - start);
    }
}
