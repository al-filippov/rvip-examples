package rvip.example25;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class Counter {
    private int count;
    private ReadWriteLock locker = new ReentrantReadWriteLock();
    public void increment() {
        locker.writeLock().lock();
        count++;
        locker.writeLock().unlock();
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
public class Program25 {
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
