
import Core.ClientManager;
import Core.ProjectManager;
import Util.InputValidator;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        ProjectManager projectManager = new ProjectManager();
        ClientManager clientManager = new ClientManager();
        InputValidator validator = new InputValidator();

        while (true) {
            System.out.println("<--------BatiCuisine Project Management-------->");
            System.out.println("Choose one of the following options:");

            System.out.println("    -> 1. Create a new project");
            System.out.println("    -> 2. Display all projects");
            System.out.println("    -> 3. Project Details");
            System.out.println("    -> 4. Exit");


            int choice = validator.validateIntegerInRange("Enter your choice: ", 1, 4);

            switch (choice) {
                case 1:
                    System.out.println("Would you like to search for an existing client or create a new one?");

                    System.out.println("    -> 1. Search for a client");
                    System.out.println("    -> 2. Create a new client");
                    int clientChoice;

                    do {

                        clientChoice = validator.validateIntegerInRange("Enter your choice: ", 1, 2);

                    } while (clientChoice != 1 && clientChoice != 2);

                    if (clientChoice == 1) {
                        String clientName;
                        do {
                            clientName = clientManager.searchClient();
                        }while (clientName == null);

                        projectManager.createProject(clientName);
                    } else if (clientChoice == 2) {
                        String clientName = clientManager.createClient();
                        projectManager.createProject(clientName);
                    }
                    break;

                case 2:
                    System.out.println("Displaying all projects...");
                    projectManager.displayAllProjects();
                    break;

                case 3:
                    System.out.println("Project Details...");
                    projectManager.projectDetails();
                    break;

                case 4:
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
