public interface ShoppingManager {

    void addNewProduct(Product product);
    void deleteProduct(String productID);
    void sortProducts();
    void saveDataToFile();
    void loadDataFromFile();

}
