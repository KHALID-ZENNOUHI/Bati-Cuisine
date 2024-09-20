package BatiCuisine;

import BatiCuisine.Config.DataBaseConnection;
import BatiCuisine.Core.ClientManager;
import BatiCuisine.Core.ProjectManager;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        ProjectManager projectManager = new ProjectManager();
        ClientManager clientManager = new ClientManager();

        while (true) {
            System.out.println("<--------BatiCuisine Project Management-------->");
            System.out.println("Choose one of the following options:");
            System.out.println("    -> 1. Create a new project");
            System.out.println("    -> 2. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            switch (choice) {
                case 1:
                    System.out.println("Would you like to search for an existing client or create a new one?");
                    System.out.println("    -> 1. Search for a client");
                    System.out.println("    -> 2. Create a new client");
                    int clientChoice;

                    do {
                        System.out.print("Enter your choice: ");
                        clientChoice = scanner.nextInt();
                        scanner.nextLine(); // Consume the newline
                    } while (clientChoice != 1 && clientChoice != 2);

                    if (clientChoice == 1) {
                        String clientName = clientManager.searchClient();
                        if (clientName == null) {
                            System.out.println("Client not found.");
                        } else {
                            projectManager.createProject(clientName); // Updated: projectManager will handle client ID
                        }
                    } else if (clientChoice == 2) {
                        String clientName = clientManager.createClient();
                        projectManager.createProject(clientName); // Updated: projectManager will handle client ID
                    }
                    break;

                case 2:
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
