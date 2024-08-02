import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    private static List<MaintenanceRequest> requests;
    private static List<Staff> staffList;

    public static void main(String[] args) {
        Generator.generateRandomTenants(10);
        staffList = Generator.generateRandomStaff(5);
        requests = Generator.generateRandomMaintenanceRequests(7);

        JFrame loginFrame = new JFrame("Login Page");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(400, 200);

        JPanel panel = new JPanel();
        loginFrame.add(panel);
        placeComponents(panel, loginFrame);

        loginFrame.setVisible(true);
    }

    private static void placeComponents(JPanel panel, JFrame loginFrame) {
        panel.setLayout(null);

        JLabel userLabel = new JLabel("User:");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        JPasswordField userText = new JPasswordField(20);
        userText.setBounds(100, 20, 165, 25);
        userText.setText("username");
        panel.add(userText);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(100, 50, 165, 25);
        passwordText.setText("password");
        panel.add(passwordText);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(150, 100, 80, 25);
        panel.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = new String(userText.getPassword());
                String password = new String(passwordText.getPassword());

                if (username.equals("username") && password.equals("password")) {
                    loginFrame.dispose();
                    showMainSoftware();
                } else {
                    JOptionPane.showMessageDialog(panel, "Invalid username or password.");
                }
            }
        });
    }

    private static void showMainSoftware() {
        JFrame mainFrame = new JFrame("Main Software Interface");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 600);
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainFrame.setResizable(true);
    
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainFrame.add(mainPanel);
    
        // Create a larger canvas in the center
        JPanel canvas = new JPanel();
        canvas.setPreferredSize(new Dimension(400, 200));  // Canvas size
        canvas.setLayout(new FlowLayout());  // Layout for the canvas
        mainPanel.add(canvas, BorderLayout.CENTER);
    
        // Create a smaller button inside the canvas for viewing requests
        JButton viewRequestsButton = new JButton("View Requests");
        viewRequestsButton.setPreferredSize(new Dimension(150, 40));  // Smaller button size
        canvas.add(viewRequestsButton);
    
        // Create a vertical sidebar on the right for staff listing
        JPanel staffPanel = new JPanel();
        staffPanel.setLayout(new BoxLayout(staffPanel, BoxLayout.Y_AXIS));
        staffPanel.setPreferredSize(new Dimension(200, mainFrame.getHeight()));
        staffPanel.setBorder(BorderFactory.createTitledBorder("Staff Members"));
        mainPanel.add(staffPanel, BorderLayout.EAST);
    
        // Group staff by occupation
        Map<String, List<Staff>> staffByOccupation = staffList.stream()
                .collect(Collectors.groupingBy(Staff::getOccupation));
    
        // Add each occupation and its members to the staff panel
        for (Map.Entry<String, List<Staff>> entry : staffByOccupation.entrySet()) {
            JLabel occupationLabel = new JLabel("<html><u><b>" + entry.getKey() + "</b></u></html>");
            occupationLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            staffPanel.add(occupationLabel);
    
            for (Staff staff : entry.getValue()) {
                JLabel staffNameLabel = new JLabel(staff.getName());
                staffNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                staffPanel.add(staffNameLabel);
            }
        }
    
        // Add action listener to the button
        viewRequestsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showRequestsTable();
            }
        });
    
        mainFrame.setVisible(true);
    }
    
    


    private static void showRequestsTable() {
        JFrame requestFrame = new JFrame("Maintenance Requests");
        requestFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        requestFrame.setSize(600, 400);

        JPanel requestPanel = new JPanel(new BorderLayout());
        requestFrame.add(requestPanel);

        String[] columnNames = {"ID", "Tenant Name", "Issue Description", "Urgency Level"};

        String[][] data = new String[requests.size()][4];
        for (int i = 0; i < requests.size(); i++) {
            MaintenanceRequest request = requests.get(i);
            data[i][0] = String.valueOf(request.getRequestId());
            data[i][1] = request.getTenantName();
            data[i][2] = request.getIssueDescription();
            data[i][3] = String.valueOf(request.getUrgencyLevel());
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        requestPanel.add(scrollPane, BorderLayout.CENTER);

        requestFrame.setVisible(true);
    }
}
