public abstract class Product {
    private String productId;
    private String productName;
    private int numOfAvailableItems;
    private double itemPrice;

    public Product(String productId, String productName, int numOfAvailableItems, double itemPrice) {
        this.productId = productId;
        this.productName = productName;
        this.numOfAvailableItems = numOfAvailableItems;
        this.itemPrice = itemPrice;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getNumOfAvailableItems() {
        return numOfAvailableItems;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public abstract void displayDetails();

}
