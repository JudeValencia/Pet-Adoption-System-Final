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
 *
 * The class is in-progress and includes TODOs for additional functionality such as
 * editing of usernames and passwords via overriding the update class.
 */
public class LoginPage extends CustomerManagement {


    // Add this field to store the current logged-in username
    private String currentUsername;

    // Add this getter method to retrieve the current username
    public String getCurrentUsername() {
        return currentUsername;
    }

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
    public void logIn() {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;

        while (isRunning) {

            logInInterfaceHeader();

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

                        logInInterfaceFooter();

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
        add();
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
        System.out.println("  Pet Haven is a safe, welcoming space for pet lovers  ");
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
    @Override
    public void add() {
        Scanner scanner = new Scanner(System.in);
        char gender;

        try {
            // sets a unique id to the customer
            Random random = new Random();
            int id = random.nextInt(1_000_000);
            setCustomerID(id);

            System.out.print("Enter Name: ");
            setName(scanner.nextLine());

            System.out.print("Enter Age: ");
            setAge(scanner.nextInt());
            scanner.nextLine();

            do {
                System.out.print("Enter Gender (M/F): ");
                gender = scanner.next().toUpperCase().charAt(0);
            } while (gender != 'M' && gender != 'F');
            setGender(gender);
            scanner.nextLine();


            System.out.print("Enter Address: ");
            setAddress(scanner.nextLine());

            System.out.print("Enter Birth Month: ");
            setBirthMonth(scanner.nextInt());
            scanner.nextLine();

            System.out.print("Enter Birth Day: ");
            setBirthDay(scanner.nextInt());
            scanner.nextLine();

            System.out.print("Enter Birth Year: ");
            setBirthYear(scanner.nextInt());
            scanner.nextLine();

            System.out.print("Enter Occupation: ");
            setOccupation(scanner.nextLine());

            System.out.print("Enter Home Type: ");
            setHomeType(scanner.nextLine());

            System.out.print("Has Other Pet/s (true/false): ");
            setHasOtherPets(scanner.nextBoolean());
            scanner.nextLine();

            System.out.print("Enter Email: ");
            setEmail(scanner.nextLine());

            System.out.print("Enter Contact Number: ");
            setContactNumber(scanner.nextLine());

        }
        catch (InputMismatchException e) {
            throw new RuntimeException(e);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))){
            writer.write(getCustomerID() + "," + getName() + "," + getAge() + "," + getGender() + "," + getAddress() +
                    "," + getBirthMonth() + "," + getBirthDay() + "," + getBirthYear() + "," + getOccupation() + "," +
                    getHomeType() + "," + getHasOtherPets() + "," + getEmail() + "," + getContactNumber() + "\n");
            createAccount();
            System.out.println("Account created successfully!\n");
            System.out.println("User Account: " + getAccount());
            System.out.println("User Password: " + getPassword());
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}