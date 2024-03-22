import java.util.*;

public class Main {
    private static WestminsterShoppingManager wstmnstrShoppingManager = new WestminsterShoppingManager();

    public static void main(String[] args) {
        displayMenu();
    }

    public static void displayMenu() {
        System.out.println("\n>>>>> Westminster Online Shopping Managing System <<<<<\n");

        Scanner input = new Scanner(System.in);
        int selection = -1; // Initialize choice to a value that ensures the loop starts

        while (selection != 0){
            System.out.println("\n################################");
            System.out.println("############# MENU #############");
            System.out.println("################################\n");
            System.out.println("<1> - Add new product to system");
            System.out.println("<2> - Delete product from the system");
            System.out.println("<3> - Print the list of products");
            System.out.println("<4> - Save data to a file");
            System.out.println("<5> - Load data from file");
            System.out.println("<6> - Open GUI");
            System.out.println("<0> - Exit programme");
            System.out.print("\nEnter your selection: ");
            // Check if the input is an integer
            if (input.hasNextInt()) {
                selection = input.nextInt();
                input.nextLine(); // Consume the newline character left by nextInt()
                System.out.println();

                switch(selection) {
                    case 1:
                        addProductToSystem();
                        break;
                    case 2:
                        //deleting the selected product
                        input = new Scanner(System.in);

                        System.out.print("Enter product ID of the item you want to delete: ");
                        String deletingProductID = input.nextLine();

                        // Pass the selected product ID to deleteProduct method in WestminsterShoppingManager class
                        wstmnstrShoppingManager.deleteProduct(deletingProductID);
                        break;
                    case 3:
                        wstmnstrShoppingManager.sortProducts();
                        break;
                    case 4:
                        wstmnstrShoppingManager.saveDataToFile();
                        break;
                    case 5:
                        wstmnstrShoppingManager.loadDataFromFile();
                        break;
                    case 6:
                        openGUI();
                        break;
                    case 0:
                        System.out.println("Exiting the system...");
                        break;
                    default:
                        System.out.println("Invalid input, please enter a valid input.\n");
                        break;
                }
            } else {
                System.out.println("Invalid input. Please enter a number.\n");
                input.nextLine(); // Consume the invalid input to avoid an infinite loop
            }
        }
    }

