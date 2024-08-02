import java.awt.*;
import javax.swing.*;

public class Main {
    private static final RequestManager requestManager = new RequestManager();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Maintenance Request Management System");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);

            Container container = frame.getContentPane();
            container.setLayout(new BorderLayout());

            JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
            container.add(panel, BorderLayout.CENTER);

            JButton createButton = new JButton("Create Request");
            JButton viewButton = new JButton("View Requests");
            JButton updateButton = new JButton("Update Request");
            JButton deleteButton = new JButton("Delete Request");

            panel.add(createButton);
            panel.add(viewButton);
            panel.add(updateButton);
            panel.add(deleteButton);

            JTextArea outputArea = new JTextArea();
            outputArea.setEditable(false);
            container.add(new JScrollPane(outputArea), BorderLayout.SOUTH);

            createButton.addActionListener(e -> createRequest(outputArea));
            viewButton.addActionListener(e -> viewRequests(outputArea));
            updateButton.addActionListener(e -> updateRequest(outputArea));
            deleteButton.addActionListener(e -> deleteRequest(outputArea));

            frame.setVisible(true);
        });
    }

    private static void createRequest(JTextArea outputArea) {
        JTextField descriptionField = new JTextField();
        JTextField urgencyField = new JTextField();
        Object[] message = {
                "Issue Description:", descriptionField,
                "Urgency Level (0: Low, 1: Medium, 2: High):", urgencyField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Create Maintenance Request", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String description = descriptionField.getText();
                int urgency = Integer.parseInt(urgencyField.getText());
                MaintenanceRequest request = new MaintenanceRequest(requestManager.viewRequests().size() + 1, "Tenant Name", description, urgency);
                requestManager.addRequest(request);
                outputArea.setText("Maintenance request created.\n");
            } catch (NumberFormatException ex) {
                outputArea.setText("Invalid input for urgency level. Please enter a number (0: Low, 1: Medium, 2: High).\n");
            }
        }
    }

    private static void viewRequests(JTextArea outputArea) {
        StringBuilder sb = new StringBuilder();
        for (MaintenanceRequest request : requestManager.viewRequests()) {
            sb.append(request.getDetails()).append("\n");
        }
        outputArea.setText(sb.toString());
    }

    private static void updateRequest(JTextArea outputArea) {
        JTextField requestIdField = new JTextField();
        JTextField descriptionField = new JTextField();
        JTextField urgencyField = new JTextField();
        Object[] message = {
                "Request ID:", requestIdField,
                "New Issue Description:", descriptionField,
                "New Urgency Level (0: Low, 1: Medium, 2: High):", urgencyField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Update Maintenance Request", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int requestId = Integer.parseInt(requestIdField.getText());
                String description = descriptionField.getText();
                int urgency = Integer.parseInt(urgencyField.getText());
                requestManager.updateRequest(requestId, description, urgency);
                outputArea.setText("Maintenance request updated.\n");
            } catch (NumberFormatException ex) {
                outputArea.setText("Invalid input. Please ensure all fields are correctly filled.\n");
            }
        }
    }

    private static void deleteRequest(JTextArea outputArea) {
        JTextField requestIdField = new JTextField();
        Object[] message = {
                "Request ID:", requestIdField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Delete Maintenance Request", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int requestId = Integer.parseInt(requestIdField.getText());
                requestManager.deleteRequest(requestId);
                outputArea.setText("Maintenance request deleted.\n");
            } catch (NumberFormatException ex) {
                outputArea.setText("Invalid input. Please ensure the Request ID is a number.\n");
            }
        }
    }
}
