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

        JButton assignRequestsButton = new JButton("Assign Requests");
        assignRequestsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        navPanel.add(assignRequestsButton);

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

        // Add action listener to the assign requests button
        assignRequestsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAssignRequestsWindow();
            }
        });

        // Add action listener to the submit request button
        submitRequestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSubmitRequestForm();
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
    
        
            private static void showSubmitRequestForm() {
                JFrame submitFrame = new JFrame("Submit a New Maintenance Request");
                submitFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                submitFrame.setSize(600, 400);
        
                JPanel submitPanel = new JPanel(new BorderLayout());
                submitFrame.add(submitPanel);
        
                // Create form elements
                JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
                submitPanel.add(formPanel, BorderLayout.CENTER);
        
                JLabel titleLabel = new JLabel("Title:");
                JTextField titleField = new JTextField();
                formPanel.add(titleLabel);
                formPanel.add(titleField);
        
                JLabel descriptionLabel = new JLabel("Description:");
                JTextArea descriptionArea = new JTextArea();
                formPanel.add(descriptionLabel);
                formPanel.add(new JScrollPane(descriptionArea));
        
                JLabel urgencyLabel = new JLabel("Urgency Level:");
                JPanel urgencyPanel = new JPanel(new FlowLayout());
                JRadioButton lowButton = new JRadioButton("Low");
                JRadioButton mediumButton = new JRadioButton("Medium");
                JRadioButton highButton = new JRadioButton("High");
                ButtonGroup urgencyGroup = new ButtonGroup();
                urgencyGroup.add(lowButton);
                urgencyGroup.add(mediumButton);
                urgencyGroup.add(highButton);
                urgencyPanel.add(lowButton);
                urgencyPanel.add(mediumButton);
                urgencyPanel.add(highButton);
                formPanel.add(urgencyLabel);
                formPanel.add(urgencyPanel);
        
                JButton submitButton = new JButton("Submit");
                submitPanel.add(submitButton, BorderLayout.SOUTH);
        
                submitButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String title = titleField.getText();
                        String description = descriptionArea.getText();
                        int urgencyLevel = -1;
                        if (lowButton.isSelected()) {
                            urgencyLevel = 0;
                        } else if (mediumButton.isSelected()) {
                            urgencyLevel = 1;
                        } else if (highButton.isSelected()) {
                            urgencyLevel = 2;
                        }
        
                        if (!title.isEmpty() && !description.isEmpty() && urgencyLevel != -1) {
                            MaintenanceRequest newRequest = new MaintenanceRequest(requests.size() + 1, title, description, urgencyLevel, "New Request");
                            requests.add(newRequest);
                            JOptionPane.showMessageDialog(submitPanel, "Request submitted successfully!");
                            submitFrame.dispose();
                        } else {
                            JOptionPane.showMessageDialog(submitPanel, "Please fill in all fields and select an urgency level.");
                        }
                    }
                });
        
                submitFrame.setVisible(true);
            }
        
            private static void showAssignRequestsWindow() {
                JFrame assignFrame = new JFrame("Assign Requests");
                assignFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                assignFrame.setSize(600, 400);
        
                JPanel assignPanel = new JPanel(new BorderLayout());
                assignFrame.add(assignPanel);
        
                // Table for displaying requests
                String[] columnNames = {"ID", "Tenant Name", "Issue Description", "Urgency Level", "Request Type", "Assigned Staff"};
                String[][] data = new String[requests.size()][6];
                for (int i = 0; i < requests.size(); i++) {
                    MaintenanceRequest request = requests.get(i);
                    data[i][0] = String.valueOf(request.getRequestId());
                    data[i][1] = request.getTenantName();
                    data[i][2] = request.getIssueDescription();
                    data[i][3] = String.valueOf(request.getUrgencyLevel());
                    data[i][4] = request.getRequestType();
                    data[i][5] = request.getAssignedStaff() != null ? request.getAssignedStaff().getName() : "Unassigned";
                }
        
                JTable requestTable = new JTable(data, columnNames);
                JScrollPane scrollPane = new JScrollPane(requestTable);
                requestTable.setFillsViewportHeight(true);
                assignPanel.add(scrollPane, BorderLayout.CENTER);
        
                // Panel for assigning staff
                JPanel staffAssignPanel = new JPanel(new FlowLayout());
                assignPanel.add(staffAssignPanel, BorderLayout.SOUTH);
        
                JLabel staffLabel = new JLabel("Assign to: ");
                staffAssignPanel.add(staffLabel);
        
                JComboBox<String> staffComboBox = new JComboBox<>();
                for (Staff staff : staffList) {
                    staffComboBox.addItem(staff.getName());
                }
                staffAssignPanel.add(staffComboBox);
        
                JButton assignButton = new JButton("Assign");
                staffAssignPanel.add(assignButton);
        
                // Action listener for the assign button
                assignButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int selectedRow = requestTable.getSelectedRow();
                        if (selectedRow != -1) {
                            String selectedStaffName = (String) staffComboBox.getSelectedItem();
                            MaintenanceRequest selectedRequest = requests.get(selectedRow);
        
                            // Assign the request to the selected staff
                            for (Staff staff : staffList) {
                                if (staff.getName().equals(selectedStaffName)) {
                                    staff.assignRequestToStaff(selectedRequest);
                                    selectedRequest.setAssignedStaff(staff);
                                    JOptionPane.showMessageDialog(assignPanel, "Request assigned to " + selectedStaffName);
                                    // Update the table to reflect the assignment
                                    requestTable.setValueAt(selectedStaffName, selectedRow, 5);
                                    break;
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(assignPanel, "Please select a request to assign.");
                        }
                    }
                });
        
                assignFrame.setVisible(true);
            }
        
            private static void showRequestsTable() {
                JFrame requestFrame = new JFrame("Maintenance Requests");
                requestFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                requestFrame.setSize(600, 400);
        
                JPanel requestPanel = new JPanel(new BorderLayout());
                requestFrame.add(requestPanel);
        
                String[] columnNames = {"ID", "Tenant Name", "Issue Description", "Urgency Level", "Request Type", "Assigned Staff"};
        
                String[][] data = new String[requests.size()][6];
                for (int i = 0; i < requests.size(); i++) {
                    MaintenanceRequest request = requests.get(i);
                    data[i][0] = String.valueOf(request.getRequestId());
                    data[i][1] = request.getTenantName();
                    data[i][2] = request.getIssueDescription();
                    data[i][3] = String.valueOf(request.getUrgencyLevel());
                    data[i][4] = request.getRequestType();
                    data[i][5] = request.getAssignedStaff() != null ? request.getAssignedStaff().getName() : "Unassigned";
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
        
                    
