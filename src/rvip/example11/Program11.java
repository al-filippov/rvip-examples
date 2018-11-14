package rvip.example11;

class DataManager implements Runnable {
    private static boolean ready = false;
    public void sendData() {
        while (!ready) {
            System.out.println("Waiting for data...");
        }
        System.out.println("Sending data...");
    }
    public void prepareData() {
        System.out.println("Data prepared");
        ready = true;
    }
    @Override
    public void run() { sendData(); }
}
public class Program11 {
    public static void main(String[] args) {
        DataManager manager = new DataManager();
        new Thread(manager).start();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ignored) { }
        manager.prepareData();
    }
}
