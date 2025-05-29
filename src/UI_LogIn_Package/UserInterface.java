package UI_LogIn_Package;

import Adoption_Management_Package.PetManagement;

/**
 * Represents the user interface for a pet adoption management system.
 * It provides menus for both users and administrators to interact with
 * the system for various functionalities such as managing pets, customers,
 * and generating reports.
 */
public class UserInterface extends PetManagement {
    final String RESET = "\u001B[0m";
    final String SKY_BLUE = "\u001B[38;2;161;227;239m";
    final String LIGHT_BLUE = "\u001B[38;2;138;170;251m";
    final String AQUA_BLUE = "\u001B[38;5;116m";

    public void logInInterfaceHeader() {

        //HeaderDisplay
        System.out.println(" _____                                                                                                                                                _____ ");
        System.out.println("( ___ )══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════( ___ )");
        System.out.println(" ║   ║                                                                                                                                                ║   ║ ");
        System.out.println(" ║   ║                                                                                                                                                ║   ║ ");
        System.out.println(" ║   ║                          "+SKY_BLUE+"  ,-.___,-. "+LIGHT_BLUE+" ██╗    ██╗███████╗██╗      ██████╗ ██████╗ ███╗   ███╗███████╗                            "+RESET+"               ║   ║ ");
        System.out.println(" ║   ║                          "+SKY_BLUE+"  |_|_ _|_| "+LIGHT_BLUE+" ██║    ██║██╔════╝██║     ██╔════╝██╔═══██╗████╗ ████║██╔════╝  "+SKY_BLUE+"  ^~^  ,   "+RESET+"                              ║   ║ ");
        System.out.println(" ║   ║                          "+SKY_BLUE+"    )O_O(   "+LIGHT_BLUE+" ██║ █╗ ██║█████╗  ██║     ██║     ██║   ██║██╔████╔██║█████╗    "+SKY_BLUE+" ('Y') )   "+RESET+"                              ║   ║ ");
        System.out.println(" ║   ║                          "+SKY_BLUE+"   { (_) }  "+LIGHT_BLUE+" ██║███╗██║██╔══╝  ██║     ██║     ██║   ██║██║╚██╔╝██║██╔══╝    "+SKY_BLUE+" /   ||/   "+RESET+"                              ║   ║ ");
        System.out.println(" ║   ║                          "+SKY_BLUE+"    `-^-'   "+LIGHT_BLUE+" ╚███╔███╔╝███████╗███████╗╚██████╗╚██████╔╝██║ ╚═╝ ██║███████╗  "+SKY_BLUE+"(|||||/)   "+RESET+"                              ║   ║ ");
        System.out.println(" ║   ║                                      "+LIGHT_BLUE+"  ╚══╝╚══╝ ╚══════╝╚══════╝ ╚═════╝ ╚═════╝ ╚═╝     ╚═╝╚══════╝                                        "+RESET+"   ║   ║ ");
        System.out.println(" ║   ║                                                                                                                                                ║   ║ ");
        System.out.println(" ║   ║                  "+LIGHT_BLUE+"      ████████╗ ██████╗     ██████╗ ███████╗████████╗    ██╗  ██╗ █████╗ ██╗   ██╗███████╗███╗   ██╗     "+RESET+"                     ║   ║ ");
        System.out.println(" ║   ║                  "+LIGHT_BLUE+"      ╚══██╔══╝██╔═══██╗    ██╔══██╗██╔════╝╚══██╔══╝    ██║  ██║██╔══██╗██║   ██║██╔════╝████╗  ██║     "+RESET+"                     ║   ║ ");
        System.out.println(" ║   ║                  "+LIGHT_BLUE+"         ██║   ██║   ██║    ██████╔╝█████╗     ██║       ███████║███████║██║   ██║█████╗  ██╔██╗ ██║     "+RESET+"                     ║   ║ ");
        System.out.println(" ║   ║                  "+LIGHT_BLUE+"         ██║   ██║   ██║    ██╔═══╝ ██╔══╝     ██║       ██╔══██║██╔══██║╚██╗ ██╔╝██╔══╝  ██║╚██╗██║     "+RESET+"                     ║   ║ ");
        System.out.println(" ║   ║                  "+LIGHT_BLUE+"         ██║   ╚██████╔╝    ██║     ███████╗   ██║       ██║  ██║██║  ██║ ╚████╔╝ ███████╗██║ ╚████║     "+RESET+"                     ║   ║ ");
        System.out.println(" ║   ║                  "+LIGHT_BLUE+"         ╚═╝    ╚═════╝     ╚═╝     ╚══════╝   ╚═╝       ╚═╝  ╚═╝╚═╝  ╚═╝  ╚═══╝  ╚══════╝╚═╝  ╚═══╝     "+RESET+"                     ║   ║ ");
        System.out.println(" ║   ║                                                                                                                                                ║   ║ ");
        System.out.println(" ║   ║            "+AQUA_BLUE+"   ╦ ╦╔═╗╦ ╦ ╦═╗  ╔╦╗╦═╗╦ ╦╔═╗╔╦╗╔═╗╔╦═╗  ╔═╗╔═╗╔╦╗  ╔═╗╔╦═╗╔═╗╔═╗╔╦╗╦╔═╗╔╗╔  ╔═╗╦ ╦╔═╗╔╦╗╔═╗╔╦╗  ╔═╗╔═╗╦═╗╔╦╗╔═╗ ╦     "+RESET+"            ║   ║ ");
        System.out.println(" ║   ║            "+AQUA_BLUE+"   ╚╦╝║ ║║ ║ ╠╦╝   ║ ╠╦╝║ ║╚═╗ ║ ║╣  ║ ║  ╠═╝║╣  ║   ╠═╣ ║ ║║ ║╠═╝ ║ ║║ ║║║║  ╚═╗╚╦╝╚═╗ ║ ║╣ ║║║  ╠═╝║ ║╠╦╝ ║ ╠═╣ ║     "+RESET+"            ║   ║ ");
        System.out.println(" ║   ║            "+AQUA_BLUE+"    ╩ ╚═╝╚═╝ ╩╚═   ╩ ╩╚═╚═╝╚═╝ ╩ ╚═╝═╩═╝  ╩  ╚═╝ ╩   ╩ ╩═╩═╝╚═╝╩   ╩ ╩╚═╝╝╚╝  ╚═╝ ╩ ╚═╝ ╩ ╚═╝╩ ╩  ╩  ╚═╝╩╚═ ╩ ╩ ╩ ╩═╝   "+RESET+"            ║   ║ ");
        System.out.println(" ║   ║                                                                                                                                                ║   ║ ");
        System.out.println(" ║   ║                                                                                                                                                ║   ║ ");
        System.out.println(" ║   ║════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════║   ║ ");
        System.out.println(" ║   ║                                                                                                                                                ║   ║ ");
        System.out.println(" ║   ║                               "+AQUA_BLUE+"        [1] Login                                            [2] Sign Up     "+RESET+"                                    ║   ║ ");
        System.out.println(" ║   ║                                                                                                                                                ║   ║ ");
        System.out.println(" ║   ║                               "+AQUA_BLUE+"        [3] About                                            [4] Exit        "+RESET+"                                    ║   ║ ");
        System.out.println(" ║___║                                                                                                                                                ║___║ ");
        System.out.println("(_____)══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════(_____)");
        System.out.println();
        System.out.print("                                             Select an option: ");


    }

