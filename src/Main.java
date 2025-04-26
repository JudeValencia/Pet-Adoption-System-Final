import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PetManagement petManager = new PetManagement();
        CustomerManagement customerManager = new CustomerManagement();
        LoginPage loginPage = new LoginPage();
        UserInterface user = new UserInterface();
        AdoptionManagement adoptionManager = new AdoptionManagement();

        boolean isRunning = true;

        while (isRunning) {
            System.out.println("\n--- (Feature Testing) Pet Management System ---");
            System.out.println("1. Add Pet");
            System.out.println("2. View, Update, and Remove Pets");
            System.out.println("3. Search Pet by Type");
            System.out.println("4. Exit");
            System.out.println("5. Add customer (Feature Testing)");
            System.out.println("6. View, Update, and Remove Customers (Feature Testing)");
            System.out.println("7. Login Page (Feature Testing)");
            System.out.println("8. User Interface (Feature Testing)");
            System.out.println("9. Adoption Management - User Panel(Feature Testing)");
            System.out.println("10. Adoption Management - Admin Panel (Feature Testing)");


            try {
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1 -> petManager.add();
                    case 2 -> petManager.view();
                    case 3 -> petManager.searchPet();
                    case 4 -> {
                        System.out.println("Exiting...");
                        isRunning = false;
                    }
                    case 5 -> customerManager.add();
                    case 6 -> customerManager.view();
                    case 7 -> loginPage.logIn();
                    case 8 -> user.userInterface();
                    case 9 -> adoptionManager.nonAdminUserInterface();
                    case 10 -> adoptionManager.adminUserInterface();
                    default -> System.out.println("Must Enter an Integer from 1 - 6");
                }
            }
            catch (InputMismatchException e) {
                System.out.println("You can only print integers from 1 to 6");
            }
            catch (RuntimeException e) {
                System.out.println("Invalid Input");
            }
        }
    }
}
