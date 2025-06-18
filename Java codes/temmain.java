import java.sql.*;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        try {
            // Establish database connection
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/messmangement", "root", "1234");
            System.out.println("Database connected successfully!");

            // Initialize shared services
            Feedback feedback = new Feedback(con);
            Inventory inventory = new Inventory(con);
            Menu menu = new Menu(con);
            Reports reports = new Reports(con);
            CounterService counterService = new CounterService(con);
            Staff staff = new Staff(con);
            Dish dish = new Dish(con);
            Scanner scanner = new Scanner(System.in);
            // Prompt user for login credentials
            System.out.println("Welcome to the College Mess Management System!");
            System.out.print("Enter User ID: ");
            String userID = scanner.nextLine();
            System.out.print("Enter Password: ");
            String password = scanner.nextLine();

            // Query the database for the user
            String query = "SELECT * FROM users WHERE UserID = ? AND Password = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, userID);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            User currentUser = null;

            // Check if user exists in the database
            if (rs.next()) {
                String name = rs.getString("Name");
                String email = rs.getString("Email");
                String role = rs.getString("Role");

                // Create user instance based on role
                switch (role.toLowerCase()) {
                    case "student":
                        currentUser = new Student(userID, name, email, password, menu, counterService, feedback);
                        break;
                    case "chef":
                        currentUser = new Chef(userID, name, email, password, inventory, feedback, menu, counterService,dish);
                        break;
                    case "manager":
                        currentUser = new Manager(userID, name, email, password, inventory, counterService, feedback, menu, reports,staff);
                        break;
                    case "college":
                        currentUser = new CollegeManagement(userID, name, email, password, feedback);
                        break;
                    default:
                        System.out.println("Invalid user role in the database.");
                }
            } else {
                System.out.println("Invalid User ID or Password. Please try again.");
            }

            // Display dashboard if user is authenticated
            if (currentUser != null) {
                currentUser.viewDashboard();
            }

            // Close resources
            rs.close();
            ps.close();
            con.close();
            scanner.close();
        } catch (SQLException e) {
            System.err.println("Database connection error: " + e.getMessage());
        }
    }
}
