package Adoption_Management_Package;

import java.io.*;
import java.util.*;

/**
 * The PetManagement class provides functionality to manage pet records, including
 * adding new pets, updating existing pet information, removing pet records, and
 * searching for pets by type. The class extends the Pet class and implements the
 * ManagementFunctions interface, which defines the required management behaviors.
 *
 * This class utilizes a text file ("Pet.txt") as the storage medium for pet data.
 * Each pet's data is stored as a comma-separated value (CSV) record.
 *
 * Key Features:
 * - Add a new pet with unique ID and attributes (name, type, breed, etc.).
 * - Remove a pet by its unique ID.
 * - Update specific attributes of an existing pet.
 * - Search for pets based on their type.
 */

public class PetManagement extends Pet implements ManagementFunctions {
    protected File file = new File("Pet.txt");

    @Override
    public void add() {
        Scanner scanner = new Scanner(System.in);
        char gender;
        String nameTemp;
        int birthMonthTemp, birthDayTemp, birthYearTemp, ageTemp;

        try {

            Validation validator = new Validation();

            // sets a unique id to the pet
            Random random = new Random();
            int id = random.nextInt(1_000_000);
            setId(id);

            final String BLUE = "\u001B[38;2;66;103;178m";
            final String RESET = "\u001B[0m";
            final String GRAY = "\u001B[38;2;137;143;156m";

            do {
                System.out.println(GRAY+"┌──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┐");
                System.out.println("│                                                                                                                                                          │");
                System.out.println("└──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┘");
                System.out.println(BLUE+"                                                                    ┌─────────────────┐                                                                     ");
                System.out.println("                                                                    │     ADD PET     │                                                                     ");
                System.out.println("                                                                    └─────────────────┘                                                                     "+RESET);
                System.out.println();
                System.out.println(GRAY+"                                             ┌──────────────────────────────────────────────────────────────┐");
                System.out.print  ("                                             │ ENTER NAME: ");
                nameTemp = scanner.nextLine();
                System.out.println("                                             └──────────────────────────────────────────────────────────────┘");
                validator.nameValidation(nameTemp);
            } while (!validator.nameValidation(nameTemp));
            setName(nameTemp);

            System.out.println("                                             ┌──────────────────────────────────────────────────────────────┐");
            System.out.print  ("                                             │ ENTER TYPE: ");
            setType(scanner.nextLine());
            System.out.println("                                             └──────────────────────────────────────────────────────────────┘");

            System.out.println("                                             ┌──────────────────────────────────────────────────────────────┐");
            System.out.print  ("                                             │ ENTER BREED: ");
            setBreed(scanner.nextLine());
            System.out.println("                                             └──────────────────────────────────────────────────────────────┘");

            do {
                System.out.println("                                             ┌──────────────────────────────────────────────────────────────┐");
                System.out.print  ("                                             │ ENTER AGE: ");
                ageTemp = scanner.nextInt();
                System.out.println("                                             └──────────────────────────────────────────────────────────────┘");
            } while(ageTemp < 0 || ageTemp > 200);
            setAge(ageTemp);
            scanner.nextLine();

            do {
                System.out.println("                                             ┌──────────────────────────────────────────────────────────────┐");
                System.out.print  ("                                             │ ENTER BIRTH-MONTH: ");
                birthMonthTemp = scanner.nextInt();
                System.out.println("                                             └──────────────────────────────────────────────────────────────┘");
            } while (birthMonthTemp < 1 || birthMonthTemp > 12);
            setBirthMonth(birthMonthTemp);
            scanner.nextLine();

            do {
                System.out.println("                                             ┌──────────────────────────────────────────────────────────────┐");
                System.out.print  ("                                             │ ENTER DAY OF BIRTH: ");
                birthDayTemp = scanner.nextInt();
                System.out.println("                                             └──────────────────────────────────────────────────────────────┘");
            } while (birthDayTemp < 1 || birthDayTemp > 31);
            setBirthDay(birthDayTemp);
            scanner.nextLine();

            do {
                System.out.println("                                             ┌──────────────────────────────────────────────────────────────┐");
                System.out.print  ("                                             │ ENTER BIRTH-YEAR: ");
                birthYearTemp = scanner.nextInt();
                System.out.println("                                             └──────────────────────────────────────────────────────────────┘");
            } while (birthYearTemp < 1825 || birthYearTemp > 2025);
            setBirthYear(birthYearTemp);
            scanner.nextLine();

            do {
                System.out.println("                                             ┌──────────────────────────────────────────────────────────────┐");
                System.out.print  ("                                             │ ENTER GENDER (F/M): ");
                gender = scanner.next().toUpperCase().charAt(0);
                System.out.println("                                             └──────────────────────────────────────────────────────────────┘");
                System.out.println();
                System.out.println(GRAY+"┌──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┐");
                System.out.println("│                                                                                                                                                          │");
                System.out.println("└──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┘");
            } while (gender != 'M' && gender != 'F');
            setGender(gender);

        } catch (InputMismatchException e) {
            throw new RuntimeException(e);
        }
        final String GREEN = "\u001B[38;2;0;210;106m";
        final String RESET = "\u001B[0m";

        // Write data to Pet.txt
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(getId() + "," + getName() + "," + getType() + "," + getBreed() + "," + getAge() + "," +
                    getBirthMonth() + "," + getBirthDay() + "," + getBirthYear() + "," + getGender() + "\n");
            System.out.println(GREEN+"                                                                  PET ADDED SUCCESSFULLY!                                                                 \n"+RESET);
        } catch (IOException e) {
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
        System.out.println(BLUE+"                                                                ┌──────────────────────┐                                                                     ");
        System.out.println("                                                                │ REMOVE PET RECORD    │                                                                     ");
        System.out.println("                                                                └──────────────────────┘                                                                     "+RESET);
        System.out.println();

        System.out.println(GRAY+"                                             ┌──────────────────────────────────────────────────────────────┐");
        System.out.print  ("                                             │ ENTER PET ID TO REMOVE: ");
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
            List<String> petList = new ArrayList<>();
            boolean found = false;

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {

                    // Ignore empty lines
                    if (line.trim().isEmpty()) continue;

                    String[] petDetails = line.split(",");

                    // Validate that petDetails[0] is an integer before parsing
                    if (!petDetails[0].matches("\\d+")) continue;

                    int petId = Integer.parseInt(petDetails[0]);

                    if (petId == removeId) {
                        found = true;
                        continue; // Skip this pet (removing it)
                    }

                    petList.add(line); // Keep other pets
                }
            } catch (IOException e) {
                throw new RuntimeException("Error reading the file.", e);
            }

