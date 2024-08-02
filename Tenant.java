//Author: Sohan Hossain
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
    public void submitRequest(String issueDescription, int urgencyLevel) {
        int requestId = tenantRequests.size() + 1; // Simple way to generate requestId
        MaintenanceRequest request = new MaintenanceRequest(requestId, getName(), issueDescription, urgencyLevel);
        tenantRequests.add(request);
    }

    public List<MaintenanceRequest> viewRequests() {
        return new ArrayList<>(tenantRequests);
    }
}
