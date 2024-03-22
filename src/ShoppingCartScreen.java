import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents the Shopping Cart Screen GUI for the user.
 */
public class ShoppingCartScreen extends JFrame {

    public DefaultTableModel shoppingTableModel;
    private JTable table;
    private JPanel top_panel = new JPanel();
    private JPanel bottom_panel = new JPanel();
    private JLabel textLbl1, textLbl2, textLbl3, textLbl4;
    private JLabel totalPrice, discount1, discount2, finalTotal;
    private JButton checkoutButton;
    private User client;

    /**
     * Constructs a ShoppingCartScreen for the specified user.
     *
     * @param client The user for whom the shopping cart is created.
     */
    public ShoppingCartScreen(User client) {
        this.client = client;

        top_panel.setBackground(new Color(173, 211, 255));
        bottom_panel.setBackground(new Color(127, 184, 252));

        // Implementing the Top Panel
        top_panel.setPreferredSize(new Dimension(0, 300));

        // Creating the shopping cart table
        String[] columns = {"Products", "Quantity", "Price"};
        shoppingTableModel = new DefaultTableModel(columns, 0);
        table = new JTable(shoppingTableModel);
        JScrollPane scroll = new JScrollPane(table);

        // Implementing the Bottom Panel
        bottom_panel.setPreferredSize(new Dimension(0, 300));
        bottom_panel.setLayout(new GridLayout(5, 2));

        // Displaying the price information
        textLbl1 = new JLabel("Total: ");
        totalPrice = new JLabel("totalPrice");
        textLbl2 = new JLabel("First Purchase Discount (10%): ");
        discount1 = new JLabel("discount_1");
        textLbl3 = new JLabel("Three items in the same category discount (20%): ");
        discount2 = new JLabel("discount_2");
        textLbl4 = new JLabel("Final total: ");
        finalTotal = new JLabel("finalTotal");

        // Creating the checkout button
        checkoutButton = new JButton("Checkout");
        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean userFound = false;

                for (String username : User.getPreviousUsernames()) {
                    if (username.equals(client.getUsername())) {
                        userFound = true;
                        break; // No need to continue checking if the user is already found
                    }
                }

                if (!userFound) {
                    // Add the user to the purchase history
                    client.addToBuyingHistory();
                }

                // Reset the shopping cart
                shoppingTableModel.setRowCount(0);
                totalPrice.setText("0");
                discount1.setText("0");
                discount2.setText("0");
                finalTotal.setText("0");
            }
        });

        // Adding components
        top_panel.add(scroll);
        bottom_panel.add(textLbl1);
        bottom_panel.add(totalPrice);
        bottom_panel.add(textLbl2);
        bottom_panel.add(discount1);
        bottom_panel.add(textLbl3);
        bottom_panel.add(discount2);
        bottom_panel.add(textLbl4);
        bottom_panel.add(finalTotal);
        bottom_panel.add(checkoutButton);

        add(top_panel, BorderLayout.NORTH);
        add(bottom_panel, BorderLayout.CENTER);

        // Setting the frame properties
        setTitle("Shopping Cart");
        setSize(800, 600);
        setVisible(true);
    }

    /**
     * Calculates the total price of items in the shopping cart and updates the corresponding label.
     */
    public void calculateTotal() {
        double totalPrice = 0.0;
        int rows = shoppingTableModel.getRowCount();
        for (int i = 0; i < rows; i++) {
            double price = (double) shoppingTableModel.getValueAt(i, 2);
            totalPrice += price;
        }
        this.totalPrice.setText(String.format("%.2f", totalPrice));
    }

    /**
     * Checks if the user is eligible for a 10% discount and updates the discount label accordingly.
     */
    private void added10Discount() {
        for (String username : User.getPreviousUsernames()) {
            if (username.equals(client.getUsername())) {
                // No discount for users with a purchase history
                discount1.setText("0");
            } else {
                calculate10Discount();
            }
        }
    }

    /**
     * Checks if the user is eligible for a 20% discount based on the quantity of items in the same category.
     */
    private void added20Discount() {
        if (has3SameCategory()) {
            System.out.println("Has 3");
        }
    }

    /**
     * Calculates a 10% discount and updates the discount label.
     */
    private void calculate10Discount() {
        // Calculate the discount
        double discount10Percent = Double.parseDouble(totalPrice.getText()) * 0.1;
        discount1.setText(String.format("%.2f", discount10Percent));
    }

    /**
     * Checks if the shopping cart has at least three items in the same category.
     *
     * @return True if there are three or more items in the same category, false otherwise.
     */
    public boolean has3SameCategory() {
        DefaultTableModel tableModel = shoppingTableModel;

        Map<String, Integer> counts = new HashMap<>();

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String productInfo = (String) tableModel.getValueAt(i, 0);

            String category = getCategory(productInfo);

            int count = counts.getOrDefault(category, 0);
            counts.put(category, count + 1);
        }

        return counts.values().stream()
                .anyMatch(count -> count >= 3);
    }

    /**
     * Extracts the category of a product from its information.
     *
     * @param productInfo The information of the product.
     * @return The category of the product.
     */
    String getCategory(String productInfo) {
        if (productInfo.contains("Electronics")) {
            return "Electronics";
        } else {
            return "Clothing";
        }
    }

    /**
     * Adds a row to the cart table, representing a product added to the shopping cart.
     *
     * @param product  The name of the product.
     * @param quantity The quantity of the product.
     * @param price    The price of the product.
     */
    public void addToCart(String product, int quantity, double price) {
        Object[] row = {product, quantity, price};
        shoppingTableModel.addRow(row);
        calculateTotal();
        added10Discount();
        added20Discount();
    }
}
