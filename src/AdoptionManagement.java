import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class AdoptionManagement extends UserInterface {
    // TODO: Update pets.txt file whenever an adoption is approved

    LoginPage loginPage = new LoginPage();
    CustomerManagement customerManager = new CustomerManagement();
    private final File adoptionReport = new File("AdoptionReports.txt");
    private final File adoptionRequest = new File("AdoptionRequest.txt");
    private final File adoptionHistory = new File("AdoptionHistory.txt");
    int requestID;

    public void nonAdminUserInterface() {

        loginPage.logIn(); // TODO: Remove after testing (temporary)
        // TODO: Place the logic of redirecting to the admin or client side in the main method
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            userInterface();

            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> view();
                case 2 -> makeAdoptionRequest();
                case 3 -> reportsManagement();
                case 4 -> System.out.println("Logging out...");
                default -> System.out.println("Invalid option. Please try again.");
            }

        } while (choice != 4);
    }

    public void adminUserInterface() {
        loginPage.logIn(); // TODO: Remove after testing (temporary)
        // TODO: Place the logic of redirecting to the admin or client side in the main method

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

        // Ask for the request ID to approve
        System.out.print("Enter the request ID to approve: ");
        Scanner scanner = new Scanner(System.in); // Input for the request ID
        String requestIdToApprove = scanner.nextLine().trim();

        adoptionReport(requestIdToApprove);

    }

    public void logAdoptionHistory(String approvedRequest) {

        // Write the approved request to the adoption history file
        try (BufferedWriter buffer = new BufferedWriter(new FileWriter(adoptionHistory, true))) {
            buffer.write(approvedRequest + " -Approved-"); // Write the approved request (text format)
            buffer.newLine(); // Separate entries with a new line
            System.out.println("✅ Adoption logged in the history file: " + approvedRequest);
        } catch (IOException e) {
            System.out.println("An error occurred while logging the adoption history.");
            e.printStackTrace();
        }
    }

    public void adoptionReport(String requestIdToApprove) {
        // Ensure the adoption request file exists
        if (!adoptionRequest.exists()) {
            System.out.println("Adoption request file not found.");
            return;
        }

        try (Scanner fileScanner = new Scanner(adoptionRequest)) {

            // Temporary list to store all requests
            List<String> allRequests = new ArrayList<>();
            boolean isApproved = false;

            // Read all requests from the file
            while (fileScanner.hasNextLine()) {
                allRequests.add(fileScanner.nextLine()); // Add each request line by line
            }
            // List to hold remaining requests
            List<String> remainingRequests = new ArrayList<>();

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(adoptionReport, true))) {
                // Iterate through all requests
                for (String request : allRequests) {
                    // Check if the request contains the ID to approve
                    if (request.contains(requestIdToApprove)) {
                        writer.write(requestIdToApprove + " request ID is approved"); // Write the approved request to AdoptionReports.txt
                        writer.newLine();

                        logAdoptionHistory(request);

                        isApproved = true; // Mark the request as approved
                    }
                    else {
                        // Add non-approved requests to the remaining list
                        remainingRequests.add(request);
                    }
                }

            }

            // Update the AdoptionRequest.txt file only if changes occurred
            if (isApproved) {
                try (BufferedWriter buffer = new BufferedWriter(new FileWriter(adoptionRequest, false))) {
                    // Write the remaining requests back to the AdoptionRequest.txt file
                    for (String remainingRequest : remainingRequests) {
                        buffer.write(remainingRequest);
                        buffer.newLine();
                    }
                }
                System.out.println("Request with ID " + requestIdToApprove + " has been approved.\n");
            }
            else {
                System.out.println("Request with ID " + requestIdToApprove + " not found.");
            }

        }
        catch (IOException e) {
            System.out.println("An error occurred while processing adoption requests.");
            e.printStackTrace();
        }
    }

    public void searchPetForAdoption(int petID) {
        String petName = null;
        boolean petExists = false;

        // Check if pet exists
        try (Scanner fileScanner = new Scanner(new File("Pet.txt"))) {
            Random random = new Random();
            int id = random.nextInt(1_000_000);
            setRequestID(id);

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();

                // Skip empty lines
                if (line.trim().isEmpty()) continue;

                String[] petDetails = line.split(",");

                if (petDetails.length == 9 && Integer.parseInt(petDetails[0]) == petID) {
                    petName = petDetails[1];
                    petExists = true;
                    break;
                }
            }

            if (!petExists) {
                System.out.println("Pet with ID " + petID + " not found.");
                return;
            }

            // Get the currently logged-in username from the LoginPage class
            String currentUsername = loginPage.getCurrentUsername();

            if (currentUsername == null || currentUsername.isEmpty()) {
                System.out.println("❌ Error: You need to be logged in to make an adoption request.");
                return;
            }

            // Use the getCustomerNameByUsername method to get the customer's full name
            String fullName = customerManager.getCustomerNameByUsername(currentUsername);

            if (fullName == null) {
                System.out.println("❌ Error: Unable to retrieve customer information.");
                return;
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(adoptionRequest, true))) {
                writer.write(getRequestID() + fullName + " is requesting to adopt pet ID: " + petID + " (" + petName + ")");
                writer.newLine();
                System.out.println("✅ Adoption request submitted successfully.");
            } catch (IOException e) {
                System.out.println("❌ Error writing to adoption request file: " + e.getMessage());
            }

        } catch (FileNotFoundException e) {
            System.out.println("❌ Pet file not found: " + e.getMessage());
        }
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
                case 2 -> customerManager.remove();
                case 3 -> customerManager.update();
                case 4 -> customerManager.view();
                case 5 -> approveAdoptionRequest();
                case 6 -> {
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

    public int getRequestID() {
        return requestID;
    }

    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }

    private static final boolean DEBUG_MODE = false; // Set to true only when debugging

}
