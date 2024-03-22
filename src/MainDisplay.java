import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the main display GUI for the shopping application.
 */
public class MainDisplay extends JFrame {

    private JLabel label = new JLabel("Select product category: ");
    private JButton shoppingCartBtn = new JButton("Shopping Cart");
    private JButton addToCartBtn = new JButton("Add to Shopping Cart");
    private JTable productTable;
    private JPanel topPanel = new JPanel();
    private JPanel tablePanel = new JPanel();
    private JPanel bottomPanel = new JPanel();
    private JComboBox<String> filterBox;
    private JTextArea productDetailsTextArea;
    private DefaultTableModel productTableModel;
    private List<Product> systemProductList;
    private static Product selectedProduct;
    private ShoppingCartScreen shoppingCartWindow;

    /**
     * Constructs a MainDisplay for the specified user and shopping manager.
     *
     * @param user              The user for whom the display is created.
     * @param shoppingManager   The shopping manager containing the product list.
     */
    public MainDisplay(User user, WestminsterShoppingManager shoppingManager) {
        systemProductList = shoppingManager.getProductList();

        // Set background color to identify panels
        topPanel.setBackground(new Color(173, 211, 255));
        tablePanel.setBackground(new Color(131, 192, 252));
        bottomPanel.setBackground(new Color(105, 178, 250));

        // Implementing the Top Panel
        topPanel.setPreferredSize(new Dimension(0, 100));
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Creating the filter combo box in Top Panel
        String[] filters = {"All", "Electronics", "Clothing"};
        filterBox = new JComboBox<>(filters);
        filterBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateProductTable();
            }
        });

        // Creating the view shopping cart button
        shoppingCartBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check if shoppingCartWindow is null (not yet created)
                if (shoppingCartWindow == null) {
                    // Create an instance of the new window
                    shoppingCartWindow = new ShoppingCartScreen(user);
                }
                // Bring the shoppingCartWindow to the front (in case it was minimized)
                shoppingCartWindow.toFront();
            }
        });

        // Implementing the Table Panel
        tablePanel.setPreferredSize(new Dimension(800, 300));

        // Creating the product information table in Table Panel
        String[] columns = {"Product ID", "Name", "Category", "Info"};
        productTableModel = new DefaultTableModel(columns, 0);
        productTable = new JTable(productTableModel);
        JScrollPane scrollPane = new JScrollPane(productTable);

        // Implementing the Bottom Panel
        bottomPanel.setPreferredSize(new Dimension(100, 200));

        // Creating the text area to show product information
        productDetailsTextArea = new JTextArea();
        productDetailsTextArea.setEditable(false);

        // Implementing displaying information after selecting a product from the table
        productTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = productTable.getSelectedRow();
                    if (selectedRow != -1) {
                        // Get the product details from the selected row
                        String productId = (String) productTableModel.getValueAt(selectedRow, 0);

                        for (Product product : systemProductList) {
                            if (product.getProductId().equals(productId)) {

                                selectedProduct = product;

                                if (product instanceof Electronics) {
                                    productDetailsTextArea.setText(
                                            "Product ID: " + product.getProductId() + "\n" +
                                                    "Product type: Electronics \n" +
                                                    "Product name: " + product.getProductName() + "\n" +
                                                    "Product price: " + product.getItemPrice() + "\n" +
                                                    "Brand: " + ((Electronics) product).getBrand() + "\n" +
                                                    "Warranty period: " + ((Electronics) product).getWarrantyPeriod()+"\n" +
                                                    "Available amount: " + product.getNumOfAvailableItems());
                                }
                                else if (product instanceof Clothing) {
                                    productDetailsTextArea.setText(
                                            "Product ID: " + product.getProductId() + "\n" +
                                                    "Product type: Clothing \n" +
                                                    "Product name: " + product.getProductName() + "\n" +
                                                    "Product price: " + product.getItemPrice() + "\n" +
                                                    "Color: " +((Clothing) product).getColour() + "\n" +
                                                    "Size: " +((Clothing) product).getSize()+"\n"+
                                                    "Available amount: " + product.getNumOfAvailableItems());
                                }
                            }
                        }
                    }
                }
            }
        });

        // Creating the add to cart button
        addToCartBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addToShoppingCart();
            }
        });

        // Adding components to panels
        topPanel.add(label);
        topPanel.add(filterBox);
        topPanel.add(shoppingCartBtn);
        tablePanel.add(scrollPane);
        bottomPanel.add(productDetailsTextArea);
        bottomPanel.add(addToCartBtn);

        // Setting panel positions
        add(topPanel, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        this.pack();

        // Setting the frame properties
        setTitle("Westminster Shopping Centre");
        setSize(800, 600);
        setLayout(new BorderLayout());
        setVisible(true);
    }

    /**
     * Updates the product table based on the selected filter.
     */
    public void updateProductTable() {
        productTableModel.setRowCount(0);  // clear table

        String filter = (String) filterBox.getSelectedItem();

        for (Product product : systemProductList) {

            if (filter.equals("All") ||
                    (filter.equals("Electronics") && product instanceof Electronics) ||
                    (filter.equals("Clothing") && product instanceof Clothing)) {

                ArrayList<Object> row = new ArrayList<>();

                row.add(product.getProductId());

                if (product.getNumOfAvailableItems() < 3) {
                    row.add("<html><font color='red'>" + product.getProductName() + "</font></html>");
                } else {
                    row.add(product.getProductName());
                }

                row.add(product.getClass().getSimpleName());

                if (product instanceof Electronics) {
                    row.add(((Electronics) product).getBrand() + ", " + ((Electronics) product).getWarrantyPeriod() + " months");
                } else if (product instanceof Clothing) {
                    row.add(((Clothing) product).getSize() + ", " + ((Clothing) product).getColour());
                }

                // Convert the ArrayList to an array before adding to the tableModel
                productTableModel.addRow(row.toArray());
            }
        }
    }

    /**
     * Adds the selected product to the shopping cart.
     */
    public void addToShoppingCart() {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.addItem(selectedProduct);

        for (Product p : shoppingCart.getProducts()) {
            String productInfo;
            int quantity = 1; // Set Default quantity to 1
            double price = p.getItemPrice();

            if (p instanceof Electronics) {
                productInfo = p.getProductId() + "\n" + p.getProductName() + "\n" + ((Electronics) p).getBrand() + ", " + ((Electronics) p).getWarrantyPeriod();
            } else if (p instanceof Clothing) {
                productInfo = p.getProductId() + "\n" + p.getProductName() + "\n" + ((Clothing) p).getSize() + ", " + ((Clothing) p).getColour();
            } else {
                continue;
            }

            // Check if the product is already in the shopping cart
            boolean found = false;
            for (int i = 0; i < shoppingCartWindow.shoppingTableModel.getRowCount(); i++) {
                String existingProductInfo = (String) shoppingCartWindow.shoppingTableModel.getValueAt(i, 0);
                if (existingProductInfo.equals(productInfo)) {
                    found = true;
                    // Update the quantity and price
                    quantity = (int) shoppingCartWindow.shoppingTableModel.getValueAt(i, 1) + 1;
                    price = (double) shoppingCartWindow.shoppingTableModel.getValueAt(i, 2) + p.getItemPrice();
                    shoppingCartWindow.calculateTotal();
                    // Update the row in the table
                    shoppingCartWindow.shoppingTableModel.setValueAt(quantity, i, 1);
                    shoppingCartWindow.shoppingTableModel.setValueAt(price, i, 2);
                    break;
                }
            }

            if (!found) {
                // If the product is not already in the shopping cart, add a new row
                shoppingCartWindow.addToCart(productInfo, quantity, price);
            }
        }
    }
}
