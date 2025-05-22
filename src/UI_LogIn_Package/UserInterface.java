package UI_LogIn_Package;

import Adoption_Management_Package.PetManagement;

/**
 * Represents the user interface for a pet adoption management system.
 * It provides menus for both users and administrators to interact with
 * the system for various functionalities such as managing pets, customers,
 * and generating reports.
 */
public class UserInterface extends PetManagement {

    public void logInInterfaceHeader() {

        //HeaderDisplay
        System.out.println(" _____                                                                                                                                                _____ ");
        System.out.println("( ___ )──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────( ___ )");
        System.out.println(" |   |                                                                                                                                                |   | ");
        System.out.println(" |   |                                                                                                                                                |   | ");
        System.out.println(" |   |                            ,-.___,-.  ██╗    ██╗███████╗██╗      ██████╗ ██████╗ ███╗   ███╗███████╗                                           |   | ");
        System.out.println(" |   |                            |_|_ _|_|  ██║    ██║██╔════╝██║     ██╔════╝██╔═══██╗████╗ ████║██╔════╝    ^~^  ,                                 |   | ");
        System.out.println(" |   |                              )O_O(    ██║ █╗ ██║█████╗  ██║     ██║     ██║   ██║██╔████╔██║█████╗     ('Y') )                                 |   | ");
        System.out.println(" |   |                             { (_) }   ██║███╗██║██╔══╝  ██║     ██║     ██║   ██║██║╚██╔╝██║██╔══╝     /   ||/                                 |   | ");
        System.out.println(" |   |                              `-^-'    ╚███╔███╔╝███████╗███████╗╚██████╗╚██████╔╝██║ ╚═╝ ██║███████╗  (|||||/)                                 |   | ");
        System.out.println(" |   |                                        ╚══╝╚══╝ ╚══════╝╚══════╝ ╚═════╝ ╚═════╝ ╚═╝     ╚═╝╚══════╝                                           |   | ");
        System.out.println(" |   |                                                                                                                                                |   | ");
        System.out.println(" |   |                        ████████╗ ██████╗     ██████╗ ███████╗████████╗    ██╗  ██╗ █████╗ ██╗   ██╗███████╗███╗   ██╗                          |   | ");
        System.out.println(" |   |                        ╚══██╔══╝██╔═══██╗    ██╔══██╗██╔════╝╚══██╔══╝    ██║  ██║██╔══██╗██║   ██║██╔════╝████╗  ██║                          |   | ");
        System.out.println(" |   |                           ██║   ██║   ██║    ██████╔╝█████╗     ██║       ███████║███████║██║   ██║█████╗  ██╔██╗ ██║                          |   | ");
        System.out.println(" |   |                           ██║   ██║   ██║    ██╔═══╝ ██╔══╝     ██║       ██╔══██║██╔══██║╚██╗ ██╔╝██╔══╝  ██║╚██╗██║                          |   | ");
        System.out.println(" |   |                           ██║   ╚██████╔╝    ██║     ███████╗   ██║       ██║  ██║██║  ██║ ╚████╔╝ ███████╗██║ ╚████║                          |   | ");
        System.out.println(" |   |                           ╚═╝    ╚═════╝     ╚═╝     ╚══════╝   ╚═╝       ╚═╝  ╚═╝╚═╝  ╚═╝  ╚═══╝  ╚══════╝╚═╝  ╚═══╝                          |   | ");
        System.out.println(" |   |                                                                                                                                                |   | ");
        System.out.println(" |   |                ┬ ┬┌─┐┬ ┬┬─┐  ┌┬┐┬─┐┬ ┬┌─┐┌┬┐┌─┐┌┬─┐  ┌─┐┌─┐┌┬┐  ┌─┐┌┬─┐┌─┐┌─┐┌┬┐┬┌─┐┌┐┌  ┌─┐┬ ┬┌─┐┌┬┐┌─┐┌┬┐  ┌─┐┌─┐┬─┐┌┬┐┌─┐┬                  |   | ");
        System.out.println(" |   |                └┬┘│ ││ │├┬┘   │ ├┬┘│ │└─┐ │ ├┤  │ │  ├─┘├┤  │   ├─┤ │ ││ │├─┘ │ ││ ││││  └─┐└┬┘└─┐ │ ├┤ │││  ├─┘│ │├┬┘ │ ├─┤│                  |   | ");
        System.out.println(" |   |                 ┴ └─┘└─┘┴└─   ┴ ┴└─└─┘└─┘ ┴ └─┘─┴─┘  ┴  └─┘ ┴   ┴ ┴ ┴─┘└─┘┴   ┴ ┴└─┘┘└┘  └─┘ ┴ └─┘ ┴ └─┘┴ ┴  ┴  └─┘┴└─ ┴ ┴ ┴┴─┘                |   | ");
        System.out.println(" |   |                                                                                                                                                |   | ");
        System.out.println(" |   |                                                                                                                                                |   | ");
        System.out.println(" |   |────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────|   | ");
        System.out.println(" |   |                                                                                                                                                |   | ");
        System.out.println(" |   |                                       [1] Login                                            [2] Sign Up                                         |   | ");
        System.out.println(" |   |                                                                                                                                                |   | ");
        System.out.println(" |   |                                       [3] About                                            [4] Exit                                            |   | ");
        System.out.println(" |___|                                                                                                                                                |___| ");
        System.out.println("(_____)──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────(_____)");
        System.out.println();
        System.out.print("                                             Select an option: ");


    }

