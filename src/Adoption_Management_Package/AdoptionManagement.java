package Adoption_Management_Package;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import Pdf_Converter_Package.PDFGenerator;
import UI_LogIn_Package.*;

/**
 * The AdoptionManagement class is responsible for managing the operations
 * and functionalities within the pet adoption system. It extends the UserInterface
 * class and provides additional functionalities specifically for handling the
 * adoption process, customer management, pet management, and report generation.
 *
 * Updated with improvements:
 * - Prevents duplicate customer registration
 * - Shows proper customer names in adoption requests
 * - Displays pet status (Available, Currently Requested, etc.)
 * - Uses Yes/No instead of true/false for "has other pets"
 */
public class AdoptionManagement extends UserInterface {

    private final LoginPage loginPage;
    CustomerManagement customerManager = new CustomerManagement();
    private final File adoptionReport = new File("AdoptionReports.txt");
    private final File adoptionRequest = new File("AdoptionRequest.txt");
    private final File adoptionHistory = new File("AdoptionHistory.txt");
    private final File remainingPetsReport = new File("RemainingPetsReport.txt");
    private final File requestedPets = new File("RequestedPets.txt");
    int requestID;

    // Constructor to inject LoginPage
    public AdoptionManagement(LoginPage loginPage) {
        this.loginPage = loginPage;
    }

    /**
     * Displays the user interface for non-administrative users, allowing them to interact
     * with available features such as viewing pets, making adoption requests, and searching for pets.
     */
    public void nonAdminUserInterface() {
        String currentUsername = loginPage.getCurrentUsername();
        if (currentUsername == null) {
            System.out.println("❌ Error: No active session.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            userInterface(); // Display menu
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> viewWithStatus(); // View pets with status
                case 2 -> makeAdoptionRequest(); // Use the persisted login state
                case 3 -> searchPet();
                case 4 -> System.out.println("Logging out...");
                default -> System.out.println("Invalid option.");
            }
        } while (choice != 4);
    }

