package rvip.example14;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;

class SomeThing implements Callable<Integer> {
    @Override
    public Integer call() {
        int count = ThreadLocalRandom.current().nextInt(1, 11);
        for (int i = 0; i <= count; i++) {
            System.out.println("Running..." + i);
        }
        return count;
    }
}
public class Program14 {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<Integer> future = executorService.submit(new SomeThing());
        try {
            System.out.println(future.get());
        } catch (InterruptedException | ExecutionException ignored) { }
        executorService.shutdownNow();
    }
}
