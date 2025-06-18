import java.sql.*;

public class Inventory {
    private final Connection con;

    public Inventory(Connection con) {
        this.con = con;
    }

    // Add an inventory item
    public void addItem(String itemName, int quantity) {
        String query = "INSERT INTO Inventory (ItemName, Quantity) VALUES (?, ?)";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, itemName);
            ps.setInt(2, quantity);
            ps.executeUpdate();
            System.out.println("Item added successfully!");
        } catch (SQLException e) {
            System.err.println("Error adding item: " + e.getMessage());
        }
    }
    public void updateItem(String itemName, int quantity) {
        String query = "UPDATE Inventory SET Quantity = Quantity + ? WHERE ItemName = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, quantity);
            ps.setString(2, itemName);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Inventory updated successfully!");
            } else {
                System.out.println("Item not found in inventory.");
            }
        } catch (SQLException e) {
            System.err.println("Error updating inventory: " + e.getMessage());
        }
    }


    // Update an inventory item's quantity
    public void updateItemQuantity(String itemName, int newQuantity) {
        String query = "UPDATE Inventory SET Quantity = ? WHERE ItemName = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, newQuantity);
            ps.setString(2, itemName);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Item updated successfully!");
            } else {
                System.out.println("Item not found in inventory.");
            }
        } catch (SQLException e) {
            System.err.println("Error updating item: " + e.getMessage());
        }
    }

    // Display all inventory items in tabular format
    public void displayInventory() {
        String query = "SELECT * FROM Inventory";
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.printf("%-20s %-10s%n", "ItemName", "Quantity");
            System.out.println("------------------------------");

            while (rs.next()) {
                System.out.printf("%-20s %-10d%n",
                        rs.getString("ItemName"),
                        rs.getInt("Quantity"));
            }
        } catch (SQLException e) {
            System.err.println("Error displaying inventory: " + e.getMessage());
        }
    }

    // Remove an inventory item
    public void removeItem(String itemName) {
        String query = "DELETE FROM Inventory WHERE ItemName = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, itemName);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Item removed successfully!");
            } else {
                System.out.println("Item not found in inventory.");
            }
        } catch (SQLException e) {
            System.err.println("Error removing item: " + e.getMessage());
        }
    }
}
