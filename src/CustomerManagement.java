import java.io.*;
import java.security.SecureRandom;
import java.util.*;

/**
 * The CustomerManagement class provides functionalities for managing customer accounts
 * and related details. It extends the Customer class and implements the ManagementFunctions
 * interface to provide essential management operations such as adding, removing, updating,
 * and viewing customer accounts and information.
 *
 * This class also includes additional methods for creating customer accounts, generating
 * secure passwords, and performing account-related operations such as searching or removing
 * a customer account. It further allows retrieving customer-specific details, for instance,
 * fetching a customer's full name by their username.
 *
 * Fields:
 * - file: Represents a file resource used for managing customers or accounts.
 * - file2: Represents another file resource specifically for account-related operations.
 * - LOWERCASE: A constant for storing lowercase alphabets (a-z).
 * - UPPERCASE: A constant for storing uppercase alphabets (A-Z).
 * - DIGITS: A constant for storing numeric digits (0-9).
 * - SPECIAL_CHARS: A constant for storing special characters (e.g., !, @, #, etc.).
 * - ALL_CHARACTERS: A combination of all character sets for password generation or validation.
 * - random: A Random instance for generating random values.
 *
 * Methods:
 * - add(): An overridden method for adding customer-related information.
 * - remove(): An overridden method for removing customer-related information.
 * - update(): An overridden method for updating customer-related information.
 * - view(): An overridden method for viewing customer-related information.
 * - createAccount(): Creates a new account for a customer, generates a username and password,
 *   and writes the details to a file.
 * - generatePassword(int length): Generates a random password based on the specified length
 *   using predefined character sets.
 * - removeAccount(int customerIdToRemove): Removes a customer account by its unique identifier
 *   from the records.
 * - searchCustomerAccount(String username, String password): Searches for a customer account
 *   based on the provided username and password, and returns whether an account matches.
 * - getCustomerNameByUsername(String username): Retrieves the full name of the customer
 *   associated with the given username.
 */
public class CustomerManagement extends Customer implements ManagementFunctions {

    protected File file = new File("Customer.txt");
    protected File file2 = new File("UserAccounts.txt");

    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARS = "!@#$%^&*()-_+=<>?";

    // Combine all character sets
    private static final String ALL_CHARACTERS = LOWERCASE + UPPERCASE + DIGITS + SPECIAL_CHARS;

    // SecureRandom provides stronger randomness
    private static SecureRandom random = new SecureRandom();

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

            System.out.print("Has Other Pet/s: ");
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
            System.out.println("Customer added successfully!\n");
            System.out.println("User Account: " + getAccount());
            System.out.println("User Password: " + getPassword());
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void remove() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Customer ID to remove: ");
        int removeId = scanner.nextInt();
        scanner.nextLine();

        List<String> customerList = new ArrayList<>();
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {

                // Ignore empty lines
                if (line.trim().isEmpty()) continue;

                String[] customerDetails = line.split(",");

                // Validate that customerDetails[0] is an integer before parsing
                if (!customerDetails[0].matches("\\d+")) continue;

                int customerId = Integer.parseInt(customerDetails[0]);

                if (customerId == removeId) {
                    found = true;
                    continue; // Skip this customer (removing it)
                }

                customerList.add(line); // Keep other customers
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading the file.", e);
        }

