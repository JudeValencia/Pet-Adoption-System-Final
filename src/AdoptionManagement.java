import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class AdoptionManagement extends UserInterface {
    // TODO: Update pets.txt file whenever an adoption is approved (Done)
    // TODO: Make a reject feature which also logs into the adoptionhistory and marks it as -rejected-


    private final LoginPage loginPage;
    CustomerManagement customerManager = new CustomerManagement();
    private final File adoptionReport = new File("AdoptionReports.txt");
    private final File adoptionRequest = new File("AdoptionRequest.txt");
    private final File adoptionHistory = new File("AdoptionHistory.txt");
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
                case 3 -> approveAdoptionRequest();
                case 4 -> rejectAdoptionRequest();
                case 5 -> {
                    System.out.println("Exiting customer management menu...");
                    isRunning = false;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }

        }
    }

    public void petManagement() {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        int choice;

        while (isRunning) {
            petManagementMenu();
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> add();
                case 2 -> remove();
                case 3 -> update();
                case 4 -> view();
                case 5 -> {
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

    public void approveAdoptionRequest() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the Request ID to approve: ");
        String requestIdToApprove = scanner.nextLine().trim();

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

        // Remove the adopted pet from Pet.txt (if Pet ID was found)
        if (adoptedPetId != -1) {
            removePetFromFile(adoptedPetId);
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

    public void rejectAdoptionRequest() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the Request ID to reject: ");
        String requestIdToReject = scanner.nextLine().trim();

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
                        if (currentRequest.toString().contains("Request ID: " + requestIdToReject)) {
                            requestFound = true;

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

        System.out.println("Request ID " + requestIdToReject + " rejected.");
    }

    private void removePetFromFile(int petId) {
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


    public void searchPetForAdoption(int petID) {
        // Variables to store pet details
        String petName = null;
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

    // --- NEW: Helper method to load customer data by username ---
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
            // 1. First try default file
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
        try {
            // 1. Try default file first
            String defaultFile = "Pet.txt";

            // 2. Fallback to manual input ONLY if debugging
            if (!new File(defaultFile).exists() && DEBUG_MODE) {
                System.out.print("[DEBUG] Enter Pet.txt path (or leave blank to cancel): ");
                String customPath = new Scanner(System.in).nextLine().trim();
                if (!customPath.isEmpty()) defaultFile = customPath;
            }

            if (new File(defaultFile).exists()) {
                pdfGenerator.generateRemainingPetsReport(defaultFile);
            } else {
                System.err.println("❌ Pet data file missing: " +
                        new File(defaultFile).getAbsolutePath());
            }
        } catch (Exception e) {
            System.err.println("❌ Failed to generate report: " + e.getMessage());
        }
    }

    @Override
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

    private static final boolean DEBUG_MODE = false; // Set to true only when debugging

}