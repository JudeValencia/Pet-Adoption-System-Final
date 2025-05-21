package UI_LogIn_Package;

import Adoption_Management_Package.PetManagement;

/**
 * Represents the user interface for a pet adoption management system.
 * It provides menus for both users and administrators to interact with
 * the system for various functionalities such as managing pets, customers,
 * and generating reports.
 */
public class UserInterface extends PetManagement {

    public void logInInterfaceHeader() {

        System.out.println("+------------------------------------------------------+");
        System.out.println("|                                                      |");
        System.out.println("|            🐾🐶 Welcome to Pet Haven 🐱🐾           |");
        System.out.println("|        Your Trusted Pet Adoption System Portal       |");
        System.out.println("|                                                      |");
        System.out.println("+------------------------------------------------------+");
        System.out.println("             [1] Login           [2] Sign Up");
        System.out.println("             [3] About           [4] Exit");
        System.out.print("             Select an option: ");

    }

    public void logInInterfaceFooter() {
        System.out.println();
        System.out.println("+------------------------------------------------------+");
        System.out.println("|      Forgot your password? Contact support team      |");
        System.out.println("|           📧 Email: help@pethaven.org                |");
        System.out.println("+------------------------------------------------------+");
        System.out.println();

    }

    public void userInterface() {

        System.out.println("\n+-------------------------------------------------+");
        System.out.println("|            🐾 Pet Haven - User Panel 🐾         |");
        System.out.println("+-------------------------------------------------+");
        System.out.println("| [1] View Available Pets                         |");
        System.out.println("| [2] Make an Adoption Request                    |");
        System.out.println("| [3] Search Pet                                  |");
        System.out.println("| [4] Log Out                                     |");
        System.out.println("+-------------------------------------------------+");
        System.out.print("Choose an option: ");

    }

    public void adminInterface() {
        System.out.println("\n+-------------------------------------------------+");
        System.out.println("|          🐾 Pet Haven - Admin Panel 🐾          |");
        System.out.println("+-------------------------------------------------+");
        System.out.println("| [1] Customer Management                         |");
        System.out.println("| [2] Pet Management                              |");
        System.out.println("| [3] Generate Reports                            |");
        System.out.println("| [4] Log Out                                     |");
        System.out.println("+-------------------------------------------------+");
        System.out.print("Choose an option: ");
    }

    public void customerManagementMenu() {
        System.out.println("\n+-------------------------------------------------+");
        System.out.println("|           Customer Management Menu              |");
        System.out.println("+-------------------------------------------------+");
        System.out.println("| [1] Add Customer                                |");
        System.out.println("| [2] View, Update, and Remove Customer Details   |");
        System.out.println("| [3] Review Adoption Request                     |");
        System.out.println("| [4] Back to Admin Panel                         |");
        System.out.println("+-------------------------------------------------+");
        System.out.print("Choose an option: ");
    }

    public void petManagementMenu() {
        System.out.println("\n+-------------------------------------------------+");
        System.out.println("|             Pet Management Menu                 |");
        System.out.println("+-------------------------------------------------+");
        System.out.println("| [1] Add Pet                                     |");
        System.out.println("| [2] View, Update, and Remove Pet Details        |");
        System.out.println("| [3] Back to Admin Panel                         |");
        System.out.println("+-------------------------------------------------+");
        System.out.print("Choose an option: ");
    }

    public void reportsMenu() {
        System.out.println("\n+-------------------------------------------------+");
        System.out.println("|               Reports Menu                      |");
        System.out.println("+-------------------------------------------------+");
        System.out.println("| [1] Generate Adoption Reports                   |");
        System.out.println("| [2] Generate Remaining Pets Report              |");
        System.out.println("| [3] Back to Admin Panel                         |");
        System.out.println("+-------------------------------------------------+");
        System.out.print("Choose an option: ");
    }
}