        if (!found) {
            System.out.println("customer with ID " + removeId + " not found.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String customers : customerList) {
                String[] customerDetails = customers.split(",");
                writer.write(String.join(",", customerDetails) + "\n");
            }

            removeAccount(removeId); // Now remove from UserAccounts.txt too
            System.out.println("customer removed.");
        } catch (IOException e) {
            throw new RuntimeException("Error writing to the file.", e);
        }

    }

    @Override
    public void update() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Customer ID to edit: ");
        int editID = scanner.nextInt();
        scanner.nextLine();

        List<String> customerList = new ArrayList<>();
        boolean found = false;
        try {
            Scanner fileScanner = new Scanner(file);

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] customerDetails = line.split(",");

                if (customerDetails.length == 13) {
                    setCustomerID(Integer.parseInt(customerDetails[0]));
                    setName(customerDetails[1]);
                    setAge(Integer.parseInt(customerDetails[2]));
                    setGender(customerDetails[3].charAt(0));
                    setAddress(customerDetails[4]);
                    setBirthMonth(Integer.parseInt(customerDetails[5]));
                    setBirthDay(Integer.parseInt(customerDetails[6]));
                    setBirthYear(Integer.parseInt(customerDetails[7]));
                    setOccupation(customerDetails[8]);
                    setHomeType(customerDetails[9]);
                    setHasOtherPets(Boolean.parseBoolean(customerDetails[10]));
                    setEmail(customerDetails[11]);
                    setContactNumber(customerDetails[12]);

                    if (getCustomerID() == editID) {
                        found = true;
                        boolean isEditing = true;

                        while (isEditing) {
                            System.out.println("""
                                     \nEdit:\s
                                     1. Name
                                     2. Age
                                     3. Gender
                                     4. Address
                                     5. Birth Month
                                     6. Birth Day
                                     7. Birth Year
                                     8. Occupation
                                     9. Home Type
                                     10. Has Other Pets (True or False)
                                     11. Email Address
                                     12. Contact Number
                                     13. Exit
                                    \s""");
                            System.out.print("Enter: ");
                            int choice = scanner.nextInt();
                            scanner.nextLine();

                            switch (choice) {
                                case 1 -> {
                                    System.out.print("Enter New Name: ");
                                    setName(scanner.nextLine());
                                }
                                case 2 -> {
                                    System.out.print("Enter New Age: ");
                                    setAge(scanner.nextInt());
                                }
                                case 3 -> {
                                    System.out.print("Enter New Gender: ");
                                    setGender(scanner.next().charAt(0));
                                }
                                case 4 -> {
                                    System.out.print("Enter New Address: ");
                                    setAddress(scanner.nextLine());
                                }
                                case 5 -> {
                                    System.out.print("Enter New Birth Month: ");
                                    setBirthMonth(scanner.nextInt());
                                }
                                case 6 -> {
                                    System.out.print("Enter New Birth Day: ");
                                    setBirthDay(scanner.nextInt());
                                }
                                case 7 -> {
                                    System.out.print("Enter New Birth Year: ");
                                    setBirthYear(scanner.nextInt());
                                }
                                case 8 -> {
                                    System.out.print("Enter New Occupation: ");
                                    setOccupation(scanner.nextLine());
                                }
                                case 9 -> {
                                    System.out.print("Enter New Home Type: ");
                                    setHomeType(scanner.nextLine());
                                }
                                case 10 -> {
                                    System.out.print("Do you have a pet (True or False): ");
                                    setHasOtherPets(scanner.nextBoolean());
                                }
                                case 11 -> {
                                    System.out.print("Enter New Contact Number: ");
                                    setContactNumber(scanner.nextLine());
                                }
                                case 12 ->  {
                                    System.out.print("Enter New Email: ");
                                    setEmail(scanner.nextLine());
                                }
                                case 13 -> {
                                    System.out.print("Exiting... ");
                                    isEditing = false;
                                }
                                default -> System.out.println("Invalid Choice! No changes made.");
                            }
                        }
                        // replace old data with new data
                        line = getCustomerID() + "," + getName() + "," + getAge() + "," + getGender() + "," +
                                getAddress() + "," + getBirthMonth() + "," + getBirthDay() + "," + getBirthYear() + "," +
                                getOccupation() + "," + getHomeType() + "," + getHasOtherPets() + "," + getEmail() +
                                "," + getContactNumber();

                    }
                }
                customerList.add(line);
            }
            // If customer was found, rewrite the file with updated data
            if (found) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    for (String customer : customerList) {
                        writer.write(customer + "\n");
                    }
                    System.out.println("Customer details updated successfully!\n");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else {
                System.out.println("Customer ID not found.\n");
            }

        }
        catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }

    }

    @Override
    public void view() {
        try(Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String[] customerDetails = scanner.nextLine().split(",");

                if (customerDetails.length == 13) {
                    setCustomerID(Integer.parseInt(customerDetails[0]));
                    setName(customerDetails[1]);
                    setAge(Integer.parseInt(customerDetails[2]));
                    setGender(customerDetails[3].charAt(0));
                    setAddress(customerDetails[4]);
                    setBirthMonth(Integer.parseInt(customerDetails[5]));
                    setBirthDay(Integer.parseInt(customerDetails[6]));
                    setBirthYear(Integer.parseInt(customerDetails[7]));
                    setOccupation(customerDetails[8]);
                    setHomeType(customerDetails[9]);
                    setHasOtherPets(Boolean.parseBoolean(customerDetails[10]));
                    setEmail(customerDetails[11]);
                    setContactNumber(customerDetails[12]);

                    System.out.println("\n--- Customer Details ---");
                    System.out.println("ID: " + getCustomerID());
                    System.out.println("Name: " + getName());
                    System.out.println("Age: " + getAge());
                    System.out.println("Gender: " + getGender());
                    System.out.println("Address: " + getAddress());
                    System.out.println("Birthday: " + getBirthMonth() + "/" +
                            getBirthDay() + "/" + getBirthYear());
                    System.out.println("Occupation: " + getOccupation());
                    System.out.println("Home Type: " + getHomeType());
                    System.out.println("Has Other Pets: " + getHasOtherPets());
                    System.out.println("Email: " + getEmail());
                    System.out.println("Contact Number: " + getContactNumber());
                    System.out.println("----------------------");
                }
            }

            Scanner scanner2 = new Scanner(System.in);
            boolean continueRunning = true;
            while (continueRunning) {
                System.out.println("""
                         \nAction:\s
                         1. Update Customer Details
                         2. Remove Customer
                         3. Exit
                        \s""");
                System.out.print("Enter Choice: ");
                int choice = scanner2.nextInt();

                switch (choice) {
                    case 1 -> update();
                    case 2 -> remove();
                    case 3 -> continueRunning = false;
                    default -> System.out.println("Invalid Choice!");
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void createAccount() {

        // Generates Username
        Random randomUsernameNumber = new Random();
        int usernameID = randomUsernameNumber.nextInt(100);

        String fullname = getName();
        String[] nameParts = fullname.split("\\s+");
        String lastName = nameParts[nameParts.length - 1];

        String username = lastName + usernameID;
        setAccount(username);

        // Generates Password
        int length = 12; // password length
        setPassword(generatePassword(length));

        // inputs the account and password to UserAccounts.txt
        try ( BufferedWriter writer = new BufferedWriter(new FileWriter(file2, true))){
            writer.write(getCustomerID() + "," +getAccount() + "," + getPassword());
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public String generatePassword(int length) {
        StringBuilder password = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(ALL_CHARACTERS.length());
            password.append(ALL_CHARACTERS.charAt(index));
        }

        return password.toString();
    }

    public void removeAccount(int customerIdToRemove) {
        List<String> accounts = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file2))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] accountDetails = line.split(",");

                // Skip empty lines or invalid lines
                if (accountDetails.length < 1 || !accountDetails[0].matches("\\d+")) continue;

                int accountId = Integer.parseInt(accountDetails[0]);
                if (accountId != customerIdToRemove) {
                    accounts.add(line); // Keep other accounts
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading the accounts file.", e);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file2))) {
            for (String account : accounts) {
                writer.write(account);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error writing to the accounts file.", e);
        }
    }

    public boolean searchCustomerAccount(String username, String password) {

        try (Scanner scanner = new Scanner(file2)) {
            while (scanner.hasNextLine()) {
                String[] accountDetails = scanner.nextLine().split(",");

                if (accountDetails.length == 3) {
                    int customerId = Integer.parseInt(accountDetails[0]);
                    String accountName = accountDetails[1];
                    String accountPassword = accountDetails[2];

                    if (username.equals(accountName) && password.equals(accountPassword)) {
                        setCustomerID(customerId);
                        setAccount(accountName);
                        setPassword(accountPassword);
                        return true;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Customer accounts file not found.", e);
        }
        return false;
    }

    /**
     * Retrieves a customer's full name by their username
     * The parameter is the username, the username to search for
     * It will return the customer's full name or null if not found
     */

    public String getCustomerNameByUsername(String username) {
        int customerId = -1;

        // First, find the customer ID associated with the username in UserAccounts.txt
        try (Scanner scanner = new Scanner(file2)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                // Skip empty lines
                if (line.trim().isEmpty()) continue;

                String[] accountDetails = line.split(",");

                // Skip invalid lines
                if (accountDetails.length < 3) continue;

                String accountUsername = accountDetails[1];

                if (username.equals(accountUsername)) {
                    customerId = Integer.parseInt(accountDetails[0]);
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Error reading accounts file.", e);
        }

        // If we didn't find the customer ID, return null
        if (customerId == -1) {
            return null;
        }

        // Now find the customer's full name in Customer.txt using the customer ID
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                // Skip empty lines
                if (line.trim().isEmpty()) continue;

                String[] customerDetails = line.split(",");

                // Skip invalid lines
                if (customerDetails.length < 13 || !customerDetails[0].matches("\\d+")) continue;

                int currentId = Integer.parseInt(customerDetails[0]);

                if (currentId == customerId) {
                    return customerDetails[1]; // Return the name (which is at index 1)
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Error reading customer file.", e);
        }

        return null;
    }

}
