// Author, Mason Barney
// The staff object holds 3 attributes; a list of requests, a position, and an id. 
// The staff object has getters and setters for all attributes.
// The staff object has methods to assign and remove requests from the list of requests.

import java.util.ArrayList;

public class Staff {
    private ArrayList<String> requests;
    private String position;
    private String id;

    // Constructor ----------------------------------------------
    public Staff(String position, String id) {
        this.requests = new ArrayList<>();
        this.position = position
        this.id = id;
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

    public String getPosition() {
        return position;
    }

    public String getId() {
        return id;
    }

    // Setters --------------------------------------------------
    public void setRequests(ArrayList<String> requests) {
        this.requests = requests;
    }
    
    public void setPosition(String position) {
        this.position = position;
    }

    public void setId(String id) {
        this.id = id;
    }
}
