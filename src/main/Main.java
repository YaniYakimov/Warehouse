package main;

public class Main {
    public static void main(String[] args) {
        Storage storage = new Storage();
        Supplier supplier = new Supplier(storage);
        supplier.start();
        Shop shop1 = new Shop();
        Shop shop2 = new Shop();
        Shop shop3 = new Shop();
        ShopOwner shopOwner1 = new ShopOwner(storage, shop1);
        shopOwner1.start();
        ShopOwner shopOwner2 = new ShopOwner(storage, shop2);
        shopOwner2.start();
        ShopOwner shopOwner3 = new ShopOwner(storage, shop3);
        shopOwner3.start();
        for (int i = 0; i < 9; i++) {
            if(i < 3) {
                Client client1 = new Client(shop1);
                client1.start();
            }
            if(i >= 3 && i < 6) {
                Client client2 = new Client(shop2);
                client2.start();
            }
            Client client3 = new Client(shop3);
            client3.start();
        }
    }
}