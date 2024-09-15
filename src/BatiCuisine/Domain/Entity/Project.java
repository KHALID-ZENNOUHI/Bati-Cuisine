package BatiCuisine.Domain.Entity;

import BatiCuisine.Domain.Enum.ProjectStatus;

import java.util.ArrayList;
import java.util.List;

public class Project {
    private Double id;
    private String name;
    private Double profitMargin;
    private Double totalCost;
    private ProjectStatus projectStatus;
    private List<Material> materials;

    public Project(Double id, String name, Double profitMargin, Double totalCost, ProjectStatus projectStatus, List<Material> materials) {
        this.id = id;
        this.name = name;
        this.profitMargin = profitMargin;
        this.totalCost = totalCost;
        this.projectStatus = projectStatus;
        this.materials = materials;
    }

    public Project() {
    }

    public Double getId() {
        return id;
    }

    public void setId(Double id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getProfitMargin() {
        return profitMargin;
    }

    public void setProfitMargin(Double profitMargin) {
        this.profitMargin = profitMargin;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public ProjectStatus getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(ProjectStatus projectStatus) {
        this.projectStatus = projectStatus;
    }

    public List<Material> getMaterials() {
        return materials;
    }

    public void setMaterials(Material material) {
        if (materials == null) {
            materials = new ArrayList<>();
        }
        materials.add(material);
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", profitMargin=" + profitMargin +
                ", totalCost=" + totalCost +
                ", projectStatus=" + projectStatus +
                ", materials=" + materials +
                '}';
    }
}
