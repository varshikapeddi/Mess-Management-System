import java.sql.*;

public class CounterService {
    private final Connection con;

    public CounterService(Connection con) {
        this.con = con;
    }

    // Add a new counter
    public void addCounter(int counterID, String status, String type) {
        String query = "INSERT INTO Counters (CounterID, Status, Type) VALUES (?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, counterID);
            ps.setString(2, status);
            ps.setString(3, type);
            ps.executeUpdate();
            System.out.println("Counter added successfully!");
        } catch (SQLException e) {
            System.err.println("Error adding counter: " + e.getMessage());
        }
    }


    // Update the status of a counter
    public void updateCounterStatus(int counterID, String newStatus) {
        String query = "UPDATE Counters SET Status = ? WHERE CounterID = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, newStatus);
            ps.setInt(2, counterID);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Counter status updated successfully!");
            } else {
                System.out.println("Counter not found.");
            }
        } catch (SQLException e) {
            System.err.println("Error updating counter status: " + e.getMessage());
        }
    }


    // Display all counters
    public void displayAllCounters() {
        String query = "SELECT * FROM Counter";
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.printf("%-15s %-15s %-10s%n", "CounterID", "Status", "Type");
            System.out.println("---------------------------------------------");

            while (rs.next()) {
                System.out.printf("%-15s %-15s %-10s%n",
                        rs.getString("CounterID"),
                        rs.getString("Status"),
                        rs.getString("Type"));
            }
        } catch (SQLException e) {
            System.err.println("Error displaying counters: " + e.getMessage());
        }
    }

    // Display active counters only
    public void displayActiveCounters() {
        String query = "SELECT * FROM Counters WHERE Status = 'Open'";
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.printf("%-10s %-15s %-10s%n", "CounterID", "Status", "Type");
            System.out.println("---------------------------------------");

            while (rs.next()) {
                System.out.printf("%-10d %-15s %-10s%n",
                        rs.getInt("CounterID"),
                        rs.getString("Status"),
                        rs.getString("Type"));
            }
        } catch (SQLException e) {
            System.err.println("Error displaying active counters: " + e.getMessage());
        }
    }


    // Remove a counter
    public void removeCounter(String counterID) {
        String query = "DELETE FROM Counter WHERE CounterID = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, counterID);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Counter removed successfully!");
            } else {
                System.out.println("Counter not found.");
            }
        } catch (SQLException e) {
            System.err.println("Error removing counter: " + e.getMessage());
        }
    }

    // Tap in a student to the mess population
    public void tapIn(String userID) {
        String query = "INSERT INTO MessPopulation (UserID) VALUES (?)";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, userID);
            ps.executeUpdate();
            System.out.println("User " + userID + " tapped in successfully!");
        } catch (SQLException e) {
            System.err.println("Error tapping in: " + e.getMessage());
        }
    }


    // Get the current mess population
    public int getCurrentMessPopulation() {
        String query = "SELECT COUNT(*) AS Population FROM MessPopulation";
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                return rs.getInt("Population");
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving current mess population: " + e.getMessage());
        }
        return 0;
    }
    public void deleteCounter(int counterID) {
        String query = "DELETE FROM Counters WHERE CounterID = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, counterID);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Counter deleted successfully!");
            } else {
                System.out.println("Counter not found.");
            }
        } catch (SQLException e) {
            System.err.println("Error deleting counter: " + e.getMessage());
        }
    }


    // Tap out a student from the mess population
    public void tapOut(String userID) {
        String query = "DELETE FROM MessPopulation WHERE UserID = ? LIMIT 1";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, userID);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User " + userID + " tapped out successfully!");
            } else {
                System.out.println("User " + userID + " not found in the mess population.");
            }
        } catch (SQLException e) {
            System.err.println("Error tapping out: " + e.getMessage());
        }
    }


    // Set multiple counters as active or inactive
    public void setActiveCounters(String[] counterIDs, String newStatus) {
        String query = "UPDATE Counter SET Status = ? WHERE CounterID = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            for (String counterID : counterIDs) {
                ps.setString(1, newStatus);
                ps.setString(2, counterID);
                ps.executeUpdate();
            }
            System.out.println("Counters updated to " + newStatus + " status successfully!");
        } catch (SQLException e) {
            System.err.println("Error updating counter statuses: " + e.getMessage());
        }
    }
}
