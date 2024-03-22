import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private List<Product> products;

    public ShoppingCart() {
        this.products = new ArrayList<>();
    }

    public void addItem(Product product) {
        products.add(product);
        System.out.println("Item added to the cart.");
    }
    public List<Product> getProducts() {
        return products;
    }

}
