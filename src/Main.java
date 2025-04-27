public class Main {
    public static void main(String[] args) {
        System.out.println("Starting Pet Haven Application...");

        LoginPage loginPage = new LoginPage();
        loginPage.logIn();

        String username = loginPage.getCurrentUsername();

        if (username != null) {
            System.out.println("Login successful!");

            // Pass the username to AdoptionManagement
            AdoptionManagement adoptionManager = new AdoptionManagement(loginPage);

            if (username.equals("admin123")) {
                System.out.println("Welcome, Administrator!");
                adoptionManager.adminUserInterface();
            } else {
                System.out.println("Welcome, " + username + "!");
                adoptionManager.nonAdminUserInterface();
            }
        } else {
            System.out.println("Login failed or canceled.");
        }
    }
}