    public void logInInterfaceFooter() {
        //FooterDisplay
        System.out.println();
        System.out.println(" _____                                                                                                                                                _____ ");
        System.out.println("( ___ )══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════( ___ )");
        System.out.println(" |   |                                                                                                                                                |   | ");
        System.out.println(" |   |                                  "+AQUA_BLUE+"                 Forgot your password? Contact support team               "+RESET+"                                    |   | ");
        System.out.println(" |   |                                  "+AQUA_BLUE+"                         @Email: help@pethaven.org                        "+RESET+"                                    |   | ");
        System.out.println(" |___|                                                                                                                                                |___| ");
        System.out.println("(_____)══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════(_____)");
        System.out.println();


    }

    public void userInterface() {

        //User Panel
        System.out.println(" _____                                                                                                                                                _____ ");
        System.out.println("( ___ )══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════( ___ )");
        System.out.println(" ║   ║                           "+SKY_BLUE+"   ,-.___,-.                                                                                                       "+RESET+"  ║   ║ ");
        System.out.println(" ║   ║                           "+SKY_BLUE+"   |_|_ _|_| "+AQUA_BLUE+" ╔═╗╔═╗╔╦╗  ╦ ╦╔═╗╦  ╦╔═╗╔╗╔       ╦ ╦╔═╗╔═╗╦═╗  ╔═╗╔═╗╔╗╔╔═╗╦   "+SKY_BLUE+"  ^~^  ,    "+RESET+"                           ║   ║ ");
        System.out.println(" ║   ║                           "+SKY_BLUE+"     )O_O(   "+AQUA_BLUE+" ╠═╝║╣  ║   ╠═╣╠═╣╚╗╔╝║╣ ║║║  ───  ║ ║╚═╗║╣ ╠╦╝  ╠═╝╠═╣║║║║╣ ║   "+SKY_BLUE+" ('Y') )    "+RESET+"                           ║   ║ ");
        System.out.println(" ║   ║                           "+SKY_BLUE+"    { (_) }  "+AQUA_BLUE+" ╩  ╚═╝ ╩   ╩ ╩╩ ╩ ╚╝ ╚═╝╝╚╝       ╚═╝╚═╝╚═╝╩╚═  ╩  ╩ ╩╝╚╝╚═╝╩═╝ "+SKY_BLUE+" /   ||/    "+RESET+"                           ║   ║ ");
        System.out.println(" ║   ║                           "+SKY_BLUE+"     `-^-'                                                                   "+SKY_BLUE+" (|||||/)                 "+RESET+"              ║   ║ ");
        System.out.println(" ║   ║════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════║   ║ ");
        System.out.println(" ║   ║                                                                                                                                                ║   ║ ");
        System.out.println(" ║   ║                            "+AQUA_BLUE+"           [1] View Available Pets                     [2] Make an Adoption Request        "+RESET+"                         ║   ║ ");
        System.out.println(" ║   ║                                                                                                                                                ║   ║ ");
        System.out.println(" ║   ║                            "+AQUA_BLUE+"           [3] Search Pet                              [4] Log Out                         "+RESET+"                         ║   ║ ");
        System.out.println(" ║___║                                                                                                                                                ║___║ ");
        System.out.println("(_____)══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════(_____)");
        System.out.println();
        System.out.print("                                             Select an option: ");

    }