    /*
     * Adds a new product to the shopping managing system
     */
    public static void addProductToSystem() {

        Scanner input = new Scanner(System.in);
        int productCategory = 0;
        String brand;
        int warrantyPeriod = 0;
        String size;
        String colour;

        while (true) {
            try {
                System.out.println("<1> - Add an Electronic item to the system");
                System.out.println("<2> - Add a Clothing item to the system");
                System.out.print("Select the type of the product you need to add: ");
                productCategory = input.nextInt();

                if (productCategory != 1 && productCategory != 2) {
                    System.out.println("Invalid input, please enter a valid input.");
                } else {
                    break;
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                input.nextLine(); // Clear the buffer
            }
            System.out.println();
        }

        String productID = validateProductID();
        String productName = validateProductName();
        int numOfAvailableItems = validateNumOfAvailableItems();
        double itemPrice = validateItemPrice();


        if (productCategory == 1) {
            input.nextLine();
            while(true) {
                System.out.print("Enter the brand: ");
                brand = input.nextLine();
                if (brand.isEmpty()) {
                    System.out.println("Invalid input. Please do not keep the brand name empty");
                }
                else{
                    break;
                }
            }


            while(true) {
                try {
                    System.out.print("Enter the warranty period in months: ");
                    warrantyPeriod = input.nextInt();
                    input.nextLine();
                    break;
                } catch (Exception e) {
                    System.out.println("Invalid input, please enter a valid input.");
                }
                input.nextLine();
            }

            // Creating a new Electronic object to add the entered details
            Electronics electronicItem = new Electronics(productID, productName, numOfAvailableItems, itemPrice, brand, warrantyPeriod);
            wstmnstrShoppingManager.addNewProduct(electronicItem);

        } else {
            input.nextLine();
            while(true) {
                System.out.print("Enter the size: ");
                size = input.nextLine();
                if (size.isEmpty()) {
                    System.out.println("Invalid input. Please do not keep the size empty");
                } else {
                    break;
                }
            }

            while(true) {
                System.out.print("Enter the colour: ");
                colour = input.nextLine();
                if (colour.isEmpty()) {
                    System.out.println("Invalid input. Please do not keep the colour empty");
                } else {
                    break;
                }
            }

            // Creating a new Clothing object to add the entered details
            Clothing clothingItem = new Clothing(productID, productName, numOfAvailableItems, itemPrice, size, colour);
            wstmnstrShoppingManager.addNewProduct(clothingItem);
        }
    }

    /*
     * Method to validate and obtain the product ID from the user
     */
    public static String validateProductID() {
        Scanner input = new Scanner(System.in);
        String productId;
        while(true) {
            System.out.print("\nEnter the product ID: ");
            productId = input.nextLine();
            if (!wstmnstrShoppingManager.checkUniqueId(productId)) {
                System.out.println("This product ID already exists. Enter a unique ID.");
            }
            else {
                break;
            }
        }
        return productId;
    }
    /*
     * Method to validate and obtain the product name from the user
     */
    public static String validateProductName() {
        Scanner input = new Scanner(System.in);
        String productName;
        while(true) {
            System.out.print("Enter the name of the product: ");
            productName = input.nextLine();
            if (productName.isEmpty()) {
                System.out.println("Please enter a product name. Do not keep it empty.");
            } else {
                break;
            }
        }
        return productName;
    }

    /*
     * Method to validate and obtain the available number of items in the system from the user
     */
    public static int validateNumOfAvailableItems() {
        Scanner input = new Scanner(System.in);
        int numOfAvailableItems;
        while(true) {
            try {
                System.out.print("Enter the number of available items: ");
                numOfAvailableItems = input.nextInt();
                break;
            } catch (Exception e) {
                System.out.println("Invalid input, please enter a valid input.");
            }
            input.nextLine();
        }
        return numOfAvailableItems;
    }

    /*
     * Method to validate and obtain the price of the product from the user
     */
    public static double validateItemPrice() {
        Scanner input = new Scanner(System.in);
        double itemPrice;
        while(true) {
            try {
                System.out.print("Enter the price of the item: ");
                itemPrice = input.nextDouble();
                break;
            } catch (Exception e) {
                System.out.println("Invalid input, please enter a valid input.");
            }
            input.nextLine();
        }
        return itemPrice;
    }

    public static void openGUI() {
        Scanner scanner = new Scanner(System.in);
        String option;
        String userName;
        String password;
        User newUser;

        ArrayList<String> usernameHistory = User.getPreviousUsernames();

        System.out.println("#######################");
        System.out.println("## Login -or- Signup ##");
        System.out.println("#######################\n");

        System.out.println("<1> Log in");
        System.out.println("<2> Sign Up");
        System.out.print("Select option: ");
        option = scanner.nextLine();

        switch (option) {
            case "1":
                while (true) {
                    System.out.print("Enter the username: ");
                    userName = scanner.nextLine();

                    if (usernameHistory.contains(userName)) {
                        System.out.print("Enter the password: ");
                        password = scanner.nextLine();
                        newUser = new User(userName, password);
                        new MainDisplay(newUser, wstmnstrShoppingManager);
                        break;
                    } else {
                        System.out.println("User name doesn't exist. Sign Up");
                        break;
                    }
                }
                break;
            case "2":
                while (true) {
                    System.out.print("Enter the username: ");
                    userName = scanner.nextLine();

                    if (usernameHistory.contains(userName)) {
                        System.out.println("That username already exists. Enter a new username");
                    } else {
                        break;
                    }
                }

                System.out.print("Enter the password: ");
                password = scanner.nextLine();

                newUser = new User(userName, password);
                new MainDisplay(newUser, wstmnstrShoppingManager);
                break;
            default:
                System.out.println("Error...");
                break;
        }
        System.out.println();
    }
}