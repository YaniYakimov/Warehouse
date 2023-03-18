package main;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Shop {
    private int minimumThreshold = 14;
    private int capacity = 15;
    private ConcurrentHashMap<ProductType, Map<ProductKind, Set<Product>>> products = new ConcurrentHashMap<>();
//    private Map<ProductType, Map<ProductKind, Set<Product>>> products = new HashMap<>();
    public Shop() {
        for (int i = 0; i < 15; i++) {
            Product banana = new Product(ProductKind.BANANA);
            Product orange = new Product(ProductKind.ORANGE);
            Product apple = new Product(ProductKind.APPLE);
            Product potato = new Product(ProductKind.POTATO);
            Product eggPlant = new Product(ProductKind.EGGPLANT);
            Product cucumber = new Product(ProductKind.CUCUMBER);
            Product pork = new Product(ProductKind.PORK);
            Product beef = new Product(ProductKind.BEEF);
            Product chicken = new Product(ProductKind.CHICKEN);
            addProduct(banana);
            addProduct(orange);
            addProduct(apple);
            addProduct(potato);
            addProduct(eggPlant);
            addProduct(cucumber);
            addProduct(pork);
            addProduct(beef);
            addProduct(chicken);
        }
        System.out.println("Now 15 of each product are available into the Shop.");
    }

    private void addProduct(Product product) {
        if(!(this.products.containsKey(product.getType()))) {
            this.products.put(product.getType(), new HashMap<>());
        }
        if(!(this.products.get(product.getType()).containsKey(product.getKind()))) {
            this.products.get(product.getType()).put(product.getKind(), new HashSet<>());
        }
        this.products.get(product.getType()).get(product.getKind()).add(product);
    }
    public synchronized void loadProduct(Product p){
        for(Map.Entry<ProductType, Map<ProductKind, Set<Product>>> entry : this.products.entrySet()) {
            for(Map.Entry<ProductKind, Set<Product>> entry1 : entry.getValue().entrySet()) {
                while (entry1.getValue().size() > minimumThreshold) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                this.products.get(entry.getKey()).get(entry1.getKey()).add(p);
                System.out.println("Shop owner just added new " + p.getKind() + " to the shop.");
            }
        }
        notifyAll();
    }
    public synchronized void cellProduct(ProductKind kind, int numberForCell, ProductType type){
        while (this.products.get(type).get(kind).size() < numberForCell) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        Optional<Product> product = this.products.get(type).get(kind).stream().findFirst();
        this.products.get(type).get(kind).remove(product.get());
        notifyAll();
        System.out.println("Client just bought " + product.get().getKind());
        System.out.println("Now the shop has " + this.products.get(type).get(kind).size() + " - " + product.get().getKind() + " in stock.");
    }

    public synchronized Product findMissingProduct() {
        Optional<Product> p = null;
        for(Map.Entry<ProductType, Map<ProductKind, Set<Product>>> entry : this.products.entrySet()) {
            for(Map.Entry<ProductKind, Set<Product>> entry1 : entry.getValue().entrySet()) {
                while (entry1.getValue().size() > minimumThreshold) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                p = entry1.getValue().stream().findFirst();
            }
        }
        return p.get();
    }
}
