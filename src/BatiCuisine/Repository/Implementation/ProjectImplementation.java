package BatiCuisine.Repository.Implementation;

import BatiCuisine.Config.DataBaseConnection;
import BatiCuisine.Domain.Entity.Component;
import BatiCuisine.Domain.Entity.Labor;
import BatiCuisine.Domain.Entity.Material;
import BatiCuisine.Domain.Entity.Project;
import BatiCuisine.Domain.Enum.ComponentType;
import BatiCuisine.Domain.Enum.ProjectStatus;
import BatiCuisine.Repository.Interface.ProjectInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProjectImplementation implements ProjectInterface {
    private Connection connection;

    public ProjectImplementation() throws SQLException {
        this.connection = DataBaseConnection.getInstance().getConnection();
    }

    @Override
    public Project save(Project project) {
        String query = "ISERT INTO project (name, profit_margin, total_cost, project_status, client_id) values (?,?,?,?::project_status,?)";
        try{
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, project.getName());
        preparedStatement.setDouble(2, project.getProfitMargin());
        preparedStatement.setDouble(3, project.getTotalCost());
        preparedStatement.setObject(4, project.getProjectStatus().name());
        preparedStatement.setInt(5, project.getClientId());
        preparedStatement.executeUpdate();
        return project;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
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
        preparedStatement.setInt(5, project.getClientId());
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
            project.setClientId(resultSet.getInt("client_id"));
            return Optional.of(project);
        }
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
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
                project.setClientId(resultSet.getInt("client_id"));
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
                project.setClientId(resultSet.getInt("client_id"));
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
                ComponentType componentType = ComponentType.valueOf(resultSet.getString("component_type"));
                Double VATRate = resultSet.getDouble("vat_rate");
                int projectId = resultSet.getInt("project_id");
                if (componentType == ComponentType.LABOR) {
                    Double hourlyRate = resultSet.getDouble("hourly_rate");
                    Double hoursWorked = resultSet.getDouble("hours_worked");
                    Double workerProductivity = resultSet.getDouble("worker_productivity");
                    components.add(new Labor(componentId, name, componentType, VATRate, projectId, hourlyRate, hoursWorked, workerProductivity));
                } else {
                    Double unitPrice = resultSet.getDouble("unit_price");
                    Double quantity = resultSet.getDouble("quantity");
                    Double transportCost = resultSet.getDouble("transport_cost");
                    Double qualityCoefficient = resultSet.getDouble("quality_coefficient");
                    components.add(new Material(componentId, name, componentType, VATRate, projectId, unitPrice, quantity, transportCost, qualityCoefficient));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return components;
    }

    @Override
    public List<Material> projectMaterials(int id) {
        String query = "SELECT * FROM component WHERE project_id = ? AND component_type = ?::component_type";
        List<Material> materials = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.setObject(2, ComponentType.MATERIAL.name());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int componentId = resultSet.getInt("id");
                String name = resultSet.getString("name");
                ComponentType componentType = ComponentType.valueOf(resultSet.getString("component_type"));
                Double VATRate = resultSet.getDouble("vat_rate");
                int projectId = resultSet.getInt("project_id");
                Double unitPrice = resultSet.getDouble("unit_price");
                Double quantity = resultSet.getDouble("quantity");
                Double transportCost = resultSet.getDouble("transport_cost");
                Double qualityCoefficient = resultSet.getDouble("quality_coefficient");
                materials.add(new Material(componentId, name, componentType, VATRate, projectId, unitPrice, quantity, transportCost, qualityCoefficient));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return materials;
    }

    @Override
    public List<Labor> projectLabors(int id) {
        String query = "SELECT * FROM component WHERE project_id = ? AND component_type = ?::component_type";
        List<Labor> labors = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.setObject(2, ComponentType.LABOR.name());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int componentId = resultSet.getInt("id");
                String name = resultSet.getString("name");
                ComponentType componentType = ComponentType.valueOf(resultSet.getString("component_type"));
                Double VATRate = resultSet.getDouble("vat_rate");
                int projectId = resultSet.getInt("project_id");
                Double hourlyRate = resultSet.getDouble("hourly_rate");
                Double hoursWorked = resultSet.getDouble("hours_worked");
                Double workerProductivity = resultSet.getDouble("worker_productivity");
                labors.add(new Labor(componentId, name, componentType, VATRate, projectId, hourlyRate, hoursWorked, workerProductivity));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return labors;
    }






}
