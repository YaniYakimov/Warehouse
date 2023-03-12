package main;

public class Product {
    private ProductType type;
    private ProductKind kind;
    public Product(ProductKind kind) {
        this.kind = kind;
        switch (kind) {
            case BANANA, ORANGE, APPLE -> this.type = ProductType.FRUITS;
            case BEEF, PORK, CHICKEN -> this.type = ProductType.MEATS;
            default -> this.type = ProductType.VEGETABLES;
        }
    }

    public ProductKind getKind() {
        return kind;
    }

    public ProductType getType() {
        return type;
    }
}
