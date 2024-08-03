import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.chart.plot.PlotOrientation;



import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

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
        Generator.generateRandomTenants(50);
        staffList = Generator.generateRandomStaff(15);
        requests = Generator.generateRandomMaintenanceRequests(74);

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

        // Create navigation panel on the left
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));
        navPanel.setPreferredSize(new Dimension(200, mainFrame.getHeight()));
        mainPanel.add(navPanel, BorderLayout.WEST);

        JLabel navTitle = new JLabel("Navigation Bar");
        navTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        navPanel.add(navTitle);

        JButton homeButton = new JButton("Home");
        homeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        navPanel.add(homeButton);

        JButton submitRequestButton = new JButton("Submit Request");
        submitRequestButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        navPanel.add(submitRequestButton);

        JButton viewRequestsButton = new JButton("View Requests");
        viewRequestsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        navPanel.add(viewRequestsButton);

        JButton profileManagementButton = new JButton("Profile Management");
        profileManagementButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        navPanel.add(profileManagementButton);

        // Create a welcome panel in the center
        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.Y_AXIS));
        mainPanel.add(welcomePanel, BorderLayout.CENTER);

        JLabel welcomeLabel = new JLabel("Welcome to the Property Management Dashboard!");
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomePanel.add(welcomeLabel);

        JLabel infoLabel = new JLabel("<html>This dashboard provides an overview of your property management tasks. Use the navigation bar on the left to access different features.<br><br>" +
                "Here are some quick tips to help get you started:<br><br>" +
                "<ul>" +
                "<li>Submit Request: Report a new maintenance issue.</li>" +
                "<li>View Request: Check the current status of your submitted requests.</li>" +
                "<li>Profile Management: Update any of your personal information.</li>" +
                "</ul><br>" +
                "Thank you for choosing Group 21 to manage your properties!</html>");
        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomePanel.add(infoLabel);

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

        // Add charts at the bottom of the main panel
        JPanel chartPanel = new JPanel();
        chartPanel.setLayout(new GridLayout(1, 2)); // One row, two columns
        chartPanel.add(createBarChartPanel());
        chartPanel.add(createPieChartPanel());
        mainPanel.add(chartPanel, BorderLayout.SOUTH);

        // Add action listener to the view requests button
        viewRequestsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showRequestsTable();
            }
        });

        mainFrame.setVisible(true);
    }

    private static JPanel createBarChartPanel() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Group requests by urgency level
        Map<Integer, Long> urgencyCount = requests.stream()
                .collect(Collectors.groupingBy(MaintenanceRequest::getUrgencyLevel, Collectors.counting()));

        // Add data to the dataset
        for (Map.Entry<Integer, Long> entry : urgencyCount.entrySet()) {
            String urgencyLabel = switch (entry.getKey()) {
                case 0 -> "Low";
                case 1 -> "Medium";
                case 2 -> "High";
                default -> "Unknown";
            };
            dataset.addValue(entry.getValue(), "Requests", urgencyLabel);
        }

        // Create the bar chart
        JFreeChart barChart = ChartFactory.createBarChart(
                "Request Priorities", // Chart title
                "Priority", // X-axis label
                "Count", // Y-axis label
                dataset, // Data
                PlotOrientation.VERTICAL,
                false, true, false);

        // Customize the plot
        CategoryPlot plot = (CategoryPlot) barChart.getPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();

        // Set colors for different priority levels
        renderer.setSeriesPaint(0, Color.YELLOW); // Low priority
        renderer.setSeriesPaint(1, Color.ORANGE); // Medium priority
        renderer.setSeriesPaint(2, Color.RED); // High priority

        return new ChartPanel(barChart);
    }

    private static JPanel createPieChartPanel() {
        DefaultPieDataset dataset = new DefaultPieDataset();

        // Group requests by type
        Map<String, Long> typeCount = requests.stream()
                .collect(Collectors.groupingBy(MaintenanceRequest::getRequestType, Collectors.counting()));

        // Add data to the dataset
        for (Map.Entry<String, Long> entry : typeCount.entrySet()) {
            dataset.setValue(entry.getKey(), entry.getValue());
        }

        // Create the pie chart
        JFreeChart pieChart = ChartFactory.createPieChart(
                "Request Types", // Chart title
                dataset, // Data
                true, true, false);

        // Customize the plot
        PiePlot plot = (PiePlot) pieChart.getPlot();
        plot.setSectionPaint("Plumbing", Color.BLUE);
        plot.setSectionPaint("Electricity", Color.RED);
        plot.setSectionPaint("Pest Control", Color.GREEN);
        // Add more colors for other request types as needed

        return new ChartPanel(pieChart);
    }



    private static void showRequestsTable() {
        JFrame requestFrame = new JFrame("Maintenance Requests");
        requestFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        requestFrame.setSize(600, 400);
    
        JPanel requestPanel = new JPanel(new BorderLayout());
        requestFrame.add(requestPanel);
    
        String[] columnNames = {"ID", "Tenant Name", "Issue Description", "Urgency Level", "Request Type"};
    
        String[][] data = new String[requests.size()][5];
        for (int i = 0; i < requests.size(); i++) {
            MaintenanceRequest request = requests.get(i);
            data[i][0] = String.valueOf(request.getRequestId());
            data[i][1] = request.getTenantName();
            data[i][2] = request.getIssueDescription();
            data[i][3] = String.valueOf(request.getUrgencyLevel());
            data[i][4] = request.getRequestType();
        }
    
        JTable table = new JTable(data, columnNames);
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(sorter);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        requestPanel.add(scrollPane, BorderLayout.CENTER);
    
        // Filter panel
        JPanel filterPanel = new JPanel();
        requestPanel.add(filterPanel, BorderLayout.NORTH);
    
        JLabel filterLabel = new JLabel("Filter by Urgency:");
        filterPanel.add(filterLabel);
    
        String[] urgencyLevels = {"All", "Low", "Medium", "High"};
        JComboBox<String> urgencyFilter = new JComboBox<>(urgencyLevels);
        filterPanel.add(urgencyFilter);
    
        // Search field
        JLabel searchLabel = new JLabel("Search:");
        filterPanel.add(searchLabel);
    
        JTextField searchField = new JTextField(15);
        filterPanel.add(searchField);
    
        // Filter functionality
        urgencyFilter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selected = (String) urgencyFilter.getSelectedItem();
                if ("All".equals(selected)) {
                    sorter.setRowFilter(null);
                } else {
                    int urgency = -1;
                    switch (selected) {
                        case "Low":
                            urgency = 0;
                            break;
                        case "Medium":
                            urgency = 1;
                            break;
                        case "High":
                            urgency = 2;
                            break;
                    }
                    int finalUrgency = urgency;
                    sorter.setRowFilter(new RowFilter<TableModel, Integer>() {
                        @Override
                        public boolean include(Entry<? extends TableModel, ? extends Integer> entry) {
                            int modelIndex = entry.getIdentifier();
                            return Integer.parseInt(entry.getModel().getValueAt(modelIndex, 3).toString()) == finalUrgency;
                        }
                    });
                }
            }
        });
    
        // Search functionality
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateFilter();
            }
    
            @Override
            public void removeUpdate(DocumentEvent e) {
                updateFilter();
            }
    
            @Override
            public void changedUpdate(DocumentEvent e) {
                updateFilter();
            }
    
            private void updateFilter() {
                String searchText = searchField.getText();
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText));
            }
        });
    
        requestFrame.setVisible(true);
    }

    
}
