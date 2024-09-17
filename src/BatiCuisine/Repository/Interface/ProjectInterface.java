package BatiCuisine.Repository.Interface;

import BatiCuisine.Domain.Entity.Component;
import BatiCuisine.Domain.Entity.Labor;
import BatiCuisine.Domain.Entity.Material;
import BatiCuisine.Domain.Entity.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectInterface {
    public Project save(Project project);
    public Project update(Project project);
    public Boolean delete(int id);
    public Optional<Project> findById(int id);
    public List<Project> findAll();
    public List<Project> findByClientId(int id);
    public List<Component> projectComponents(int id);
    public List<Material> projectMaterials(int id);
    public List<Labor> projectLabors(int id);
}
