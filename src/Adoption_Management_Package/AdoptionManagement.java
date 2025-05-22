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
     * Fields:
     * - loginPage: Handles user login and authentication.
     * - customerManager: Manages customer-related data and operations.
     * - adoptionReport: File storing adoption reports data.
     * - adoptionRequest: File storing pending adoption requests.
     * - adoptionHistory: File tracking historical data of past adoptions.
     * - requestID: Stores the identification number of the current adoption request.
     * - DEBUG_MODE: A flag to indicate system debugging mode.
     *
     * Methods:
     * - Constructor AdoptionManagement(LoginPage loginPage): Initializes the AdoptionManagement class by injecting a LoginPage instance.
     * - nonAdminUserInterface(): Displays the user interface for non-admin users and allows interaction.
     * - adminUserInterface(): Displays the admin interface and provides access to admin-specific functionalities.
     * - customerManagement(): Handles all operations related to customer management.
     * - petManagement(): Manages all operations related to adding, updating, and removing pet records.
     * - reportsManagement(): Handles generation of adoption and pet-related reports.
     * - makeAdoptionRequest(): Facilitates the process for users to make an adoption request.
     * - reviewAdoptionRequest(): Provides a two-step process for reviewing adoption requests (view summaries then details).
     * - viewAdoptionRequestSummaries(): Displays brief summaries of all pending adoption requests.
     * - approveAdoptionRequest(String requestId): Approves a specific adoption request.
     * - rejectAdoptionRequest(String requestId): Rejects a specific adoption request.
     * - removePetFromPetTextFile(int petId): Removes a pet record from the system by pet ID (private method).
     * - searchPetForAdoption(int petID): Searches for a pet available for adoption using the pet's unique ID.
     * - loadCustomerByUsername(String username): Helper method to load customer information by username (private method).
     * - handleAdoptionReport(Report_Management_Package.PDFGenerator pdfGenerator): Handles the generation of an adoption report (private method).
     * - handleRemainingPetsReport(Report_Management_Package.PDFGenerator pdfGenerator): Handles the generation of a report listing remaining pets in the system (private method).
     * - view(): Overrides the view method of the parent UserInterface class to define specific behavior of this class.
     * - setRequestID(int requestID): Sets the request ID for the current adoption request.
     * - getRequestID(): Gets the current request ID.
     * - searchAdoptionRequest(): Searches for a specific adoption request by ID.
     */

    public class AdoptionManagement extends UserInterface {

        private final LoginPage loginPage;
        CustomerManagement customerManager = new CustomerManagement();
        private final File adoptionReport = new File("AdoptionReports.txt");
        private final File adoptionRequest = new File("AdoptionRequest.txt");
        private final File adoptionHistory = new File("AdoptionHistory.txt");
        private final File remainingPetsReport = new File("RemainingPetsReport.txt");
        private final File  requestedPets = new File("RequestedPets.txt");
        int requestID;

        // Constructor to inject LoginPage
        public AdoptionManagement(LoginPage loginPage) {
            this.loginPage = loginPage;
        }

        public void nonAdminUserInterface() {

            // Use the injected loginPage instead of creating a new one
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
                    case 1 -> view(); // View pets
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
                    case 1 -> customerManager.add();
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
                }
                catch (Exception e) {
                    System.err.println("Error: " + e.getMessage());
                    scanner.nextLine(); // Clear invalid input
                }
            }
        }

        public void makeAdoptionRequest() {
            Scanner scanner = new Scanner(System.in);

            try {
                System.out.print("Enter Pet ID to Adopt: ");
                int petID = scanner.nextInt();
                scanner.nextLine();

                searchPetForAdoption(petID);

            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input. Please enter a valid pet ID.");
            }

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

            // Remove the adopted pet from Pet.txt (if .Pet ID was found)
            if (adoptedPetId != -1) {
                removePetFromPetTextFile(adoptedPetId);
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
                    if (petDetails.length >= 1 && Integer.parseInt(petDetails[0]) == petId) {
                        petRecord = line; // Found the pet to return
                    } else {
                        remainingRequests.add(line); // Keep other requests
                    }
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

            // Read pet details from Pet.txt
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

            // Load customer data
            Customer customer = loadCustomerByUsername(currentUsername);
            if (customer == null) {
                System.out.println("❌ Error: Customer not found.");
                return;
            }

            // Generate a random request ID
            Random random = new Random();
            int requestId = random.nextInt(1_000_000);
            setRequestID(requestId);

            // Temporarily stores pet data to RequestedData.txt
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(requestedPets, true))) {
                writer.write(petID + "," + petName + "," + petBreed + "," + petAge + "," + petBirthDay + "," + petBirthMonth + "," + petBirthYear + "," + petGender);
                writer.newLine();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            // Temporarily removes pets whenever it is requested to be adopted
            removePetFromPetTextFile(petID);

            // Write adoption request
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(adoptionRequest, true))) {
                writer.write("--------------------------------------------------");
                writer.newLine();
                writer.write("Request ID: " + requestId);
                writer.newLine();
                writer.write("Customer Information:");
                writer.newLine();
                writer.write("   Name: " + customer.getName());
                writer.newLine();
                writer.write("   Age: " + customer.getAge());
                writer.newLine();
                writer.write("   Contact Number: " + customer.getContactNumber());
                writer.newLine();
                writer.write("   Occupation: " + customer.getOccupation());
                writer.newLine();
                writer.write("   Home Type: " + customer.getHomeType());
                writer.newLine();
                writer.write("   Has Other Pets: " + customer.getHasOtherPets());
                writer.newLine();
                writer.write("Pet Information:");
                writer.newLine();
                writer.write("   Pet ID: " + petID);
                writer.newLine();
                writer.write("   Name: " + petName);
                writer.newLine();
                writer.write("   Type: " + petType);
                writer.newLine();
                writer.write("   Breed: " + petBreed);  // Use loaded pet data
                writer.newLine();
                writer.write("   Age: " + petAge);      // Use loaded pet data
                writer.newLine();
                writer.write("   Birthday: " + petBirthDay + "/" + petBirthMonth + "/" + petBirthYear); // Use loaded pet data
                writer.newLine();
                writer.write("   Gender: " + petGender); // Use loaded pet data
                writer.newLine();
                writer.write("--------------------------------------------------");
                writer.newLine();
                System.out.println("✅ Adoption request submitted successfully.");
            } catch (IOException e) {
                System.out.println("❌ Error writing to file: " + e.getMessage());
            }
        }

        // Helper method to load customer data by username ---
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

                        // Check if this customer matches the username
                        if (customerManager.getCustomerNameByUsername(username).equals(customer.getName())) {
                            return customer; // Return the fully loaded customer
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

        public void view() {
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
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        public void setRequestID(int requestID) {
            this.requestID = requestID;
        }

        public void viewAdoptionRequestSummaries() {
            try (BufferedReader reader = new BufferedReader(new FileReader(adoptionRequest))) {
                String line;
                StringBuilder currentRequest = new StringBuilder();
                boolean hasRequest = false;

                System.out.println("\n=== Pending Adoption Requests ===");

                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("--------------------------------------------------")) {
                        if (hasRequest) {
                            // Extract and display summary info
                            String[] lines = currentRequest.toString().split("\n");
                            String requestId = "";
                            String customerName = "";
                            String petName = "";

                            for (String l : lines) {
                                if (l.trim().startsWith("Request ID:")) {
                                    requestId = l.trim().substring("Request ID:".length()).trim();
                                } else if (l.trim().startsWith("   Name:")) {
                                    if (l.contains("Customer Information:")) {
                                        // This is customer name
                                        customerName = l.trim().substring("Name:".length()).trim();
                                    } else if (l.contains("Pet Information:")) {
                                        // This is pet name
                                        petName = l.trim().substring("Name:".length()).trim();
                                    }
                                }
                            }

                            System.out.printf("[%s] %s wants to adopt %s\n",
                                    requestId, customerName, petName);
                            hasRequest = false;
                            currentRequest = new StringBuilder();
                        }
                    } else {
                        currentRequest.append(line).append("\n");
                        hasRequest = true;
                    }
                }
            } catch (IOException e) {
                System.err.println("Error reading adoption requests: " + e.getMessage());
            }
        }

        public void reviewAdoptionRequest() {
            Scanner scanner = new Scanner(System.in);

            // First show summaries
            viewAdoptionRequestSummaries();

            System.out.print("\nEnter Request ID to review (or '0' to cancel): ");
            String requestId = scanner.nextLine().trim();

            if (requestId.equals("0")) {
                return;
            }

            // Now show full details
            boolean found = false;
            try (BufferedReader reader = new BufferedReader(new FileReader(adoptionRequest))) {
                String line;
                StringBuilder currentRequest = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("--------------------------------------------------")) {
                        if (currentRequest.toString().contains("Request ID: " + requestId)) {
                            found = true;
                            System.out.println("\n=== Adoption Request Details ===");
                            System.out.println(currentRequest);
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

            // Prompt for action
            System.out.println("\n1. Approve request");
            System.out.println("2. Reject request");
            System.out.println("3. Cancel");
            System.out.print("Select action: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> approveAdoptionRequest(requestId); // Modified to take requestId
                case 2 -> rejectAdoptionRequest(requestId);  // Modified to take requestId
                case 3 -> System.out.println("Action cancelled.");
                default -> System.out.println("Invalid option.");
            }
        }
    }