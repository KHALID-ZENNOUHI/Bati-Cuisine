package BatiCuisine.Service.Implementation;

import BatiCuisine.Domain.Entity.Component;
import BatiCuisine.Domain.Entity.Labor;
import BatiCuisine.Domain.Entity.Material;
import BatiCuisine.Repository.Implementation.ComponentRepositoryImpl;
import BatiCuisine.Service.Interface.ComponentService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ComponentServiceImpl implements ComponentService {
    private final ComponentRepositoryImpl componentRepository;

    public ComponentServiceImpl() throws SQLException {
        this.componentRepository = new ComponentRepositoryImpl();
    }

   @Override
   public Component save(Component component) {
        return this.componentRepository.save(component);
   }

   @Override
   public Component update(Component component) {
        return this.componentRepository.update(component);
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
}