    public void adminInterface() {
        //Admin Panel
        System.out.println(" _____                                                                                                                                                _____ ");
        System.out.println("( ___ )══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════( ___ )");
        System.out.println(" ║   ║                         "+SKY_BLUE+"   ,-.___,-.                                                                                                         "+RESET+"  ║   ║ ");
        System.out.println(" ║   ║                         "+SKY_BLUE+"   |_|_ _|_| "+AQUA_BLUE+" ╔═╗╔═╗╔╦╗  ╦ ╦╔═╗╦  ╦╔═╗╔╗╔       ╔═╗╔╦═╗╔╦╗╦╔╗╔  ╔═╗╔═╗╔╗╔╔═╗╦   "+SKY_BLUE+" ^~^  ,    "+RESET+"                            ║   ║ ");
        System.out.println(" ║   ║                         "+SKY_BLUE+"     )O_O(   "+AQUA_BLUE+" ╠═╝║╣  ║   ╠═╣╠═╣╚╗╔╝║╣ ║║║  ───  ╠═╣ ║ ║║║║║║║║  ╠═╝╠═╣║║║║╣ ║   "+SKY_BLUE+"('Y') )    "+RESET+"                            ║   ║ ");
        System.out.println(" ║   ║                         "+SKY_BLUE+"    { (_) }  "+AQUA_BLUE+" ╩  ╚═╝ ╩   ╩ ╩╩ ╩ ╚╝ ╚═╝╝╚╝       ╩ ╩═╩═╝╩ ╩╩╝╚╝  ╩  ╩ ╩╝╚╝╚═╝╩═╝ "+SKY_BLUE+"/   ||/    "+RESET+"                            ║   ║ ");
        System.out.println(" ║   ║                         "+SKY_BLUE+"     `-^-'                                                                     "+SKY_BLUE+"(|||||/)                  "+RESET+"              ║   ║ ");
        System.out.println(" ║   ║════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════║   ║ ");
        System.out.println(" ║   ║                                                                                                                                                ║   ║ ");
        System.out.println(" ║   ║                            "+AQUA_BLUE+"           [1] Customer Management                     [2] Pet Management              "+RESET+"                             ║   ║ ");
        System.out.println(" ║   ║                                                                                                                                                ║   ║ ");
        System.out.println(" ║   ║                            "+AQUA_BLUE+"           [3] Generate Reports                        [4] Log Out                     "+RESET+"                             ║   ║ ");
        System.out.println(" ║___║                                                                                                                                                ║___║ ");
        System.out.println("(_____)══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════(_____)");
        System.out.println();
        System.out.print("                                             Select an option: ");

    }

