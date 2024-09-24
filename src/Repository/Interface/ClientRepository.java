package Repository.Interface;

import Domain.Entity.Client;
import Domain.Entity.Project;

import java.util.List;
import java.util.Optional;

public interface ClientRepository {
    public Client save(Client client);
    public Client update(Client client);
    public Boolean delete(int id);
    public Optional<Client> findByName(String name);
    public List<Client> findAll();
    public List<Project> getProjects(int id);
    public Optional<Client> findById(int id);
}
