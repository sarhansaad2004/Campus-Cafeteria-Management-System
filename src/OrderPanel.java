import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class OrderPanel extends JPanel {

    // Form Fields
    private JTextField orderIdField;
    private JTextField customerNameField;
    private JTextField quantityField;
    private JTextField totalPriceField;
    private JTextField searchField;

    // Combo Box
    private JComboBox<String> foodComboBox;

    // Cart Area
    private JTextArea cartArea;

    // Buttons
    private JButton addToCartButton;
    private JButton placeOrderButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton clearButton;

    // Table
    private JTable orderTable;
    private DefaultTableModel tableModel;

    // Managers
    private OrderManager orderManager;
    private FoodItemManager foodManager;

    // Cart Variables
    private String orderedItems = "";
    private String cartDetails = "";
    private double grandTotal = 0;

    // Constructor
    public OrderPanel() {

        orderManager = new OrderManager();
        foodManager = new FoodItemManager();

        setLayout(new BorderLayout(10, 10));

        // =========================
        // Form Panel
        // =========================
        JPanel formPanel =
                new JPanel(new GridLayout(6, 2, 8, 8));

        formPanel.setBorder(
                BorderFactory.createTitledBorder(
                        "Order Information"
                )
        );

        // Fields
        orderIdField = new JTextField();

        customerNameField = new JTextField();

        quantityField = new JTextField();

        totalPriceField = new JTextField();
        totalPriceField.setEditable(false);

        searchField = new JTextField();

        // ComboBox
        foodComboBox = new JComboBox<String>();

        loadFoodItemsIntoComboBox();

        // Cart Area

        cartArea = new JTextArea(2, 20);


        cartArea.setEditable(false);

        cartArea.setLineWrap(true);

        cartArea.setWrapStyleWord(true);


        JScrollPane cartScrollPane =
                new JScrollPane(cartArea);
        cartScrollPane.setPreferredSize(
                new Dimension(400, 120)
        );

        // Add Components
        formPanel.add(new JLabel("Order ID:"));
        formPanel.add(orderIdField);

        formPanel.add(new JLabel("Customer Name:"));
        formPanel.add(customerNameField);

        formPanel.add(new JLabel("Select Food:"));
        formPanel.add(foodComboBox);

        formPanel.add(new JLabel("Quantity:"));
        formPanel.add(quantityField);

        formPanel.add(new JLabel("Grand Total:"));
        formPanel.add(totalPriceField);

        formPanel.add(new JLabel("Search Order:"));
        formPanel.add(searchField);


        add(formPanel, BorderLayout.NORTH);
        // =========================
// Cart Panel
// =========================
        JPanel cartPanel =
                new JPanel(new BorderLayout());

        cartPanel.setBorder(
                BorderFactory.createTitledBorder("Cart")
        );

        cartPanel.add(cartScrollPane);

        add(cartPanel, BorderLayout.WEST);

        // =========================
        // Table
        // =========================
        String[] columns = {

                "Order ID",
                "Customer Name",
                "Ordered Items",
                "Grand Total"
        };

        tableModel =
                new DefaultTableModel(columns, 0);

        orderTable = new JTable(tableModel);

        orderTable.setRowHeight(25);

        JScrollPane scrollPane =
                new JScrollPane(orderTable);

        add(scrollPane, BorderLayout.CENTER);
        scrollPane.setPreferredSize(
                new Dimension(800, 300)
        );

        // Load Saved Orders
        loadTableData();

        // =========================
        // Button Panel
        // =========================
        JPanel buttonPanel =
                new JPanel(new FlowLayout());

        addToCartButton =
                new JButton("Add To Cart");

        placeOrderButton =
                new JButton("Place Order");

        updateButton =
                new JButton("Update");

        deleteButton =
                new JButton("Delete");

        clearButton =
                new JButton("Clear");

        buttonPanel.add(addToCartButton);
        buttonPanel.add(placeOrderButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // =========================
        // Add To Cart
        // =========================
        addToCartButton.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        try {

                            String selectedFood =
                                    (String) foodComboBox.getSelectedItem();

                            int quantity =
                                    Integer.parseInt(
                                            quantityField.getText()
                                    );

                            ArrayList<FoodItem> foodList =
                                    foodManager.getAllFoodItems();

                            for (int i = 0;
                                 i < foodList.size();
                                 i++) {

                                FoodItem foodItem =
                                        foodList.get(i);

                                if (foodItem.getFoodName()
                                        .equals(selectedFood)) {

                                    double itemTotal =
                                            foodItem.getPrice() * quantity;

                                    grandTotal =
                                            grandTotal + itemTotal;

                                    orderedItems =
                                            orderedItems +
                                                    selectedFood +
                                                    ", ";

                                    cartDetails =
                                            cartDetails +
                                                    selectedFood +
                                                    " x" +
                                                    quantity +
                                                    " = " +
                                                    itemTotal +
                                                    " (Per unit price " +
                                                    foodItem.getPrice() +
                                                    ")\n";

                                    cartArea.setText(cartDetails);

                                    totalPriceField.setText(
                                            String.valueOf(grandTotal)
                                    );
                                }
                            }

                            quantityField.setText("");

                        } catch (Exception ex) {

                            JOptionPane.showMessageDialog(
                                    OrderPanel.this,
                                    "Invalid Quantity!"
                            );
                        }
                    }
                });

        // =========================
        // Place Order
        // =========================
        placeOrderButton.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        try {

                            if (orderIdField.getText()
                                    .trim()
                                    .isEmpty() ||

                                    customerNameField.getText()
                                            .trim()
                                            .isEmpty() ||

                                    orderedItems.isEmpty()) {

                                JOptionPane.showMessageDialog(
                                        OrderPanel.this,
                                        "Please complete the order!",
                                        "Validation Error",
                                        JOptionPane.WARNING_MESSAGE
                                );

                                return;
                            }

                            int orderId =
                                    Integer.parseInt(
                                            orderIdField.getText()
                                    );

                            String customerName =
                                    customerNameField.getText();
                            String finalItems =
                                    orderedItems.substring(
                                            0,
                                            orderedItems.length() - 2
                                    );

                            Order order =
                                    new Order(
                                            orderId,
                                            customerName,
                                            finalItems,
                                            cartDetails,
                                            grandTotal
                                    );

                            orderManager.addOrder(order);

                            loadTableData();

                            JOptionPane.showMessageDialog(
                                    OrderPanel.this,
                                    "Order Placed Successfully!"
                            );

                            orderIdField.setText("");
                            customerNameField.setText("");
                            quantityField.setText("");
                            searchField.setText("");

                            orderedItems = "";
                            grandTotal = 0;

                        } catch (Exception ex) {

                            JOptionPane.showMessageDialog(
                                    OrderPanel.this,
                                    "Invalid Input!"
                            );
                        }
                    }
                });

        // =========================
