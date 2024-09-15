package BatiCuisine.Domain.Entity;

import java.time.LocalDate;

public class Quote {
    private Double id;
    private Double estimatedAmount;
    private LocalDate issueDate;
    private LocalDate validityDate;
    private Boolean accepted;
    private Double projectId;

    public Quote(Double id, Double estimatedAmount, LocalDate issueDate, LocalDate validityDate, Boolean accepted, Double projectId) {
        this.id = id;
        this.estimatedAmount = estimatedAmount;
        this.issueDate = issueDate;
        this.validityDate = validityDate;
        this.accepted = accepted;
        this.projectId = projectId;
    }

    public Quote() {
    }

    public Double getId() {
        return id;
    }

    public void setId(Double id) {
        this.id = id;
    }

    public Double getEstimatedAmount() {
        return estimatedAmount;
    }

    public void setEstimatedAmount(Double estimatedAmount) {
        this.estimatedAmount = estimatedAmount;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDate getValidityDate() {
        return validityDate;
    }

    public void setValidityDate(LocalDate validityDate) {
        this.validityDate = validityDate;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    public Double getProjectId() {
        return projectId;
    }

    public void setProjectId(Double projectId) {
        this.projectId = projectId;
    }

    @Override
    public String toString() {
        return "Quote{" +
                "id=" + id +
                ", estimatedAmount=" + estimatedAmount +
                ", issueDate=" + issueDate +
                ", validityDate=" + validityDate +
                ", accepted=" + accepted +
                ", projectId=" + projectId +
                '}';
    }
}
