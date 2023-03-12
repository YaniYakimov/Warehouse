package main;

public class ShopOwner extends Thread{
    Storage storage;
    Shop shop;
    public ShopOwner(Storage storage, Shop shop) {
        this.storage = storage;
        this.shop = shop;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("Zapochvam rabota!");
            Product p = this.shop.findMissingProduct();
            Product product = this.storage.takeProduct(p);
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            this.shop.loadProduct(product);
        }
    }
}