            if (!found) {
                System.out.println(RED + "                                                                PET WITH ID " + removeId + " NOT FOUND." + RESET);
                return;
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (String pets : petList) {
                    String[] petDetails = pets.split(",");
                    writer.write(String.join(",", petDetails) + "\n");
                }
                System.out.println(GREEN + "                                                          PET SUCCESSFULLY REMOVED!" + RESET);
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
        System.out.println(BLUE+"                                                                ┌──────────────────────┐                                                                     ");
        System.out.println("                                                                │ UPDATE PET RECORD    │                                                                     ");
        System.out.println("                                                                └──────────────────────┘                                                                     "+RESET);
        System.out.println();

        System.out.println(GRAY+"                                             ┌──────────────────────────────────────────────────────────────┐");
        System.out.print  ("                                             │ ENTER PET ID TO EDIT: ");
        int editID = scanner.nextInt();
        System.out.println("                                             └──────────────────────────────────────────────────────────────┘");

        System.out.println();
        System.out.println("┌──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                                                                                                                                                          │");
        System.out.println("└──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┘"+RESET);

        scanner.nextLine();

        List<String> petList = new ArrayList<>();
        boolean found = false;
        char gender;
        String nameTemp;
        int birthMonthTemp, birthDayTemp, birthYearTemp, ageTemp;

        try {
            Scanner fileScanner = new Scanner(file);
            Validation validator = new Validation();
            boolean updated = false;

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] petDetails = line.split(",");

                if (petDetails.length == 9) {
                    setId(Integer.parseInt(petDetails[0]));
                    setName(petDetails[1]);
                    setType(petDetails[2]);
                    setBreed(petDetails[3]);
                    setAge(Integer.parseInt(petDetails[4]));
                    setBirthMonth(Integer.parseInt(petDetails[5]));
                    setBirthDay(Integer.parseInt(petDetails[6]));
                    setBirthYear(Integer.parseInt(petDetails[7]));
                    setGender(petDetails[8].charAt(0));

                    if (getId() == editID) {
                        found = true;
                        boolean isEditing = true;


                        while (isEditing) {
                            System.out.println(BLUE + "                                                                 ┌──────────────────┐");
                            System.out.println("                                                                 │ EDIT FIELD MENU  │");
                            System.out.println("                                                                 └──────────────────┘" + RESET);
                            System.out.println();
                            System.out.println(GRAY + "                                                         1. Name             6. Birth Day");
                            System.out.println("                                                         2. Type             7. Birth Year");
                            System.out.println("                                                         3. Breed            8. Gender");
                            System.out.println("                                                         4. Age              9. Exit");
                            System.out.println("                                                         5. Birth Month");
                            System.out.println();
                            System.out.println("                                            ┌──────────────────────────────────────────────────────────────┐");
                            System.out.print  ("                                            │ ENTER CHOICE: ");
                            int choice = scanner.nextInt();
                            System.out.println("                                            └──────────────────────────────────────────────────────────────┘" + RESET);
                            scanner.nextLine();

                            switch (choice) {
                                case 1 -> {
                                    do {
                                        System.out.println(GRAY+"┌──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┐");
                                        System.out.println("│                                                                                                                                                          │");
                                        System.out.println("└──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┘");
                                        System.out.println(BLUE+"                                                                   ┌──────────────────┐                                                                     ");
                                        System.out.println("                                                                   │   PET DETAILS    │                                                                     ");
                                        System.out.println("                                                                   └──────────────────┘                                                                     "+RESET);
                                        System.out.println();

                                        System.out.println(GRAY+"                                             ┌──────────────────────────────────────────────────────────────┐");
                                        System.out.print  ("                                             │ ENTER NAME: ");
                                        nameTemp = scanner.nextLine();
                                        System.out.println("                                             └──────────────────────────────────────────────────────────────┘");
                                        validator.nameValidation(nameTemp);
                                    } while (!validator.nameValidation(nameTemp));
                                    setName(nameTemp);
                                    updated = true;
                                }
                                case 2 -> {
                                    System.out.println(GRAY+"                                             ┌──────────────────────────────────────────────────────────────┐");
                                    System.out.print  ("                                             │ ENTER TYPE: ");
                                    setType(scanner.nextLine());
                                    System.out.println("                                             └──────────────────────────────────────────────────────────────┘");
                                    updated = true;
                                }
                                case 3 -> {
                                    System.out.println(GRAY+"                                             ┌──────────────────────────────────────────────────────────────┐");
                                    System.out.print  ("                                             │ ENTER BREED: ");
                                    setBreed(scanner.nextLine());
                                    System.out.println("                                             └──────────────────────────────────────────────────────────────┘");
                                    updated = true;
                                }
                                case 4 -> {
                                    do {
                                        System.out.println(GRAY+"                                             ┌──────────────────────────────────────────────────────────────┐");
                                        System.out.print  ("                                             │ ENTER AGE: ");
                                        ageTemp = scanner.nextInt();
                                        System.out.println("                                             └──────────────────────────────────────────────────────────────┘");
                                    } while(ageTemp < 0 || ageTemp > 200);
                                    setAge(ageTemp);
                                    scanner.nextLine();
                                    updated = true;
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
                                    updated = true;
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
                                    updated = true;
                                }
                                case 7 -> {
                                    do {
                                        System.out.println(GRAY+"                                             ┌──────────────────────────────────────────────────────────────┐");
                                        System.out.print  ("                                             │ ENTER BIRTH-YEAR: ");
                                        birthYearTemp = scanner.nextInt();
                                        System.out.println("                                             └──────────────────────────────────────────────────────────────┘");
                                    } while (birthYearTemp < 1825 || birthYearTemp > 2025);
                                    setBirthYear(birthYearTemp);
                                    scanner.nextLine();
                                    updated = true;
                                }
                                case 8 -> {
                                    do {
                                        System.out.println(GRAY+"                                             ┌──────────────────────────────────────────────────────────────┐");
                                        System.out.print  ("                                             │ ENTER GENDER (F/M): ");
                                        gender = scanner.next().toUpperCase().charAt(0);
                                        System.out.println("                                             └──────────────────────────────────────────────────────────────┘");
                                        System.out.println();
                                        System.out.println("┌──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┐");
                                        System.out.println("│                                                                                                                                                          │");
                                        System.out.println("└──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┘"+RESET);
                                    } while (gender != 'M' && gender != 'F');
                                    setGender(gender);
                                    scanner.nextLine();
                                    updated = true;
                                }
                                case 9 -> {
                                    System.out.println(GREEN + "                                              EXITING EDIT MODE..." + RESET);
                                    isEditing = false;
                                }
                                default -> System.out.println(RED + "                                              INVALID CHOICE! NO CHANGES MADE." + RESET);
                            }
                        }
                        // replace old data with new data
                        line = getId() + "," + getName() + "," + getType() + "," + getBreed() + "," + getAge() + "," +
                                getBirthMonth() + "," + getBirthDay() + "," + getBirthYear() + "," + getGender();

                    }
                }
                petList.add(line);
            }
            // If pet was found, rewrite the file with updated data
            if (found) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    for (String pet : petList) {
                        writer.write(pet + "\n");
                    } if(updated){
                        System.out.println(GREEN + "                                              PET DETAILS UPDATED SUCCESSFULLY!" + RESET);
                    }

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else {
                System.out.println(RED + "                                              PET ID NOT FOUND." + RESET);
            }

        }
        catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void searchPet() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("                                             ┌──────────────────────────────────────────────────────────────┐");
        System.out.print  ("                                             │ ENTER PET TYPE TO SEARCH: ");
        String searchType = scanner.nextLine().trim();
        System.out.println("                                             └──────────────────────────────────────────────────────────────┘");


        try (Scanner fileScanner = new Scanner(file)) {
            boolean found = false;
            String padding = "│                               "; // to align with main box sides
            final String RESET = "\033[0m";
            final String AQUA_LIGHT = "\u001B[38;2;161;227;239m"+"\033[1m";

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] petDetails = line.split(",");

                if (petDetails.length == 9) { // Ensures correct format
                    setId(Integer.parseInt(petDetails[0]));
                    setName(petDetails[1]);
                    setType(petDetails[2]);
                    setBreed(petDetails[3]);
                    setAge(Integer.parseInt(petDetails[4]));
                    setBirthMonth(Integer.parseInt(petDetails[5]));
                    setBirthDay(Integer.parseInt(petDetails[6]));
                    setBirthYear(Integer.parseInt(petDetails[7]));
                    setGender(petDetails[8].charAt(0));

                    if (getType().equalsIgnoreCase(searchType)) {
                        setId(Integer.parseInt(petDetails[0]));
                        setName(petDetails[1]);
                        setType(petDetails[2]);
                        setBreed(petDetails[3]);
                        setAge(Integer.parseInt(petDetails[4]));
                        setBirthMonth(Integer.parseInt(petDetails[5]));
                        setBirthDay(Integer.parseInt(petDetails[6]));
                        setBirthYear(Integer.parseInt(petDetails[7]));
                        setGender(petDetails[8].charAt(0));

                        found = true;

                        System.out.println();
                        System.out.println("┌──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┐");
                        System.out.println("│                                                               "+AQUA_LIGHT+"       PET DETAILS     "+RESET+"                                                                    │");
                        System.out.println("├──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┤");

                        printCenteredBox(padding, "ID", String.valueOf(getId()));
                        printCenteredBox(padding, "NAME", getName());
                        printCenteredBox(padding, "TYPE", getType());
                        printCenteredBox(padding, "BREED", getBreed());
                        printCenteredBox(padding, "AGE", String.valueOf(getAge()));
                        printCenteredBox(padding, "BIRTHDAY", getBirthMonth() + "/" + getBirthDay() + "/" + getBirthYear());
                        printCenteredBox(padding, "GENDER", String.valueOf(getGender()));

                        // Bottom Border
                        System.out.println("└──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┘"+RESET);

                    }
                }
            }

            if (!found) {
                System.out.println("Pet Type not found.");
            }
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void view() {
        List<String[]> petData = new ArrayList<>();

        // Read all pet data first
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String[] petDetails = scanner.nextLine().split(",");
                if (petDetails.length == 9) {
                    petData.add(petDetails);
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        if (petData.isEmpty()) {
            System.out.println("\n                                             No pet records found.");
            return;
        }

        // Define table headers
        String[] headers = {"ID", "Name", "Type", "Breed", "Age", "Birthday", "Gender"};

        // Calculate column widths
        int[] columnWidths = new int[headers.length];

        // Initialize with header lengths
        for (int i = 0; i < headers.length; i++) {
            columnWidths[i] = headers[i].length();
        }

        // Check data lengths and update column widths
        for (String[] pet : petData) {
            columnWidths[0] = Math.max(columnWidths[0], pet[0].length()); // ID
            columnWidths[1] = Math.max(columnWidths[1], pet[1].length()); // Name
            columnWidths[2] = Math.max(columnWidths[2], pet[2].length()); // Type
            columnWidths[3] = Math.max(columnWidths[3], pet[3].length()); // Breed
            columnWidths[4] = Math.max(columnWidths[4], pet[4].length()); // Age

            // Birthday format: MM/DD/YYYY
            String birthday = pet[5] + "/" + pet[6] + "/" + pet[7];
            columnWidths[5] = Math.max(columnWidths[5], birthday.length());

            columnWidths[6] = Math.max(columnWidths[6], pet[8].length()); // Gender
        }

        // Add padding to column widths (minimum 2 spaces padding per column)
        for (int i = 0; i < columnWidths.length; i++) {
            columnWidths[i] += 4; // Increased padding for better spacing
        }

        // Calculate total table width more accurately
        int totalWidth = 1; // Left border
        for (int i = 0; i < columnWidths.length; i++) {
            totalWidth += columnWidths[i] + 1; // Column width + right border
        }

        // Calculate left padding to center the table
        // Using 140 as terminal width to match your ASCII art width
        int terminalWidth = 140;
        int leftPadding = Math.max(0, (terminalWidth - totalWidth) / 2) + 8; // Added 8 spaces to move right
        String padding = " ".repeat(leftPadding);

        System.out.println(); // Add some space before the table

        // Print top border
        System.out.print(padding + "┌");
        for (int i = 0; i < columnWidths.length; i++) {
            System.out.print("─".repeat(columnWidths[i]));
            if (i < columnWidths.length - 1) {
                System.out.print("┬");
            }
        }
        System.out.println("┐");

        // Print headers
        System.out.print(padding + "│");
        for (int i = 0; i < headers.length; i++) {
            System.out.printf(" %-" + (columnWidths[i] - 1) + "s│", headers[i]);
        }
        System.out.println();

        // Print header separator
        System.out.print(padding + "├");
        for (int i = 0; i < columnWidths.length; i++) {
            System.out.print("─".repeat(columnWidths[i]));
            if (i < columnWidths.length - 1) {
                System.out.print("┼");
            }
        }
        System.out.println("┤");

        // Print pet data rows
        for (String[] pet : petData) {
            System.out.print(padding + "│");

            // Print each column with proper formatting
            System.out.printf(" %-" + (columnWidths[0] - 1) + "s│", pet[0]); // ID
            System.out.printf(" %-" + (columnWidths[1] - 1) + "s│", pet[1]); // Name
            System.out.printf(" %-" + (columnWidths[2] - 1) + "s│", pet[2]); // Type
            System.out.printf(" %-" + (columnWidths[3] - 1) + "s│", pet[3]); // Breed
            System.out.printf(" %-" + (columnWidths[4] - 1) + "s│", pet[4]); // Age

            // Birthday
            String birthday = pet[5] + "/" + pet[6] + "/" + pet[7];
            System.out.printf(" %-" + (columnWidths[5] - 1) + "s│", birthday);

            System.out.printf(" %-" + (columnWidths[6] - 1) + "s│", pet[8]); // Gender

            System.out.println();
        }

        // Print bottom border
        System.out.print(padding + "└");
        for (int i = 0; i < columnWidths.length; i++) {
            System.out.print("─".repeat(columnWidths[i]));
            if (i < columnWidths.length - 1) {
                System.out.print("┴");
            }
        }
        System.out.println("┘");

        // Add some space after the table
        System.out.println();

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

    public static void printCenteredBox(String padding, String label, String value) {
        int boxWidth = 90;

        // Colors
        final String RESET = "\u001B[0m";
        final String AQUA_LIGHT = "\u001B[38;2;161;227;239m"+"\033[1m";
        final String SKY_BLUE = "\u001B[38;2;147;211;233m"+"\033[1m";

        // Build colored content
        String content = String.format("│ %s%-35s%s │  %s%-50s%s│",
                AQUA_LIGHT, label, RESET,
                SKY_BLUE, value, RESET);

        String top = "┌" + "─".repeat(boxWidth) + "┐";
        String bottom = "└" + "─".repeat(boxWidth) + "┘";

        System.out.println(padding + top + "                               │");
        System.out.println(padding + content + "                               │");
        System.out.println(padding + bottom + "                               │");
    }
}