package Adoption_Management_Package;

import java.io.*;
import java.security.SecureRandom;
import java.util.*;

/**
 * The CustomerManagement class is responsible for managing customer-related
 * operations and functionalities. This class provides methods for creating,
 * updating, viewing, and removing customer accounts, along with password
 * generation and user authentication features. It extends functionality from
 * the Customer class and implements abstract methods defined in the
 * ManagementFunctions interface.
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

    /**
     * Check if a customer already exists based on name, email, or contact number
     */
    private boolean customerExists(String name, String email, String contactNumber) {
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.trim().isEmpty()) continue;

                String[] customerDetails = line.split(",");
                if (customerDetails.length >= 13) {
                    String existingName = customerDetails[1].trim().toLowerCase();
                    String existingEmail = customerDetails[11].trim().toLowerCase();
                    String existingContact = customerDetails[12].trim();

                    // Check for duplicates
                    if (existingName.equals(name.trim().toLowerCase()) ||
                            existingEmail.equals(email.trim().toLowerCase()) ||
                            existingContact.equals(contactNumber.trim())) {
                        return true;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            // If file doesn't exist, no duplicates exist
            return false;
        }
        return false;
    }

    @Override
    public void add() {
        Scanner scanner = new Scanner(System.in);
        char gender;
        String nameTemp, addressTemp, homeTypeTemp, occupationTemp, emailTemp, contactNumberTemp;
        int ageTemp, birthMonthTemp, birthDayTemp, birthYearTemp;
        String hasPetsResponse;
        boolean hasOtherPets;

        // Guardian information variables
        String guardianNameTemp = "";
        int guardianAgeTemp = 0;
        String guardianOccupationTemp = "";

        final String RESET = "\u001B[0m";
        final String GRAY = "\u001B[38;2;137;143;156m";
        final String RED = "\u001B[31m";
        final String BLUE = "\u001B[38;2;66;103;178m";

        try {
            Validation validator = new Validation();

            // sets a unique id to the customer
            Random random = new Random();
            int id = random.nextInt(1_000_000);
            setCustomerID(id);

            do {
                System.out.println();
                System.out.println(GRAY+"                                             ┌──────────────────────────────────────────────────────────────┐");
                System.out.print  ("                                             │ ENTER NAME: ");
                nameTemp = scanner.nextLine();
                System.out.println("                                             └──────────────────────────────────────────────────────────────┘");
                validator.nameValidation(nameTemp);
            } while (!validator.nameValidation(nameTemp));

            do {
                System.out.println("                                             ┌──────────────────────────────────────────────────────────────┐");
                System.out.print  ("                                             │ ENTER AGE: ");
                ageTemp = scanner.nextInt();
                System.out.println("                                             └──────────────────────────────────────────────────────────────┘");

            } while(ageTemp < 18 || ageTemp > 120);
            scanner.nextLine();

            do {
                System.out.println("                                             ┌──────────────────────────────────────────────────────────────┐");
                System.out.print  ("                                             │ ENTER GENDER (M/F): ");
                gender = scanner.next().toUpperCase().charAt(0);
                System.out.println("                                             └──────────────────────────────────────────────────────────────┘");
            } while (gender != 'M' && gender != 'F');
            scanner.nextLine();

            do {
                System.out.println("                                             ┌──────────────────────────────────────────────────────────────┐");
                System.out.print  ("                                             │ ENTER ADDRESS: ");
                addressTemp = scanner.nextLine();
                System.out.println("                                             └──────────────────────────────────────────────────────────────┘");
            } while (validator.addressValidation(addressTemp));

            do {
                System.out.println("                                             ┌──────────────────────────────────────────────────────────────┐");
                System.out.print  ("                                             │ ENTER BIRTH-MONTH: ");
                birthMonthTemp = scanner.nextInt();
                System.out.println("                                             └──────────────────────────────────────────────────────────────┘");
            } while (birthMonthTemp < 1 || birthMonthTemp > 12);
            scanner.nextLine();

            do {
                System.out.println("                                             ┌──────────────────────────────────────────────────────────────┐");
                System.out.print  ("                                             │ ENTER DAY OF BIRTH: ");
                birthDayTemp = scanner.nextInt();
                System.out.println("                                             └──────────────────────────────────────────────────────────────┘");
            } while (birthDayTemp < 1 || birthDayTemp > 31);
            scanner.nextLine();

            do {
                System.out.println("                                             ┌──────────────────────────────────────────────────────────────┐");
                System.out.print  ("                                             │ ENTER BIRTH-YEAR: ");
                birthYearTemp = scanner.nextInt();
                System.out.println("                                             └──────────────────────────────────────────────────────────────┘");
            } while (birthYearTemp < 1900 || birthYearTemp > 2025);
            scanner.nextLine();

            do {
                System.out.println("                                             ┌──────────────────────────────────────────────────────────────┐");
                System.out.print  ("                                             │ ENTER OCCUPATION: ");
                occupationTemp = scanner.nextLine();
                System.out.println("                                             └──────────────────────────────────────────────────────────────┘");
            } while (validator.occupationAndHomeTypeValidation(occupationTemp));

            // Check if occupation is "student" (case-insensitive) and ask for guardian information
            if (occupationTemp.toLowerCase().trim().equals("student")) {
                System.out.println();
                System.out.println(GRAY+"┌──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┐");
                System.out.println("│                                                                                                                                                          │");
                System.out.println("└──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┘");
                System.out.println(BLUE+"                                                                 ┌─────────────────────────┐                                                                     ");
                System.out.println("                                                                 │ GUARDIAN INFORMATION    │                                                                     ");
                System.out.println("                                                                 └─────────────────────────┘                                                                     "+RESET);
                System.out.println();

                // Guardian Name
                do {
                    System.out.println(GRAY+"                                             ┌──────────────────────────────────────────────────────────────┐");
                    System.out.print  ("                                             │ ENTER GUARDIAN'S NAME: ");
                    guardianNameTemp = scanner.nextLine();
                    System.out.println("                                             └──────────────────────────────────────────────────────────────┘");
                    validator.nameValidation(guardianNameTemp);
                } while (!validator.nameValidation(guardianNameTemp));

                // Guardian Age
                do {
                    System.out.println("                                             ┌──────────────────────────────────────────────────────────────┐");
                    System.out.print  ("                                             │ ENTER GUARDIAN'S AGE: ");
                    guardianAgeTemp = scanner.nextInt();
                    System.out.println("                                             └──────────────────────────────────────────────────────────────┘");
                } while(guardianAgeTemp < 18 || guardianAgeTemp > 120);
                scanner.nextLine();

                // Guardian Occupation
                do {
                    System.out.println("                                             ┌──────────────────────────────────────────────────────────────┐");
                    System.out.print  ("                                             │ ENTER GUARDIAN'S OCCUPATION: ");
                    guardianOccupationTemp = scanner.nextLine();
                    System.out.println("                                             └──────────────────────────────────────────────────────────────┘");
                } while (validator.occupationAndHomeTypeValidation(guardianOccupationTemp));

                System.out.println();
                System.out.println("┌──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┐");
                System.out.println("│                                                                                                                                                          │");
                System.out.println("└──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┘"+RESET);
            }

            do {
                System.out.println("                                             ┌──────────────────────────────────────────────────────────────┐");
                System.out.print  ("                                             │ ENTER HOME TYPE: ");
                homeTypeTemp = scanner.nextLine();
                System.out.println("                                             └──────────────────────────────────────────────────────────────┘");
            } while (validator.occupationAndHomeTypeValidation(homeTypeTemp));

            // Updated prompt for has other pets - now using yes/no instead of true/false
            do {
                System.out.println("                                             ┌──────────────────────────────────────────────────────────────┐");
                System.out.print  ("                                             │ HAS OTHER PET/S (yes/no): ");
                hasPetsResponse = scanner.nextLine().toLowerCase().trim();
                System.out.println("                                             └──────────────────────────────────────────────────────────────┘");
            } while (!hasPetsResponse.equals("yes") && !hasPetsResponse.equals("no"));

            hasOtherPets = hasPetsResponse.equals("yes");

            do {
                System.out.println("                                             ┌──────────────────────────────────────────────────────────────┐");
                System.out.print  ("                                             │ ENTER EMAIL: ");
                emailTemp = scanner.nextLine();
                System.out.println("                                             └──────────────────────────────────────────────────────────────┘");
            } while(validator.emailValidation(emailTemp));

            do {
                System.out.println("                                             ┌──────────────────────────────────────────────────────────────┐");
                System.out.print  ("                                             │ ENTER CONTACT NUMBER: ");
                contactNumberTemp = scanner.nextLine();
                System.out.println("                                             └──────────────────────────────────────────────────────────────┘");
                System.out.println();
                System.out.println("┌──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┐");
                System.out.println("│                                                                                                                                                          │");
                System.out.println("└──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┘"+RESET);
            } while(validator.contactNumberValidation(contactNumberTemp));

            // Check for duplicate customer before saving
            if (customerExists(nameTemp, emailTemp, contactNumberTemp)) {
                System.out.println(RED + "                                                    ❌ CUSTOMER ALREADY EXISTS!" + RESET);
                System.out.println(RED + "                                          A customer with this name, email, or contact number already exists." + RESET);
                return;
            }

            // Set all the validated values
            setName(nameTemp);
            setAge(ageTemp);
            setGender(gender);
            setAddress(addressTemp);
            setBirthMonth(birthMonthTemp);
            setBirthDay(birthDayTemp);
            setBirthYear(birthYearTemp);
            setOccupation(occupationTemp);
            setHomeType(homeTypeTemp);
            setHasOtherPets(hasOtherPets);
            setEmail(emailTemp);
            setContactNumber(contactNumberTemp);

            // Set guardian information if student
            if (occupationTemp.toLowerCase().trim().equals("student")) {
                setGuardianName(guardianNameTemp);
                setGuardianAge(guardianAgeTemp);
                setGuardianOccupation(guardianOccupationTemp);
            }

        }
        catch (InputMismatchException e) {
            throw new RuntimeException(e);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))){
            // Updated file write to include guardian information
            if (getOccupation().toLowerCase().trim().equals("student")) {
                writer.write(getCustomerID() + "," + getName() + "," + getAge() + "," + getGender() + "," + getAddress() +
                        "," + getBirthMonth() + "," + getBirthDay() + "," + getBirthYear() + "," + getOccupation() + "," +
                        getHomeType() + "," + getHasOtherPets() + "," + getEmail() + "," + getContactNumber() + "," +
                        getGuardianName() + "," + getGuardianAge() + "," + getGuardianOccupation() + "\n");
            } else {
                // For non-students, write empty guardian fields to maintain consistency
                writer.write(getCustomerID() + "," + getName() + "," + getAge() + "," + getGender() + "," + getAddress() +
                        "," + getBirthMonth() + "," + getBirthDay() + "," + getBirthYear() + "," + getOccupation() + "," +
                        getHomeType() + "," + getHasOtherPets() + "," + getEmail() + "," + getContactNumber() + "," +
                        "N/A,N/A,N/A\n");
            }
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

        final String BLUE = "\u001B[38;2;66;103;178m";
        final String RESET = "\u001B[0m";
        final String GRAY = "\u001B[38;2;137;143;156m";
        final String RED = "\u001B[31m";
        final String GREEN = "\u001B[32m";

        System.out.println();
        System.out.println(GRAY+"┌──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                                                                                                                                                          │");
        System.out.println("└──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┘");
        System.out.println(BLUE+"                                                                ┌────────────────────────┐                                                                     ");
        System.out.println("                                                                │ REMOVE CUSTOMER RECORD │                                                                     ");
        System.out.println("                                                                └────────────────────────┘                                                                     "+RESET);
        System.out.println();

        System.out.println(GRAY+"                                             ┌──────────────────────────────────────────────────────────────┐");
        System.out.print  ("                                             │ ENTER CUSTOMER ID TO REMOVE: ");
        int removeId = scanner.nextInt();
        System.out.println("                                             └──────────────────────────────────────────────────────────────┘");
        scanner.nextLine();

        System.out.println("                                             ┌──────────────────────────────────────────────────────────────┐");
        System.out.print  ("                                             │ ARE YOU SURE? (yes/no): ");
        String choice = scanner.nextLine().toLowerCase().trim();
        System.out.println("                                             └──────────────────────────────────────────────────────────────┘");
        System.out.println();
        System.out.println("┌──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                                                                                                                                                          │");
        System.out.println("└──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┘"+RESET);

        if (choice.equalsIgnoreCase("yes")) {
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
                System.out.println(RED + "                                                          CUSTOMER WITH ID " + removeId + " NOT FOUND." + RESET);
                return;
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (String customers : customerList) {
                    String[] customerDetails = customers.split(",");
                    writer.write(String.join(",", customerDetails) + "\n");
                }

                removeAccount(removeId); // Now remove from UserAccounts.txt too
                System.out.println(GREEN + "                                                          CUSTOMER SUCCESSFULLY REMOVED!" + RESET);
            } catch (IOException e) {
                throw new RuntimeException(RED + "                                                          ERROR WRITING TO THE FILE." + RESET, e);
            }
        } else {
            System.out.println(GRAY + "                                                                 REMOVAL CANCELLED." + RESET);
        }
    }

    @Override
    public void update() {

        Scanner scanner = new Scanner(System.in);

        final String BLUE = "\u001B[38;2;66;103;178m";
        final String RESET = "\u001B[0m";
        final String GRAY = "\u001B[38;2;137;143;156m";
        final String GREEN = "\u001B[32m";
        final String RED = "\u001B[31m";

        System.out.println();
        System.out.println(GRAY+"┌──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                                                                                                                                                          │");
        System.out.println("└──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┘");
        System.out.println(BLUE+"                                                                ┌────────────────────────┐                                                                     ");
        System.out.println("                                                                │ UPDATE CUSTOMER RECORD │                                                                     ");
        System.out.println("                                                                └────────────────────────┘                                                                     "+RESET);
        System.out.println();

        System.out.println(GRAY+"                                             ┌──────────────────────────────────────────────────────────────┐");
        System.out.print  ("                                             │ ENTER CUSTOMER ID TO EDIT: ");
        int editID = scanner.nextInt();
        System.out.println("                                             └──────────────────────────────────────────────────────────────┘");

        System.out.println();
        System.out.println("┌──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                                                                                                                                                          │");
        System.out.println("└──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┘"+RESET);

        scanner.nextLine();

        List<String> customerList = new ArrayList<>();
        boolean found = false;
        char gender;
        String nameTemp, addressTemp, homeTypeTemp, occupationTemp, emailTemp, contactNumberTemp;
        int ageTemp, birthMonthTemp, birthDayTemp, birthYearTemp;
        String hasPetsResponse;

        try {
            Scanner fileScanner = new Scanner(file);
            Validation validator = new Validation();

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
                            System.out.println(BLUE + "                                                                    ┌──────────────────┐");
                            System.out.println("                                                                    │ EDIT FIELD MENU  │");
                            System.out.println("                                                                    └──────────────────┘" + RESET);
                            System.out.println();
                            System.out.println(GRAY + "                                                            1. Name             8. Occupation");
                            System.out.println("                                                            2. Age              9. Home Type");
                            System.out.println("                                                            3. Gender           10. Has Other Pets");
                            System.out.println("                                                            4. Address          11. Email Address");
                            System.out.println("                                                            5. Birth Month      12. Contact Number");
                            System.out.println("                                                            6. Birth Day        13. Exit");
                            System.out.println("                                                            7. Birth Year");
                            System.out.println();
                            System.out.println("                                             ┌──────────────────────────────────────────────────────────────┐");
                            System.out.print  ("                                             │ ENTER CHOICE: ");
                            int choice = scanner.nextInt();
                            System.out.println("                                             └──────────────────────────────────────────────────────────────┘" + RESET);
                            scanner.nextLine();

                            switch (choice) {
                                case 1 -> {
                                    do {
                                        System.out.println(GRAY+"┌──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┐");
                                        System.out.println("│                                                                                                                                                          │");
                                        System.out.println("└──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┘");
                                        System.out.println(BLUE+"                                                                   ┌──────────────────┐                                                                     ");
                                        System.out.println("                                                                   │ CUSTOMER DETAILS │                                                                     ");
                                        System.out.println("                                                                   └──────────────────┘                                                                     "+RESET);
                                        System.out.println();

                                        System.out.println(GRAY+"                                             ┌──────────────────────────────────────────────────────────────┐");
                                        System.out.print  ("                                             │ ENTER NAME: ");
                                        nameTemp = scanner.nextLine();
                                        System.out.println("                                             └──────────────────────────────────────────────────────────────┘");
                                        validator.nameValidation(nameTemp);
                                    } while (!validator.nameValidation(nameTemp));
                                    setName(nameTemp);
                                }
                                case 2 -> {
                                    do {
                                        System.out.println(GRAY+"                                             ┌──────────────────────────────────────────────────────────────┐");
                                        System.out.print  ("                                             │ ENTER AGE: ");
                                        ageTemp = scanner.nextInt();
                                        System.out.println("                                             └──────────────────────────────────────────────────────────────┘");
                                    } while(ageTemp < 18 || ageTemp > 120);
                                    setAge(ageTemp);
                                    scanner.nextLine();
                                }
                                case 3 -> {
                                    do {
                                        System.out.println(GRAY+"                                             ┌──────────────────────────────────────────────────────────────┐");
                                        System.out.print  ("                                             │ ENTER GENDER (M/F): ");
                                        gender = scanner.next().toUpperCase().charAt(0);
                                        System.out.println("                                             └──────────────────────────────────────────────────────────────┘");
                                    } while (gender != 'M' && gender != 'F');
                                    setGender(gender);
                                    scanner.nextLine();
                                }
                                case 4 -> {
                                    do {
                                        System.out.println(GRAY+"                                             ┌──────────────────────────────────────────────────────────────┐");
                                        System.out.print  ("                                             │ ENTER ADDRESS: ");
                                        addressTemp = scanner.nextLine();
                                        System.out.println("                                             └──────────────────────────────────────────────────────────────┘");
                                    } while (validator.addressValidation(addressTemp));
                                    setAddress(addressTemp);
                                }
                                case 5 -> {
                                    do {
                                        System.out.println(GRAY+"                                             ┌──────────────────────────────────────────────────────────────┐");
                                        System.out.print  ("                                             │ ENTER BIRTH-MONTH: ");
                                        birthMonthTemp = scanner.nextInt();
                                        System.out.println("                                             └──────────────────────────────────────────────────────────────┘");
                                    } while (birthMonthTemp < 1 || birthMonthTemp > 12);
                                    setBirthMonth(birthMonthTemp);
                                    scanner.nextLine();
                                }
                                case 6 -> {
                                    do {
                                        System.out.println(GRAY+"                                             ┌──────────────────────────────────────────────────────────────┐");
                                        System.out.print  ("                                             │ ENTER DAY OF BIRTH: ");
                                        birthDayTemp = scanner.nextInt();
                                        System.out.println("                                             └──────────────────────────────────────────────────────────────┘");
                                    } while (birthDayTemp < 1 || birthDayTemp > 31);
                                    setBirthDay(birthDayTemp);
                                    scanner.nextLine();
                                }
                                case 7 -> {
                                    do {
                                        System.out.println(GRAY+"                                             ┌──────────────────────────────────────────────────────────────┐");
                                        System.out.print  ("                                             │ ENTER BIRTH-YEAR: ");
                                        birthYearTemp = scanner.nextInt();
                                        System.out.println("                                             └──────────────────────────────────────────────────────────────┘");
                                    } while (birthYearTemp < 1900 || birthYearTemp > 2025);
                                    setBirthYear(birthYearTemp);
                                    scanner.nextLine();
                                }
                                case 8 -> {
                                    do {
                                        System.out.println(GRAY+"                                             ┌──────────────────────────────────────────────────────────────┐");
                                        System.out.print  ("                                             │ ENTER OCCUPATION: ");
                                        occupationTemp = scanner.nextLine();
                                        System.out.println("                                             └──────────────────────────────────────────────────────────────┘");
                                    } while (validator.occupationAndHomeTypeValidation(occupationTemp));
                                    setOccupation(occupationTemp);
                                }
                                case 9 -> {
                                    do {
                                        System.out.println(GRAY+"                                             ┌──────────────────────────────────────────────────────────────┐");
                                        System.out.print  ("                                             │ ENTER HOME TYPE: ");
                                        homeTypeTemp = scanner.nextLine();
                                        System.out.println("                                             └──────────────────────────────────────────────────────────────┘");
                                    } while (validator.occupationAndHomeTypeValidation(homeTypeTemp));
                                    setHomeType(homeTypeTemp);
                                }
                                case 10 -> {
                                    // Updated to use yes/no instead of true/false
                                    do {
                                        System.out.println(GRAY+"                                             ┌──────────────────────────────────────────────────────────────┐");
                                        System.out.print  ("                                             │ HAS OTHER PET/S (yes/no): ");
                                        hasPetsResponse = scanner.nextLine().toLowerCase().trim();
                                        System.out.println("                                             └──────────────────────────────────────────────────────────────┘");
                                    } while (!hasPetsResponse.equals("yes") && !hasPetsResponse.equals("no"));
                                    setHasOtherPets(hasPetsResponse.equals("yes"));
                                }
                                case 11 -> {
                                    do {
                                        System.out.println(GRAY+"                                             ┌──────────────────────────────────────────────────────────────┐");
                                        System.out.print  ("                                             │ ENTER EMAIL: ");
                                        emailTemp = scanner.nextLine();
                                        System.out.println("                                             └──────────────────────────────────────────────────────────────┘");
                                    } while(validator.emailValidation(emailTemp));
                                    setEmail(emailTemp);
                                }
                                case 12 ->  {
                                    do {
                                        System.out.println(GRAY+"                                             ┌──────────────────────────────────────────────────────────────┐");
                                        System.out.print  ("                                             │ ENTER CONTACT NUMBER: ");
                                        contactNumberTemp = scanner.nextLine();
                                        System.out.println("                                             └──────────────────────────────────────────────────────────────┘");
                                        System.out.println();
                                        System.out.println("┌──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┐");
                                        System.out.println("│                                                                                                                                                          │");
                                        System.out.println("└──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┘"+RESET);
                                    } while(validator.contactNumberValidation(contactNumberTemp));
                                    setContactNumber(contactNumberTemp);
                                }
                                case 13 -> {
                                    System.out.println(GREEN + "                                                          EXITING EDIT MODE..." + RESET);
                                    isEditing = false;
                                }
                                default -> System.out.println(RED + "                                                          INVALID CHOICE! NO CHANGES MADE." + RESET);
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
        List<String[]> customerData = new ArrayList<>();

        // Read all customer data first
        try(Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String[] customerDetails = scanner.nextLine().split(",");
                // Updated to handle both old format (13 fields) and new format (16 fields)
                if (customerDetails.length == 13 || customerDetails.length == 16) {
                    customerData.add(customerDetails);
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        if (customerData.isEmpty()) {
            System.out.println("No customer records found.");
            return;
        }

        // Define table headers - updated to include guardian information
        String[] headers = {"ID", "Name", "Age", "Gender", "Address", "Birthday",
                "Occupation", "Home Type", "Has Pets", "Email", "Contact", "Guardian", "G.Age", "G.Occupation"};

        // Calculate column widths
        int[] columnWidths = new int[headers.length];

        // Initialize with header lengths
        for (int i = 0; i < headers.length; i++) {
            columnWidths[i] = headers[i].length();
        }

        // Check data lengths and update column widths
        for (String[] customer : customerData) {
            columnWidths[0] = Math.max(columnWidths[0], customer[0].length()); // ID
            columnWidths[1] = Math.max(columnWidths[1], customer[1].length()); // Name
            columnWidths[2] = Math.max(columnWidths[2], customer[2].length()); // Age
            columnWidths[3] = Math.max(columnWidths[3], customer[3].length()); // Gender
            columnWidths[4] = Math.max(columnWidths[4], customer[4].length()); // Address

            // Birthday format: MM/DD/YYYY
            String birthday = customer[5] + "/" + customer[6] + "/" + customer[7];
            columnWidths[5] = Math.max(columnWidths[5], birthday.length());

            columnWidths[6] = Math.max(columnWidths[6], customer[8].length()); // Occupation
            columnWidths[7] = Math.max(columnWidths[7], customer[9].length()); // Home Type
            columnWidths[8] = Math.max(columnWidths[8], customer[10].length()); // Has Pets
            columnWidths[9] = Math.max(columnWidths[9], customer[11].length()); // Email
            columnWidths[10] = Math.max(columnWidths[10], customer[12].length()); // Contact

            // Guardian information (check if fields exist)
            if (customer.length > 13) {
                columnWidths[11] = Math.max(columnWidths[11], customer[13].length()); // Guardian Name
                columnWidths[12] = Math.max(columnWidths[12], customer[14].length()); // Guardian Age
                columnWidths[13] = Math.max(columnWidths[13], customer[15].length()); // Guardian Occupation
            } else {
                // For old format records without guardian info
                columnWidths[11] = Math.max(columnWidths[11], 3); // "N/A"
                columnWidths[12] = Math.max(columnWidths[12], 3); // "N/A"
                columnWidths[13] = Math.max(columnWidths[13], 3); // "N/A"
            }
        }

        // Add padding to column widths
        for (int i = 0; i < columnWidths.length; i++) {
            columnWidths[i] += 2;
        }

        // Print top border
        System.out.print("┌");
        for (int i = 0; i < columnWidths.length; i++) {
            for (int j = 0; j < columnWidths[i]; j++) {
                System.out.print("─");
            }
            if (i < columnWidths.length - 1) {
                System.out.print("┬");
            }
        }
        System.out.println("┐");

        // Print headers
        System.out.print("│");
        for (int i = 0; i < headers.length; i++) {
            System.out.printf(" %-" + (columnWidths[i] - 1) + "s│", headers[i]);
        }
        System.out.println();

        // Print header separator
        System.out.print("├");
        for (int i = 0; i < columnWidths.length; i++) {
            for (int j = 0; j < columnWidths[i]; j++) {
                System.out.print("─");
            }
            if (i < columnWidths.length - 1) {
                System.out.print("┼");
            }
        }
        System.out.println("┤");

        // Print customer data rows
        for (int row = 0; row < customerData.size(); row++) {
            String[] customer = customerData.get(row);
            System.out.print("│");

            // Print each column
            System.out.printf(" %-" + (columnWidths[0] - 1) + "s│", customer[0]); // ID
            System.out.printf(" %-" + (columnWidths[1] - 1) + "s│", customer[1]); // Name
            System.out.printf(" %-" + (columnWidths[2] - 1) + "s│", customer[2]); // Age
            System.out.printf(" %-" + (columnWidths[3] - 1) + "s│", customer[3]); // Gender
            System.out.printf(" %-" + (columnWidths[4] - 1) + "s│", customer[4]); // Address

            // Birthday
            String birthday = customer[5] + "/" + customer[6] + "/" + customer[7];
            System.out.printf(" %-" + (columnWidths[5] - 1) + "s│", birthday);

            System.out.printf(" %-" + (columnWidths[6] - 1) + "s│", customer[8]); // Occupation
            System.out.printf(" %-" + (columnWidths[7] - 1) + "s│", customer[9]); // Home Type
            System.out.printf(" %-" + (columnWidths[8] - 1) + "s│", customer[10]); // Has Pets
            System.out.printf(" %-" + (columnWidths[9] - 1) + "s│", customer[11]); // Email
            System.out.printf(" %-" + (columnWidths[10] - 1) + "s│", customer[12]); // Contact

            // Guardian information
            if (customer.length > 13) {
                System.out.printf(" %-" + (columnWidths[11] - 1) + "s│", customer[13]); // Guardian Name
                System.out.printf(" %-" + (columnWidths[12] - 1) + "s│", customer[14]); // Guardian Age
                System.out.printf(" %-" + (columnWidths[13] - 1) + "s│", customer[15]); // Guardian Occupation
            } else {
                // For old format records
                System.out.printf(" %-" + (columnWidths[11] - 1) + "s│", "N/A"); // Guardian Name
                System.out.printf(" %-" + (columnWidths[12] - 1) + "s│", "N/A"); // Guardian Age
                System.out.printf(" %-" + (columnWidths[13] - 1) + "s│", "N/A"); // Guardian Occupation
            }

            System.out.println();
        }

        // Print bottom border
        System.out.print("└");
        for (int i = 0; i < columnWidths.length; i++) {
            for (int j = 0; j < columnWidths[i]; j++) {
                System.out.print("─");
            }
            if (i < columnWidths.length - 1) {
                System.out.print("┴");
            }
        }
        System.out.println("┘");

        // Rest of your action menu code...
        Scanner scanner2 = new Scanner(System.in);
        boolean continueRunning = true;

        final String BLUE = "\u001B[38;2;66;103;178m";
        final String RESET = "\u001B[0m";
        final String GRAY = "\u001B[38;2;137;143;156m";
        final String RED = "\u001B[31m";

        while (continueRunning) {
            System.out.println();
            System.out.println(GRAY+"┌──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┐");
            System.out.println("│                                                                                                                                                          │");
            System.out.println("└──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┘");
            System.out.println(BLUE+"                                                                      ┌─────────────┐                                                                           ");
            System.out.println("                                                                      │   ACTIONS   │                                                                           ");
            System.out.println("                                                                      └─────────────┘                                                                           "+RESET);
            System.out.println();
            System.out.println(GRAY+"                                                            1. Update Customer Details");
            System.out.println("                                                            2. Remove Customer");
            System.out.println("                                                            3. Exit");
            System.out.println();
            System.out.println("                                             ┌──────────────────────────────────────────────────────────────┐");
            System.out.print  ("                                             │ ENTER CHOICE: ");
            int choice = scanner2.nextInt();
            System.out.println("                                             └──────────────────────────────────────────────────────────────┘");
            System.out.println();
            System.out.println("┌──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┐");
            System.out.println("│                                                                                                                                                          │");
            System.out.println("└──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┘"+RESET);

            switch (choice) {
                case 1 -> update();
                case 2 -> remove();
                case 3 -> {
                    System.out.println(GRAY + "                                                                   EXITING..." + RESET);
                    continueRunning = false;
                }
                default -> System.out.println(RED + "                                                          INVALID CHOICE! PLEASE TRY AGAIN." + RESET);
            }
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