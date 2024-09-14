package BatiCuisine.Domain.Entity;

public class Material extends Component{
    private Double transportCost;
    private Double qualityCoefficient;

    public Material(Double id, String name, Double unitCost, Double quantity, String componentType, Double VATRate, Double transportCost, Double qualityCoefficient) {
        super(id, name, unitCost, quantity, componentType, VATRate);
        this.transportCost = transportCost;
        this.qualityCoefficient = qualityCoefficient;
    }

    public Material(Double transportCost, Double qualityCoefficient) {
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

    public Double calculateCost() {
        return null;
    }

    @Override
    public String toString() {
        return "Material{" +
                "transportCost=" + transportCost +
                ", qualityCoefficient=" + qualityCoefficient +
                '}';
    }
}
