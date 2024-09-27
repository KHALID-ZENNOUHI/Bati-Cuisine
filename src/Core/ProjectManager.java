package Core;

import Domain.Entity.*;
import Domain.Enum.ProjectStatus;
import Service.Implementation.ClientServiceImpl;
import Service.Implementation.ComponentServiceImpl;
import Service.Implementation.ProjectServiceImpl;
import Service.Implementation.QuoteServiceImpl;
import Util.InputValidator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ProjectManager {
    private final ProjectServiceImpl projectService;
    private Scanner scanner;
    private final InputValidator validator;
    private final ClientServiceImpl clientService;
    private final ComponentServiceImpl componentService;
    private final QuoteServiceImpl quoteService;
    private final ComponentManager componentManager;
    private final QuoteManagement quoteManager;

    public ProjectManager() throws SQLException {
        this.projectService = new ProjectServiceImpl();
        this.scanner = new Scanner(System.in);
        this.clientService = new ClientServiceImpl();
        this.componentService = new ComponentServiceImpl();
        this.quoteService = new QuoteServiceImpl();
        this.componentManager = new ComponentManager();
        this.quoteManager = new QuoteManagement();
        this.validator = new InputValidator();
    }

    public void createProject(String clientName) {
        Project project = new Project();
        System.out.println("--- Create Project ---");

        String projectName = validator.validateString("Enter project name: ");
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

        Double VATRate = validator.validateDouble("Enter the project VAT rate: ");
        materials.forEach(component -> component.setVATRate(VATRate));
        labors.forEach(component -> component.setVATRate(VATRate));

        Double profitMargin = validator.validateDouble("Add profit margin to the project: ");



        List<Component> components = new ArrayList<>();
        components.addAll(materials);
        components.addAll(labors);
        project.setComponents(components);

        Double componentsCost = components.stream().mapToDouble(Component::calculateCost).sum();
        project.setProfitMargin((componentsCost * (profitMargin / 100)));
        String choice;
        if (project.getClient().getProfessional()) {
            do {
                choice = this.validator.validateString("The client is professional would you like to apply a discount (y/n): ");
            }while (!choice.equalsIgnoreCase("y") && !choice.equalsIgnoreCase("n"));
            if (choice.equalsIgnoreCase("y")){
                Double discount  = this.validator.validateDoubleInRange("enter the discount (between 0% and 100 % of profit margin): ", 0.0, 100.0);
                Double newProfitMargin = project.getProfitMargin() - (project.getProfitMargin()*discount / 100);
                project.setProfitMargin(newProfitMargin);
            }
        }
        project.setTotalCost(componentsCost + project.getProfitMargin());

        reviewProject(project, materials, labors, componentsCost, clientOptional);


        project.setProjectStatus(ProjectStatus.in_progress);
        Project savedProject = projectService.save(project);
        if (savedProject != null) {
            materials.forEach(material -> material.setProject(savedProject));
            labors.forEach(labor -> labor.setProject(savedProject));
            this.componentService.saveAllLabors(labors);
            this.componentService.saveAllMaterials(materials);
            Quote quote = this.quoteManager.createQuote(savedProject);
        } else {
            System.out.println("Project creation failed!");
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
        System.out.println("Profit margin of the project: " + project.getProfitMargin());
        System.out.println("Total cost after profit margin: " + project.getTotalCost());
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

    public void projectDetails () {
        String projectName = this.validator.validateString("Enter the project name: ");
        this.projectService.findByName(projectName).ifPresentOrElse(project -> {
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
        }, () -> System.out.println("Project not found!"));
    }

}