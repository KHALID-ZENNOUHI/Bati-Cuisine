package BatiCuisine.Service.Implementation;

import BatiCuisine.Domain.Entity.Client;
import BatiCuisine.Domain.Entity.Project;
import BatiCuisine.Repository.Implementation.ClientRepositoryImpl;
import BatiCuisine.Service.Interface.ClientService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ClientServiceImpl implements ClientService {
    private final ClientRepositoryImpl clientRepository;

    public ClientServiceImpl() throws SQLException {
        this.clientRepository = new ClientRepositoryImpl();
    }

    @Override
    public Client save(Client client) {
        return this.clientRepository.save(client);
    }

    @Override
    public Client update(Client client) {
        return this.clientRepository.update(client);
    }

    @Override
    public Boolean delete(int id) {
        return this.clientRepository.delete(id);
    }

    @Override
    public Optional<Client> findByName(String name) {
        return this.clientRepository.findByName(name);
    }

    @Override
    public List<Project> clientProjects(int id) {
        return this.clientRepository.getProjects(id);
    }

    @Override
    public List<Client> findAll() {
        return this.clientRepository.findAll();
    }


}
