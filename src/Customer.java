public class Customer extends User {

    // Private Field
    private String phoneNumber;

    // Default Constructor
    public Customer() {

    }

    // Parameterized Constructor
    public Customer(int userId, String userName, String phoneNumber) {

        // Calling Parent Constructor
        super(userId, userName);

        this.phoneNumber = phoneNumber;
    }

    // Getter Method
    public String getPhoneNumber() {
        return phoneNumber;
    }

    // Setter Method
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    // Overriding Abstract Method
    @Override
    public void displayRole() {
        System.out.println("Role: Customer");
    }

    // toString Method
    @Override
    public String toString() {
        return getUserId() + " - " + getUserName();
    }
}