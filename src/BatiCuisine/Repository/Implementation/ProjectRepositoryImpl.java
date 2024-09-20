package BatiCuisine.Repository.Implementation;

import BatiCuisine.Config.DataBaseConnection;
import BatiCuisine.Domain.Entity.Component;
import BatiCuisine.Domain.Entity.Labor;
import BatiCuisine.Domain.Entity.Material;
import BatiCuisine.Domain.Entity.Project;
import BatiCuisine.Domain.Enum.ProjectStatus;
import BatiCuisine.Repository.Interface.ProjectRepository;
import BatiCuisine.Service.Implementation.ClientServiceImpl;

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
    public List<Component> projectComponents(int id) {
        String query = "SELECT * FROM component WHERE project_id = ?";
        List<Component> components = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int componentId = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String componentType = resultSet.getString("component_type");
                Double VATRate = resultSet.getDouble("vat_rate");
                int projectId = resultSet.getInt("project_id");
                Project project = this.findById(projectId).get();
                if (componentType.equalsIgnoreCase("labor")) {
                    Double hourlyRate = resultSet.getDouble("hourly_rate");
                    Double hoursWorked = resultSet.getDouble("hours_worked");
                    Double workerProductivity = resultSet.getDouble("worker_productivity");
                    components.add(new Labor(componentId, name, componentType, VATRate, project, hourlyRate, hoursWorked, workerProductivity));
                } else {
                    Double unitPrice = resultSet.getDouble("unit_price");
                    Double quantity = resultSet.getDouble("quantity");
                    Double transportCost = resultSet.getDouble("transport_cost");
                    Double qualityCoefficient = resultSet.getDouble("quality_coefficient");
                    components.add(new Material(componentId, name, componentType, VATRate, project, unitPrice, quantity, transportCost, qualityCoefficient));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return components;
    }

    @Override
    public List<Material> projectMaterials(int id) {
        String query = "SELECT * FROM component WHERE project_id = ? AND component_type = ?";
        List<Material> materials = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, "material");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int componentId = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String componentType = resultSet.getString("component_type");
                Double VATRate = resultSet.getDouble("vat_rate");
                int projectId = resultSet.getInt("project_id");
                Project project = this.findById(projectId).get();
                Double unitPrice = resultSet.getDouble("unit_price");
                Double quantity = resultSet.getDouble("quantity");
                Double transportCost = resultSet.getDouble("transport_cost");
                Double qualityCoefficient = resultSet.getDouble("quality_coefficient");
                materials.add(new Material(componentId, name, componentType, VATRate, project, unitPrice, quantity, transportCost, qualityCoefficient));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return materials;
    }

    @Override
    public List<Labor> projectLabors(int id) {
        String query = "SELECT * FROM component WHERE project_id = ? AND component_type = ?";
        List<Labor> labors = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, "labor");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int componentId = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String componentType = resultSet.getString("component_type");
                Double VATRate = resultSet.getDouble("vat_rate");
                int projectId = resultSet.getInt("project_id");
                Project project = this.findById(projectId).get();
                Double hourlyRate = resultSet.getDouble("hourly_rate");
                Double hoursWorked = resultSet.getDouble("hours_worked");
                Double workerProductivity = resultSet.getDouble("worker_productivity");
                labors.add(new Labor(componentId, name, componentType, VATRate, project, hourlyRate, hoursWorked, workerProductivity));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return labors;
    }






}
