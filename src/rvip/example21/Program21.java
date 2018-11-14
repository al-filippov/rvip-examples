package rvip.example21;

import java.util.concurrent.CountDownLatch;

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
        Program21.latch.countDown();
    }
}
public class Program21 {
    public static CountDownLatch latch = new CountDownLatch(2);
    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();
        Thread t1 = new Thread(new SomeThing(counter));
        Thread t2 = new Thread(new SomeThing(counter));
        long start = System.currentTimeMillis();
        t1.start(); t2.start();
        Program21.latch.await();
        long stop = System.currentTimeMillis();
        System.out.printf("Value: %s; Time: %s ms; %n",
                counter.getCount(), stop - start);
    }
}
