package rvip.example10;

class Friend {
    private final String name;
    public Friend(String name) { this.name = name; }
    public String getName() { return this.name; }
    public synchronized void bow(Friend bower) {
        System.out.format("%s: %s bow to me!%n",
                this.name, bower.getName());
        bower.bowBack(this);
    }
    public synchronized void bowBack(Friend bower) {
        System.out.format("%s: %s bow back!%n",
                this.name, bower.getName());
    }
}
public class Program10 {
    public static void main(String[] args) {
        final Friend f1 = new Friend("Ivan");
        final Friend f2 = new Friend("Andrew");
        new Thread(() -> f1.bow(f2)).start();
        new Thread(() -> f2.bow(f1)).start();
    }
}
