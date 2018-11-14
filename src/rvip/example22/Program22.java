package rvip.example22;

import java.util.concurrent.Exchanger;

class Friend {
    private final String name;
    private Exchanger<String> exchanger;
    public Friend(Exchanger<String> exchanger, String name) {
        this.exchanger = exchanger;
        this.name = name;
    }
    public String getName() { return this.name; }
    public void bow(Friend bower) {
        System.out.format("%s: %s bow to me!%n",
                this.name, bower.getName());
        bower.bowBack();
    }
    public void bowBack() {
        try {
            String bowerName = this.exchanger.exchange(name);
            System.out.format("%s: %s bow back!%n",
                    bowerName, name);
        } catch (InterruptedException ignored) {}
    }
}
public class Program22 {
    public static void main(String[] args) {
        Exchanger<String> exchanger = new Exchanger<>();
        final Friend f1 = new Friend(exchanger, "Ivan");
        final Friend f2 = new Friend(exchanger, "Andrew");
        new Thread(() -> f1.bow(f2)).start();
        new Thread(() -> f2.bow(f1)).start();
    }
}