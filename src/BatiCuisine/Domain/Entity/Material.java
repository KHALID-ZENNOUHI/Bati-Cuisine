package BatiCuisine.Domain.Entity;

import BatiCuisine.Domain.Enum.ComponentType;

public class Material extends Component{
    private Double unitCost;
    private Double quantity;
    private Double transportCost;
    private Double qualityCoefficient;

    public Material(int id, String name, ComponentType componentType, Double VATRate, int projectId, Double unitCost, Double quantity, Double transportCost, Double qualityCoefficient) {
        super(id, name, componentType, VATRate, projectId);
        this.unitCost = unitCost;
        this.quantity = quantity;
        this.transportCost = transportCost;
        this.qualityCoefficient = qualityCoefficient;
    }

    public Material(Double unitCost, Double quantity, Double transportCost, Double qualityCoefficient) {
        this.unitCost = unitCost;
        this.quantity = quantity;
        this.transportCost = transportCost;
        this.qualityCoefficient = qualityCoefficient;
    }

    public Material() {
        super();
    }

    public Double getTransportCost() {
        return transportCost;
    }

    public void setTransportCost(Double transportCost) {
        this.transportCost = transportCost;
    }

    public Double getQualityCoefficient() {
        return qualityCoefficient;
    }

    public void setQualityCoefficient(Double qualityCoefficient) {
        this.qualityCoefficient = qualityCoefficient;
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

    public Double calculateCost() {
        return unitCost * quantity * qualityCoefficient * (1 + getVATRate() / 100);
    }

    @Override
    public String toString() {
        return "Material{" +
                "transportCost=" + transportCost +
                ", qualityCoefficient=" + qualityCoefficient +
                ", unitCost=" + unitCost +
                ", quantity=" + quantity +
                '}';
    }
}
