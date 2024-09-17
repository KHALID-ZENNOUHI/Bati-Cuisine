package BatiCuisine.Domain.Entity;

import BatiCuisine.Domain.Enum.ComponentType;

public abstract class Component {
    private int id;
    private String name;
    private ComponentType componentType;
    private Double VATRate;
    private int projectId;


    public Component(int id, String name, ComponentType componentType, Double VATRate, int projectId) {
        this.id = id;
        this.name = name;
        this.componentType = componentType;
        this.VATRate = VATRate;
        this.projectId = projectId;
    }

    public Component() {

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ComponentType getComponentType() {
        return componentType;
    }

    public void setComponentType(ComponentType componentType) {
        this.componentType = componentType;
    }

    public Double getVATRate() {
        return VATRate;
    }

    public void setVATRate(Double VATRate) {
        this.VATRate = VATRate;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public abstract Double calculateCost();

    @Override
    public String toString() {
        return "Component{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", componentType='" + componentType + '\'' +
                ", VATRate=" + VATRate +
                ", projectId=" + projectId +
                '}';
    }
}
