package main;

public class Supplier extends Thread{
    Storage storage;
    public Supplier(Storage storage) {
        this.storage = storage;
    }
    @Override
    public void run() {
        while (true) {
            this.storage.putProduct();
        }
    }
}
