package BatiCuisine.Core;

import BatiCuisine.Domain.Entity.Labor;
import BatiCuisine.Domain.Entity.Material;
import BatiCuisine.Util.InputValidator;

import java.util.ArrayList;
import java.util.List;

public class ComponentManager {
    private final List<Labor> labors;
    private final List<Material> materials;
    private final InputValidator validator;

    public ComponentManager() {
        this.labors = new ArrayList<>();
        this.materials = new ArrayList<>();
        this.validator = new InputValidator();
    }

    public List<Material> addMaterials() {
        System.out.println("--- Add Project Materials ---");
        String another;
        do {
            Material material = new Material();

            String materialName = validator.validateString("Enter material name: ");
            material.setName(materialName);
            material.setComponentType("material");

            Double materialQuantity = validator.validateDouble("Enter material quantity: ");
            Double materialUnitCost = validator.validateDouble("Enter material unit cost: ");
            Double transportCost = validator.validateDouble("Enter transport cost: ");
            Double qualityCoefficient = validator.validateDoubleInRange("Enter quality coefficient (1.0 = standard, > 1.0 = haute quality): ", 1.0, 1.9);

            material.setQuantity(materialQuantity);
            material.setUnitCost(materialUnitCost);
            material.setTransportCost(transportCost);
            material.setQualityCoefficient(qualityCoefficient);
            this.materials.add(material);

            another = validator.validateString("Do you want to add another material? (y/n): ");
        } while (another.equalsIgnoreCase("y"));

        return this.materials;
    }

    public List<Labor> addLabor() {
        System.out.println("--- Add Project Labor ---");
        String another;
        do {
            Labor labor = new Labor();

            String laborName = validator.validateString("Enter labor name: ");
            labor.setName(laborName);
            labor.setComponentType("labor");

            Double hourlyRate = validator.validateDouble("Enter labor hourly rate: ");
            Double hoursWorked = validator.validateDouble("Enter labor hours worked: ");
            Double workerProductivity = validator.validateDoubleInRange("Enter labor worker productivity (1.0 = standard, > 1.0 = haute productivity): ", 1.0, 1.9);

            labor.setHourlyRate(hourlyRate);
            labor.setHoursWorked(hoursWorked);
            labor.setWorkerProductivity(workerProductivity);
            this.labors.add(labor);

            another = validator.validateString("Do you want to add another labor? (y/n): ");
        } while (another.equalsIgnoreCase("y"));

        return this.labors;
    }


}
