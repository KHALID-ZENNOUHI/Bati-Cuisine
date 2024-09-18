package BatiCuisine.Repository.Interface;

import BatiCuisine.Domain.Entity.Component;
import BatiCuisine.Domain.Entity.Labor;
import BatiCuisine.Domain.Entity.Material;

import java.util.List;
import java.util.Optional;

public interface ComponentInterface {
    public int save(Component component);
    public Labor saveLabor(Labor labor);
    public Material saveMaterial(Material material);
    public int update(Component component);
    public Labor updateLabor(Labor labor);
    public Material updateMaterial(Material material);
    public Boolean delete(int id);
    public Optional<Component> findById(int id);
    public List<Component> findAll();
}
