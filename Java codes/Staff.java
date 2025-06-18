import java.sql.*;

public class Staff {
    private final Connection con;

    public Staff(Connection con) {
        this.con = con;
    }

    // Add a new staff member
    public void addStaff(String staffID, String shift) {
        String query = "INSERT INTO Staff (StaffID, Shift) VALUES (?, ?)";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, staffID);
            ps.setString(2, shift);
            ps.executeUpdate();
            System.out.println("Staff member added successfully!");
        } catch (SQLException e) {
            System.err.println("Error adding staff member: " + e.getMessage());
        }
    }

    // Assign a task to a staff member
    public void assignTask(String staffID, String task) {
        String query = "UPDATE Staff SET TaskAssigned = ? WHERE StaffID = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, task);
            ps.setString(2, staffID);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Task assigned successfully!");
            } else {
                System.out.println("Staff member not found.");
            }
        } catch (SQLException e) {
            System.err.println("Error assigning task: " + e.getMessage());
        }
    }

    // Update the shift for a staff member
    public void updateShift(String staffID, String shift) {
        String query = "UPDATE Staff SET Shift = ? WHERE StaffID = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, shift);
            ps.setString(2, staffID);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Shift updated successfully!");
            } else {
                System.out.println("Staff member not found.");
            }
        } catch (SQLException e) {
            System.err.println("Error updating shift: " + e.getMessage());
        }
    }

    // Display all staff details in tabular format
    public void displayAllStaff() {
        String query = "SELECT * FROM Staff";
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.printf("%-10s %-30s %-15s%n", "StaffID", "TaskAssigned", "Shift");
            System.out.println("----------------------------------------------------");

            while (rs.next()) {
                System.out.printf("%-10s %-30s %-15s%n",
                        rs.getString("StaffID"),
                        rs.getString("TaskAssigned") != null ? rs.getString("TaskAssigned") : "No Task Assigned",
                        rs.getString("Shift"));
            }
        } catch (SQLException e) {
            System.err.println("Error displaying staff details: " + e.getMessage());
        }
    }

    // Remove a staff member
    public void removeStaff(String staffID) {
        String query = "DELETE FROM Staff WHERE StaffID = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, staffID);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Staff member removed successfully!");
            } else {
                System.out.println("Staff member not found.");
            }
        } catch (SQLException e) {
            System.err.println("Error removing staff member: " + e.getMessage());
        }
    }
}
