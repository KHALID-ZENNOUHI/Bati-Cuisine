package Service.Implementation;

import Domain.Entity.Client;
import Domain.Entity.Project;
import Repository.Implementation.ClientRepositoryImpl;
import Service.Interface.ClientService;

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
    public Optional<Client> findById(int id) {
        return this.clientRepository.findById(id);
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
