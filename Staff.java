// Author, Mason Barney
// The staff object holds 1 attribute; a list of requests
// The staff object has methods to assign and remove requests from that list.

import java.util.ArrayList;

public class Staff extends User {
    private ArrayList<String> requests;
    private String occupation; 

    // Constructor ----------------------------------------------
    public Staff(String userID, String name, String email, String role, String occupation) {
        super(userID, name, email, role);
        this.requests = new ArrayList<>();
        this.occupation = occupation;
    }

    // Methods
    public void assignRequestToStaff(String request) {
        requests.add(request);
    }

    public void removeRequestFromStaff(String request) {
        requests.remove(request);
    }

    // Getters
    public ArrayList<String> getAssignedRequests() {
        return requests;
    }

    // Setters
    public void setRequests(ArrayList<String> requests) {
        this.requests = requests;
    }
    public String getOccupation() { 
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }
}