// Table Selection
// =========================
        orderTable.addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mouseClicked(MouseEvent e) {

                        int selectedRow =
                                orderTable.getSelectedRow();

                        orderIdField.setText(
                                tableModel.getValueAt(
                                        selectedRow,
                                        0
                                ).toString()
                        );

                        customerNameField.setText(
                                tableModel.getValueAt(
                                        selectedRow,
                                        1
                                ).toString()
                        );

                        totalPriceField.setText(
                                tableModel.getValueAt(
                                        selectedRow,
                                        3
                                ).toString()
                        );

                        // Keep cart details
                        cartArea.setText(cartDetails);
                        ArrayList<Order> orderList =
                                orderManager.getAllOrders();

                        Order selectedOrder =
                                orderList.get(selectedRow);

                        cartArea.setText(
                                selectedOrder.getCartDetails()
                        );
                    }
                });

        // =========================
        // Update Button
        // =========================
        updateButton.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        int selectedRow =
                                orderTable.getSelectedRow();

                        if (selectedRow == -1) {

                            JOptionPane.showMessageDialog(
                                    OrderPanel.this,
                                    "Select a row first!"
                            );

                            return;
                        }

                        try {

                            int orderId =
                                    Integer.parseInt(
                                            orderIdField.getText()
                                    );

                            String customerName =
                                    customerNameField.getText();

                            Order updatedOrder =
                                    new Order(
                                            orderId,
                                            customerName,
                                            orderedItems,
                                            cartDetails,
                                            Double.parseDouble(
                                                    totalPriceField.getText()
                                            )
                                    );

                            orderManager.updateOrder(
                                    selectedRow,
                                    updatedOrder
                            );

                            loadTableData();

                            JOptionPane.showMessageDialog(
                                    OrderPanel.this,
                                    "Order Updated!"
                            );

                            clearFields();

                        } catch (Exception ex) {

                            JOptionPane.showMessageDialog(
                                    OrderPanel.this,
                                    "Invalid Input!"
                            );
                        }
                    }
                });

        // =========================
        // Delete Button
        // =========================
        deleteButton.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        int selectedRow =
                                orderTable.getSelectedRow();

                        if (selectedRow == -1) {

                            JOptionPane.showMessageDialog(
                                    OrderPanel.this,
                                    "Select a row first!"
                            );

                            return;
                        }

                        int confirm =
                                JOptionPane.showConfirmDialog(
                                        OrderPanel.this,
                                        "Delete this order?",
                                        "Confirm Delete",
                                        JOptionPane.YES_NO_OPTION
                                );

                        if (confirm ==
                                JOptionPane.YES_OPTION) {

                            orderManager.deleteOrder(
                                    selectedRow
                            );

                            loadTableData();

                            clearFields();
                        }
                    }
                });

        // =========================
        // Clear Button
        // =========================
        clearButton.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        clearFields();
                    }
                });

        // =========================
        // Search Event
        // =========================
        searchField.addKeyListener(
                new KeyAdapter() {

                    @Override
                    public void keyReleased(KeyEvent e) {

                        String keyword =
                                searchField.getText();

                        if (keyword.trim().isEmpty()) {

                            loadTableData();

                            return;
                        }

                        ArrayList<Order> searchList =
                                orderManager.searchOrder(keyword);

                        tableModel.setRowCount(0);

                        for (int i = 0;
                             i < searchList.size();
                             i++) {

                            Order order =
                                    searchList.get(i);

                            tableModel.addRow(new Object[]{

                                    order.getOrderId(),
                                    order.getCustomerName(),
                                    order.getOrderedItems(),
                                    order.getTotalPrice()
                            });
                        }
                    }
                });
    }

    // =========================
    // Load Food ComboBox
    // =========================
    private void loadFoodItemsIntoComboBox() {

        ArrayList<FoodItem> foodList =
                foodManager.getAllFoodItems();

        for (int i = 0;
             i < foodList.size();
             i++) {

            FoodItem foodItem =
                    foodList.get(i);

            foodComboBox.addItem(
                    foodItem.getFoodName()
            );
        }
    }

    // =========================
    // Load Table Data
    // =========================
    private void loadTableData() {

        tableModel.setRowCount(0);

        ArrayList<Order> orderList =
                orderManager.getAllOrders();

        for (int i = 0;
             i < orderList.size();
             i++) {

            Order order =
                    orderList.get(i);

            tableModel.addRow(new Object[]{

                    order.getOrderId(),
                    order.getCustomerName(),
                    order.getOrderedItems(),
                    order.getTotalPrice()
            });
        }
    }

    // =========================
    // Clear Fields
    // =========================
    private void clearFields() {

        orderIdField.setText("");
        customerNameField.setText("");
        quantityField.setText("");
        totalPriceField.setText("");
        searchField.setText("");

        cartArea.setText("");

        orderedItems = "";
        cartDetails = "";
        grandTotal = 0;
    }
}