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

    final String RESET = "\u001B[0m";
    final String RED = "\u001B[31m";
    final String BLUE = "\u001B[38;2;66;103;178m";
    final String GREEN = "\u001B[38;2;0;210;106m";
    final String GRAY = "\u001B[38;2;137;143;156m";

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
                        System.out.println();
                        System.out.println(GRAY+"┌──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┐");
                        System.out.println("│                                                                                                                                                          │");
                        System.out.println("└──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┘"+RESET);
                        System.out.println();
                        System.out.println(BLUE+"                                                                     ┌───────────────┐                                                                 ");
                        System.out.println("                                                                     │     LOGIN     │                                                                      ");
                        System.out.println("                                                                     └───────────────┘                                                                "+RESET);
                        System.out.println();
                        System.out.println(GRAY+"                                                           ┌───────────────────────────────────┐                                                       ");
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
                        System.out.println("└──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┘"+RESET);


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
                                    System.out.println(RED+"                                                     INCORRECT USERNAME OR PASSWORD. ATTEMPTS LEFT: "+RESET+ (maxAttempts - attempts));
                                } else {
                                    System.out.println(RED+"                                                          TOO MANY FAILED ATTEMPTS. ACCESS DENIED!                                                "+RESET);
                                    System.out.println();

                                    // Prompts the user if they want to change their password
                                    System.out.print(BLUE+"                                                   WOULD YOU LIKE TO RECOVER YOUR PASSWORD? (yes/no): "+RESET);
                                    String choice = scanner.nextLine();

                                    if (choice.equalsIgnoreCase("yes")) {
                                        forgotPassword();
                                    } else {
                                        System.out.println("                                                                  RETURNING TO MAIN MENU...                                                                 ");
                                    }
                                }
                            }
                        }
                    }
                }

                case 2 -> signUp();
                case 3 -> about();

                case 4 -> {
                    System.out.println("                                                                         EXITING...                                                                         ");
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

        final String SKY_BLUE = "\u001B[38;2;161;227;239m";
        final String LIGHT_BLUE = "\u001B[38;2;138;170;251m";
        final String AQUA_BLUE = "\u001B[38;5;116m";
        final String RESET = "\u001B[0m";

        //AboutUsDisplay
        System.out.println(" _____                                                                                                                                                _____ ");
        System.out.println("( ___ )══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════( ___ )");
        System.out.println(" ║   ║                                                                                                                                                ║   ║ ");
        System.out.println(" ║   ║                       "+SKY_BLUE+"   ,-.___,-.   "+LIGHT_BLUE+"  █████╗ ██████╗  ██████╗ ██╗   ██╗████████╗    ██╗   ██╗███████╗                      "+RESET+"                   ║   ║ ");
        System.out.println(" ║   ║                       "+SKY_BLUE+"   |_|_ _|_|   "+LIGHT_BLUE+" ██╔══██╗██╔══██╗██╔═══██╗██║   ██║╚══██╔══╝    ██║   ██║██╔════╝   "+SKY_BLUE+"  ^~^  ,           "+RESET+"                   ║   ║ ");
        System.out.println(" ║   ║                       "+SKY_BLUE+"     )O_O(     "+LIGHT_BLUE+" ███████║██████╔╝██║   ██║██║   ██║   ██║       ██║   ██║███████╗   "+SKY_BLUE+" ('Y') )           "+RESET+"                   ║   ║ ");
        System.out.println(" ║   ║                       "+SKY_BLUE+"    { (_) }    "+LIGHT_BLUE+" ██╔══██║██╔══██╗██║   ██║██║   ██║   ██║       ██║   ██║╚════██║   "+SKY_BLUE+" /   ||/           "+RESET+"                   ║   ║ ");
        System.out.println(" ║   ║                       "+SKY_BLUE+"     `-^-'     "+LIGHT_BLUE+" ██║  ██║██████╔╝╚██████╔╝╚██████╔╝   ██║       ╚██████╔╝███████║   "+SKY_BLUE+"(|||||/)           "+RESET+"                   ║   ║ ");
        System.out.println(" ║   ║                                      "+LIGHT_BLUE+" ╚═╝  ╚═╝╚═════╝  ╚═════╝  ╚═════╝    ╚═╝        ╚═════╝ ╚══════╝                      "+RESET+"                   ║   ║ ");
        System.out.println(" ║   ║════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════║   ║ ");
        System.out.println(" ║   ║                                                                                                                                                ║   ║ ");
        System.out.println(" ║   ║              "+AQUA_BLUE+"                                                 Welcome to Pet Haven!                                        "+RESET+"                    ║   ║ ");
        System.out.println(" ║   ║                                                                                                                                                ║   ║ ");
        System.out.println(" ║   ║              "+AQUA_BLUE+"                  Pet Haven is a console-based Pet Adoption System designed to simplify and streamline        "+RESET+"                    ║   ║ ");
        System.out.println(" ║   ║              "+AQUA_BLUE+"                                 the process of finding loving homes for pets in need.                        "+RESET+"                    ║   ║ ");
        System.out.println(" ║   ║                                                                                                                                                ║   ║ ");
        System.out.println(" ║   ║              "+AQUA_BLUE+"                                 Features:                                                                    "+RESET+"                    ║   ║ ");
        System.out.println(" ║   ║              "+AQUA_BLUE+"                                     User Sign Up and Secure Login                                            "+RESET+"                    ║   ║ ");
        System.out.println(" ║   ║              "+AQUA_BLUE+"                                     Password Recovery                                                        "+RESET+"                    ║   ║ ");
        System.out.println(" ║   ║              "+AQUA_BLUE+"                                     Adoption History Tracking                                                "+RESET+"                    ║   ║ ");
        System.out.println(" ║   ║              "+AQUA_BLUE+"                                     Pet Listings and Management                                              "+RESET+"                    ║   ║ ");
        System.out.println(" ║   ║              "+AQUA_BLUE+"                                     Customer Account System                                                  "+RESET+"                    ║   ║ ");
        System.out.println(" ║   ║                                                                                                                                                ║   ║ ");
        System.out.println(" ║   ║              "+AQUA_BLUE+"                         Developed as part of a Java-based final project, Pet Haven is a safe,                "+RESET+"                    ║   ║ ");
        System.out.println(" ║   ║              "+AQUA_BLUE+"                                  welcome space for pet lovers and future pet parents.                        "+RESET+"                    ║   ║ ");
        System.out.println(" ║   ║                                                                                                                                                ║   ║ ");
        System.out.println(" ║   ║                                                                                                                                                ║   ║ ");
        System.out.println(" ║   ║              "+AQUA_BLUE+"                                      @Need help? Contact us at help@pethaven.org                             "+RESET+"                    ║   ║ ");
        System.out.println(" ║___║                                                                                                                                                ║___║ ");
        System.out.println("(_____)══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════(_____)");

    }

    public void forgotPassword() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(GRAY+"┌──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                                                                                                                                                          │");
        System.out.println("└──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┘"+RESET);
        System.out.println();
        System.out.println(BLUE+"                                                                ┌─────────────────────────┐                                                              ");
        System.out.println("                                                                │     FORGOT PASSWORD     │                                                                  ");
        System.out.println("                                                                └─────────────────────────┘                                                            "+RESET);
        System.out.println();

        System.out.println(GRAY+"                                              ┌──────────────────────────────────────────────────────────────┐");
        System.out.print  ("                                              │ ENTER YOUR USERNAME TO RESET PASSWORD: ");
        String username = scanner.nextLine();
        System.out.println("                                              └──────────────────────────────────────────────────────────────┘");
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
                        System.out.println("                                              ┌──────────────────────────────────────────────────────────────┐");
                        System.out.print  ("                                              │ ENTER YOUR NEW PASSWORD: ");
                        newPassword = scanner.nextLine();
                        System.out.println("                                              └──────────────────────────────────────────────────────────────┘");
                        System.out.println();
                        System.out.println();
                        System.out.println("┌──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┐");
                        System.out.println("│                                                                                                                                                          │");
                        System.out.println("└──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┘"+RESET);
                    } while(!validator.passwordValidation(newPassword));

                    updatedLines.add(accountDetails[0] + "," + username + "," + newPassword);
                    System.out.println(GREEN+"                                                             ✅ PASSWORD SUCCESSFULLY UPDATED.                                                        ");
                } else {
                    updatedLines.add(line);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(RED+"                                                                ❌ ACCOUNT FILE NOT FOUND.                                                              ");
            return;
        }

        if (!found) {
            System.out.println(GRAY+"                                              ┌──────────────────────────────────────────────────────────────┐");
            System.out.println("                                              │ "+RED+"NO ACCOUNT FOUND WITH THE USERNAME! " + username);
            System.out.println(GRAY+"                                              └──────────────────────────────────────────────────────────────┘"+RESET);
            return;
        }

        // Now write the updated lines back to file2
        try (PrintWriter writer = new PrintWriter(file2)) {
            for (String updatedLine : updatedLines) {
                writer.println(updatedLine);
            }
            writer.flush();
        } catch (FileNotFoundException e) {
            System.out.println(RED+"                                                        ❌ UNABLE TO WRITE BACK TO THE ACCOUNT FILE.                                             "+RESET);
        }
    }
}