//Author: Sohan Hossain

/* 
 * The tenant class represents any tenant that would interact with the property management system.
 * It extends the User class, and is comprised of additional properties and methods for the class specific to tenants.
 */
import java.util.ArrayList;
import java.util.List;

public class Tenant extends User {
    private String roomNumber;
    private List<MaintenanceRequest> tenantRequests;

    // Constructor
    public Tenant(String userId, String name, String email, String role, String roomNumber) {
        super(userId, name, email, role);
        this.roomNumber = roomNumber;
        this.tenantRequests = new ArrayList<>();
    }

    // Getters and Setters
    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public List<MaintenanceRequest> getTenantRequests() {
        return tenantRequests;
    }

    // Methods
    public void submitRequest(String issueDescription, int urgencyLevel, String orderType) {
        int requestId = tenantRequests.size() + 1;
        MaintenanceRequest request = new MaintenanceRequest(requestId, getName(), issueDescription, urgencyLevel, orderType);
        tenantRequests.add(request);
    }

    public List<MaintenanceRequest> viewRequests() {
        return new ArrayList<>(tenantRequests);
    }
}
