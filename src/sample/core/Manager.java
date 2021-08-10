package sample.core;

public class Manager {
    String name;
    String email;
    String password;

    public static Manager getFirst() {
        Manager manager = new Manager();
        manager.name = "Mike";
        manager.email = "asap.deliveries.uk@gmail.com";
        manager.password = "1741505407";
        return manager;
    }

    static Manager getSecond() {
        Manager manager = new Manager();
        manager.name = "Mike";
        manager.email = "George.deliveries.uk@gmail.com";
        manager.password = "1741505407";
        return manager;
    }
}
