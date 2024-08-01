// Author, Mason Barney
// The staff object holds 1 attribute; a list of requests
// The staff object has methods to assign and remove requests from that list.

import java.util.ArrayList;
import user.java;

public class Staff extends user {
    private ArrayList<String> requests;

    // Constructor ----------------------------------------------
    public Staff() {
        this.requests = new ArrayList<>();
    }

    // Methods --------------------------------------------------
    public void assignRequestToStaff(String request) {
        requests.add(request);
    }

    public void removeRequestFromStaff(String request) {
        requests.remove(request);
    }

    // Getters --------------------------------------------------
    public ArrayList<String> getAssignedRequests() {
        return requests;
    }

    // Setters --------------------------------------------------
    public void setRequests(ArrayList<String> requests) {
        this.requests = requests;
    }
}
