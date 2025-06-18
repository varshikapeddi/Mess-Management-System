import java.util.Scanner;

public class Student extends User {
    private final Menu menu;
    private final CounterService counterService;
    private final Feedback feedback;

    public Student(String userID, String name, String email, String password, Menu menu, CounterService counterService, Feedback feedback) {
        super(userID, name, email, password);
        this.menu = menu;
        this.counterService = counterService;
        this.feedback = feedback;
    }


    @Override
    public void viewDashboard() {
        Scanner scanner = new Scanner(System.in);

        // Automatically tap in when the student logs in
        counterService.tapIn(userID);

        // Display the mandatory menu
        System.out.println("\n--- Today's Menu ---");
        menu.displayMenuForDate(getCurrentDate());

        while (true) {
            // Display the options
            System.out.println("\nChoose an option:");
            System.out.println("1. View Active Counters");
            System.out.println("2. Submit Feedback");
            System.out.println("3. View Current Mess Population");
            System.out.println("4. Exit Dashboard");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1: // View Active Counters
                    System.out.println("\n--- Active Counters ---");
                    counterService.displayActiveCounters();
                    break;

                case 2: // Submit Feedback
                    System.out.println("\n--- Submit Feedback ---");
                    System.out.print("Enter Dish Name: ");
                    String dishName = scanner.nextLine();
                    System.out.print("Enter Rating (1-5): ");
                    int rating = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter Comments: ");
                    String comments = scanner.nextLine();

                    feedback.addFeedback(userID, dishName, rating, comments);
                    break;

                case 3: // View Current Mess Population
                    int currentPopulation = counterService.getCurrentMessPopulation();
                    System.out.println("\nCurrent Mess Population: " + currentPopulation);
                    break;

                case 4: // Exit Dashboard
                    System.out.println("Exiting Student Dashboard.");
                    // Tap out when the student exits
                    counterService.tapOut(userID);
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // Helper method to get the current date in YYYY-MM-DD format
    private String getCurrentDate() {
        java.util.Date today = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(today);
    }
}
