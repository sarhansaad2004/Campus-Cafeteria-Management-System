public class Order {

    // Private Fields
    private int orderId;
    private String customerName;
    private String orderedItems;
    private double totalPrice;
    private String cartDetails;

    // Default Constructor
    public Order() {

    }

    // Parameterized Constructor

    public Order(
            int orderId,
            String customerName,
            String orderedItems,
            String cartDetails,
            double totalPrice
    ) {

        this.orderId = orderId;
        this.customerName = customerName;
        this.orderedItems = orderedItems;
        this.cartDetails = cartDetails;
        this.totalPrice = totalPrice;
    }
    // Getter Methods
    public int getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getOrderedItems() {
        return orderedItems;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    // Setter Methods
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setOrderedItems(String orderedItems) {
        this.orderedItems = orderedItems;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    public String getCartDetails() {
        return cartDetails;
    }

    public void setCartDetails(String cartDetails) {
        this.cartDetails = cartDetails;
    }
    // toString Method
    @Override
    public String toString() {

        return orderId + " - " + customerName;
    }
}