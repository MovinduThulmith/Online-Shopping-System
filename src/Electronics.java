public class Electronics extends Product{
    private String brand;
    private int warrantyPeriod;

    public Electronics(String productID, String productName, int numOfAvailableItems, double itemPrice, String brand, int warrantyPeriod) {
        super(productID, productName, numOfAvailableItems, itemPrice);
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }

    public String getBrand() {
        return brand;
    }

    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }

    @Override
    public void displayDetails() {
        System.out.println("Category -> Electronics");
        System.out.println("Product ID -> " + getProductId());
        System.out.println("Product Name -> " + getProductName());
        System.out.println("Number of Available items -> " + getNumOfAvailableItems());
        System.out.println("Price -> Rs." + getItemPrice()+" /=");
        System.out.println("Brand -> " + brand);
        System.out.println("Period of warranty -> " + warrantyPeriod + " months");
    }
}
