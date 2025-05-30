package Main_Package;//package Main_Package;

import Adoption_Management_Package.AdoptionManagement;
import UI_LogIn_Package.LoginPage;

public class Main {
    public static void main(String[] args) {

        final String RESET = "\u001B[0m";
        final String GREEN = "\u001B[38;2;0;210;106m";

        System.out.println("\n                                                           STARTING PET HAVEN APPLICATION...");
        System.out.println( );
        LoginPage loginPage = new LoginPage();


        loginPage.logIn();

        String username = loginPage.getCurrentUsername();

        if (username != null) {
            System.out.println(GREEN+"                                                                  LOGIN SUCCESSFULLY!                                                                       "+RESET);

            // Pass the username to AdoptionManagement
            AdoptionManagement adoptionManager = new AdoptionManagement(loginPage);

            if (username.equals("admin123")) {
                System.out.println("                                                                WELCOME, ADMINISTRATOR!");
                adoptionManager.adminUserInterface();
            }
            else {
                System.out.println("                                                                  WELCOME, " + username + "!");
                adoptionManager.nonAdminUserInterface();
            }
        }
    }
}