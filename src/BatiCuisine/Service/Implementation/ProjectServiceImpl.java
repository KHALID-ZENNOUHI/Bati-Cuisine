package BatiCuisine.Service.Implementation;

import BatiCuisine.Domain.Entity.Component;
import BatiCuisine.Domain.Entity.Labor;
import BatiCuisine.Domain.Entity.Material;
import BatiCuisine.Domain.Entity.Project;
import BatiCuisine.Repository.Implementation.ProjectRepositoryImpl;
import BatiCuisine.Service.Interface.ProjectService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepositoryImpl projectRepository;

    public ProjectServiceImpl() throws SQLException {
        this.projectRepository = new ProjectRepositoryImpl();
    }

    @Override
    public Project save(Project project) {
        return this.projectRepository.save(project);
    }

    @Override
    public Project update(Project project) {
        return this.projectRepository.update(project);
    }

    @Override
    public Boolean delete(int id) {
        return this.projectRepository.delete(id);
    }

    @Override
    public Optional<Project> findById(int id) {
        return this.projectRepository.findById(id);
    }

    @Override
    public Optional<Project> findByName(String name) {
        return this.projectRepository.findByName(name);
    }

    @Override
    public List<Project> findAll() {
        return this.projectRepository.findAll();
    }

    @Override
    public List<Project> findByClientId(int id) {
        return this.projectRepository.findByClientId(id);
    }
}
