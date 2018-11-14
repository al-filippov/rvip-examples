package rvip.example2;

class SomeThing implements Runnable {
    public void run() {
        System.out.println("Hello from thread!");
    }
}
public class Program2 {
    public static void main(String[] args) {
        new Thread(new SomeThing()).start();
        System.out.println("Bye from main thread");
    }
}
