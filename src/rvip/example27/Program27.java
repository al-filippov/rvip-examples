package rvip.example27;

import rvip.example2.Program2;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class SomeThing implements Runnable {
    @Override
    public void run() {
        try {
            Thread.sleep(1_000);
        } catch (InterruptedException ignored) {
        } finally {
            Program27.generatorLocker.lock();
            try { Program27.poolIsFull.signal();
            } finally { Program27.generatorLocker.unlock(); }
        }
    }
}
public class Program27 {
    public static ReentrantLock generatorLocker = new ReentrantLock();
    public static Condition poolIsFull = generatorLocker.newCondition();
    public static void main(String[] args) throws InterruptedException {
        int mBytes = 1024 * 1024; int threadCount = 8;
        ThreadPoolExecutor executorService =
                (ThreadPoolExecutor) Executors.newFixedThreadPool(threadCount);
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            generatorLocker.lockInterruptibly();
            try {
                while (executorService.getQueue().size() > threadCount) {
                    poolIsFull.await();
                }
                executorService.execute(new SomeThing());
                if (executorService.getQueue().size() <= threadCount) {
                    poolIsFull.signal();
                }
            } finally {
                generatorLocker.unlock();
            }
            System.out.printf("i = %s; Queue size is: %s; Heap size is: %s Mb; " +
                            "Max heap size is: %s Mb %n",
                    i, executorService.getQueue().size(),
                    Runtime.getRuntime().totalMemory() / mBytes,
                    Runtime.getRuntime().maxMemory() / mBytes);
        }
        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
    }
}