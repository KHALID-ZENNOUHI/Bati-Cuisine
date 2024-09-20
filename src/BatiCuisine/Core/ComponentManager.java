package BatiCuisine.Core;

import BatiCuisine.Domain.Entity.Labor;
import BatiCuisine.Domain.Entity.Material;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ComponentManager {
    private Scanner scanner;
    private List<Labor> labors;
    private List<Material> materials;

    public ComponentManager() {
        this.scanner = new Scanner(System.in);
        this.labors = new ArrayList<>();
        this.materials = new ArrayList<>();
    }


    public List<Material> addMaterials() {
        System.out.println("--- Add Project Materials ---");
        do {
            Material material = new Material();
            System.out.print("Enter material name: ");
            String materialName = this.scanner.nextLine();
            material.setName(materialName);
            material.setComponentType("material");

            System.out.print("Enter material quantity: ");
            Double materialQuantity = this.scanner.nextDouble();
            System.out.print("Enter material unit cost: ");
            Double materialUnitCost = this.scanner.nextDouble();
            System.out.print("Enter transport cost: ");
            Double transportCost = this.scanner.nextDouble();
            System.out.print("Enter quality coefficient: ");
            Double qualityCoefficient = this.scanner.nextDouble();
            this.scanner.nextLine(); // Consume leftover newline

            material.setQuantity(materialQuantity);
            material.setUnitCost(materialUnitCost);
            material.setTransportCost(transportCost);
            material.setQualityCoefficient(qualityCoefficient);
            this.materials.add(material);

            System.out.print("Do you want to add another material? (y/n): ");
        } while (this.scanner.nextLine().equalsIgnoreCase("y"));
        return this.materials;
    }

    public List<Labor> addLabor() {
        System.out.println("--- Add Project Labor ---");
        do {
            Labor labor = new Labor();
            System.out.print("Enter labor name: ");
            String laborName = this.scanner.nextLine();
            labor.setName(laborName);
            labor.setComponentType("labor");

            System.out.print("Enter labor hourly rate: ");
            Double hourlyRate = this.scanner.nextDouble();
            System.out.print("Enter labor hours worked: ");
            Double hoursWorked = this.scanner.nextDouble();
            System.out.print("Enter labor worker productivity: ");
            Double workerProductivity = this.scanner.nextDouble();
            this.scanner.nextLine(); // Consume leftover newline

            labor.setHourlyRate(hourlyRate);
            labor.setHoursWorked(hoursWorked);
            labor.setWorkerProductivity(workerProductivity);
            this.labors.add(labor);

            System.out.print("Do you want to add another labor? (y/n): ");
        } while (this.scanner.nextLine().equalsIgnoreCase("y"));
        return this.labors;
    }
}
