// Author, Mason Barney
// The staff object holds 1 attribute; a list of requests
// The staff object has methods to assign and remove requests from that list.

import java.util.ArrayList;

public class Staff extends User {
    private ArrayList<MaintenanceRequest> requests; // Changed to hold MaintenanceRequest objects
    private String occupation;

    // Constructor
    public Staff(String userID, String name, String email, String role, String occupation) {
        super(userID, name, email, role);
        this.requests = new ArrayList<>();
        this.occupation = occupation;
    }

    // Methods
    public void assignRequestToStaff(MaintenanceRequest request) {
        requests.add(request);
        request.setAssignedStaff(this);
    }

    public void removeRequestFromStaff(MaintenanceRequest request) {
        requests.remove(request);
        request.setAssignedStaff(null);
    }

    // Getters
    public ArrayList<MaintenanceRequest> getAssignedRequests() {
        return requests;
    }

    // Setters
    public void setRequests(ArrayList<MaintenanceRequest> requests) {
        this.requests = requests;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }
}
