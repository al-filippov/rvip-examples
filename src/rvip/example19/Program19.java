package rvip.example19;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

class SomeThing extends RecursiveTask<Integer> {
    private int[] arr;
    public SomeThing(int[] arr) { this.arr = arr; }
    @Override
    protected Integer compute() {
        if (arr.length > 3) {
            return ForkJoinTask.invokeAll(createSubtasks()).stream()
                    .mapToInt(ForkJoinTask::join).sum();
        } else { return processing(arr); }
    }
    private Collection<SomeThing> createSubtasks() {
        List<SomeThing> subTasks = new ArrayList<>();
        subTasks.add(new SomeThing(Arrays.copyOfRange(arr, 0, arr.length / 2)));
        subTasks.add(new SomeThing(Arrays.copyOfRange(arr, arr.length / 2, arr.length)));
        return subTasks;
    }
    private Integer processing(int[] arr) {
        int result = Arrays.stream(arr).sum();
        System.out.printf("Result: %s; Thread: %s %n",
                result,  Thread.currentThread().getName());
        return result;
    }
}
public class Program19 {
    public static void main(String[] args) throws InterruptedException {
        ForkJoinPool commonPool = ForkJoinPool.commonPool();
        RecursiveTask<Integer> sumArrayTask = new SomeThing(
                new int[] {0, 1, 2, 3, 4, 5, 6 ,7 ,8, 9, 10});
        commonPool.execute(sumArrayTask);
        try {
            System.out.printf("Result: %s %n", sumArrayTask.get());
        } catch (InterruptedException | ExecutionException ignored) { }
        commonPool.shutdownNow();
    }
}
