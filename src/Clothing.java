public class Clothing extends Product {
    private String size;
    private String colour;

    public Clothing(String productID, String productName, int numOfAvailableItems, double itemPrice, String size, String color) {
        super(productID, productName, numOfAvailableItems, itemPrice);
        this.size = size;
        this.colour = color;
    }

    public String getSize() {
        return size;
    }

    public String getColour() {
        return colour;
    }

    @Override
    public void displayDetails() {
        System.out.println("Category -> Clothing");
        System.out.println("Product ID -> " + getProductId());
        System.out.println("Product Name -> " + getProductName());
        System.out.println("Number of Available items -> " + getNumOfAvailableItems());
        System.out.println("Price -> Rs." + getItemPrice()+" /=");
        System.out.println("Size -> " + size);
        System.out.println("Colour -> " + colour);
    }
}
