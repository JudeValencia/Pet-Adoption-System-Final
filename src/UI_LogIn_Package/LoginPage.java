package UI_LogIn_Package;

import Adoption_Management_Package.CustomerManagement;

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
                        System.out.println();
                        System.out.println("                    ┌────────────────┐");
                        System.out.println("                    │     LOGIN      │");
                        System.out.println("                    └────────────────┘");
                        System.out.println();
                        System.out.print("                   ┌──────────────────┐\n");
                        System.out.print("                   │ Username: ");
                        String username = scanner.nextLine();
                        System.out.println("                   └──────────────────┘");
                        System.out.print("                   ┌──────────────────┐\n");
                        System.out.print("                   │ Password: ");
                        String password = scanner.nextLine();
                        System.out.println("                   └──────────────────┘");
                        System.out.println();

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
        CustomerManagement customerManager = new CustomerManagement();
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
        System.out.println("+------------------------------------------------------+");
        System.out.println("|                      ABOUT US                        |");
        System.out.println("+------------------------------------------------------+");
        System.out.println("  🐾 Welcome to Pet Haven!                             ");
        System.out.println("                                                      ");
        System.out.println("  Pet Haven is a console-based Pet Adoption System     ");
        System.out.println("  designed to simplify and streamline the process of   ");
        System.out.println("  finding loving homes for pets in need.               ");
        System.out.println("                                                      ");
        System.out.println("  Features:                                            ");
        System.out.println("   🐶 User Sign Up and Secure Login                    ");
        System.out.println("   🐱 Password Recovery                                ");
        System.out.println("   🐾 Adoption History Tracking                        ");
        System.out.println("   🐕 Pet Listings and Management                      ");
        System.out.println("   📋 Customer Account System                          ");
        System.out.println("                                                      ");
        System.out.println("  Developed as part of a Java-based final project,     ");
        System.out.println("   Haven is a safe, welcoming space for pet lovers  ");
        System.out.println("  and future pet parents.                              ");
        System.out.println("                                                      ");
        System.out.println("  📧 Need help? Contact us at help@pethaven.org        ");
        System.out.println("+------------------------------------------------------+");
        System.out.println();
    }

    public void forgotPassword() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your username to reset your password: ");
        String username = scanner.nextLine();

        List<String> updatedLines = new ArrayList<>();
        boolean found = false;

        try (Scanner fileScanner = new Scanner(file2)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] accountDetails = line.split(",");

                if (accountDetails.length == 3 && accountDetails[1].equals(username)) {
                    found = true;
                    System.out.print("Enter your new password: ");
                    String newPassword = scanner.nextLine();
                    // TODO: Add an if statement here with a parameter of a method that validates new password using regex
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