    public void customerManagementMenu() {
        //Customer Management Menu
        System.out.println(" _____                                                                                                                                                _____ ");
        System.out.println("( ___ )══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════( ___ )");
        System.out.println(" ║   ║                       "+SKY_BLUE+"   ,-.___,-.                                                                                                           "+RESET+"  ║   ║ ");
        System.out.println(" ║   ║                       "+SKY_BLUE+"   |_|_ _|_| "+AQUA_BLUE+" ╔═╗╦ ╦╔═╗╔╦╗╔═╗╔╦╗╔═╗╦═╗  ╔╦╗╔═╗╔╗╔╔═╗╔═╗╔═╗╔╦╗╔═╗╔╗╔╔╦╗  ╔╦╗╔═╗╔╗╔ ╦ ╦  "+SKY_BLUE+" ^~^  ,    "+RESET+"                       ║   ║ ");
        System.out.println(" ║   ║                       "+SKY_BLUE+"     )O_O(   "+AQUA_BLUE+" ║  ║ ║╚═╗ ║ ║ ║║║║║╣ ╠╦╝  ║║║╠═╣║║║╠═╣║ ╦║╣ ║║║║╣ ║║║ ║   ║║║║╣ ║║║ ║ ║  "+SKY_BLUE+"('Y') )    "+RESET+"                       ║   ║ ");
        System.out.println(" ║   ║                       "+SKY_BLUE+"    { (_) }  "+AQUA_BLUE+" ╚═╝╚═╝╚═╝ ╩ ╚═╝╩ ╩╚═╝╩╚═  ╩ ╩╩ ╩╝╚╝╩ ╩╚═╝╚═╝╩ ╩╚═╝╝╚╝ ╩   ╩ ╩╚═╝╝╚╝ ╚═╝  "+SKY_BLUE+"/   ||/    "+RESET+"                       ║   ║ ");
        System.out.println(" ║   ║                       "+SKY_BLUE+"     `-^-'                                                                            "+SKY_BLUE+"(|||||/)                  "+RESET+"         ║   ║ ");
        System.out.println(" ║   ║════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════║   ║ ");
        System.out.println(" ║   ║                                                                                                                                                ║   ║ ");
        System.out.println(" ║   ║                            "+AQUA_BLUE+"           [1] Add Customer                            [2] View, Update, and Remove Customer Details           "+RESET+"     ║   ║ ");
        System.out.println(" ║   ║                                                                                                                                                ║   ║ ");
        System.out.println(" ║   ║                            "+AQUA_BLUE+"           [3] Review Adoption Request                 [4] Back to Admin Panel                                 "+RESET+"     ║   ║ ");
        System.out.println(" ║___║                                                                                                                                                ║___║ ");
        System.out.println("(_____)══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════(_____)");
        System.out.println();
        System.out.print("                                             Select an option: ");

    }

