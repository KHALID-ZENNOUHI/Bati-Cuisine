package BatiCuisine.Service.Interface;

import BatiCuisine.Domain.Entity.Client;
import BatiCuisine.Domain.Entity.Project;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    public Client save(Client client);
    public Client update(Client client);
    public Boolean delete(int id);
    public Optional<Client> findByName(String name);
    public List<Client> findAll();
    public List<Project> clientProjects(int id);
}
