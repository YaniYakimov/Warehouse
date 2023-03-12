package main;

import java.util.*;

public class Storage {
    private int minimumThreshold = 5;
    private int numberToBeTaken = 5;
    private Map<ProductType, Map<ProductKind, Set<Product>>> products = new HashMap<>();
    public Storage() {
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
        System.out.println("Now 15 of each product are available into the Storage.");
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

    public synchronized void putProduct(){
        for(Map.Entry<ProductType, Map<ProductKind, Set<Product>>> entry : this.products.entrySet()) {
            for(Map.Entry<ProductKind, Set<Product>> entry1 : entry.getValue().entrySet()) {
                while (entry1.getValue().size() > minimumThreshold) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                Optional<Product> p =  this.products.get(entry.getKey()).get(entry1.getKey()).stream().findFirst();
                for (int i = 0; i < 25; i++) {
                    this.products.get(entry.getKey()).get(entry1.getKey()).add(new Product(p.get().getKind()));
                    System.out.println("Supplier just added new " + p.get().getKind() + " to the storage.");
                }
            }
        }
        notifyAll();
    }
    public synchronized Product takeProduct(Product p){
        while (this.products.get(p.getType()).get(p.getKind()).size() < minimumThreshold) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        Optional<Product> product = this.products.get(p.getType()).get(p.getKind()).stream().findFirst();
        this.products.get(p.getType()).get(p.getKind()).remove(product);
        notifyAll();
        System.out.println("Shop owner just take " + product.get().getKind() + " from Storage.");
        return product.get();
    }
}
