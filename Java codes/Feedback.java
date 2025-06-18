import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.logging.Logger;

public class Feedback {
    private final Connection con; // Connection is passed once and reused throughout
    private static final Logger logger = Logger.getLogger(Feedback.class.getName());

    public Feedback(Connection con) {
        this.con = con;
    }

    // Add feedback for a dish
    public void addFeedback(String userID, String dishName, int rating, String comments) {
        String query = "INSERT INTO Feedback (UserID, DishName, Rating, Comments) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, userID);
            ps.setString(2, dishName);
            ps.setInt(3, rating);
            ps.setString(4, comments);
            ps.executeUpdate();
            System.out.println("Feedback submitted successfully!");
        } catch (SQLException e) {
            logger.severe("An error occurred: " + e.getMessage());

        }
    }

    // Display all feedback in tabular format
    public void displayAllFeedback() {
        String query = "SELECT * FROM Feedback";
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.printf("%-10s %-20s %-20s %-10s %-30s %-20s%n",
                    "FeedbackID", "UserID", "DishName", "Rating", "Comments", "CreatedAt");
            System.out.println("-----------------------------------------------------------------------------------------");

            while (rs.next()) {
                System.out.printf("%-10d %-20s %-20s %-10d %-30s %-20s%n",
                        rs.getInt("FeedbackID"),
                        rs.getString("UserID"),
                        rs.getString("DishName"),
                        rs.getInt("Rating"),
                        rs.getString("Comments"),
                        rs.getTimestamp("CreatedAt"));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving feedback: " + e.getMessage());
        }
    }


    // Display feedback for a specific dish in tabular format
    public void displayFeedbackForDish(String dishName) {
        String query = "SELECT * FROM Feedback WHERE DishName = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, dishName);
            ResultSet rs = ps.executeQuery();

            System.out.printf("%-20s %-10s %-30s %-20s%n",
                    "UserID", "DishName", "Rating", "Comments", "CreatedAt");
            System.out.println("-------------------------------------------------------------------------------");

            while (rs.next()) {
                System.out.printf("%-20s %-10d %-30s %-20s%n",
                        rs.getString("UserID"),
                        rs.getInt("Rating"),
                        rs.getString("Comments"),
                        rs.getTimestamp("CreatedAt"));
            }
        } catch (SQLException e) {
            logger.severe("An error occurred: " + e.getMessage());
        }
    }
}
