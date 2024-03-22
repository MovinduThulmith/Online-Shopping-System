import java.io.*;
import java.util.*;

public class WestminsterShoppingManager implements ShoppingManager {
    private static final int maximumNumOfProducts = 50;
    protected ArrayList<Product> listOfProducts;

    public WestminsterShoppingManager() {
        listOfProducts = new ArrayList<>();
    }

    /**
     * Adds a new product to the available product list
     * @param newProduct a new product object with new product details
     */
    @Override
    public void addNewProduct(Product newProduct) {
        // Check if the maximum number of products has been reached
        if (listOfProducts.size() < maximumNumOfProducts) {
            listOfProducts.add(newProduct);
            System.out.println("\nThe Product was added successfully");
        }
        else {
            System.out.println("\nThe product capacity exceeded. Cannot add new products.");
        }
        System.out.println("There are " + listOfProducts.size() + " products in the system.\n");
    }

    /**
     * Delete a product selected by the user
     * @param productID Product ID of the deleting product
     */
    @Override
    public void deleteProduct(String productID) {
        for (Product item : listOfProducts) {
            if (item.getProductId().equals(productID)) {
                listOfProducts.remove(item);
                System.out.println("\nThe product (product ID- " + productID + ") was deleted successfully.\nNumber of items left in system: " + listOfProducts.size());
                item.displayDetails(); // Displaying details about the deleting product
                return;
            }
        }System.out.println("The product " + productID + " is not in the system.\n");
    }

    //Saving the data to a text file
    @Override
    public void saveDataToFile() {
        try (FileWriter fileWriter = new FileWriter("data_file.txt")) {

            // Header for the file
            fileWriter.write("####################################\n");
            fileWriter.write("Westminster Shopping Managing System\n");
            fileWriter.write("####################################\n\n");

            for (Product item : listOfProducts) {
                if (item instanceof Electronics) {
                    // Writing to the text file
                    // Format: "Category-Electronics-productId-productName-itemPrice-numOfAvailableItems-brand-warrantyPeriod"
                    fileWriter.write("Category-Electronics-" + item.getProductId() + "-" + item.getProductName() + "-" + item.getItemPrice() + "-" + item.getNumOfAvailableItems() + "-" +
                            ((Electronics) item).getBrand() + "-" +
                            ((Electronics) item).getWarrantyPeriod() + "\n");
                } else if (item instanceof Clothing) {
                    // Format: "Category-Clothing-productId-productName-itemPrice-numOfAvailableItems-colour-size"
                    fileWriter.write("Category-Clothing-" + item.getProductId() + "-" + item.getProductName() + "-" + item.getItemPrice() + "-" + item.getNumOfAvailableItems() + "-" +
                            ((Clothing) item).getColour() + "-" +
                            ((Clothing) item).getSize() + "\n");
                }
            }
            System.out.println("Data saved to file successfully.");
        } catch (IOException e) {
            System.out.println("Error..");
        }
    }

    /*
     * Loads the data from the text file to the system
     */
    @Override
    public void loadDataFromFile() {

        //Clear the products in the system
        listOfProducts.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader("data_file.txt"))) {
            String selectedLine;

            // Read data from the file, line by line using BufferedReader
            while ((selectedLine = reader.readLine()) != null) {
                // Adding the data to a string array
                String[] productData = selectedLine.split("-");

                if ((productData.length == 8) && (productData[1].equals("Electronics"))) {
                    String productId = productData[2];
                    String productName = productData[3];
                    double itemPrice = Double.parseDouble(productData[4]);
                    int numOfAvailableItems = Integer.parseInt(productData[5]);
                    String brand = productData[6];
                    int warrantyPeriod = Integer.parseInt(productData[7]);

                    // Declaring an Electronics object and passing the values taken from the file using the constructor
                    Electronics electronicProduct = new Electronics(productId, productName, numOfAvailableItems, itemPrice, brand, warrantyPeriod);
                    listOfProducts.add(electronicProduct);
                }
                if ((productData.length == 8) && (productData[1].equals("Clothing"))) {
                    String productId = productData[2];
                    String productName = productData[3];
                    double itemPrice = Double.parseDouble(productData[4]);
                    int numOfAvailableItems = Integer.parseInt(productData[5]);
                    String colour = productData[6];
                    String size = productData[7];

                    // Declaring a Clothing object and passing the values taken from the file using the constructor
                    Clothing clothingProduct = new Clothing(productId, productName, numOfAvailableItems, itemPrice, size, colour);
                    listOfProducts.add(clothingProduct);
                }
            }
        } catch (IOException e) {
            System.out.println("Error...");
        }
        System.out.println("Text file data loaded to the system successfully");
    }

    /**
     * Checks if the entered product ID already exists in the system
     * @param productId Entered product ID
     * @return Ture if the ID is unique, False if exists
     */
    public boolean checkUniqueId(String productId) {
        for (Product item : listOfProducts) {
            if (item.getProductId().equals(productId)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void sortProducts() {
        if (listOfProducts.isEmpty()) {
            System.out.println("There are no products.");
            return;
        }
        // Sort the productList alphabetically by product name using Comparator interface with a lambda expression
        listOfProducts.sort(Comparator.comparing(Product::getProductName));

        for (Product product : listOfProducts) {
            product.displayDetails();
            System.out.println("--------------------------\n");
        }
    }

    public ArrayList<Product> getProductList() {
        return listOfProducts;
    }
}


/*
REFERENCE
https://stackoverflow.com/questions/23701943/sorting-arraylist-with-lambda-in-java-8
 */
