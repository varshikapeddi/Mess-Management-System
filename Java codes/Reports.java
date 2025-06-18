import java.io.*;
import java.sql.*;
import java.util.*;

public class Reports {
        private final Connection con;

        public Reports(Connection con) {
            this.con = con;
        }


    // Generate a report and save it as a text file
    public void generateReport(String reportType, String dateRange, String filePath) {
        String query = "";
        switch (reportType.toLowerCase()) {
            case "feedback":
                query = "SELECT * FROM Feedback WHERE CreatedAt BETWEEN ? AND ?";
                break;
            case "inventory":
                query = "SELECT * FROM Inventory";
                break;
            case "staff":
                query = "SELECT * FROM Staff";
                break;
            default:
                System.out.println("Invalid report type. Available types: Feedback, Inventory, Staff.");
                return;
        }

        try (PreparedStatement ps = con.prepareStatement(query)) {
            if (reportType.equalsIgnoreCase("feedback")) {
                String[] dates = dateRange.split(" to ");
                ps.setString(1, dates[0]);
                ps.setString(2, dates[1]);
            }

            ResultSet rs = ps.executeQuery();
            File file = new File(filePath);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                // Write report headers
                writer.write("Report Type: " + reportType);
                writer.newLine();
                writer.write("Date Range: " + dateRange);
                writer.newLine();
                writer.write("-------------------------------------------");
                writer.newLine();

                // Write report data
                switch (reportType.toLowerCase()) {
                    case "feedback":
                        writer.write(String.format("%-10s %-20s %-15s %-10s %-30s %-20s%n",
                                "FeedbackID", "UserID", "DishName", "Rating", "Comments", "CreatedAt"));
                        while (rs.next()) {
                            writer.write(String.format("%-10d %-20s %-15s %-10d %-30s %-20s%n",
                                    rs.getInt("FeedbackID"),
                                    rs.getString("UserID"),
                                    rs.getString("DishName"),
                                    rs.getInt("Rating"),
                                    rs.getString("Comments"),
                                    rs.getTimestamp("CreatedAt")));
                        }
                        break;
                    case "inventory":
                        writer.write(String.format("%-20s %-10s%n", "ItemName", "Quantity"));
                        while (rs.next()) {
                            writer.write(String.format("%-20s %-10d%n",
                                    rs.getString("ItemName"),
                                    rs.getInt("Quantity")));
                        }
                        break;
                    case "staff":
                        writer.write(String.format("%-10s %-30s %-15s%n", "StaffID", "TaskAssigned", "Shift"));
                        while (rs.next()) {
                            writer.write(String.format("%-10s %-30s %-15s%n",
                                    rs.getString("StaffID"),
                                    rs.getString("TaskAssigned") != null ? rs.getString("TaskAssigned") : "No Task Assigned",
                                    rs.getString("Shift")));
                        }
                        break;
                }
                System.out.println("Report generated and saved to: " + filePath);
            }
        } catch (Exception e) {
            System.err.println("Error generating report: " + e.getMessage());
        }
    }

    // Simulate sending a report to college management
    public void sendReportToCollegeManagement(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("Report file does not exist. Please generate it first.");
            return;
        }

        try {
            // Simulating sending the report (e.g., email or upload)
            System.out.println("Sending report to College Management...");
            System.out.println("Report sent successfully! File: " + filePath);
        } catch (Exception e) {
            System.err.println("Error sending report: " + e.getMessage());
        }
    }
}
