package UI_LogIn_Package;

import Adoption_Management_Package.CustomerManagement;
import Adoption_Management_Package.Validation;

import java.io.*;
import java.util.*;

/**
 * The LoginPage class represents the interface and functionality for the login
 * and registration system of the Pet Haven platform. It extends the
 * CustomerManagement class to utilize account management functionalities.
 *
 * This class manages user interactions related to logging in, signing up,
 * recovering passwords, and accessing information about the application.
 * It includes methods to display the login interface, handle authentication,
 * and provide user guidance.
 *
 * Features:
 * - Customizable login and sign-up forms.
 * - Password recovery and management.
 * - Displays an informational "About Us" section.
 * - Handles user input for login selection.
 */
public class LoginPage extends CustomerManagement {


    // Add this field to store the current logged-in username
    private String currentUsername;

    // Add this getter method to retrieve the current username
    public String getCurrentUsername() {
        return currentUsername;
    }

    public void logIn() {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;

        while (isRunning) {
            UserInterface ui = new UserInterface();

            ui.logInInterfaceHeader();

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> {
                    int maxAttempts = 5;
                    int attempts = 0;
                    boolean isAuthenticated = false;

                    while (attempts < maxAttempts && !isAuthenticated) {
                        //LoginDisplay
                        System.out.println("┌──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┐");
                        System.out.println("│                                                                                                                                                          │");
                        System.out.println("└──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┘");
                        System.out.println();
                        System.out.println("                                                                     ┌───────────────┐                                                                 ");
                        System.out.println("                                                                     │     LOGIN     │                                                                      ");
                        System.out.println("                                                                     └───────────────┘                                                                ");
                        System.out.println();
                        System.out.println("                                                           ┌───────────────────────────────────┐                                                       ");
                        System.out.print  ("                                                           │ USERNAME: ");

                        String username = scanner.nextLine();
                        System.out.println("                                                           └───────────────────────────────────┘                                                            ");
                        System.out.println("                                                           ┌───────────────────────────────────┐                                                            ");
                        System.out.print  ("                                                           │ PASSWORD: ");

                        String password = scanner.nextLine();
                        System.out.println("                                                           └───────────────────────────────────┘                                                            ");
                        System.out.println();
                        System.out.println();
                        System.out.println("┌──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┐");
                        System.out.println("│                                                                                                                                                          │");
                        System.out.println("└──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┘");


                        ui.logInInterfaceFooter();

                        if (username.equals("admin123") && password.equals("password123")) {
                            isAuthenticated = true;
                            isRunning = false;
                            this.currentUsername = username; // Store admin username
                        }
                        else {
                            if (signIn(username, password)) {
                                isAuthenticated = true;
                                isRunning = false;
                                this.currentUsername = username; // Store customer username
                            } else {
                                attempts++;
                                if (attempts < maxAttempts) {
                                    System.out.println("Incorrect username or password. Attempts left: " + (maxAttempts - attempts));
                                } else {
                                    System.out.println("Too many failed attempts. Access denied.");
                                    System.out.println();

                                    // Prompts the user if they want to change their password
                                    System.out.print("Would you like to recover your password? (yes/no): ");
                                    String choice = scanner.nextLine();

                                    if (choice.equalsIgnoreCase("yes")) {
                                        forgotPassword();
                                    } else {
                                        System.out.println("Returning to main menu...");
                                    }
                                }
                            }
                        }
                    }
                }

                case 2 -> signUp();
                case 3 -> about();

                case 4 -> {
                    System.out.println("Exiting... ");
                    isRunning = false;
                }
            }
        }

    }

    public void signUp() {
        final String BLUE = "\u001B[38;2;66;103;178m";
        final String RESET = "\u001B[0m";
        final String GRAY = "\u001B[38;2;137;143;156m";
        CustomerManagement customerManager = new CustomerManagement();

        System.out.println(GRAY+"┌──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                                                                                                                                                          │");
        System.out.println("└──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┘");
        System.out.println(BLUE+"                                                                    ┌─────────────────┐                                                                     ");
        System.out.println("                                                                    │     SIGN UP     │                                                                     ");
        System.out.println("                                                                    └─────────────────┘                                                                     "+RESET);
        customerManager.add();
    }

    public boolean signIn(String username, String password) {
        // Store the username if authentication is successful
        boolean loginSuccessful = searchCustomerAccount(username, password);
        if (loginSuccessful) {
            this.currentUsername = username;
        }
        return loginSuccessful;
    }

    public void about() {
        //AboutUsDisplay
        System.out.println(" _____                                                                                                                                                _____ ");
        System.out.println("( ___ )──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────( ___ )");
        System.out.println(" |   |                                                                                                                                                |   | ");
        System.out.println(" |   |                          ,-.___,-.     █████╗ ██████╗  ██████╗ ██╗   ██╗████████╗    ██╗   ██╗███████╗                                         |   | ");
        System.out.println(" |   |                          |_|_ _|_|    ██╔══██╗██╔══██╗██╔═══██╗██║   ██║╚══██╔══╝    ██║   ██║██╔════╝     ^~^  ,                              |   | ");
        System.out.println(" |   |                            )O_O(      ███████║██████╔╝██║   ██║██║   ██║   ██║       ██║   ██║███████╗    ('Y') )                              |   | ");
        System.out.println(" |   |                           { (_) }     ██╔══██║██╔══██╗██║   ██║██║   ██║   ██║       ██║   ██║╚════██║    /   ||/                              |   | ");
        System.out.println(" |   |                            `-^-'      ██║  ██║██████╔╝╚██████╔╝╚██████╔╝   ██║       ╚██████╔╝███████║   (|||||/)                              |   | ");
        System.out.println(" |   |                                       ╚═╝  ╚═╝╚═════╝  ╚═════╝  ╚═════╝    ╚═╝        ╚═════╝ ╚══════╝                                         |   | ");
        System.out.println(" |   |────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────|   | ");
        System.out.println(" |   |                                                                                                                                                |   | ");
        System.out.println(" |   |                                                               Welcome to Pet Haven!                                                            |   | ");
        System.out.println(" |   |                                                                                                                                                |   | ");
        System.out.println(" |   |                                Pet Haven is a console-based Pet Adoption System designed to simplify and streamline                            |   | ");
        System.out.println(" |   |                                               the process of finding loving homes for pets in need.                                            |   | ");
        System.out.println(" |   |                                                                                                                                                |   | ");
        System.out.println(" |   |                                               Features:                                                                                        |   | ");
        System.out.println(" |   |                                                   User Sign Up and Secure Login                                                                |   | ");
        System.out.println(" |   |                                                   Password Recovery                                                                            |   | ");
        System.out.println(" |   |                                                   Adoption History Tracking                                                                    |   | ");
        System.out.println(" |   |                                                   Pet Listings and Management                                                                  |   | ");
        System.out.println(" |   |                                                   Customer Account System                                                                      |   | ");
        System.out.println(" |   |                                                                                                                                                |   | ");
        System.out.println(" |   |                                       Developed as part of a Java-based final project, Pet Haven is a safe,                                    |   | ");
        System.out.println(" |   |                                                welcome space for pet lovers and future pet parents.                                            |   | ");
        System.out.println(" |   |                                                                                                                                                |   | ");
        System.out.println(" |   |                                                                                                                                                |   | ");
        System.out.println(" |   |                                                    @Need help? Contact us at help@pethaven.org                                                 |   | ");
        System.out.println(" |___|                                                                                                                                                |___| ");
        System.out.println("(_____)──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────(_____)");

    }

    public void forgotPassword() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your username to reset your password: ");
        String username = scanner.nextLine();
        String newPassword;

        List<String> updatedLines = new ArrayList<>();
        boolean found = false;
        Validation validator = new Validation();

        try (Scanner fileScanner = new Scanner(file2)) {

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] accountDetails = line.split(",");

                if (accountDetails.length == 3 && accountDetails[1].equals(username)) {
                    found = true;
                    do{
                        System.out.print("Enter your new password: ");
                        newPassword = scanner.nextLine();
                    } while(!validator.passwordValidation(newPassword));

                    updatedLines.add(accountDetails[0] + "," + username + "," + newPassword);
                    System.out.println("✅ Password successfully updated.");
                } else {
                    updatedLines.add(line);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("❌ Account file not found.");
            return;
        }

        if (!found) {
            System.out.println("No account found with the username: " + username);
            return;
        }

        // Now write the updated lines back to file2
        try (PrintWriter writer = new PrintWriter(file2)) {
            for (String updatedLine : updatedLines) {
                writer.println(updatedLine);
            }
            writer.flush();
        } catch (FileNotFoundException e) {
            System.out.println("❌ Unable to write back to the account file.");
        }
    }
}