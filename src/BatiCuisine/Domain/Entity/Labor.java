package BatiCuisine.Domain.Entity;

public class Labor extends Component{
    private Double hourlyRate;
    private Double hoursWorked;
    private  Double workerProductivity;

    public Labor(Double id, String name, Double unitCost, Double quantity, String componentType, Double VATRate, Double hourlyRate, Double hoursWorked, Double workerProductivity) {
        super(id, name, unitCost, quantity, componentType, VATRate);
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
        this.workerProductivity = workerProductivity;
    }

    public Labor() {
        super();
    }

    public Double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(Double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public Double getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(Double hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public Double getWorkerProductivity() {
        return workerProductivity;
    }

    public void setWorkerProductivity(Double workerProductivity) {
        this.workerProductivity = workerProductivity;
    }

    public Double calculateCost() {
        return null;
    }

    @Override
    public String toString() {
        return "Labor{" +
                "hourlyRate=" + hourlyRate +
                ", hoursWorked=" + hoursWorked +
                ", workerProductivity=" + workerProductivity +
                '}';
    }
}
