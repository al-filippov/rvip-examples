package rvip.example15;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class SomeThing implements Callable<Integer> {
    private int count;
    public SomeThing(int count) { this.count = count; }
    @Override
    public Integer call() {
        for (int i = 0; i <= count; i++) {
            System.out.printf("Thread %s: Running... %s %n",
                    Thread.currentThread().getName(), i);
        }
        return count;
    }
}
public class Program15 {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<Future<Integer>> results = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            results.add(executorService.submit(new SomeThing(i)));
        }
        for (Future<Integer> task : results) {
            try {
                System.out.println(task.get());
            } catch (InterruptedException | ExecutionException ignored) { }
        }
        executorService.shutdownNow();
    }
}
