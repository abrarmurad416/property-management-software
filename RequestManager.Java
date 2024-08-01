import java.util.ArrayList;
import java.util.List;

public class RequestManager {
    private List<MaintenanceRequest> requests;

    // Constructor
    public RequestManager() {
        this.requests = new ArrayList<>();
    }

    // Add a new maintenance request
    public void addRequest(MaintenanceRequest request) {
        this.requests.add(request);
    }

    // View all maintenance requests
    public List<MaintenanceRequest> viewRequests() {
        return new ArrayList<>(requests);
    }

    // Update an existing maintenance request
    public void updateRequest(int requestId, String issueDescription, int urgencyLevel) {
        for (MaintenanceRequest request : requests) {
            if (request.getRequestId() == requestId) {
                request.updateDetails(issueDescription, urgencyLevel);
                break;
            }
        }
    }

    // Update the status of an existing maintenance request
    public void updateRequestStatus(int requestId, String status) {
        for (MaintenanceRequest request : requests) {
            if (request.getRequestId() == requestId) {
                request.updateStatus(status);
                break;
            }
        }
    }

    // Assign a staff member to a maintenance request
    public void assignRequest(int requestId, String staffId) {
        for (MaintenanceRequest request : requests) {
            if (request.getRequestId() == requestId) {
                request.assignStaff(staffId);
                break;
            }
        }
    }

    // Delete a maintenance request
    public void deleteRequest(int requestId) {
        requests.removeIf(request -> request.getRequestId() == requestId);
    }
}