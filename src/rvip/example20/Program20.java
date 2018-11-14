package rvip.example20;

import java.util.concurrent.Semaphore;

class Counter {
    private int count;
    private final Semaphore semaphore = new Semaphore(1);
    public void increment() {
        try {
            semaphore.acquire();
            count++;
        } catch (InterruptedException ignored) {
        } finally {
            semaphore.release();
        }
    }
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
public class Program20 {
    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();
        Thread t1 = new Thread(new SomeThing(counter));
        Thread t2 = new Thread(new SomeThing(counter));
        long start = System.currentTimeMillis();
        t1.start(); t2.start();
        t1.join(); t2.join();
        long stop = System.currentTimeMillis();
        System.out.printf("Value: %s; Time: %s ms; %n",
                counter.getCount(), stop - start);
    }
}
