import java.util.ArrayList;
import java.util.List;

public class RequestManager {
    private List<MaintenanceRequest> requests;

    // Constructor
    public RequestManager() {
        this.requests = new ArrayList<>();
        this.users = new ArrayList<>();
    }

    // Add a new maintenance request
    public void addRequest(MaintenanceRequest request) {
        this.requests.add(request);
    }

    // View all maintenance requests
    public List<MaintenanceRequest> viewRequests() {
        return new ArrayList<>(requests);
    }

    // View a specific maintenance request
    public MaintenanceRequest viewRequest(int requestId) {
        for (MaintenanceRequest request : requests) {
            if (request.getRequestId() == requestId) {
                return request;
            }
        }
        return null;
    }

    // View all maintenance requests assigned to a specific staff member
    public List<MaintenanceRequest> viewRequestsByStaff(String staffId) {
        List<MaintenanceRequest> staffRequests = new ArrayList<>();
        for (MaintenanceRequest request : requests) {
            if (request.getAssignedStaff().equals(staffId)) {
                staffRequests.add(request);
            }
        }
        return staffRequests;
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
                // if the status is set to "Complete", remove the assigned staff member from the request
                if (status.equals("Complete")) {
                    for (User user : users) {
                        if (user.getRole().equals("staff")) {
                            Staff staff = (Staff) user;
                            staff.removeRequestFromStaff(requestId);
                            break;
                        }
                    }
                }

                request.updateStatus(status);
                break;
            }
        }
    }

    // Assign a staff member to a maintenance request
    public void assignRequest(int requestId, String staffId) {
        for (MaintenanceRequest request : requests) {
            if (request.getRequestId() == requestId) {
                // iterate through the list of staff to find the staff member with the matching ID
                for (User user : users) {
                    if (user.getUserId().equals(staffId) && user.getRole().equals("staff")) {
                        user.assignRequestToStaff(requestId);
                        request.assignStaff(staffId);
                        break;
                    }
                }
                request.assignStaff(staffId);
                break;
            }
        }
    }

    // Delete a maintenance request
    public void deleteRequest(int requestId) {
        // find if there is an assigned staff member to the request and remove it from their list
        for (User user : users) {
            if (user.getRole().equals("staff")) {
                Staff staff = (Staff) user;
                staff.removeRequestFromStaff(requestId);
                break;
            }
        }

        requests.removeIf(request -> request.getRequestId() == requestId);
    }
}