    public void adminUserInterface() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            adminInterface();
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> customerManagement();
                case 2 -> petManagement();
                case 3 -> reportsManagement();
                case 4 -> System.out.println("Logging out...");
                default -> System.out.println("Invalid option. Please try again.");
            }
        } while (choice != 4);
    }

    public void customerManagement() {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        int choice;

        while (isRunning) {
            customerManagementMenu();
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addCustomerWithDuplicateCheck();
                case 2 -> customerManager.view();
                case 3 -> reviewAdoptionRequest();
                case 4 -> {
                    System.out.println("Exiting customer management menu...");
                    isRunning = false;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    public void petManagement() {
        Scanner scanner = new Scanner(System.in);
        PetManagement petManagement = new PetManagement();

        boolean isRunning = true;
        int choice;

        while (isRunning) {
            petManagementMenu();
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> petManagement.add();
                case 2 -> petManagement.view();
                case 3 -> {
                    System.out.println("Exiting pet management menu...");
                    isRunning = false;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    public void reportsManagement() {
        Scanner scanner = new Scanner(System.in);
        PDFGenerator pdfGenerator = new PDFGenerator();

        boolean isRunning = true;
        while (isRunning) {
            reportsMenu();

            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1 -> handleAdoptionReport(pdfGenerator);
                    case 2 -> handleRemainingPetsReport(pdfGenerator);
                    case 3 -> isRunning = false;
                    default -> System.out.println("Invalid option");
                }
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
                scanner.nextLine(); // Clear invalid input
            }
        }
    }

    /**
     * Enhanced method to add customer with duplicate checking
     */
    public void addCustomerWithDuplicateCheck() {
        final String BLUE = "\u001B[38;2;66;103;178m";
        final String RESET = "\u001B[0m";
        final String GRAY = "\u001B[38;2;137;143;156m";
        final String RED = "\u001B[31m";
        final String GREEN = "\u001B[32m";

        Scanner scanner = new Scanner(System.in);

        System.out.println(GRAY+"┌──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                                                                                                                                                          │");
        System.out.println("└──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┘");
        System.out.println(BLUE+"                                                                    ┌─────────────────┐                                                                     ");
        System.out.println("                                                                    │   ADD CUSTOMER   │                                                                     ");
        System.out.println("                                                                    └─────────────────┘                                                                     "+RESET);

        // Get customer details first
        System.out.println(GRAY+"                                             ┌──────────────────────────────────────────────────────────────┐");
        System.out.print  ("                                             │ ENTER CUSTOMER NAME: ");
        String name = scanner.nextLine();
        System.out.println("                                             └──────────────────────────────────────────────────────────────┘");

        System.out.println("                                             ┌──────────────────────────────────────────────────────────────┐");
        System.out.print  ("                                             │ ENTER EMAIL: ");
        String email = scanner.nextLine();
        System.out.println("                                             └──────────────────────────────────────────────────────────────┘");

        System.out.println("                                             ┌──────────────────────────────────────────────────────────────┐");
        System.out.print  ("                                             │ ENTER CONTACT NUMBER: ");
        String contactNumber = scanner.nextLine();
        System.out.println("                                             └──────────────────────────────────────────────────────────────┘"+RESET);

        // Check for duplicates
        if (isCustomerDuplicate(name, email, contactNumber)) {
            System.out.println();
            System.out.println(RED+"                                                      ❌ CUSTOMER ALREADY EXISTS!                                                            ");
            System.out.println("                                          A customer with the same name, email, or contact number already exists."+RESET);
            System.out.println();
            System.out.println(GRAY+"┌──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┐");
            System.out.println("│                                                                                                                                                          │");
            System.out.println("└──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┘"+RESET);
            return;
        }

        // If no duplicates, proceed with adding the customer
        customerManager.add();
    }

    /**
     * Check if customer already exists based on name, email, or contact number
     */
    private boolean isCustomerDuplicate(String name, String email, String contactNumber) {
        try (Scanner scanner = new Scanner(new File("Customer.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.trim().isEmpty()) continue;

                String[] details = line.split(",");
                if (details.length >= 13) {
                    String existingName = details[1].trim();
                    String existingEmail = details[11].trim();
                    String existingContact = details[12].trim();

                    // Check for duplicates (case-insensitive for name and email)
                    if (existingName.equalsIgnoreCase(name.trim()) ||
                            existingEmail.equalsIgnoreCase(email.trim()) ||
                            existingContact.equals(contactNumber.trim())) {
                        return true;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            // If file doesn't exist, no duplicates
            return false;
        }
        return false;
    }

    /**
     * Enhanced view method that shows pet status (Available, Currently Requested, etc.)
     */
    public void viewWithStatus() {
        List<String[]> petData = new ArrayList<>();
        List<String> petStatuses = new ArrayList<>();

        // Read all pet data first
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String[] petDetails = scanner.nextLine().split(",");
                if (petDetails.length == 9) {
                    petData.add(petDetails);
                    petStatuses.add("Available");
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Check for requested pets
        try (Scanner scanner = new Scanner(requestedPets)) {
            while (scanner.hasNextLine()) {
                String[] requestedPetDetails = scanner.nextLine().split(",");
                if (requestedPetDetails.length >= 1) {
                    int requestedPetId = Integer.parseInt(requestedPetDetails[0]);

                    // Add requested pets to the list with "Currently Requested" status
                    if (requestedPetDetails.length == 9) {
                        petData.add(requestedPetDetails);
                        petStatuses.add("Currently Requested");
                    }
                }
            }
        } catch (FileNotFoundException e) {
            // RequestedPets.txt doesn't exist, that's okay
        }

        if (petData.isEmpty()) {
            System.out.println("\n                                             No pet records found.");
            return;
        }

        // Define table headers (added Status column)
        String[] headers = {"ID", "Name", "Type", "Breed", "Age", "Birthday", "Gender", "Status"};

        // Calculate column widths
        int[] columnWidths = new int[headers.length];

        // Initialize with header lengths
        for (int i = 0; i < headers.length; i++) {
            columnWidths[i] = headers[i].length();
        }

        // Check data lengths and update column widths
        for (int i = 0; i < petData.size(); i++) {
            String[] pet = petData.get(i);
            String status = petStatuses.get(i);

            columnWidths[0] = Math.max(columnWidths[0], pet[0].length()); // ID
            columnWidths[1] = Math.max(columnWidths[1], pet[1].length()); // Name
            columnWidths[2] = Math.max(columnWidths[2], pet[2].length()); // Type
            columnWidths[3] = Math.max(columnWidths[3], pet[3].length()); // Breed
            columnWidths[4] = Math.max(columnWidths[4], pet[4].length()); // Age

            // Birthday format: MM/DD/YYYY
            String birthday = pet[5] + "/" + pet[6] + "/" + pet[7];
            columnWidths[5] = Math.max(columnWidths[5], birthday.length());

            columnWidths[6] = Math.max(columnWidths[6], pet[8].length()); // Gender
            columnWidths[7] = Math.max(columnWidths[7], status.length()); // Status
        }

        // Add padding to column widths
        for (int i = 0; i < columnWidths.length; i++) {
            columnWidths[i] += 4;
        }

        // Calculate total table width
        int totalWidth = 1;
        for (int i = 0; i < columnWidths.length; i++) {
            totalWidth += columnWidths[i] + 1;
        }

        // Calculate left padding to center the table
        int terminalWidth = 140;
        int leftPadding = Math.max(0, (terminalWidth - totalWidth) / 2) + 8;
        String padding = " ".repeat(leftPadding);

        System.out.println();

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
        for (int i = 0; i < petData.size(); i++) {
            String[] pet = petData.get(i);
            String status = petStatuses.get(i);

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

            // Status with color coding
            final String RED = "\u001B[31m";
            final String GREEN = "\u001B[32m";
            final String RESET = "\u001B[0m";

            if (status.equals("Currently Requested")) {
                System.out.printf(" " + RED + "%-" + (columnWidths[7] - 1) + "s" + RESET + "│", status);
            } else {
                System.out.printf(" " + GREEN + "%-" + (columnWidths[7] - 1) + "s" + RESET + "│", status);
            }

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

        System.out.println();
    }

    public void makeAdoptionRequest() {
        Scanner scanner = new Scanner(System.in);
        final String GRAY = "\u001B[38;2;137;143;156m";
        final String RED = "\u001B[31m";
        final String RESET = "\u001B[0m";

        try {
            viewWithStatus(); // Show pets with their status

            System.out.println();
            System.out.println(GRAY+"                                             ┌──────────────────────────────────────────────────────────────┐");
            System.out.print  ("                                             │ ENTER PET ID TO REQUEST: ");
            int petID = scanner.nextInt();
            System.out.println("                                             └──────────────────────────────────────────────────────────────┘"+RESET);

            // Remove the check that prevents requesting already requested pets
            // The original code had this check:
        /*
        if (isPetCurrentlyRequested(petID)) {
            System.out.println();
            System.out.println(RED+"                                                ❌ PET IS CURRENTLY REQUESTED BY ANOTHER USER                                          ");
            System.out.println("                                              This pet is not available for adoption at the moment."+RESET);
            System.out.println();
            return;
        }
        */

            // Now directly proceed to create the adoption request
            searchPetForAdoption(petID);

        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid input. Please enter a valid pet ID.");
        }
    }

    /**
     * Check if a pet is currently requested
     */
    private boolean isPetCurrentlyRequested(int petID) {
        try (Scanner scanner = new Scanner(requestedPets)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.trim().isEmpty()) continue;

                String[] petDetails = line.split(",");
                if (petDetails.length >= 1) {
                    try {
                        int currentPetId = Integer.parseInt(petDetails[0].trim());
                        if (currentPetId == petID) {
                            return true;
                        }
                    } catch (NumberFormatException e) {
                        // Skip invalid lines
                    }
                }
            }
        } catch (FileNotFoundException e) {
            // RequestedPets.txt doesn't exist, so no pets are currently requested
            return false;
        }
        return false;
    }

    @Override
    public void view() {
        viewWithStatus(); // Use the enhanced view method
    }

    public void approveAdoptionRequest(String requestIdToApprove) {
        // Variables to store the Pet ID of the approved request
        int adoptedPetId = -1;

        // Read all requests from AdoptionRequest.txt
        List<String> requests = new ArrayList<>();
        boolean requestFound = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(adoptionRequest))) {
            String line;
            StringBuilder currentRequest = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("--------------------------------------------------")) {
                    if (!currentRequest.isEmpty()) {
                        // Check if this is the request to approve
                        if (currentRequest.toString().contains("Request ID: " + requestIdToApprove)) {
                            requestFound = true;
                            // Extract Pet ID from the request
                            String[] lines = currentRequest.toString().split("\n");
                            for (String l : lines) {
                                if (l.trim().startsWith("Pet ID:")) {
                                    adoptedPetId = Integer.parseInt(l.trim().split(":")[1].trim());
                                    break;
                                }
                            }
                            // Write to AdoptionReports.txt
                            try (BufferedWriter reportWriter = new BufferedWriter(new FileWriter(adoptionReport, true))) {
                                reportWriter.write(currentRequest.toString());
                                reportWriter.newLine();
                                reportWriter.write("Status: Approved");
                                reportWriter.newLine();
                                reportWriter.write("--------------------------------------------------");
                                reportWriter.newLine();
                            }
                            // Log to AdoptionHistory.txt
                            try (BufferedWriter historyWriter = new BufferedWriter(new FileWriter(adoptionHistory, true))) {
                                historyWriter.write(currentRequest.toString());
                                historyWriter.newLine();
                                historyWriter.write("Status: Approved");
                                historyWriter.newLine();
                                historyWriter.write("--------------------------------------------------");
                                historyWriter.newLine();
                            }
                        } else {
                            // Keep other requests
                            requests.add(currentRequest.toString());
                        }
                    }
                    currentRequest = new StringBuilder();
                } else {
                    currentRequest.append(line).append("\n");
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading AdoptionRequest.txt: " + e.getMessage());
            return;
        }

        if (!requestFound) {
            System.out.println("Request ID " + requestIdToApprove + " not found.");
            return;
        }

        // Remove the adopted pet from RequestedPets.txt (if Pet ID was found)
        if (adoptedPetId != -1) {
            removeRequestedPet(adoptedPetId);
        }

        // Rewrite AdoptionRequest.txt without the approved request
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(adoptionRequest, false))) {
            for (String request : requests) {
                writer.write(request);
                writer.write("--------------------------------------------------");
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error updating AdoptionRequest.txt: " + e.getMessage());
        }

        System.out.println("Request ID " + requestIdToApprove + " approved. Pet removed from records.");
    }

    public void rejectAdoptionRequest(String requestIdToReject) {
        // Variables to store the Pet ID of the rejected request
        int rejectedPetId = -1;

        // Read all requests from AdoptionRequest.txt
        List<String> requests = new ArrayList<>();
        boolean requestFound = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(adoptionRequest))) {
            String line;
            StringBuilder currentRequest = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("--------------------------------------------------")) {
                    if (!currentRequest.isEmpty()) {
                        // Check if this is the request to reject
                        if (currentRequest.toString().contains("Request ID: " + requestIdToReject)) {
                            requestFound = true;
                            // Extract Pet ID from the request
                            String[] lines = currentRequest.toString().split("\n");
                            for (String l : lines) {
                                if (l.trim().startsWith("Pet ID:")) {
                                    rejectedPetId = Integer.parseInt(l.trim().split(":")[1].trim());
                                    break;
                                }
                            }

                            // Log to AdoptionHistory.txt
                            try (BufferedWriter historyWriter = new BufferedWriter(new FileWriter(adoptionHistory, true))) {
                                historyWriter.write(currentRequest.toString());
                                historyWriter.newLine();
                                historyWriter.write("Status: Rejected");
                                historyWriter.newLine();
                                historyWriter.write("--------------------------------------------------");
                                historyWriter.newLine();
                            }
                        } else {
                            // Keep other requests
                            requests.add(currentRequest.toString());
                        }
                    }
                    currentRequest = new StringBuilder();
                } else {
                    currentRequest.append(line).append("\n");
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading AdoptionRequest.txt: " + e.getMessage());
            return;
        }

        if (!requestFound) {
            System.out.println("Request ID " + requestIdToReject + " not found.");
            return;
        }

        // Rewrite AdoptionRequest.txt without the rejected request
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(adoptionRequest, false))) {
            for (String request : requests) {
                writer.write(request);
                writer.write("--------------------------------------------------");
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error updating AdoptionRequest.txt: " + e.getMessage());
        }

        // Return the pet to Pet.txt if we found its ID
        if (rejectedPetId != -1) {
            returnPetToPetTextFile(rejectedPetId);
        }

        System.out.println("Request ID " + requestIdToReject + " rejected.");
    }

    private void removeRequestedPet(int petId) {
        List<String> remainingPets = new ArrayList<>();
        boolean petFound = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(requestedPets))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] petDetails = line.split(",");
                if (petDetails.length >= 1 && Integer.parseInt(petDetails[0]) == petId) {
                    petFound = true; // Skip this pet (it's being adopted)
                } else {
                    remainingPets.add(line); // Keep other pets
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading RequestedPets.txt: " + e.getMessage());
            return;
        }

        if (!petFound) {
            System.err.println("Pet ID " + petId + " not found in RequestedPets.txt.");
            return;
        }

        // Rewrite RequestedPets.txt without the adopted pet
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(requestedPets, false))) {
            for (String pet : remainingPets) {
                writer.write(pet);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error updating RequestedPets.txt: " + e.getMessage());
        }
    }

    private void removePetFromPetTextFile(int petId) {
        List<String> remainingPets = new ArrayList<>();
        boolean petFound = false;

        try (BufferedReader reader = new BufferedReader(new FileReader("Pet.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] petDetails = line.split(",");
                if (petDetails.length >= 1 && Integer.parseInt(petDetails[0]) == petId) {
                    petFound = true; // Skip this pet (it's being adopted)
                } else {
                    remainingPets.add(line); // Keep other pets
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading Pet.txt: " + e.getMessage());
            return;
        }

        if (!petFound) {
            System.err.println("Pet ID " + petId + " not found in Pet.txt.");
            return;
        }

        // Rewrite Pet.txt without the adopted pet
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Pet.txt", false))) {
            for (String pet : remainingPets) {
                writer.write(pet);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error updating Pet.txt: " + e.getMessage());
        }
    }

    private void returnPetToPetTextFile(int petId) {
        // First find the pet in RequestedPets.txt
        String petRecord = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(requestedPets))) {
            String line;
            List<String> remainingRequests = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] petDetails = line.split(",");

                if (petDetails.length >= 1) {
                    try {
                        int currentPetId = Integer.parseInt(petDetails[0].trim());
                        if (currentPetId == petId) {
                            petRecord = line; // Found the pet to return
                            continue; // Skip adding to remainingRequests
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid!");
                    }
                }
                remainingRequests.add(line);
            }

            // Update RequestedPets.txt by removing the returned pet
            if (petRecord != null) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(requestedPets, false))) {
                    for (String request : remainingRequests) {
                        writer.write(request);
                        writer.newLine();
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading RequestedPets.txt: " + e.getMessage());
            return;
        }

        if (petRecord == null) {
            System.err.println("Pet ID " + petId + " not found in RequestedPets.txt.");
            return;
        }

        // Add the pet back to Pet.txt
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Pet.txt", true))) {
            writer.write(petRecord);
            writer.newLine();
            System.out.println("✅ Pet ID " + petId + " has been returned to available pets.");
        } catch (IOException e) {
            System.err.println("Error writing to Pet.txt: " + e.getMessage());
        }
    }

    public void searchPetForAdoption(int petID) {
        // Variables to store pet details
        String petName = null;
        String petType = null;
        String petBreed = null;
        int petAge = 0;
        int petBirthDay = 0;
        int petBirthMonth = 0;
        int petBirthYear = 0;
        char petGender = ' ';

        boolean petExists = false;

        // First check Pet.txt (available pets)
        try (Scanner fileScanner = new Scanner(new File("Pet.txt"))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                if (line.trim().isEmpty()) continue;

                String[] petDetails = line.split(",");
                if (petDetails.length == 9 && Integer.parseInt(petDetails[0]) == petID) {
                    petName = petDetails[1];
                    petType = petDetails[2];
                    petBreed = petDetails[3];
                    petAge = Integer.parseInt(petDetails[4]);
                    petBirthMonth = Integer.parseInt(petDetails[5]);
                    petBirthDay = Integer.parseInt(petDetails[6]);
                    petBirthYear = Integer.parseInt(petDetails[7]);
                    petGender = petDetails[8].charAt(0);
                    petExists = true;
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("❌ Pet file not found: " + e.getMessage());
            return;
        }

        // If not found in Pet.txt, check RequestedPets.txt (currently requested pets)
        if (!petExists) {
            try (Scanner fileScanner = new Scanner(new File("RequestedPets.txt"))) {
                while (fileScanner.hasNextLine()) {
                    String line = fileScanner.nextLine();
                    if (line.trim().isEmpty()) continue;

                    String[] petDetails = line.split(",");
                    if (petDetails.length == 9 && Integer.parseInt(petDetails[0]) == petID) {
                        petName = petDetails[1];
                        petType = petDetails[2];
                        petBreed = petDetails[3];
                        petAge = Integer.parseInt(petDetails[4]);
                        petBirthDay = Integer.parseInt(petDetails[5]);
                        petBirthMonth = Integer.parseInt(petDetails[6]);
                        petBirthYear = Integer.parseInt(petDetails[7]);
                        petGender = petDetails[8].charAt(0);
                        petExists = true;
                        break;
                    }
                }
            } catch (FileNotFoundException e) {
                // RequestedPets.txt doesn't exist, that's okay
            }
        }

        if (!petExists) {
            System.out.println("Pet with ID " + petID + " not found.");
            return;
        }

        // Get the logged-in username
        String currentUsername = loginPage.getCurrentUsername();
        if (currentUsername == null || currentUsername.isEmpty()) {
            System.out.println("❌ Error: You need to be logged in.");
            return;
        }

        // Load customer data with improved name retrieval
        Customer customer = loadCustomerByUsername(currentUsername);
        if (customer == null) {
            System.out.println("❌ Error: Customer not found.");
            return;
        }

        // Generate a random request ID
        Random random = new Random();
        int requestId = random.nextInt(1_000_000);
        setRequestID(requestId);

        // Only add to RequestedPets.txt if the pet is currently in Pet.txt (available)
        // If it's already in RequestedPets.txt, don't duplicate it
        boolean isCurrentlyAvailable = false;
        try (Scanner fileScanner = new Scanner(new File("Pet.txt"))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                if (line.trim().isEmpty()) continue;

                String[] petDetails = line.split(",");
                if (petDetails.length == 9 && Integer.parseInt(petDetails[0]) == petID) {
                    isCurrentlyAvailable = true;
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            // Pet.txt doesn't exist
        }

        // Only move pet to RequestedPets.txt if it's currently available
        if (isCurrentlyAvailable) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(requestedPets, true))) {
                writer.write(petID + "," + petName + "," + petType + "," + petBreed + "," + petAge + "," + petBirthDay + "," + petBirthMonth + "," + petBirthYear + "," + petGender);
                writer.newLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            // Remove from Pet.txt only if it was available
            removePetFromPetTextFile(petID);
        }

        // Write adoption request with proper customer name
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(adoptionRequest, true))) {
            writer.write("--------------------------------------------------");
            writer.newLine();
            writer.write("Request ID: " + requestId);
            writer.newLine();
            writer.write("Customer Information:");
            writer.newLine();
            writer.write("   Name: " + customer.getName()); // This will now show the correct customer name
            writer.newLine();
            writer.write("   Age: " + customer.getAge());
            writer.newLine();
            writer.write("   Contact Number: " + customer.getContactNumber());
            writer.newLine();
            writer.write("   Occupation: " + customer.getOccupation());
            writer.newLine();
            writer.write("   Home Type: " + customer.getHomeType());
            writer.newLine();
            // Convert boolean to Yes/No format
            writer.write("   Has Other Pets: " + (customer.getHasOtherPets() ? "Yes" : "No"));
            writer.newLine();
            writer.write("Pet Information:");
            writer.newLine();
            writer.write("   Pet ID: " + petID);
            writer.newLine();
            writer.write("   Pet Name: " + petName);
            writer.newLine();
            writer.write("   Type: " + petType);
            writer.newLine();
            writer.write("   Breed: " + petBreed);
            writer.newLine();
            writer.write("   Pet Age: " + petAge);
            writer.newLine();
            writer.write("   Birthday: " + petBirthDay + "/" + petBirthMonth + "/" + petBirthYear);
            writer.newLine();
            writer.write("   Gender: " + petGender);
            writer.newLine();
            writer.write("--------------------------------------------------");
            writer.newLine();
            System.out.println("✅ Adoption request submitted successfully.");
        } catch (IOException e) {
            System.out.println("❌ Error writing to file: " + e.getMessage());
        }
    }

    // Enhanced helper method to load customer data by username
    private Customer loadCustomerByUsername(String username) {
        try (Scanner scanner = new Scanner(new File("Customer.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.trim().isEmpty()) continue;

                String[] details = line.split(",");
                if (details.length >= 13) {
                    Customer customer = new Customer();
                    customer.setCustomerID(Integer.parseInt(details[0]));
                    customer.setName(details[1]);
                    customer.setAge(Integer.parseInt(details[2]));
                    customer.setGender(details[3].charAt(0));
                    customer.setAddress(details[4]);
                    customer.setBirthMonth(Integer.parseInt(details[5]));
                    customer.setBirthDay(Integer.parseInt(details[6]));
                    customer.setBirthYear(Integer.parseInt(details[7]));
                    customer.setOccupation(details[8]);
                    customer.setHomeType(details[9]);
                    customer.setHasOtherPets(Boolean.parseBoolean(details[10]));
                    customer.setEmail(details[11]);
                    customer.setContactNumber(details[12]);

                    // Check if this customer matches the username by checking the account file
                    String customerNameFromAccount = customerManager.getCustomerNameByUsername(username);
                    if (customerNameFromAccount != null && customerNameFromAccount.equals(customer.getName())) {
                        return customer;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error loading customer data: " + e.getMessage());
        }
        return null;
    }

    private void handleAdoptionReport(PDFGenerator pdfGenerator) {
        try {
            // 1. First try a default file
            String defaultFile = "AdoptionReports.txt";
            File file = new File(defaultFile);

            // 2. If default doesn't exist, try finding newest .txt
            if (!file.exists()) {
                System.out.println("⚠️ Default file not found. Searching for latest .txt...");
                defaultFile = pdfGenerator.getMostRecentTextFile(".");
            }

            // 3. Final check before generation
            if (defaultFile != null) {
                pdfGenerator.generateAdoptionReport(defaultFile);
            }
            else {
                System.err.println("❌ No adoption data files found. Expected:");
                System.err.println("- " + new File("AdoptionReports.txt").getAbsolutePath());
                System.err.println("- Or any .txt file in project root");
            }
        }
        catch (Exception e) {
            System.err.println("❌ Failed to generate report: " + e.getMessage());
        }
    }

    private void handleRemainingPetsReport(PDFGenerator pdfGenerator) {
        try (BufferedReader reader = new BufferedReader(new FileReader("Pet.txt"));
             BufferedWriter writer = new BufferedWriter(new FileWriter(remainingPetsReport))){

            String line;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] petDetails = line.split(",");

                if (petDetails.length < 9) {
                    System.err.println("Invalid data format: " + line);
                }

                String formatted = formatPetDate(petDetails);

                writer.write(formatted);
                writer.newLine();
                writer.newLine();
            }

            writer.flush();

            if (new File(String.valueOf(remainingPetsReport)).exists() &&
                    new File(String.valueOf(remainingPetsReport)).length() > 0) {
                pdfGenerator.generateRemainingPetsReport(String.valueOf(remainingPetsReport));
            } else {
                System.err.println("❌ Failed to create report file: " +
                        new File(String.valueOf(remainingPetsReport)).getAbsolutePath());
            }
        } catch (Exception e) {
            System.err.println("❌ Failed to generate report: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static String formatPetDate(String[] petData) {
        return "--------------------------------------------------\n" +
                "Pet Information:\n" +
                "   Pet ID: " + petData[0] + "\n" +
                "   Name: " + petData[1] + "\n" +
                "   Species: " + petData[2] + "\n" +
                "   Breed: " + petData[3] + "\n" +
                "   Age: " + petData[4] + "\n" +
                "   Birthday: " + petData[5] + "/" + petData[6] + "/" + petData[7] + "\n" +
                "   Gender: " + petData[8] + "\n" +
                "--------------------------------------------------";
    }

    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }

    public void viewAdoptionRequestSummaries() {
        final String BLUE = "\u001B[38;2;66;103;178m";
        final String RESET = "\u001B[0m";
        final String GRAY = "\u001B[38;2;137;143;156m";
        final String RED = "\u001B[31m";
        final String GREEN = "\u001B[32m";
        final String AQUA_LIGHT = "\u001B[38;2;161;227;239m";

        try (BufferedReader reader = new BufferedReader(new FileReader(adoptionRequest))) {
            String line;
            StringBuilder currentRequest = new StringBuilder();
            boolean hasRequest = false;
            int requestCount = 0;

            System.out.println();
            System.out.println(GRAY+"┌──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┐");
            System.out.println("│                                                                                                                                                          │");
            System.out.println("└──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┘");
            System.out.println(BLUE+"                                                             ┌─────────────────────────────┐                                                               ");
            System.out.println("                                                             │ PENDING ADOPTION REQUESTS   │                                                               ");
            System.out.println("                                                             └─────────────────────────────┘                                                               "+RESET);
            System.out.println();

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("--------------------------------------------------")) {
                    if (hasRequest && !currentRequest.toString().trim().isEmpty()) {
                        // Extract and display summary info
                        String[] lines = currentRequest.toString().split("\n");
                        String requestId = "";
                        String customerName = "";
                        String petName = "";

                        for (String l : lines) {
                            String trimmed = l.trim();
                            if (trimmed.startsWith("Request ID:")) {
                                requestId = trimmed.substring("Request ID:".length()).trim();
                            } else if (trimmed.startsWith("Name:")) {
                                // Make sure we're getting the customer name, not pet name
                                if (customerName.isEmpty()) {
                                    customerName = trimmed.substring("Name:".length()).trim();
                                }
                            } else if (trimmed.startsWith("Pet Name:")) {
                                petName = trimmed.substring("Pet Name:".length()).trim();
                            }
                        }

                        // Only display if we have all required information
                        if (!requestId.isEmpty() && !customerName.isEmpty() && !petName.isEmpty()) {
                            requestCount++;
                            System.out.println(GRAY+"                                             ┌──────────────────────────────────────────────────────────────┐");
                            System.out.printf ("                                             │ " + AQUA_LIGHT + "[%s]" + RESET + GRAY + " %s wants to adopt %s",
                                    requestId, customerName, petName);

                            // Calculate padding to align the closing border
                            int contentLength = String.format("[%s] %s wants to adopt %s", requestId, customerName, petName).length();
                            int padding = 58 - contentLength; // 58 is the inner width of the box
                            for (int i = 0; i < Math.max(0, padding); i++) {
                                System.out.print(" ");
                            }
                            System.out.println("   │");
                            System.out.println("                                             └──────────────────────────────────────────────────────────────┘"+RESET);
                        }
                    }
                    hasRequest = false;
                    currentRequest = new StringBuilder();
                } else {
                    currentRequest.append(line).append("\n");
                    hasRequest = true;
                }
            }

            // Handle the last request if the file doesn't end with separator
            if (hasRequest && !currentRequest.toString().trim().isEmpty()) {
                String[] lines = currentRequest.toString().split("\n");
                String requestId = "";
                String customerName = "";
                String petName = "";

                for (String l : lines) {
                    String trimmed = l.trim();
                    if (trimmed.startsWith("Request ID:")) {
                        requestId = trimmed.substring("Request ID:".length()).trim();
                    } else if (trimmed.startsWith("Name:")) {
                        if (customerName.isEmpty()) {
                            customerName = trimmed.substring("Name:".length()).trim();
                        }
                    } else if (trimmed.startsWith("Pet Name:")) {
                        petName = trimmed.substring("Pet Name:".length()).trim();
                    }
                }

                if (!requestId.isEmpty() && !customerName.isEmpty() && !petName.isEmpty()) {
                    requestCount++;
                    System.out.println(GRAY+"                                             ┌──────────────────────────────────────────────────────────────┐");
                    System.out.printf ("                                             │ " + AQUA_LIGHT + "[%s]" + RESET + GRAY + " %s wants to adopt %s",
                            requestId, customerName, petName);

                    int contentLength = String.format("[%s] %s wants to adopt %s", requestId, customerName, petName).length();
                    int padding = 58 - contentLength;
                    for (int i = 0; i < Math.max(0, padding); i++) {
                        System.out.print(" ");
                    }
                    System.out.println("   │");
                    System.out.println("                                             └──────────────────────────────────────────────────────────────┘"+RESET);
                }
            }

            System.out.println();
            if (requestCount == 0) {
                System.out.println(GRAY+"                                             ┌──────────────────────────────────────────────────────────────┐");
                System.out.println("                                             │                  NO PENDING REQUESTS FOUND                   │");
                System.out.println("                                             └──────────────────────────────────────────────────────────────┘"+RESET);
            } else {
                System.out.println(GREEN+"                                             ┌──────────────────────────────────────────────────────────────┐");
                System.out.printf ("                                             │                  TOTAL PENDING REQUESTS: %-2d                  │\n", requestCount);
                System.out.println("                                             └──────────────────────────────────────────────────────────────┘"+RESET);
            }

            System.out.println();
            System.out.println(GRAY+"┌──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┐");
            System.out.println("│                                                                                                                                                          │");
            System.out.println("└──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┘"+RESET);

        } catch (IOException e) {
            System.out.println();
            System.out.println(RED+"                                             ┌──────────────────────────────────────────────────────────────┐");
            System.out.println("                                             │           ERROR READING ADOPTION REQUESTS FILE               │");
            System.out.println("                                             └──────────────────────────────────────────────────────────────┘"+RESET);
            System.err.println("Error details: " + e.getMessage());
        }
    }

    public void reviewAdoptionRequest() {
        Scanner scanner = new Scanner(System.in);

        // First show summaries
        viewAdoptionRequestSummaries();

        System.out.println("\n                                             ┌──────────────────────────────────────────────────────────────┐");
        System.out.print  ("                                             │ ENTER REQUEST ID: ");
        String requestId = scanner.next();
        System.out.println("                                             └──────────────────────────────────────────────────────────────┘");
        scanner.nextLine();

        if (requestId.equals("0")) {
            return;
        }

        // Now show full details
        boolean found = false;
        String customerName = "", customerAge = "", customerContact = "", customerOccupation = "", customerHomeType = "", customerHasOtherPets = "";
        String petId = "", petName = "", petType = "", petBreed = "", petAge = "", petBirthday = "", petGender = "";

        try (BufferedReader reader = new BufferedReader(new FileReader(adoptionRequest))) {
            String line;
            StringBuilder currentRequest = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("--------------------------------------------------")) {
                    if (currentRequest.toString().contains("Request ID: " + requestId)) {
                        found = true;

                        // Parse the request details
                        String[] lines = currentRequest.toString().split("\n");
                        for (String l : lines) {
                            String trimmed = l.trim();
                            if (trimmed.startsWith("Name:")) {
                                customerName = trimmed.substring("Name:".length()).trim();
                            } else if (trimmed.startsWith("Age:")) {
                                customerAge = trimmed.substring("Age:".length()).trim();
                            } else if (trimmed.startsWith("Contact Number:")) {
                                customerContact = trimmed.substring("Contact Number:".length()).trim();
                            } else if (trimmed.startsWith("Occupation:")) {
                                customerOccupation = trimmed.substring("Occupation:".length()).trim();
                            } else if (trimmed.startsWith("Home Type:")) {
                                customerHomeType = trimmed.substring("Home Type:".length()).trim();
                            } else if (trimmed.startsWith("Has Other Pets:")) {
                                customerHasOtherPets = trimmed.substring("Has Other Pets:".length()).trim();
                            } else if (trimmed.startsWith("Pet ID:")) {
                                petId = trimmed.substring("Pet ID:".length()).trim();
                            } else if (trimmed.startsWith("Pet Name:") && petName.isEmpty()) {
                                petName = trimmed.substring("Pet Name:".length()).trim();
                            } else if (trimmed.startsWith("Type:")) {
                                petType = trimmed.substring("Type:".length()).trim();
                            } else if (trimmed.startsWith("Breed:")) {
                                petBreed = trimmed.substring("Breed:".length()).trim();
                            } else if (trimmed.startsWith("Pet Age:") && petAge.isEmpty()) {
                                petAge = trimmed.substring("Pet Age:".length()).trim();
                            } else if (trimmed.startsWith("Birthday:")) {
                                petBirthday = trimmed.substring("Birthday:".length()).trim();
                            } else if (trimmed.startsWith("Gender:")) {
                                petGender = trimmed.substring("Gender:".length()).trim();
                            }
                        }
                        break;
                    }
                    currentRequest = new StringBuilder();
                } else {
                    currentRequest.append(line).append("\n");
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading adoption requests: " + e.getMessage());
        }

        if (!found) {
            System.out.println("Request ID " + requestId + " not found.");
            return;
        }

        // Color definitions
        final String BLUE = "\u001B[38;2;66;103;178m";
        final String RESET = "\u001B[0m";
        final String GRAY = "\u001B[38;2;137;143;156m";
        final String RED = "\u001B[31m";
        final String GREEN = "\u001B[32m";

        // Display customer details in formatted style
        System.out.println();
        System.out.println("┌──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                                                                  CUSTOMER DETAILS                                                                        │");
        System.out.println("├──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┤");
        System.out.printf("│                               ┌──────────────────────────────────────────────────────────────────────────────────────────┐                               │%n");
        System.out.printf("│                               │ NAME                                │  %-47s   │                               │%n", customerName);
        System.out.printf("│                               └──────────────────────────────────────────────────────────────────────────────────────────┘                               │%n");
        System.out.printf("│                               ┌──────────────────────────────────────────────────────────────────────────────────────────┐                               │%n");
        System.out.printf("│                               │ AGE                                 │  %-47s   │                               │%n", customerAge);
        System.out.printf("│                               └──────────────────────────────────────────────────────────────────────────────────────────┘                               │%n");
        System.out.printf("│                               ┌──────────────────────────────────────────────────────────────────────────────────────────┐                               │%n");
        System.out.printf("│                               │ CONTACT NUMBER                      │  %-47s   │                               │%n", customerContact);
        System.out.printf("│                               └──────────────────────────────────────────────────────────────────────────────────────────┘                               │%n");
        System.out.printf("│                               ┌──────────────────────────────────────────────────────────────────────────────────────────┐                               │%n");
        System.out.printf("│                               │ OCCUPATION                          │  %-47s   │                               │%n", customerOccupation);
        System.out.printf("│                               └──────────────────────────────────────────────────────────────────────────────────────────┘                               │%n");
        System.out.printf("│                               ┌──────────────────────────────────────────────────────────────────────────────────────────┐                               │%n");
        System.out.printf("│                               │ HOME TYPE                           │  %-47s   │                               │%n", customerHomeType);
        System.out.printf("│                               └──────────────────────────────────────────────────────────────────────────────────────────┘                               │%n");
        System.out.printf("│                               ┌──────────────────────────────────────────────────────────────────────────────────────────┐                               │%n");
        System.out.printf("│                               │ HAS OTHER PETS                      │  %-47s   │                               │%n", customerHasOtherPets);
        System.out.printf("│                               └──────────────────────────────────────────────────────────────────────────────────────────┘                               │%n");
        System.out.println("└──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┘");

        System.out.println();

        // Display pet details in formatted style
        System.out.println("┌──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┐");
        System.out.println("│                                                                      PET DETAILS                                                                         │");
        System.out.println("├──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┤");
        System.out.printf("│                               ┌──────────────────────────────────────────────────────────────────────────────────────────┐                               │%n");
        System.out.printf("│                               │ ID                                  │  %-47s   │                               │%n", petId);
        System.out.printf("│                               └──────────────────────────────────────────────────────────────────────────────────────────┘                               │%n");
        System.out.printf("│                               ┌──────────────────────────────────────────────────────────────────────────────────────────┐                               │%n");
        System.out.printf("│                               │ NAME                                │  %-47s   │                               │%n", petName);
        System.out.printf("│                               └──────────────────────────────────────────────────────────────────────────────────────────┘                               │%n");
        System.out.printf("│                               ┌──────────────────────────────────────────────────────────────────────────────────────────┐                               │%n");
        System.out.printf("│                               │ TYPE                                │  %-47s   │                               │%n", petType);
        System.out.printf("│                               └──────────────────────────────────────────────────────────────────────────────────────────┘                               │%n");
        System.out.printf("│                               ┌──────────────────────────────────────────────────────────────────────────────────────────┐                               │%n");
        System.out.printf("│                               │ BREED                               │  %-47s   │                               │%n", petBreed);
        System.out.printf("│                               └──────────────────────────────────────────────────────────────────────────────────────────┘                               │%n");
        System.out.printf("│                               ┌──────────────────────────────────────────────────────────────────────────────────────────┐                               │%n");
        System.out.printf("│                               │ AGE                                 │  %-47s   │                               │%n", petAge);
        System.out.printf("│                               └──────────────────────────────────────────────────────────────────────────────────────────┘                               │%n");
        System.out.printf("│                               ┌──────────────────────────────────────────────────────────────────────────────────────────┐                               │%n");
        System.out.printf("│                               │ BIRTHDAY                            │  %-47s   │                               │%n", petBirthday);
        System.out.printf("│                               └──────────────────────────────────────────────────────────────────────────────────────────┘                               │%n");
        System.out.printf("│                               ┌──────────────────────────────────────────────────────────────────────────────────────────┐                               │%n");
        System.out.printf("│                               │ GENDER                              │  %-47s   │                               │%n", petGender);
        System.out.printf("│                               └──────────────────────────────────────────────────────────────────────────────────────────┘                               │%n");
        System.out.println("└──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┘");

        // Action menu with improved formatting
        Scanner scanner2 = new Scanner(System.in);
        boolean continueRunning = true;

        while (continueRunning) {
            System.out.println();
            System.out.println(GRAY+"┌──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┐");
            System.out.println("│                                                                                                                                                          │");
            System.out.println("└──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────┘");
            System.out.println(BLUE+"                                                                      ┌─────────────┐                                                                           ");
            System.out.println("                                                                      │   ACTIONS   │                                                                           ");
            System.out.println("                                                                      └─────────────┘                                                                           "+RESET);
            System.out.println();
            System.out.println(GRAY+"                                                            1. Approve Request");
            System.out.println("                                                            2. Reject Request");
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
                case 1 -> {
                    approveAdoptionRequest(requestId);
                    continueRunning = false;
                }
                case 2 -> {
                    rejectAdoptionRequest(requestId);
                    continueRunning = false;
                }
                case 3 -> {
                    System.out.println(GRAY + "                                                                   EXITING..." + RESET);
                    continueRunning = false;
                }
                default -> System.out.println(RED + "                                                          INVALID CHOICE! PLEASE TRY AGAIN." + RESET);
            }
        }
    }
}