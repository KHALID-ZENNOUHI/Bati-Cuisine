package Service.Interface;

import Domain.Entity.Component;
import Domain.Entity.Labor;
import Domain.Entity.Material;

import java.util.List;
import java.util.Optional;

public interface ComponentService {
    public Labor saveLabor(Labor labor);
    public Material saveMaterial(Material material);
    public Boolean delete(int id);
    public Optional<Component> findById(int id);
    public List<Component> findAll();
    public List<Labor> saveAllLabors(List<Labor> labors);
    public List<Material> saveAllMaterials(List<Material> materials);
    public List<Material> projectMaterials(int id);
    public List<Labor> projectLabors(int id);
}
