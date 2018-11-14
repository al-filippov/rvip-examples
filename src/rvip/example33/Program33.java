package rvip.example33;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class Message {
    private String msg;
    public Message(String str){ this.msg=str; }
    public String getMsg() { return msg; }
}
class Producer implements Runnable {
    private BlockingQueue<Message> queue;
    public Producer(BlockingQueue<Message> queue) { this.queue=queue; }
    public void run() {
        for(int i=0; i<100; i++) {
            Message msg = new Message(String.valueOf(i));
            try {
                Thread.sleep(i);
                queue.put(msg);
                System.out.printf("Produced %s %n", msg.getMsg());
            } catch (InterruptedException ignored) { }
        }
    }
}
class Consumer implements Runnable{
    private BlockingQueue<Message> queue;
    public Consumer(BlockingQueue<Message> queue) { this.queue=queue; }
    public void run() {
        try{
            Message msg;
            while((msg = queue.take()) != null) {
                Thread.sleep(10);
                System.out.printf("Consumed %s %n", msg.getMsg());
            }
        } catch(InterruptedException ignored) { }
    }
}
public class Program33 {
    public static final AtomicInteger time = new AtomicInteger(0);
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Message> queue = new ArrayBlockingQueue<>(10);
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            if (queue.isEmpty()) {
                System.out.printf("Time is %s %n", time.addAndGet(1));
            } else {
                time.set(0);
            }
            if (time.get() >= 10) {
                System.exit(0);
            }
        }, 0, 1, TimeUnit.SECONDS);
        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);
        Thread p = new Thread(producer); p.start();
        Thread c = new Thread(consumer); c.start();
        p.join(); c.join();
    }
}
