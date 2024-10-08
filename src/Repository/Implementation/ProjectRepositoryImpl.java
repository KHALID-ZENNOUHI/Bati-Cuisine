package Repository.Implementation;

import Config.DataBaseConnection;
import Domain.Entity.Component;
import Domain.Entity.Labor;
import Domain.Entity.Material;
import Domain.Entity.Project;
import Domain.Enum.ProjectStatus;
import Repository.Interface.ProjectRepository;
import Service.Implementation.ClientServiceImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProjectRepositoryImpl implements ProjectRepository {
    private Connection connection;
    private ClientServiceImpl clientService;

    public ProjectRepositoryImpl() throws SQLException {
        this.connection = DataBaseConnection.getInstance().getConnection();
        this.clientService = new ClientServiceImpl();
    }

    @Override
    public Project save(Project project) {
        String query = "INSERT INTO project (name, profit_margin, total_cost, project_status, client_id) values (?,?,?,?::project_status,?)";
        try{
        PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, project.getName());
        preparedStatement.setDouble(2, project.getProfitMargin());
        preparedStatement.setDouble(3, project.getTotalCost());
        preparedStatement.setObject(4, project.getProjectStatus().name());
        preparedStatement.setInt(5, project.getClient().getId());
        preparedStatement.executeUpdate();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        if (resultSet.next()) {
            project.setId(resultSet.getInt(1));
            return project;
        }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return null;
    }

    @Override
    public Project update(Project project)  {
        String query = "UPDATE project SET name = ?, profit_margin = ?, total_cost = ?, project_status = ?::project_status, client_id = ? WHERE id = ?";
        try{
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, project.getName());
        preparedStatement.setDouble(2, project.getProfitMargin());
        preparedStatement.setDouble(3, project.getTotalCost());
        preparedStatement.setObject(4, project.getProjectStatus().name());
        preparedStatement.setInt(5, project.getClient().getId());
        preparedStatement.setInt(6, project.getId());
        preparedStatement.executeUpdate();
        return project;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Boolean delete(int id)  {
        String query = "DELETE FROM project WHERE id = ?";
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
    public Optional<Project> findById(int id)  {
        String query = "SELECT * FROM project WHERE id = ?";
        try {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            Project project = new Project();
            project.setId(resultSet.getInt("id"));
            project.setName(resultSet.getString("name"));
            project.setProfitMargin(resultSet.getDouble("profit_margin"));
            project.setTotalCost(resultSet.getDouble("total_cost"));
            project.setProjectStatus(ProjectStatus.valueOf(resultSet.getString("project_status")));
            project.setClient(this.clientService.findById(resultSet.getInt("client_id")).get());
            return Optional.of(project);
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Project> findAll()  {
        String query = "SELECT * FROM project";
        List<Project> projects = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Project project = new Project();
                project.setId(resultSet.getInt("id"));
                project.setName(resultSet.getString("name"));
                project.setProfitMargin(resultSet.getDouble("profit_margin"));
                project.setTotalCost(resultSet.getDouble("total_cost"));
                project.setProjectStatus(ProjectStatus.valueOf(resultSet.getString("project_status")));
                project.setClient(this.clientService.findById(resultSet.getInt("client_id")).get());
                projects.add(project);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }

    @Override
    public List<Project> findByClientId(int id)  {
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
                project.setClient(this.clientService.findById(resultSet.getInt("client_id")).get());
                projects.add(project);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }

    @Override
    public Optional<Project> findByName(String name)  {
        String query = "SELECT * FROM project WHERE name = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Project project = new Project();
                project.setId(resultSet.getInt("id"));
                project.setName(resultSet.getString("name"));
                project.setProfitMargin(resultSet.getDouble("profit_margin"));
                project.setTotalCost(resultSet.getDouble("total_cost"));
                project.setProjectStatus(ProjectStatus.valueOf(resultSet.getString("project_status")));
                project.setClient(this.clientService.findById(resultSet.getInt("client_id")).get());
                return Optional.of(project);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }






}
