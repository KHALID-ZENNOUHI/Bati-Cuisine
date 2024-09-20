package BatiCuisine.Domain.Entity;

public abstract class Component {
    private int id;
    private String name;
    private String componentType;
    private Double VATRate;
    private Project project;


    public Component(int id, String name, String componentType, Double VATRate, Project project) {
        this.id = id;
        this.name = name;
        this.componentType = componentType;
        this.VATRate = VATRate;
        this.project = project;
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

    public String getComponentType() {
        return componentType;
    }

    public void setComponentType(String componentType) {
        this.componentType = componentType;
    }

    public Double getVATRate() {
        return VATRate;
    }

    public void setVATRate(Double VATRate) {
        this.VATRate = VATRate;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public abstract Double calculateCost();

    @Override
    public String toString() {
        return "Component{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", componentType='" + componentType + '\'' +
                ", VATRate=" + VATRate +
                ", project=" + project +
                '}';
    }
}
