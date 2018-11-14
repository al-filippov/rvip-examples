package rvip.example18;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

class SomeThing extends RecursiveAction {
    private String workLoad = "";
    public SomeThing(String workLoad) { this.workLoad = workLoad; }
    @Override
    protected void compute() {
        if (workLoad.length() > 4) {
            ForkJoinTask.invokeAll(createSubtasks());
        } else { processing(workLoad); }
    }
    private Collection<SomeThing> createSubtasks() {
        List<SomeThing> subTasks = new ArrayList<>();
        String partOne = workLoad.substring(0, workLoad.length() / 2);
        String partTwo = workLoad.substring(workLoad.length() / 2);
        subTasks.add(new SomeThing(partOne)); subTasks.add(new SomeThing(partTwo));
        return subTasks;
    }
    private void processing(String work) {
        System.out.printf("Result: %s; Thread: %s %n",
                work.toUpperCase(), Thread.currentThread().getName());
    }
}
public class Program18 {
    public static void main(String[] args) throws InterruptedException {
        ForkJoinPool commonPool = ForkJoinPool.commonPool();
        commonPool.execute(new SomeThing("Example of RecursiveAction usage"));
        commonPool.shutdown();
        commonPool.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
    }
}
