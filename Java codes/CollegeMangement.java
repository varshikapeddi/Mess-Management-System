import java.io.*;
import java.util.Scanner;

class CollegeManagement extends User {
    private final Feedback feedback; // Reference to Feedback class

    public CollegeManagement(String userID, String name, String email, String password, Feedback feedback) {
        super(userID, name, email, password);
        this.feedback = feedback;
    }

    @Override
    public void viewDashboard() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the College Management Dashboard!");

        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. View Feedback");
            System.out.println("2. View Received Reports");
            System.out.println("3. Exit Dashboard");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1: // View Feedback
                    System.out.println("\n--- Viewing All Feedback ---");
                    feedback.displayAllFeedback(); // Call to Feedback class method
                    break;

                case 2: // View Received Reports
                    viewReceivedReports(scanner); // Call method to view reports
                    break;

                case 3: // Exit Dashboard
                    System.out.println("Exiting College Management Dashboard.");
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // Method to view received reports from file
    private void viewReceivedReports(Scanner scanner) {
        System.out.print("\nEnter the path of the report file to view: ");
        String filePath = scanner.nextLine();

        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("The specified file does not exist. Please provide a valid file path.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            System.out.println("\n--- Report Contents ---");
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading the report file: " + e.getMessage());
        }
    }
}
