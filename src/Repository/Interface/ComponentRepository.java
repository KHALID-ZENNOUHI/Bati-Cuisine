package Repository.Interface;

import Domain.Entity.Component;
import Domain.Entity.Labor;
import Domain.Entity.Material;

import java.util.List;
import java.util.Optional;

public interface ComponentRepository {
    public Labor saveLabor(Labor labor);
    public Material saveMaterial(Material material);
    public Labor updateLabor(Labor labor);
    public Material updateMaterial(Material material);
    public Boolean delete(int id);
    public Optional<Component> findById(int id);
    public List<Component> findAll();
    public List<Material> projectMaterials(int projectId);
    public List<Labor> projectLabors(int projectId);
}
