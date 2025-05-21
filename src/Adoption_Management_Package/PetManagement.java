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

        try {

            // sets a unique id to the pet
            Random random = new Random();
            int id = random.nextInt(1_000_000);
            setId(id);

            System.out.print("Enter Name: ");
            setName(scanner.nextLine());

            System.out.print("Enter Type: ");
            setType(scanner.nextLine());

            System.out.print("Enter Breed: ");
            setBreed(scanner.nextLine());

            System.out.print("Enter Age: ");
            setAge(scanner.nextInt());

            System.out.print("Enter Birth Month: ");
            setBirthMonth(scanner.nextInt());

            System.out.print("Enter Birth Day: ");
            setBirthDay(scanner.nextInt());

            System.out.print("Enter Birth Year: ");
            setBirthYear(scanner.nextInt());

            do {
                System.out.print("Enter Gender (M/F): ");
                gender = scanner.next().toUpperCase().charAt(0);
            } while (gender != 'M' && gender != 'F');
            setGender(gender);

        } catch (InputMismatchException e) {
            throw new RuntimeException(e);
        }

        // Write data to Pet.txt
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(getId() + "," + getName() + "," + getType() + "," + getBreed() + "," + getAge() + "," +
                    getBirthMonth() + "," + getBirthDay() + "," + getBirthYear() + "," + getGender() + "\n");
            System.out.println("Pet added successfully!\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Pet ID to remove: ");
        int removeId = scanner.nextInt();
        scanner.nextLine();

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
            System.out.println("Pet with ID " + removeId + " not found.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String pets : petList) {
                String[] petDetails = pets.split(",");
                writer.write(String.join(",", petDetails) + "\n");
            }
            System.out.println("Pet removed.");
        } catch (IOException e) {
            throw new RuntimeException("Error writing to the file.", e);
        }
    }

    @Override
    public void update() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Pet ID to edit: ");
        int editID = scanner.nextInt();
        scanner.nextLine();

        List<String> petList = new ArrayList<>();
        boolean found = false;

        try {
            Scanner fileScanner = new Scanner(file);

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] petDetails = line.split(",");

                // Ensure correct format
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
                            System.out.println("""
                                     \nEdit:\s
                                     1. Name
                                     2. Type
                                     3. Breed
                                     4. Age
                                     5. Birth Month
                                     6. Birth Day
                                     7. Birth Year
                                     8. Gender
                                     9. Exit
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
                                    System.out.print("Enter New Type: ");
                                    setType(scanner.nextLine());
                                }
                                case 3 -> {
                                    System.out.print("Enter New Breed: ");
                                    setBreed(scanner.nextLine());
                                }
                                case 4 -> {
                                    System.out.print("Enter New Age: ");
                                    setAge(scanner.nextInt());
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
                                    System.out.print("Enter New Gender: ");
                                    setGender(scanner.next().charAt(0));
                                }
                                case 9 -> {
                                    System.out.print("Exiting... ");
                                    isEditing = false;
                                }
                                default -> System.out.println("Invalid Choice! No changes made.");
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
                    }
                    System.out.println("Pet details updated successfully!\n");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else {
                System.out.println("Pet ID not found.\n");
            }

        }
        catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }

    }

    public void searchPet() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Pet Type to search: ");
        String searchType = scanner.nextLine().trim();

        try (Scanner fileScanner = new Scanner(file)) {
            boolean found = false;

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
                        System.out.println("\nPet Found!");
                        System.out.println("ID: " + getId());
                        System.out.println("Name: " + getName());
                        System.out.println("Type: " + getType());
                        System.out.println("Breed: " + getBreed());
                        System.out.println("Age: " + getAge());
                        System.out.println("Birthday: " + getBirthMonth() + "/" +
                                getBirthDay() + "/" + getBirthYear());
                        System.out.println("Gender: " + getGender());
                        found = true;
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
        boolean continueRunning = true;
        try (Scanner scanner = new Scanner(file)) {

            while (scanner.hasNextLine()) {
                String[] petDetails = scanner.nextLine().split(",");

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

                    System.out.println("\n--- Pet Details ---");
                    System.out.println("ID: " + getId());
                    System.out.println("Name: " + getName());
                    System.out.println("Type: " + getType());
                    System.out.println("Breed: " + getBreed());
                    System.out.println("Age: " + getAge());
                    System.out.println("Birthday: " + getBirthMonth() + "/" + getBirthDay() + "/" + getBirthYear());
                    System.out.println("Gender: " + getGender());
                    System.out.println("----------------------");
                }
            }
            Scanner scanner2 = new Scanner(System.in);
            while (continueRunning) {
                System.out.println("""
                         \nAction:\s
                         1. Update Pet Details
                         2. Remove Pet
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

}