    public void logInInterfaceFooter() {
        //FooterDisplay
        System.out.println();
        System.out.println(" _____                                                                                                                                                _____ ");
        System.out.println("( ___ )──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────( ___ )");
        System.out.println(" |   |                                                                                                                                                |   | ");
        System.out.println(" |   |                                                   Forgot your password? Contact support team                                                   |   | ");
        System.out.println(" |   |                                                           @Email: help@pethaven.org                                                            |   | ");
        System.out.println(" |___|                                                                                                                                                |___| ");
        System.out.println("(_____)──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────(_____)");
        System.out.println();


    }

    public void userInterface() {

        //User Panel
        System.out.println(" _____                                                                                                                                                _____ ");
        System.out.println("( ___ )──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────( ___ )");
        System.out.println(" |   |                             ,-.___,-.                                                                                                          |   | ");
        System.out.println(" |   |                             |_|_ _|_|  ┌─┐┌─┐┌┬┐  ┬ ┬┌─┐┬  ┬┌─┐┌┐┌       ┬ ┬┌─┐┌─┐┬─┐  ┌─┐┌─┐┌┐┌┌─┐┬     ^~^  ,                                |   | ");
        System.out.println(" |   |                               )O_O(    ├─┘├┤  │   ├─┤├─┤└┐┌┘├┤ │││  ───  │ │└─┐├┤ ├┬┘  ├─┘├─┤│││├┤ │    ('Y') )                                |   | ");
        System.out.println(" |   |                              { (_) }   ┴  └─┘ ┴   ┴ ┴┴ ┴ └┘ └─┘┘└┘       └─┘└─┘└─┘┴└─  ┴  ┴ ┴┘└┘└─┘┴─┘  /   ||/                                |   | ");
        System.out.println(" |   |                               `-^-'                                                                    (|||||/)                                |   | ");
        System.out.println(" |   |────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────|   | ");
        System.out.println(" |   |                                                                                                                                                |   | ");
        System.out.println(" |   |                                       [1] View Available Pets                     [2] Make an Adoption Request                                 |   | ");
        System.out.println(" |   |                                                                                                                                                |   | ");
        System.out.println(" |   |                                       [3] Search Pet                              [4] Log Out                                                  |   | ");
        System.out.println(" |___|                                                                                                                                                |___| ");
        System.out.println("(_____)──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────(_____)");
        System.out.println();
        System.out.print("                                             Select an option: ");

    }

    public void adminInterface() {
        //Admin Panel
        System.out.println(" _____                                                                                                                                                _____ ");
        System.out.println("( ___ )──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────( ___ )");
        System.out.println(" |   |                            ,-.___,-.                                                                                                           |   | ");
        System.out.println(" |   |                            |_|_ _|_|  ┌─┐┌─┐┌┬┐  ┬ ┬┌─┐┬  ┬┌─┐┌┐┌       ┌─┐┌┬─┐┌┬┐┬┌┐┌  ┌─┐┌─┐┌┐┌┌─┐┬    ^~^  ,                                |   | ");
        System.out.println(" |   |                              )O_O(    ├─┘├┤  │   ├─┤├─┤└┐┌┘├┤ │││  ───  ├─┤ │ ││││││││  ├─┘├─┤│││├┤ │   ('Y') )                                |   | ");
        System.out.println(" |   |                             { (_) }   ┴  └─┘ ┴   ┴ ┴┴ ┴ └┘ └─┘┘└┘       ┴ ┴─┴─┘┴ ┴┴┘└┘  ┴  ┴ ┴┘└┘└─┘┴─┘ /   ||/                                |   | ");
        System.out.println(" |   |                              `-^-'                                                                     (|||||/)                                |   | ");
        System.out.println(" |   |────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────|   | ");
        System.out.println(" |   |                                                                                                                                                |   | ");
        System.out.println(" |   |                                       [1] Customer Management                     [2] Pet Management                                           |   | ");
        System.out.println(" |   |                                                                                                                                                |   | ");
        System.out.println(" |   |                                       [3] Generate Reports                        [4] Log Out                                                  |   | ");
        System.out.println(" |___|                                                                                                                                                |___| ");
        System.out.println("(_____)──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────(_____)");
        System.out.println();
        System.out.print("                                             Select an option: ");

    }

