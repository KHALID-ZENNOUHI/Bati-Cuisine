package BatiCuisine.Repository.Implementation;

import BatiCuisine.Config.DataBaseConnection;
import BatiCuisine.Domain.Entity.Client;
import BatiCuisine.Domain.Entity.Project;
import BatiCuisine.Domain.Enum.ProjectStatus;
import BatiCuisine.Repository.Interface.ClientInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class ClientImplementation implements ClientInterface {
    private Connection connection;

    public ClientImplementation(String table) throws SQLException {
        this.connection = DataBaseConnection.getInstance().getConnection();
    }

    @Override
    public Client save(Client client) {
        String query = "INSERT INTO client (name, address, phone, isProfessional) VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, client.getName());
            preparedStatement.setString(2, client.getAddress());
            preparedStatement.setString(3, client.getPhone());
            preparedStatement.setBoolean(4, client.getProfessional());
            preparedStatement.executeUpdate();
            return client;
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }

    }

    @Override
    public Client update(Client client) {
        String query = "UPDATE client SET name = ?, address = ?, phone = ?, isProfessional = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, client.getName());
            preparedStatement.setString(2, client.getAddress());
            preparedStatement.setString(3, client.getPhone());
            preparedStatement.setBoolean(4, client.getProfessional());
            preparedStatement.setInt(5, client.getId());
            preparedStatement.executeUpdate();
            return client;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Boolean delete(int id) {
        String query = "DELETE FROM client WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Optional<Client> findByName(String name) {
        String query = "SELECT * FROM client WHERE name = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new Client(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("address"),
                        resultSet.getString("phone"),
                        resultSet.getBoolean("isProfessional")
                ));
            }
            return Optional.empty();
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public List<Client> findAll() {
        String query = "SELECT * FROM client";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Client> clients = new ArrayList<>();
            while (resultSet.next()) {
                clients.add(new Client(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("address"),
                        resultSet.getString("phone"),
                        resultSet.getBoolean("isProfessional")
                ));
            }
            return clients;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Project> getProjects(int id) {
        String query = "SELECT * FROM project WHERE client_id = ?";
        List<Project> projects = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Project project = new Project();
                project.setId(resultSet.getInt("id"));
                project.setName(resultSet.getString("name"));
                project.setProfitMargin(resultSet.getDouble("profit_margin"));
                project.setTotalCost(resultSet.getDouble("total_cost"));
                project.setProjectStatus(ProjectStatus.valueOf(resultSet.getString("project_status")));
                project.setClientId(resultSet.getInt("client_id"));
                projects.add(project);
            }
            return projects;
        } catch (SQLException e) {
            e.printStackTrace();
            return projects;
        }
    }

}
