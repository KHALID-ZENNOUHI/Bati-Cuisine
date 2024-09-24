package Service.Implementation;

import Domain.Entity.Component;
import Domain.Entity.Labor;
import Domain.Entity.Material;
import Repository.Implementation.ComponentRepositoryImpl;
import Service.Interface.ComponentService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ComponentServiceImpl implements ComponentService {
    private final ComponentRepositoryImpl componentRepository;

    public ComponentServiceImpl() throws SQLException {
        this.componentRepository = new ComponentRepositoryImpl();
    }

    @Override
    public Labor saveLabor(Labor labor) {
        return this.componentRepository.saveLabor(labor);
    }

    @Override
    public Material saveMaterial(Material material) {
        return this.componentRepository.saveMaterial(material);
    }

    @Override
    public List<Labor> saveAllLabors(List<Labor> labors) {
        labors.forEach(this.componentRepository::saveLabor);
        return labors;
    }

    @Override
    public List<Material> saveAllMaterials(List<Material> materials) {
        materials.forEach(this.componentRepository::saveMaterial);
        return materials;
    }

    @Override
    public Boolean delete(int id) {
        return this.componentRepository.delete(id);
    }

    @Override
    public Optional<Component> findById(int id) {
        return this.componentRepository.findById(id);
    }

    @Override
    public List<Component> findAll() {
        return this.componentRepository.findAll();
    }

    @Override
    public List<Material> projectMaterials(int id) {
        return this.componentRepository.projectMaterials(id);
    }

    @Override
    public List<Labor> projectLabors(int id) {
        return this.componentRepository.projectLabors(id);
    }
}
