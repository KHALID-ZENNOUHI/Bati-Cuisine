package Service.Interface;

import Domain.Entity.*;

import java.util.List;
import java.util.Optional;

public interface ProjectService {
    public Project save(Project project);
    public Project update(Project project);
    public Boolean delete(int id);
    public Optional<Project> findById(int id);
    public Optional<Project> findByName(String name);
    public List<Project> findAll();
    public List<Project> findByClientId(int id);

}
