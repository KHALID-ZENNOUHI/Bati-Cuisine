package BatiCuisine.Service.Interface;

import BatiCuisine.Domain.Entity.Component;
import BatiCuisine.Domain.Entity.Labor;
import BatiCuisine.Domain.Entity.Material;

import java.util.List;
import java.util.Optional;

public interface ComponentService {
    public Component save(Component component);
    public Component update(Component component);
    public Boolean delete(int id);
    public Optional<Component> findById(int id);
    public List<Component> findAll();
}
