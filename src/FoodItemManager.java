import java.util.ArrayList;
import java.io.*;

public class FoodItemManager {

    // ArrayList to store food items
    private ArrayList<FoodItem> foodList;

    // File Name
    private final String FILE_NAME = "fooditems.txt";

    // Constructor
    public FoodItemManager() {

        foodList = new ArrayList<FoodItem>();

        // Load saved food items
        loadFoodItemsFromFile();
    }

    // =========================
    // Add Food Item
    // =========================
    public void addFoodItem(FoodItem foodItem) {

        foodList.add(foodItem);

        saveFoodItemsToFile();
    }

    // =========================
    // Get All Food Items
    // =========================
    public ArrayList<FoodItem> getAllFoodItems() {
        return foodList;
    }

    // =========================
    // Update Food Item
    // =========================
    public void updateFoodItem(int index, FoodItem updatedFoodItem) {

        if (index >= 0 && index < foodList.size()) {

            foodList.set(index, updatedFoodItem);

            saveFoodItemsToFile();
        }
    }

    // =========================
    // Delete Food Item
    // =========================
    public void deleteFoodItem(int index) {

        if (index >= 0 && index < foodList.size()) {

            foodList.remove(index);

            saveFoodItemsToFile();
        }
    }

    // =========================
    // Search Food Item
    // =========================
    public ArrayList<FoodItem> searchFoodItem(String keyword) {

        ArrayList<FoodItem> searchList =
                new ArrayList<FoodItem>();

        for (int i = 0; i < foodList.size(); i++) {

            FoodItem foodItem = foodList.get(i);

            if (foodItem.getFoodName()
                    .toLowerCase()
                    .contains(keyword.toLowerCase())) {

                searchList.add(foodItem);
            }
        }

        return searchList;
    }

    // =========================
    // Save Food Items To File
    // =========================
    private void saveFoodItemsToFile() {

        try {

            BufferedWriter writer =
                    new BufferedWriter(
                            new FileWriter(FILE_NAME)
                    );

            for (int i = 0; i < foodList.size(); i++) {

                FoodItem foodItem = foodList.get(i);

                writer.write(
                        foodItem.getFoodId() + "," +
                                foodItem.getFoodName() + "," +
                                foodItem.getCategory() + "," +
                                foodItem.getPrice()
                );

                writer.newLine();
            }

            writer.close();

        } catch (Exception e) {

            System.out.println("Error Saving File");
        }
    }

    // =========================
    // Load Food Items From File
    // =========================
    private void loadFoodItemsFromFile() {

        try {

            File file = new File(FILE_NAME);

            // If file does not exist
            if (!file.exists()) {
                return;
            }

            BufferedReader reader =
                    new BufferedReader(
                            new FileReader(FILE_NAME)
                    );

            String line;

            while ((line = reader.readLine()) != null) {

                String[] data = line.split(",");

                int id = Integer.parseInt(data[0]);
                String name = data[1];
                String category = data[2];
                double price = Double.parseDouble(data[3]);

                FoodItem foodItem =
                        new FoodItem(id, name, category, price);

                foodList.add(foodItem);
            }

            reader.close();

        } catch (Exception e) {

            System.out.println("Error Loading File");
        }
    }
}