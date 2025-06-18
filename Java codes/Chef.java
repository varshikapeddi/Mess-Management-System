import java.util.Scanner;

public class Chef extends User {
    private final Inventory inventory;
    private final Feedback feedback;
    private final Menu menu;
    private final CounterService counterService;
    private final Dish dish;

    public Chef(String userID, String name, String email, String password, Inventory inventory, Feedback feedback, Menu menu, CounterService counterService,Dish dish) {
        super(userID, name, email, password);
        this.inventory = inventory;
        this.feedback = feedback;
        this.menu = menu;
        this.counterService = counterService;
        this.dish = dish;
    }


    @Override
    public void viewDashboard() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Chef Dashboard!");

        while (true) {
            // Display options
            System.out.println("\nChoose an option:");
            System.out.println("1. Update Inventory");
            System.out.println("2. View Menu for Whole Day");
            System.out.println("3. View Menu for Meal to Prepare");
            System.out.println("4. Add Dish");
            System.out.println("5. View Feedback");
            System.out.println("6. View Inventory");
            System.out.println("7. Exit Dashboard");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1: // Update Inventory
                    System.out.println("\n--- Update Inventory ---");
                    System.out.print("Enter Item Name: ");
                    String itemName = scanner.nextLine();
                    System.out.print("Enter Quantity to Add: ");
                    int quantity = scanner.nextInt();
                    inventory.updateItem(itemName, quantity);
                    break;

                case 2: // View Menu for Whole Day
                    System.out.println("\n--- Menu for the Whole Day ---");
                    menu.displayMenuForDate(getCurrentDate());
                    break;

                case 3: // View Menu for Meal to Prepare
                    System.out.println("\n--- Menu for the Next Meal ---");
                    menu.viewNextMealMenu();
                    break;

                case 4: // Add Dish
                    System.out.println("Enter Dish Name: ");
                    String dishName = scanner.nextLine();

                    System.out.println("Enter Ingredient 1: ");
                    String ingredient1 = scanner.nextLine();
                    System.out.println("Enter Quantity 1: ");
                    int quantity1 = scanner.nextInt();
                    scanner.nextLine(); // consume leftover newline

                    System.out.println("Enter Ingredient 2: ");
                    String ingredient2 = scanner.nextLine();
                    System.out.println("Enter Quantity 2: ");
                    int quantity2 = scanner.nextInt();
                    scanner.nextLine(); // consume leftover newline

                    System.out.println("Enter Ingredient 3: ");
                    String ingredient3 = scanner.nextLine();
                    System.out.println("Enter Quantity 3: ");
                    int quantity3 = scanner.nextInt();

                    // Call addDish from the Dish class
                    dish.addDish(dishName, ingredient1, quantity1, ingredient2, quantity2, ingredient3, quantity3);
                    break;
                case 5: // View Feedback
                    System.out.println("\n--- Feedback for Dishes ---");
                    feedback.displayAllFeedback();
                    break;

                case 6: // View Inventory
                    System.out.println("\n--- Current Inventory ---");
                    inventory.displayInventory();
                    break;

                case 7: // Exit Dashboard
                    System.out.println("Exiting Chef Dashboard.");
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
