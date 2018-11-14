package rvip.example1;

class SomeThing extends Thread {
    @Override
    public void run() {
        System.out.println("Hello from thread!");
    }
}
public class Program1 {
    public static void main(String[] args) {
        new SomeThing().start();
        System.out.println("Bye from main thread");
    }
}
