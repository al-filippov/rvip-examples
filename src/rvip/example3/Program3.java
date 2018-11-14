package rvip.example3;

public class Program3 {
    public static void main(String[] args) {
        new Thread(new Runnable() {
            public void run() {
                System.out.println("Hello from thread!");
            }
        }).start();
        System.out.println("Bye from main thread");
    }
}
