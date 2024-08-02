//Author: Sohan Hossain
public class MaintenanceRequest {
    private int requestId;
    private String tenantName;
    private String issueDescription;
    private int urgencyLevel; // 0: Low, 1: Medium, 2: High
    private String status;
    private String assignedStaff;
    private String requestType;

    // Constructor
    public MaintenanceRequest(int requestId, String tenantName, String issueDescription, int urgencyLevel, String requestType) {
        this.requestId = requestId;
        this.tenantName = tenantName;
        this.issueDescription = issueDescription;
        this.urgencyLevel = urgencyLevel;
        this.status = "Pending";
        this.assignedStaff = "";
        this.requestType = requestType;

    }

    // Getters
    public int getRequestId() {
        return requestId;
    }

    public String getTenantName() {
        return tenantName;
    }

    public String getIssueDescription() {
        return issueDescription;
    }

    public int getUrgencyLevel() {
        return urgencyLevel;
    }

    public String getStatus() {
        return status;
    }

    public String getAssignedStaff() {
        return assignedStaff;
    }

    // Setters
    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public void setIssueDescription(String issueDescription) {
        this.issueDescription = issueDescription;
    }

    public void setUrgencyLevel(int urgencyLevel) {
        this.urgencyLevel = urgencyLevel;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAssignedStaff(String assignedStaff) {
        this.assignedStaff = assignedStaff;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    // Override toString method
    @Override
    public String toString() {
        return "Request ID: " + requestId + "\n" +
                "Tenant Name: " + tenantName + "\n" +
                "Issue Description: " + issueDescription + "\n" +
                "Urgency Level: " + urgencyLevel + "\n" +
                "Status: " + status + "\n" +
                "Assigned Staff: " + assignedStaff + "\n";
    }

    // Other Methods
    public String getDetails() {
        return "Request ID: " + requestId + "\n" +
               "Tenant Name: " + tenantName + "\n" +
               "Issue Description: " + issueDescription + "\n" +
               "Urgency Level: " + urgencyLevel + "\n" +
               "Status: " + status + "\n" +
               "Assigned Staff: " + assignedStaff;
    }

    public void updateStatus(String status) {
        this.status = status;
    }

    public void updateDetails(String issueDescription, int urgencyLevel) {
        this.issueDescription = issueDescription;
        this.urgencyLevel = urgencyLevel;
    }

    public void assignStaff(String staffId) {
        this.assignedStaff = staffId;
    }
}
