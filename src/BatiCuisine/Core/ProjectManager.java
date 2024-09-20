package BatiCuisine.Core;

import BatiCuisine.Domain.Entity.*;
import BatiCuisine.Domain.Enum.ProjectStatus;
import BatiCuisine.Service.Implementation.ClientServiceImpl;
import BatiCuisine.Service.Implementation.ComponentServiceImpl;
import BatiCuisine.Service.Implementation.ProjectServiceImpl;
import BatiCuisine.Service.Implementation.QuoteServiceImpl;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ProjectManager {
    private ProjectServiceImpl projectService;
    private Scanner scanner;
    private ClientServiceImpl clientService;
    private DateTimeFormatter dateFormatter;
    private ComponentServiceImpl componentService;
    private QuoteServiceImpl quoteService;
    private ComponentManager componentManager;

    public ProjectManager() throws SQLException {
        this.projectService = new ProjectServiceImpl();
        this.scanner = new Scanner(System.in);
        this.clientService = new ClientServiceImpl();
        this.componentService = new ComponentServiceImpl();
        this.quoteService = new QuoteServiceImpl();
        this.dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.componentManager = new ComponentManager();
    }
    public void createProject(String clientName) {
        Project project = new Project();
        System.out.println("--- Create Project ---");
        System.out.print("Enter project name: ");
        String projectName = scanner.nextLine();
        project.setName(projectName);

        // Fetch the client by name and set the client ID in the project
        Optional<Client> clientOptional = this.clientService.findByName(clientName);
        if (clientOptional.isPresent()) {
            Client client = clientOptional.get();
            project.setClient(client); // Set client ID in the project
        } else {
            System.out.println("Client not found! Project creation aborted.");
            return; // Exit if client is not found
        }

        List<Material> materials = this.componentManager.addMaterials();
        List<Labor> labors = this.componentManager.addLabor();

        System.out.println("Calculate total cost:");
        System.out.print("Enter the project VAT rate: ");
        Double VATRate = scanner.nextDouble();
        materials.forEach(component -> component.setVATRate(VATRate));
        labors.forEach(component -> component.setVATRate(VATRate));

        System.out.print("Add profit margin to the project: ");
        Double profitMargin = scanner.nextDouble();
        project.setProfitMargin(profitMargin);

        List<Component> components = new ArrayList<>();
        components.addAll(materials);
        components.addAll(labors);
        project.setComponents(components);

        Double componentsCost = components.stream().mapToDouble(Component::calculateCost).sum();
        project.setTotalCost(componentsCost + (componentsCost * (project.getProfitMargin() / 100)));

        System.out.println("--- Review Project ---");
        System.out.println("Project name: " + project.getName());
        System.out.println("Client: " + clientOptional.get().getName());
        System.out.println("Address: " + clientOptional.get().getAddress());

        System.out.println("--- Cost details ---");
        System.out.println("1- Materials: ");
        materials.forEach(material -> {
            System.out.println("- Component name: " + material.getName() + ", Component cost: " + material.calculateCost() + " DH, unit cost: " + material.getUnitCost() + " DH, quantity: " + material.getQuantity());
        });

        System.out.println("2- Labors: ");
        labors.forEach(labor -> {
            System.out.println("- Component name: " + labor.getName() + ", Component cost: " + labor.calculateCost() + " DH, hourly rate: " + labor.getHourlyRate() + " DH, hours worked: " + labor.getHoursWorked());
        });

        System.out.println("Total cost before profit margin: " + componentsCost);
        System.out.println("Profit margin of the project: " + (project.getProfitMargin() / 100));
        Double estimatedAmount = project.getTotalCost();
        System.out.println("Total cost after profit margin: " + estimatedAmount);

        System.out.println("--- Project Quotation ---");
        Quote quote = new Quote();
        scanner.nextLine(); // Consume leftover newline from previous input

        System.out.print("Enter the issue date (dd/MM/yyyy): ");
        String issueDate = scanner.nextLine();
        LocalDate issueDateFormatted = LocalDate.parse(issueDate, this.dateFormatter);
        quote.setIssueDate(issueDateFormatted);

        System.out.print("Enter the validity date (dd/MM/yyyy): ");
        String validityDate = scanner.nextLine();
        LocalDate validityDateFormatted = LocalDate.parse(validityDate, this.dateFormatter);
        quote.setValidityDate(validityDateFormatted);
        quote.setEstimatedAmount(estimatedAmount);

        System.out.println("Do you want to save the project? (y/n)");
        if (scanner.nextLine().equalsIgnoreCase("y")) {
            project.setProjectStatus(ProjectStatus.in_progress);
            Project savedProject = projectService.save(project); // Save the project

            if (savedProject != null) {
                // Set project ID in components
                materials.forEach(material -> material.setProject(savedProject));
                labors.forEach(labor -> labor.setProject(savedProject));

                // Save components
                this.componentService.saveAllMaterials(materials);
                this.componentService.saveAllLabors(labors);

                // Save the quote
                quote.setProject(savedProject);
                quote.setAccepted(true);
                this.quoteService.save(quote);

                System.out.println("Project saved successfully!");
            } else {
                System.out.println("Failed to save the project.");
            }
        } else {
            System.out.println("Project not saved!");
        }
    }



}