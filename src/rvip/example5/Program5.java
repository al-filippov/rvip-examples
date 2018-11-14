package rvip.example5;

public class Program5 {
    public static void main(String[] args) {
        new Thread(() -> System.out.println("Hello from thread!")).start();
        System.out.println("Bye from main thread");
    }
}
