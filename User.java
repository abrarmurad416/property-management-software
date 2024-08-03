// Author, Abrar Murad

/** 
 * The user class will represent the users that interact with the property managment system.
 * It is comprised of basic user information, such as the user's ID, name, email, and role.
 */

public class User {
    private String userId;
    private String name;
    private String email;
    private String role; // "tenant" or "staff"

    // Constructor
    public User(String userId, String name, String email, String role) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // Methods
    public String getProfile() {
        return "User ID: " + userId + "\nName: " + name + "\nEmail: " + email + "\nRole: " + role;
    }

    public void updateProfile(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public void deleteProfile() {
        this.userId = null;
        this.name = null;
        this.email = null;
        this.role = null;
    }
}