    public void petManagementMenu() {
        //Pet Management Menu
        System.out.println(" _____                                                                                                                                                _____ ");
        System.out.println("( ___ )══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════( ___ )");
        System.out.println(" ║   ║                              "+SKY_BLUE+"   ,-.___,-.                                                                                                     "+RESET+" ║   ║ ");
        System.out.println(" ║   ║                              "+SKY_BLUE+"   |_|_ _|_| "+AQUA_BLUE+" ╔═╗╔═╗╔╦╗  ╔╦╗╔═╗╔╗╔╔═╗╔═╗╔═╗╔╦╗╔═╗╔╗╔╔╦╗  ╔╦╗╔═╗╔╗╔ ╦ ╦  "+SKY_BLUE+" ^~^  ,        "+RESET+"                           ║   ║ ");
        System.out.println(" ║   ║                              "+SKY_BLUE+"     )O_O(   "+AQUA_BLUE+" ╠═╝║╣  ║   ║║║╠═╣║║║╠═╣║ ╦║╣ ║║║║╣ ║║║ ║   ║║║║╣ ║║║ ║ ║  "+SKY_BLUE+"('Y') )        "+RESET+"                           ║   ║ ");
        System.out.println(" ║   ║                              "+SKY_BLUE+"    { (_) }  "+AQUA_BLUE+" ╩  ╚═╝ ╩   ╩ ╩╩ ╩╝╚╝╩ ╩╚═╝╚═╝╩ ╩╚═╝╝╚╝ ╩   ╩ ╩╚═╝╝╚╝ ╚═╝  "+SKY_BLUE+"/   ||/        "+RESET+"                           ║   ║ ");
        System.out.println(" ║   ║                              "+SKY_BLUE+"     `-^-'                                                             "+SKY_BLUE+"(|||||/)                      "+RESET+"             ║   ║ ");
        System.out.println(" ║   ║════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════║   ║ ");
        System.out.println(" ║   ║                                                                                                                                                ║   ║ ");
        System.out.println(" ║   ║                            "+AQUA_BLUE+"           [1] Add Pet                                [2] View, Update, Remove Pet Details          "+RESET+"                ║   ║ ");
        System.out.println(" ║   ║                                                                                                                                                ║   ║ ");
        System.out.println(" ║   ║                            "+AQUA_BLUE+"           [3]  Back to Admin Panel                                                 "+RESET+"                                ║   ║ ");
        System.out.println(" ║   ║                                                                                                                                                ║   ║ ");
        System.out.println(" ║___║                                                                                                                                                ║___║ ");
        System.out.println("(_____)══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════(_____)");
        System.out.println();
        System.out.print("                                             Select an option: ");
    }

    public void reportsMenu() {
        //Reports Menu
        System.out.println(" _____                                                                                                                                                _____ ");
        System.out.println("( ___ )══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════( ___ )");
        System.out.println(" ║   ║                                        "+SKY_BLUE+"   ,-.___,-.                                                                                          "+RESET+"  ║   ║ ");
        System.out.println(" ║   ║                                        "+SKY_BLUE+"   |_|_ _|_| "+AQUA_BLUE+" ╦═╗╔═╗╔═╗╔═╗╦═╗╔╦╗╔═╗   ╔╦╗╔═╗╔╗╔ ╦ ╦  "+SKY_BLUE+" ^~^  ,                "+RESET+"                            ║   ║ ");
        System.out.println(" ║   ║                                        "+SKY_BLUE+"     )O_O(   "+AQUA_BLUE+" ╠╦╝║╣ ╠═╝║ ║╠╦╝ ║ ╚═╗   ║║║║╣ ║║║ ║ ║  "+SKY_BLUE+"('Y') )                "+RESET+"                            ║   ║ ");
        System.out.println(" ║   ║                                        "+SKY_BLUE+"    { (_) }  "+AQUA_BLUE+" ╩╚═╚═╝╩  ╚═╝╩╚═ ╩ ╚═╝   ╩ ╩╚═╝╝╚╝ ╚═╝  "+SKY_BLUE+"/   ||/                "+RESET+"                            ║   ║ ");
        System.out.println(" ║   ║                                        "+SKY_BLUE+"     `-^-'                                          "+SKY_BLUE+"(|||||/)                              "+RESET+"              ║   ║ ");
        System.out.println(" ║   ║════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════║   ║ ");
        System.out.println(" ║   ║                                                                                                                                                ║   ║ ");
        System.out.println(" ║   ║                            "+AQUA_BLUE+"           [1] Generate Adoption Reports               [2] Generate Remaining Pets Report      "+RESET+"                     ║   ║ ");
        System.out.println(" ║   ║                                                                                                                                                ║   ║ ");
        System.out.println(" ║   ║                            "+AQUA_BLUE+"           [3] Back to Admin Panel                                                             "+RESET+"                     ║   ║ ");
        System.out.println(" ║___║                                                                                                                                                ║___║ ");
        System.out.println("(_____)══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════(_____)");
        System.out.println();
        System.out.print("                                             Select an option: ");
    }
}