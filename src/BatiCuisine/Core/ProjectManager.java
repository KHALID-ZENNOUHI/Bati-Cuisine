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
    private ComponentServiceImpl componentService;
    private QuoteServiceImpl quoteService;
    private ComponentManager componentManager;
    private QuoteManagement quoteManager;

    public ProjectManager() throws SQLException {
        this.projectService = new ProjectServiceImpl();
        this.scanner = new Scanner(System.in);
        this.clientService = new ClientServiceImpl();
        this.componentService = new ComponentServiceImpl();
        this.quoteService = new QuoteServiceImpl();
        this.componentManager = new ComponentManager();
        this.quoteManager = new QuoteManagement();
    }
    public void createProject(String clientName) {
        Project project = new Project();
        System.out.println("--- Create Project ---");
        System.out.print("Enter project name: ");
        String projectName = scanner.nextLine();
        project.setName(projectName);

        Optional<Client> clientOptional = this.clientService.findByName(clientName);
        if (clientOptional.isPresent()) {
            Client client = clientOptional.get();
            project.setClient(client);
        } else {
            System.out.println("Client not found! Project creation aborted.");
            return;
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

        reviewProject(project, materials, labors, componentsCost, clientOptional);

       Quote quote = this.quoteManager.createQuote(project);
        do {
            System.out.print("Do you want to save the project (y/n) ?  ");
        }while (!scanner.next().equalsIgnoreCase("y") && !scanner.next().equalsIgnoreCase("n"));

        if (scanner.next().equalsIgnoreCase("y")) {
            project.setProjectStatus(ProjectStatus.in_progress);
            Project savedProject = projectService.save(project);

            if (savedProject != null) {

                materials.forEach(material -> material.setProject(savedProject));
                labors.forEach(labor -> labor.setProject(savedProject));


                this.componentService.saveAllMaterials(materials);
                this.componentService.saveAllLabors(labors);

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


    public void reviewProject(Project project, List<Material> materials, List<Labor> labors, Double componentsCost, Optional<Client> clientOptional) {
        System.out.println("--- Review Project ---");
        System.out.println("Project name: " + project.getName());
        clientOptional.ifPresent(client -> {
            System.out.println("Client: " + client.getName());
            System.out.println("Address: " + client.getAddress());
        });

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
    }

    public void displayAllProjects() {
        List<Project> projects = this.projectService.findAll();
        projects.forEach(project -> {
            System.out.println("Project name: " + project.getName());
            System.out.println("Client: " + project.getClient().getName());
            System.out.println("Total cost: " + project.getTotalCost());
            System.out.println("Profit margin: " + project.getProfitMargin());
            System.out.println("Status: " + project.getProjectStatus());
            int projectId = project.getId();
            System.out.println("Project Materials: ");
            this.componentService.projectMaterials(projectId).forEach(component -> {
                System.out.println("- Component name: " + component.getName() + ", Component cost: " + component.calculateCost() + " DH");
            });
            System.out.println("Project Labors: ");
            this.componentService.projectLabors(projectId).forEach(component -> {
                System.out.println("- Component name: " + component.getName() + ", Component cost: " + component.calculateCost() + " DH");
            });
            this.quoteService.findByProjectId(projectId).ifPresent(quote -> {
                System.out.println("Quote: ");
                System.out.println("Quote amount: " + quote.getEstimatedAmount());
                System.out.println("Accepted: " + quote.getAccepted());
                System.out.println("Issue date: " + quote.getIssueDate());
                System.out.println("Validity date: " + quote.getValidityDate());
            });
            System.out.println("-------------------------------------------------");
        });
    }
}