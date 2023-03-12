package main;

import java.util.Random;

public class Client extends Thread{
    Shop shop;
    public Client(Shop shop) {
        this.shop = shop;
    }

    @Override
    public void run() {
        while (true) {
            ProductKind kind = null;
            ProductType type = null;
            int numberOfProducts = new Random().nextInt(1, 5);
            int chanceType = new Random().nextInt(3);
            int chanceKind = new Random().nextInt(3);
            switch (chanceType) {
                case 0 -> {
                    type = ProductType.FRUITS;
                    switch (chanceKind) {
                        case 0 -> kind = ProductKind.BANANA;
                        case 1 -> kind = ProductKind.ORANGE;
                        default -> kind = ProductKind.APPLE;
                    }
                }
                case 1 -> {
                    type = ProductType.VEGETABLES;
                    switch (chanceKind) {
                        case 0 -> kind = ProductKind.POTATO;
                        case 1 -> kind = ProductKind.EGGPLANT;
                        default -> kind = ProductKind.CUCUMBER;
                    }
                }
                default -> {
                    type = ProductType.MEATS;
                    switch (chanceKind) {
                        case 0 -> kind = ProductKind.PORK;
                        case 1 -> kind = ProductKind.BEEF;
                        default -> kind = ProductKind.CHICKEN;
                    }
                }
            }
            for (int i = 0; i < numberOfProducts; i++) {
                shop.cellProduct(kind, numberOfProducts, type);
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
