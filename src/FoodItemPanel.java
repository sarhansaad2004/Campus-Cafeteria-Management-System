import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class FoodItemPanel extends JPanel {

    // Form Fields
    private JTextField idField;
    private JTextField nameField;
    private JTextField categoryField;
    private JTextField priceField;
    private JTextField searchField;

    // Buttons
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton clearButton;

    // Table
    private JTable foodTable;
    private DefaultTableModel tableModel;

    // Manager
    private FoodItemManager foodManager;

    // Constructor
    public FoodItemPanel() {

        foodManager = new FoodItemManager();

        setLayout(new BorderLayout(10, 10));

        // =========================
        // Form Panel
        // =========================
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 8, 8));

        formPanel.setBorder(
                BorderFactory.createTitledBorder("Food Item Information")
        );

        idField = new JTextField();
        nameField = new JTextField();
        categoryField = new JTextField();
        priceField = new JTextField();
        searchField = new JTextField();

        formPanel.add(new JLabel("Food ID:"));
        formPanel.add(idField);

        formPanel.add(new JLabel("Food Name:"));
        formPanel.add(nameField);

        formPanel.add(new JLabel("Category:"));
        formPanel.add(categoryField);

        formPanel.add(new JLabel("Price:"));
        formPanel.add(priceField);

        formPanel.add(new JLabel("Search:"));
        formPanel.add(searchField);

        add(formPanel, BorderLayout.NORTH);

        // =========================
        // Table
        // =========================
        String[] columns = {
                "Food ID",
                "Food Name",
                "Category",
                "Price"
        };

        tableModel = new DefaultTableModel(columns, 0);

        foodTable = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(foodTable);

        add(scrollPane, BorderLayout.CENTER);

        // Load saved data into table
        loadTableData();

        // =========================
        // Button Panel
        // =========================
        JPanel buttonPanel = new JPanel(new FlowLayout());

        addButton = new JButton("Add");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        clearButton = new JButton("Clear");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // =========================
        // Add Button Event
        // =========================
        addButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                try {

                    // Validation
                    if (idField.getText().trim().isEmpty() ||
                            nameField.getText().trim().isEmpty() ||
                            categoryField.getText().trim().isEmpty() ||
                            priceField.getText().trim().isEmpty()) {

                        JOptionPane.showMessageDialog(
                                FoodItemPanel.this,
                                "Please fill all fields!",
                                "Validation Error",
                                JOptionPane.WARNING_MESSAGE
                        );

                        return;
                    }

                    int id = Integer.parseInt(idField.getText());

                    String name = nameField.getText();

                    String category = categoryField.getText();

                    double price =
                            Double.parseDouble(priceField.getText());

                    FoodItem foodItem =
                            new FoodItem(id, name, category, price);

                    foodManager.addFoodItem(foodItem);

                    loadTableData();

                    clearFields();

                } catch (Exception ex) {

                    JOptionPane.showMessageDialog(
                            FoodItemPanel.this,
                            "Invalid Input!",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });

        // =========================
        // Table Row Selection
        // =========================
        foodTable.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {

                int selectedRow = foodTable.getSelectedRow();

                idField.setText(
                        tableModel.getValueAt(selectedRow, 0).toString()
                );

                nameField.setText(
                        tableModel.getValueAt(selectedRow, 1).toString()
                );

                categoryField.setText(
                        tableModel.getValueAt(selectedRow, 2).toString()
                );

                priceField.setText(
                        tableModel.getValueAt(selectedRow, 3).toString()
                );
            }
        });

        // =========================
        // Update Button Event
        // =========================
        updateButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                int selectedRow = foodTable.getSelectedRow();

                if (selectedRow == -1) {

                    JOptionPane.showMessageDialog(
                            FoodItemPanel.this,
                            "Select a row first!"
                    );

                    return;
                }

                try {

                    int id = Integer.parseInt(idField.getText());

                    String name = nameField.getText();

                    String category = categoryField.getText();

                    double price =
                            Double.parseDouble(priceField.getText());

                    FoodItem updatedFood =
                            new FoodItem(id, name, category, price);

                    foodManager.updateFoodItem(
                            selectedRow,
                            updatedFood
                    );

                    loadTableData();

                    clearFields();

                } catch (Exception ex) {

                    JOptionPane.showMessageDialog(
                            FoodItemPanel.this,
                            "Invalid Input!"
                    );
                }
            }
        });

        // =========================
        // Delete Button Event
        // =========================
        deleteButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                int selectedRow = foodTable.getSelectedRow();

                if (selectedRow == -1) {

                    JOptionPane.showMessageDialog(
                            FoodItemPanel.this,
                            "Select a row first!"
                    );

                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(
                        FoodItemPanel.this,
                        "Do you want to delete this item?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {

                    foodManager.deleteFoodItem(selectedRow);

                    loadTableData();

                    clearFields();
                }
            }
        });

        // =========================
        // Clear Button Event
        // =========================
        clearButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                clearFields();
            }
        });

        // =========================
        // Search Event
        // =========================
        searchField.addKeyListener(new KeyAdapter() {

            @Override
            public void keyReleased(KeyEvent e) {

                String keyword =
                        searchField.getText().trim();

                // If search box is empty
                if (keyword.isEmpty()) {

                    loadTableData();

                    return;
                }

                ArrayList<FoodItem> searchList =
                        foodManager.searchFoodItem(keyword);

                tableModel.setRowCount(0);

                for (int i = 0; i < searchList.size(); i++) {

                    FoodItem foodItem = searchList.get(i);

                    tableModel.addRow(new Object[]{

                            foodItem.getFoodId(),
                            foodItem.getFoodName(),
                            foodItem.getCategory(),
                            foodItem.getPrice()
                    });
                }
            }
        });
    }

    // =========================
    // Clear Fields
    // =========================
    private void clearFields() {

        idField.setText("");
        nameField.setText("");
        categoryField.setText("");
        priceField.setText("");
    }

    // =========================
    // Load Table Data
    // =========================
    private void loadTableData() {

        // Clear Table
        tableModel.setRowCount(0);

        ArrayList<FoodItem> foodList =
                foodManager.getAllFoodItems();

        for (int i = 0; i < foodList.size(); i++) {

            FoodItem foodItem = foodList.get(i);

            tableModel.addRow(new Object[]{

                    foodItem.getFoodId(),
                    foodItem.getFoodName(),
                    foodItem.getCategory(),
                    foodItem.getPrice()
            });
        }
    }
}