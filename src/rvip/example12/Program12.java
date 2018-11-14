package rvip.example12;

class DataManager implements Runnable {
    private static boolean ready = false;
    private static final Object monitor = new Object();
    public void sendData() {
        synchronized (monitor) {
            try {
                while (!ready) {
                    System.out.println("Waiting for data...");
                    monitor.wait();
                }
            } catch (InterruptedException ignored) { }
        }
        System.out.println("Sending data...");
    }
    public void prepareData() {
        synchronized (monitor) {
            System.out.println("Data prepared");
            ready = true;
            monitor.notifyAll();
        }
    }
    @Override
    public void run() { sendData(); }
}
public class Program12 {
    public static void main(String[] args) {
        DataManager manager = new DataManager();
        new Thread(manager).start();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ignored) { }
        manager.prepareData();
    }
}
