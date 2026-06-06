public abstract class User {

    // Private Fields
    private int userId;
    private String userName;

    // Default Constructor
    public User() {

    }

    // Parameterized Constructor
    public User(int userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    // Getter Methods
    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    // Setter Methods
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    // Abstract Method
    public abstract void displayRole();
}