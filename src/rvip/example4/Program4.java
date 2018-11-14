package rvip.example4;

public class Program4 {
    public static void main(String[] args) {
        new Thread(() -> {
            System.out.println("Hello from thread!");
        }).start();
        System.out.println("Bye from main thread");
    }
}
