public class FoodItem {

    // Private fields (Encapsulation)
    private int foodId;
    private String foodName;
    private String category;
    private double price;

    // Default Constructor
    public FoodItem() {

    }

    // Parameterized Constructor
    public FoodItem(int foodId, String foodName, String category, double price) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.category = category;
        this.price = price;
    }

    // Getter Methods
    public int getFoodId() {
        return foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    // Setter Methods
    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // toString Method
    @Override
    public String toString() {
        return foodId + " - " + foodName;
    }
}