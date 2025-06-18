import java.sql.*;
import java.time.LocalTime;

public class Menu {
    private final Connection con;

    public Menu(Connection con) {
        this.con = con;
    }

    // View the next meal menu
    public void viewNextMealMenu() {
        try {
            // Get the current time
            LocalTime currentTime = LocalTime.now();
            String nextMealTime = "";

            // Determine the next meal based on current time
            if (currentTime.isBefore(LocalTime.of(10, 0))) {
                nextMealTime = "Breakfast";
            } else if (currentTime.isBefore(LocalTime.of(14, 0))) {
                nextMealTime = "Lunch";
            } else if (currentTime.isBefore(LocalTime.of(18, 0))) {
                nextMealTime = "Snack";
            } else {
                nextMealTime = "Dinner";
            }

            // Query to fetch the next meal menu
            String query = "SELECT * FROM Menu WHERE Time = ? AND Date = CURDATE()";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, nextMealTime);
            ResultSet rs = ps.executeQuery();

            // Display the menu for the next meal
            System.out.printf("Next Meal: %s%n", nextMealTime);
            System.out.printf("%-15s %-20s %-10s %-15s%n", "MealName", "DishName", "Time", "Date");
            System.out.println("---------------------------------------------------------------");

            boolean hasMenu = false;
            while (rs.next()) {
                hasMenu = true;
                System.out.printf("%-15s %-20s %-10s %-15s%n",
                        rs.getString("MealName"),
                        rs.getString("DishName"),
                        rs.getString("Time"),
                        rs.getDate("Date").toString());
            }

            if (!hasMenu) {
                System.out.println("No menu available for the next meal.");
            }

            rs.close();
        } catch (SQLException e) {
            System.err.println("Error fetching the next meal menu: " + e.getMessage());
        }
    }


// Add a meal to the menu
    public void addMeal(String mealName, String dishName, String time, String date) {
        String query = "INSERT INTO Menu (MealName, DishName, Time, Date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, mealName);
            ps.setString(2, dishName);
            ps.setString(3, time);
            ps.setString(4, date);
            ps.executeUpdate();
            System.out.println("Meal added to the menu successfully!");
        } catch (SQLException e) {
            System.err.println("Error adding meal to the menu: " + e.getMessage());
        }
    }

    public void addDish(String dishName, String inventoryRequired, String dishType) {
        String query = "INSERT INTO Menu (DishName, InventoryRequired, DishType) VALUES (?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, dishName);
            ps.setString(2, inventoryRequired);
            ps.setString(3, dishType);
            ps.executeUpdate();
            System.out.println("Dish added successfully!");
        } catch (SQLException e) {
            System.err.println("Error adding dish: " + e.getMessage());
        }
    }


    // Display the menu for a specific date
    public void displayMenuForDate(String date) {
        String query = "SELECT * FROM Menu WHERE Date = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, date);
            ResultSet rs = ps.executeQuery();

            System.out.printf("%-15s %-20s %-10s %-15s%n", "MealName", "DishName", "Time", "Date");
            System.out.println("---------------------------------------------------------------");

            while (rs.next()) {
                System.out.printf("%-15s %-20s %-10s %-15s%n",
                        rs.getString("MealName"),
                        rs.getString("DishName"),
                        rs.getString("Time"),
                        rs.getDate("Date").toString());
            }
        } catch (SQLException e) {
            System.err.println("Error displaying menu: " + e.getMessage());
        }
    }

    // Remove a meal from the menu
    public void removeMeal(String mealName, String date) {
        String query = "DELETE FROM Menu WHERE MealName = ? AND Date = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, mealName);
            ps.setString(2, date);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Meal removed from the menu successfully!");
            } else {
                System.out.println("Meal not found for the specified date.");
            }
        } catch (SQLException e) {
            System.err.println("Error removing meal: " + e.getMessage());
        }
    }
}
