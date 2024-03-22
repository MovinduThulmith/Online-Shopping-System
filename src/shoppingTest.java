import org.junit.Test;
import static org.junit.Assert.*;

public class shoppingTest {

    @Test
    public void testAddNewProduct() {
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();

        // Creating a sample electronic product
        Electronics electronicProduct = new Electronics("123", "Laptop", 10, 1000.0, "Lenovo", 12);

        // Adding the product to the system
        shoppingManager.addNewProduct(electronicProduct);

        // Checking if the product is added successfully
        assertTrue(shoppingManager.listOfProducts.contains(electronicProduct));
    }

    @Test
    public void testDeleteProduct() {
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();

        // Creating a sample clothing product
        Clothing clothingProduct = new Clothing("234", "Shirt", 20, 20.0, "M", "Blue");

        // Adding the product to the system
        shoppingManager.addNewProduct(clothingProduct);

        // Deleting the product from the system
        shoppingManager.deleteProduct("234");

        // Checking if the product is deleted successfully
        assertFalse(shoppingManager.listOfProducts.contains(clothingProduct));
    }

    @Test
    public void testCheckUniqueId() {
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();

        // Creating a sample electronic product
        Electronics electronicProduct = new Electronics("123", "Laptop", 10, 1000.0, "Apple", 12);

        // Adding the product to the system
        shoppingManager.addNewProduct(electronicProduct);

        // Checking if the unique ID check works
        assertFalse(shoppingManager.checkUniqueId("123")); // ID already exists
        assertTrue(shoppingManager.checkUniqueId("456"));  // New unique ID
    }

    @Test
    public void testSortProducts() {
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();

        // Creating sample products
        Electronics electronicProduct = new Electronics("123", "Laptop", 10, 1000.0, "Apple", 12);
        Clothing clothingProduct = new Clothing("567", "Blouse", 20, 20.0, "M", "Blue");

        // Adding products to the system
        shoppingManager.addNewProduct(electronicProduct);
        shoppingManager.addNewProduct(clothingProduct);

        // Sorting products
        shoppingManager.sortProducts();

        // Checking if the blouse is listed before laptop
        assertEquals("567", shoppingManager.listOfProducts.get(0).getProductId());
        assertEquals("123", shoppingManager.listOfProducts.get(1).getProductId());
    }
    @Test
    public void testDeleteNonExistingProduct() {
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();

        //Adding a new product
        Electronics electronicProduct = new Electronics("123", "Phone", 10, 1000.0, "Apple", 12);
        shoppingManager.addNewProduct(electronicProduct);

        // Deleting a non-existing product
        shoppingManager.deleteProduct("456");

        // Checking if nothing was deleted
        assertEquals(1, shoppingManager.listOfProducts.size());
    }
    @Test
    public void testAddProductAtMaximumCapacity() {
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();

        // Adding maximum number of products (50)
        for (int i = 1; i <= 50; i++) {
            String productId = "P" + i;
            String productName = "Product" + i;
            double itemPrice = 20.0 * i;
            int numOfAvailableItems = 10 * i;

            // Create a sample electronic product
            Electronics electronicProduct = new Electronics(productId, productName, numOfAvailableItems, itemPrice, "Brand" + i, 12);

            // Add the product to the system
            shoppingManager.addNewProduct(electronicProduct);
        }

        // Attempting to add one more product when the system is at maximum capacity
        String newProductId = "P51";
        String newProductName = "PlayStation";
        double newItemPrice = 10000.0;
        int newNumOfAvailableItems = 5;

        // Create a sample electronic product
        Electronics newElectronicProduct = new Electronics(newProductId, newProductName, newNumOfAvailableItems, newItemPrice, "Sony", 24);

        // Adding the new product
        shoppingManager.addNewProduct(newElectronicProduct);

        // Checking if the new product is not added since the system is at maximum capacity
        assertEquals(50, shoppingManager.listOfProducts.size());
        assertFalse(shoppingManager.listOfProducts.contains(newElectronicProduct));
    }

}

