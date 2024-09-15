package BatiCuisine.Domain.Entity;

import java.util.ArrayList;
import java.util.List;

public class Client {
    private Double id;
    private String name;
    private String address;
    private String phone;
    private Boolean isProfessional;
    private List<Project> projects;

    public Client() {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getProfessional() {
        return isProfessional;
    }

    public void setProfessional(Boolean professional) {
        isProfessional = professional;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(Project project) {
        if (projects ==  null) {
            projects = new ArrayList<>();
        }
        projects.add(project);

    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", isProfessional=" + isProfessional +
                ", projects=" + projects +
                '}';
    }
}
