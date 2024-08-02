import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Generator {
    private static final String[] LETTERS = {"A", "B", "C", "D"};
    private static final String[] FIRST_NAMES = {
        "John", "Jane", "Alice", "Bob", "Charlie", "David", "Eve", "Frank", "Grace", "Heidi",
        "Ivan", "Judy", "Kathy", "Leo", "Mallory", "Nina", "Oscar", "Peggy", "Quentin", "Ruth",
        "Sam", "Trudy", "Uma", "Victor", "Wendy", "Xavier", "Yvonne", "Zach", "Amy", "Brian",
        "Catherine", "Derek", "Elaine", "Fred", "Gina", "Harry", "Isabel", "Jack", "Karen",
        "Liam", "Mia", "Nathan", "Olivia", "Paul", "Queenie", "Rob", "Sara", "Tom", "Ursula",
        "Vanessa", "Will", "Xena", "Yara", "Zane", "Abby", "Blake", "Claire", "Dylan", "Erica",
        "Finn", "Gloria", "Hank", "Ingrid", "Jake", "Kelly", "Luke", "Megan", "Noah", "Opal",
        "Pete", "Quincy", "Rita", "Steve", "Tina", "Ulysses", "Vince", "Whitney", "Xander",
        "Yasmin", "Zeke", "Aria", "Ben", "Cara", "Dean", "Elena", "Felix", "Gwen", "Henry",
        "Ivy", "Joel"
    };
    private static final String[] LAST_NAMES = {
        "Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis", "Rodriguez", "Martinez",
        "Hernandez", "Lopez", "Gonzalez", "Wilson", "Anderson", "Thomas", "Taylor", "Moore", "Jackson", "Martin",
        "Lee", "Perez", "Thompson", "White", "Harris", "Sanchez", "Clark", "Ramirez", "Lewis", "Robinson",
        "Walker", "Young", "Allen", "King", "Wright", "Scott", "Torres", "Nguyen", "Hill", "Flores",
        "Green", "Adams", "Nelson", "Baker", "Hall", "Rivera", "Campbell", "Mitchell", "Carter", "Roberts",
        "Gomez", "Phillips", "Evans", "Turner", "Diaz", "Parker", "Cruz", "Edwards", "Collins", "Reyes",
        "Stewart", "Morris", "Morales", "Murphy", "Cook", "Rogers", "Gutierrez", "Ortiz", "Morgan", "Cooper",
        "Peterson", "Bailey", "Reed", "Kelly", "Howard", "Ramos", "Kim", "Cox", "Ward", "Richardson",
        "Watson", "Brooks", "Chavez", "Wood", "James", "Bennett", "Gray", "Mendoza", "Ruiz", "Hughes",
        "Price", "Alvarez", "Castillo", "Sanders", "Patel", "Myers", "Long", "Ross", "Foster", "Jimenez"
    };
    private static final String[] EMAIL_DOMAINS = {"outlook.com", "gmail.com", "email.com", "hotmail.com", "mylaurier.ca"};
    private static final String[] OCCUPATIONS = {"Electrician", "Plumber", "Janitor", "IT Support", "Security"};
    private static final String[] ISSUES = {"Leaky faucet", "Broken heater", "Clogged drain", "Electrical outage", "Pest control"};
    private static final String[] REQUEST_TYPES = {"Plumbing", "Electrician", "Janitor", "IT Support", "Security"};


    private static List<Tenant> tenants = new ArrayList<>();

    public static List<Tenant> generateRandomTenants(int count) {
        tenants.clear(); // Clear the list before generating new tenants
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            String letter = LETTERS[random.nextInt(LETTERS.length)];
            int floor = random.nextInt(6) * 100; // Floors: 0, 100, 200, 300, 400, 500
            int room = random.nextInt(30) + 1;
            String roomNumber = String.format("%s-%03d", letter, floor + room);
            String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
            String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
            String emailDomain = EMAIL_DOMAINS[random.nextInt(EMAIL_DOMAINS.length)];
            String email = firstName.toLowerCase() + lastName.toLowerCase() + roomNumber.replace("-", "").toLowerCase() + "@" + emailDomain;

            Tenant tenant = new Tenant("ID" + i, firstName + " " + lastName, email, "tenant", roomNumber);
            tenants.add(tenant);
        }

        return tenants;
    }

    public static List<Staff> generateRandomStaff(int count) {
        List<Staff> staff = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
            String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
            String emailDomain = EMAIL_DOMAINS[random.nextInt(EMAIL_DOMAINS.length)];
            String occupation = OCCUPATIONS[random.nextInt(OCCUPATIONS.length)];
            String email = firstName.toLowerCase() + lastName.toLowerCase() + "@" + emailDomain;

            Staff member = new Staff("ID" + i, firstName + " " + lastName, email, "staff", occupation);
            staff.add(member);
        }

        return staff;
    }
    public static List<MaintenanceRequest> generateRandomMaintenanceRequests(int count) {
        List<MaintenanceRequest> requests = new ArrayList<>();
        Random random = new Random();
    
        for (int i = 0; i < count; i++) {
            Tenant tenant = tenants.get(random.nextInt(tenants.size()));
            String description = ISSUES[random.nextInt(ISSUES.length)];
            int urgency = random.nextInt(3); // 0: Low, 1: Medium, 2: High
            String requestType = REQUEST_TYPES[random.nextInt(REQUEST_TYPES.length)]; // New field assignment
            MaintenanceRequest request = new MaintenanceRequest(i + 1, tenant.getName(), description, urgency, requestType);
            requests.add(request);
        }
    
        return requests;
    }
    
}
