import java.util.ArrayList;
import java.io.*;

public class OrderManager {

    // Order List
    private ArrayList<Order> orderList;

    // File Name
    private final String FILE_NAME = "orders.txt";

    // Constructor
    public OrderManager() {

        orderList = new ArrayList<Order>();

        // Load saved orders
        loadOrdersFromFile();
    }

    // =========================
    // Add Order
    // =========================
    public void addOrder(Order order) {

        orderList.add(order);

        saveOrdersToFile();
    }

    // =========================
    // Get All Orders
    // =========================
    public ArrayList<Order> getAllOrders() {

        return orderList;
    }

    // =========================
    // Update Order
    // =========================
    public void updateOrder(
            int index,
            Order updatedOrder
    ) {

        if (index >= 0 &&
                index < orderList.size()) {

            orderList.set(index, updatedOrder);

            saveOrdersToFile();
        }
    }

    // =========================
    // Delete Order
    // =========================
    public void deleteOrder(int index) {

        if (index >= 0 &&
                index < orderList.size()) {

            orderList.remove(index);

            saveOrdersToFile();
        }
    }

    // =========================
    // Search Order
    // =========================
    public ArrayList<Order> searchOrder(
            String keyword
    ) {

        ArrayList<Order> searchList =
                new ArrayList<Order>();

        for (int i = 0;
             i < orderList.size();
             i++) {

            Order order =
                    orderList.get(i);

            if (order.getCustomerName()
                    .toLowerCase()
                    .contains(
                            keyword.toLowerCase()
                    )) {

                searchList.add(order);
            }
        }

        return searchList;
    }

    // =========================
    // Save Orders To File
    // =========================
    private void saveOrdersToFile() {

        try {

            BufferedWriter writer =
                    new BufferedWriter(
                            new FileWriter(FILE_NAME)
                    );

            for (int i = 0;
                 i < orderList.size();
                 i++) {

                Order order =
                        orderList.get(i);

                writer.write(
                        order.getOrderId() + "|" +
                                order.getCustomerName() + "|" +
                                order.getOrderedItems()
                                        .replace("\n", "<br>") + "|" +
                                order.getCartDetails()
                                        .replace("\n", "<br>") + "|" +
                                order.getTotalPrice()
                );

                writer.newLine();
            }

            writer.close();

        } catch (Exception e) {

            System.out.println(
                    "Error Saving Orders"
            );
        }
    }

    // =========================
    // Load Orders From File
    // =========================
    private void loadOrdersFromFile() {

        try {

            File file =
                    new File(FILE_NAME);

            // If file does not exist
            if (!file.exists()) {
                return;
            }

            BufferedReader reader =
                    new BufferedReader(
                            new FileReader(FILE_NAME)
                    );

            String line;

            while ((line =
                    reader.readLine()) != null) {

                String[] data =
                        line.split("\\|");

                int orderId =
                        Integer.parseInt(data[0]);

                String customerName =
                        data[1];

                String orderedItems =
                        data[2].replace(
                                "<br>",
                                "\n"
                        );

                String cartDetails =
                        data[3].replace(
                                "<br>",
                                "\n"
                        );

                double totalPrice =
                        Double.parseDouble(data[4]);

                Order order =
                        new Order(
                                orderId,
                                customerName,
                                orderedItems,
                                cartDetails,
                                totalPrice
                        );

                orderList.add(order);
            }

            reader.close();

        } catch (Exception e) {

            System.out.println(
                    "Error Loading Orders"
            );
        }
    }
}