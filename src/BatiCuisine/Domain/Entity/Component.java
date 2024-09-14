package BatiCuisine.Domain.Entity;

public abstract class Component {
    private Double id;
    private String name;
    private Double unitCost;
    private Double quantity;
    private String componentType;
    private Double VATRate;

    public Component(Double id, String name, Double unitCost, Double quantity, String componentType, Double VATRate) {
        this.id = id;
        this.name = name;
        this.unitCost = unitCost;
        this.quantity = quantity;
        this.componentType = componentType;
        this.VATRate = VATRate;
    }

    public Component() {

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

    public Double getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(Double unitCost) {
        this.unitCost = unitCost;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
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

    public abstract Double calculateCost();

    @Override
    public String toString() {
        return "Component{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", unitCost=" + unitCost +
                ", quantity=" + quantity +
                ", componentType='" + componentType + '\'' +
                ", VATRate=" + VATRate +
                '}';
    }
}
