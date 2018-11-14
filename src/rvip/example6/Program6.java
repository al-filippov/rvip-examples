package rvip.example6;

class SomeThing implements Runnable {
    public void run() {
        System.out.println("Hello from thread!");
    }
}
public class Program6 {
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(new SomeThing());
        t.start();
        t.join();
        System.out.println("Bye from main thread");
    }
}