    public void customerManagementMenu() {
        //Customer Management Menu
        System.out.println(" _____                                                                                                                                                _____ ");
        System.out.println("( ___ )──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────( ___ )");
        System.out.println(" |   |                          ,-.___,-.                                                                                                             |   | ");
        System.out.println(" |   |                          |_|_ _|_|  ┌─┐┬ ┬┌─┐┌┬┐┌─┐┌┬┐┌─┐┬─┐  ┌┬┐┌─┐┌┐┌┌─┐┌─┐┌─┐┌┬┐┌─┐┌┐┌┌┬┐  ┌┬┐┌─┐┌┐┌ ┬ ┬   ^~^  ,                           |   | ");
        System.out.println(" |   |                            )O_O(    │  │ │└─┐ │ │ ││││├┤ ├┬┘  │││├─┤│││├─┤│ ┬├┤ │││├┤ │││ │   │││├┤ │││ │ │  ('Y') )                           |   | ");
        System.out.println(" |   |                           { (_) }   └─┘└─┘└─┘ ┴ └─┘┴ ┴└─┘┴└─  ┴ ┴┴ ┴┘└┘┴ ┴└─┘└─┘┴ ┴└─┘┘└┘ ┴   ┴ ┴└─┘┘└┘ └─┘  /   ||/                           |   | ");
        System.out.println(" |   |                            `-^-'                                                                            (|||||/)                           |   | ");
        System.out.println(" |   |────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────|   | ");
        System.out.println(" |   |                                                                                                                                                |   | ");
        System.out.println(" |   |                                       [1] Add Customer                            [2] View, Update, and Remove Customer Details                |   | ");
        System.out.println(" |   |                                                                                                                                                |   | ");
        System.out.println(" |   |                                       [3] Review Adoption Request                 [4] Back to Admin Panel                                      |   | ");
        System.out.println(" |___|                                                                                                                                                |___| ");
        System.out.println("(_____)──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────(_____)");
        System.out.println();
        System.out.print("                                             Select an option: ");

    }

    public void petManagementMenu() {
        //Pet Management Menu
        System.out.println(" _____                                                                                                                                                _____ ");
        System.out.println("( ___ )──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────( ___ )");
        System.out.println(" |   |                                 ,-.___,-.                                                                                                      |   | ");
        System.out.println(" |   |                                 |_|_ _|_|  ┌─┐┌─┐┌┬┐  ┌┬┐┌─┐┌┐┌┌─┐┌─┐┌─┐┌┬┐┌─┐┌┐┌┌┬┐  ┌┬┐┌─┐┌┐┌ ┬ ┬   ^~^  ,                                   |   | ");
        System.out.println(" |   |                                   )O_O(    ├─┘├┤  │   │││├─┤│││├─┤│ ┬├┤ │││├┤ │││ │   │││├┤ │││ │ │  ('Y') )                                   |   | ");
        System.out.println(" |   |                                  { (_) }   ┴  └─┘ ┴   ┴ ┴┴ ┴┘└┘┴ ┴└─┘└─┘┴ ┴└─┘┘└┘ ┴   ┴ ┴└─┘┘└┘ └─┘  /   ||/                                   |   | ");
        System.out.println(" |   |                                   `-^-'                                                             (|||||/)                                   |   | ");
        System.out.println(" |   |────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────|   | ");
        System.out.println(" |   |                                                                                                                                                |   | ");
        System.out.println(" |   |                                       [1] Add Pet                                 [2] View, Update, Remove Pet Details                         |   | ");
        System.out.println(" |   |                                                                                                                                                |   | ");
        System.out.println(" |   |                                       [3] Back to Admin Panel                                                                                  |   | ");
        System.out.println(" |   |                                                                                                                                                |   | ");
        System.out.println(" |___|                                                                                                                                                |___| ");
        System.out.println("(_____)──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────(_____)");
        System.out.println();
        System.out.print("                                             Select an option: ");
    }

    public void reportsMenu() {
        //Reports Menu
        System.out.println(" _____                                                                                                                                                _____ ");
        System.out.println("( ___ )──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────( ___ )");
        System.out.println(" |   |                                           ,-.___,-.                                                                                            |   | ");
        System.out.println(" |   |                                           |_|_ _|_|  ┬─┐┌─┐┌─┐┌─┐┬─┐┌┬┐┌─┐   ┌┬┐┌─┐┌┐┌ ┬ ┬   ^~^  ,                                            |   | ");
        System.out.println(" |   |                                             )O_O(    ├┬┘├┤ ├─┘│ │├┬┘ │ └─┐   │││├┤ │││ │ │  ('Y') )                                            |   | ");
        System.out.println(" |   |                                            { (_) }   ┴└─└─┘┴  └─┘┴└─ ┴ └─┘   ┴ ┴└─┘┘└┘ └─┘  /   ||/                                            |   | ");
        System.out.println(" |   |                                             `-^-'                                          (|||||/)                                            |   | ");
        System.out.println(" |   |────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────|   | ");
        System.out.println(" |   |                                                                                                                                                |   | ");
        System.out.println(" |   |                                       [1] Generate Adoption Reports               [2] Generate Remaining Pets Report                           |   | ");
        System.out.println(" |   |                                                                                                                                                |   | ");
        System.out.println(" |   |                                       [3] Back to Admin Panel                                                                                  |   | ");
        System.out.println(" |___|                                                                                                                                                |___| ");
        System.out.println("(_____)──────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────(_____)");
        System.out.println();
        System.out.print("                                             Select an option: ");
    }
}