import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

    public class Dish {
        private final Connection con;

        public Dish(Connection con) {
            this.con = con;
        }

        // Method to add a dish with all required fields
        public void addDish(String dishName, String ingredient1, int quantity1, String ingredient2, int quantity2, String ingredient3, int quantity3) {
            String query = "INSERT INTO Dishes (DishName, Ingredient1, Quantity1, Ingredient2, Quantity2, Ingredient3, Quantity3) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setString(1, dishName);
                ps.setString(2, ingredient1);
                ps.setInt(3, quantity1);
                ps.setString(4, ingredient2);
                ps.setInt(5, quantity2);
                ps.setString(6, ingredient3);
                ps.setInt(7, quantity3);
                ps.executeUpdate();
                System.out.println("Dish added successfully!");
            } catch (SQLException e) {
                System.err.println("Error adding dish: " + e.getMessage());
            }
        }

    // Display all dishes in tabular format
    public void displayDishes() {
        String query = "SELECT * FROM Dishes";
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.printf("%-20s %-20s %-10s %-20s %-10s %-20s %-10s%n",
                    "DishName", "Ingredient1", "Qty1", "Ingredient2", "Qty2", "Ingredient3", "Qty3");
            System.out.println("--------------------------------------------------------------------------------");

            while (rs.next()) {
                System.out.printf("%-20s %-20s %-10d %-20s %-10d %-20s %-10d%n",
                        rs.getString("DishName"),
                        rs.getString("Ingredient1"), rs.getInt("Quantity1"),
                        rs.getString("Ingredient2"), rs.getInt("Quantity2"),
                        rs.getString("Ingredient3"), rs.getInt("Quantity3"));
            }
        } catch (SQLException e) {
            System.err.println("Error displaying dishes: " + e.getMessage());
        }
    }


    // Remove a dish
    public void removeDish(String dishName) {
        String query = "DELETE FROM Dishes WHERE DishName = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, dishName);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Dish removed successfully!");
            } else {
                System.out.println("Dish not found.");
            }
        } catch (SQLException e) {
            System.err.println("Error removing dish: " + e.getMessage());
        }
    }
}
