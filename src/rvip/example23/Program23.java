package rvip.example23;

import java.util.concurrent.locks.ReentrantLock;

class Counter {
    private int count;
    private ReentrantLock locker = new ReentrantLock();
    public void increment() {
        locker.lock();
        count++;
        locker.unlock();
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
public class Program23 {
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
