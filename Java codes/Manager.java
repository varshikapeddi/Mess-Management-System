import java.util.Scanner;

public class Manager extends User {
    private final Inventory inventory;        // Reference to Inventory class
    private final CounterService counterService; // Reference to CounterService class
    private final Feedback feedback;         // Reference to Feedback class
    private final Menu menu;                 // Reference to Menu class
    private final Reports reports;
    private final Staff staff;

    public Manager(String userID, String name, String email, String password,
                   Inventory inventory, CounterService counterService,
                   Feedback feedback, Menu menu, Reports reports,Staff staff) {
        super(userID, name, email, password);
        this.inventory = inventory;
        this.counterService = counterService;
        this.feedback = feedback;
        this.menu = menu;
        this.reports = reports;
        this.staff = staff;
    }

    @Override
    public void viewDashboard() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Manager Dashboard!");

        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. View Feedback");
            System.out.println("2. Manage Menu");
            System.out.println("3. Assign Tasks to Staff");
            System.out.println("4. Generate and Send Report");
            System.out.println("5. Manage Counters");
            System.out.println("6. Manage Inventory");
            System.out.println("7. Exit Dashboard");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1: // View Feedback
                    System.out.println("\n--- Feedback for Dishes ---");
                    feedback.displayAllFeedback();
                    break;

                case 2: // Manage Menu
                    manageMenu(scanner);
                    break;

                case 3: // Assign Tasks to Staff
                    assignTaskToStaff(scanner);
                    break;

                case 4: // Generate and Send Report
                    generateAndSendReport(scanner);
                    break;

                case 5: // Manage Counters
                    manageCounters(scanner);
                    break;

                case 6: // Manage Inventory
                    manageInventory(scanner);
                    break;

                case 7: // Exit Dashboard
                    System.out.println("Exiting Manager Dashboard.");
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // Helper method to generate and send reports
    private void generateAndSendReport(Scanner scanner) {
        System.out.println("\n--- Generate and Send Report ---");
        System.out.print("Enter Report Type (Feedback, Inventory, Staff): ");
        String reportType = scanner.nextLine();

        String dateRange = "";
        if (reportType.equalsIgnoreCase("feedback")) {
            System.out.print("Enter Date Range (YYYY-MM-DD to YYYY-MM-DD): ");
            dateRange = scanner.nextLine();
        }

        System.out.print("Enter File Path to Save the Report: ");
        String filePath = scanner.nextLine();

        // Generate report
        reports.generateReport(reportType, dateRange, filePath);

        // Confirm and send report to College Management
        System.out.print("Do you want to send the report to College Management? (yes/no): ");
        String confirmation = scanner.nextLine();
        if (confirmation.equalsIgnoreCase("yes")) {
            reports.sendReportToCollegeManagement(filePath);
        } else {
            System.out.println("Report not sent.");
        }
    }
    // Helper method to manage the menu
    private void manageMenu(Scanner scanner) {
        while (true) {
            System.out.println("\n--- Manage Menu ---");
            System.out.println("1. Add Meal");
            System.out.println("2. Display Menu for Date");
            System.out.println("3. Remove Meal");
            System.out.println("4. Back to Dashboard");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1: // Add Meal
                    System.out.print("Enter Meal Name (e.g., Breakfast, Lunch): ");
                    String mealName = scanner.nextLine();
                    System.out.print("Enter Dish Name: ");
                    String dishName = scanner.nextLine();
                    System.out.print("Enter Time (Breakfast, Lunch, Dinner, Snack): ");
                    String time = scanner.nextLine();
                    System.out.print("Enter Date (YYYY-MM-DD): ");
                    String date = scanner.nextLine();
                    menu.addMeal(mealName, dishName, time, date);
                    break;

                case 2: // Display Menu for Date
                    System.out.print("Enter Date (YYYY-MM-DD): ");
                    String menuDate = scanner.nextLine();
                    menu.displayMenuForDate(menuDate);
                    break;

                case 3: // Remove Meal
                    System.out.print("Enter Meal Name: ");
                    String removeMealName = scanner.nextLine();
                    System.out.print("Enter Date (YYYY-MM-DD): ");
                    String removeDate = scanner.nextLine();
                    menu.removeMeal(removeMealName, removeDate);
                    break;

                case 4: // Back to Dashboard
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // Helper method to assign tasks to staff
    private void assignTaskToStaff(Scanner scanner) {
        System.out.println("\n--- Assign Tasks to Staff ---");
        System.out.print("Enter Staff ID: ");
        String staffID = scanner.nextLine();
        System.out.print("Enter Task Description: ");
        String taskDescription = scanner.nextLine();
        staff.assignTask(staffID, taskDescription); // Call to Staff class
    }

    // Helper method to manage counters
    private void manageCounters(Scanner scanner) {
        while (true) {
            System.out.println("\n--- Manage Counters ---");
            System.out.println("1. View Active Counters");
            System.out.println("2. Update Counter Status");
            System.out.println("3. Add Counter");
            System.out.println("4. Delete Counter");
            System.out.println("5. Back to Dashboard");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    counterService.displayActiveCounters();
                    break;

                case 2:
                    System.out.print("Enter Counter ID: ");
                    int counterID = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter New Status (Open, Closed, In Use): ");
                    String newStatus = scanner.nextLine();
                    counterService.updateCounterStatus(counterID, newStatus);
                    break;

                case 3:
                    System.out.print("Enter Counter ID: ");
                    int newCounterID = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Status (Open, Closed, In Use): ");
                    String counterStatus = scanner.nextLine();
                    System.out.print("Enter Type (Veg/Non-Veg): ");
                    String counterType = scanner.nextLine();
                    counterService.addCounter(newCounterID, counterStatus, counterType);
                    break;

                case 4:
                    System.out.print("Enter Counter ID to Delete: ");
                    int deleteCounterID = scanner.nextInt();
                    scanner.nextLine();
                    counterService.deleteCounter(deleteCounterID);
                    break;

                case 5:
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // Helper method to manage inventory
    private void manageInventory(Scanner scanner) {
        while (true) {
            System.out.println("\n--- Manage Inventory ---");
            System.out.println("1. View Inventory");
            System.out.println("2. Update Inventory");
            System.out.println("3. Back to Dashboard");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    inventory.displayInventory();
                    break;

                case 2:
                    System.out.print("Enter Item Name: ");
                    String itemName = scanner.nextLine();
                    System.out.print("Enter Quantity to Add: ");
                    int quantity = scanner.nextInt();
                    scanner.nextLine();
                    inventory.updateItem(itemName, quantity);
                    break;

                case 3:
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
