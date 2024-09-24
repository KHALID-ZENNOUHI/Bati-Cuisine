package Repository.Implementation;

import Config.DataBaseConnection;
import Domain.Entity.Component;
import Domain.Entity.Labor;
import Domain.Entity.Material;
import Domain.Entity.Project;
import Repository.Interface.ComponentRepository;
import Service.Implementation.ClientServiceImpl;
import Service.Implementation.ProjectServiceImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ComponentRepositoryImpl implements ComponentRepository {
    private final Connection connection;
    private final ProjectServiceImpl projectService;
    private ClientServiceImpl clientService;


    public ComponentRepositoryImpl() throws SQLException {
        this.connection = DataBaseConnection.getInstance().getConnection();
        this.projectService = new ProjectServiceImpl();
        this.clientService = new ClientServiceImpl();
    }

    @Override
    public Labor saveLabor (Labor labor) {
            String query = "INSERT INTO labor (name, component_type, vat_rate, project_id, hourly_rate, hours_worked, worker_productivity) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, labor.getName());
                preparedStatement.setString(2, labor.getComponentType());
                preparedStatement.setDouble(3, labor.getVATRate());
                preparedStatement.setInt(4, labor.getProject().getId());
                preparedStatement.setDouble(5, labor.getHourlyRate());
                preparedStatement.setDouble(6, labor.getHoursWorked());
                preparedStatement.setDouble(7, labor.getWorkerProductivity());
                preparedStatement.executeUpdate();
                return labor;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }

    }

    @Override
    public Material saveMaterial (Material material) {

            String query = "INSERT INTO material (name, component_type, vat_rate, project_id, unit_cost, quantity, transport_cost, quality_coefficient) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, material.getName());
                preparedStatement.setString(2, material.getComponentType());
                preparedStatement.setDouble(3, material.getVATRate());
                preparedStatement.setInt(4, material.getProject().getId());
                preparedStatement.setDouble(5, material.getUnitCost());
                preparedStatement.setDouble(6, material.getQuantity());
                preparedStatement.setDouble(7, material.getTransportCost());
                preparedStatement.setDouble(8, material.getQualityCoefficient());
                preparedStatement.executeUpdate();
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    material.setId(resultSet.getInt(1));
                }
                return material;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
    }

    @Override
    public Labor updateLabor (Labor labor) {
            String query = "UPDATE labor SET name = ?, component_type = ?, vat_rate = ?, project_id = ?, hourly_rate = ?, hours_worked = ?, worker_productivity = ? WHERE id = ?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, labor.getName());
                preparedStatement.setString(2, labor.getComponentType());
                preparedStatement.setDouble(3, labor.getVATRate());
                preparedStatement.setInt(4, labor.getProject().getId());
                preparedStatement.setDouble(5, labor.getHourlyRate());
                preparedStatement.setDouble(6, labor.getHoursWorked());
                preparedStatement.setDouble(7, labor.getWorkerProductivity());
                preparedStatement.executeUpdate();
                return labor;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }

    }

    @Override
    public Material updateMaterial (Material material) {
            String query = "UPDATE material SET name = ?, component_type = ?, vat_rate = ?, project_id = ?, unit_cost = ?, quantity = ? WHERE id = ?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, material.getName());
                preparedStatement.setString(2, material.getComponentType());
                preparedStatement.setDouble(3, material.getVATRate());
                preparedStatement.setInt(4, material.getProject().getId());
                preparedStatement.setDouble(5, material.getUnitCost());
                preparedStatement.setDouble(6, material.getQuantity());
                preparedStatement.executeUpdate();
                return material;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
    }

    @Override
    public List<Component> findAll() {
        String query = "SELECT c.*, m.*, l.* FROM component c LEFT JOIN material m ON c.id = m.id LEFT JOIN labor l ON c.id = l.id";
        List<Component> components = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String componentType = resultSet.getString("component_type");
                    Double VATRate = resultSet.getDouble("vat_rate");
                    int projectId = resultSet.getInt("project_id");
                    Project project = this.projectService.findById(projectId).get();
                    if (componentType.equalsIgnoreCase("material")) {
                        Double hourlyRate = resultSet.getDouble("hourly_rate");
                        Double hoursWorked = resultSet.getDouble("hours_worked");
                        Double workerProductivity = resultSet.getDouble("worker_productivity");
                        components.add(new Labor(id, name, componentType, VATRate, project, hourlyRate, hoursWorked, workerProductivity));
                    } else {
                        Double unitCost = resultSet.getDouble("unit_cost");
                        Double quantity = resultSet.getDouble("quantity");
                        Double transportCost = resultSet.getDouble("transport_cost");
                        Double qualityCoefficient = resultSet.getDouble("quality_coefficient");
                        components.add(new Material(id, name, componentType, VATRate, project, unitCost, quantity, transportCost, qualityCoefficient));
                    }
                }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return components;
    }

    @Override
    public Optional<Component> findById(int id) {
        String query = "SELECT c.*, m.*, l.* FROM component c LEFT JOIN material m ON c.id = m.id LEFT JOIN labor l ON c.id = l.id WHERE c.id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String componentType = resultSet.getString("component_type");
                Double VATRate = resultSet.getDouble("vat_rate");
                int projectId = resultSet.getInt("project_id");
                Project project = this.projectService.findById(projectId).get();
                if (componentType.equalsIgnoreCase("labor")) {
                    Double hourlyRate = resultSet.getDouble("hourly_rate");
                    Double hoursWorked = resultSet.getDouble("hours_worked");
                    Double workerProductivity = resultSet.getDouble("worker_productivity");
                    return Optional.of(new Labor(id, name, componentType, VATRate, project, hourlyRate, hoursWorked, workerProductivity));
                } else {
                    Double unitCost = resultSet.getDouble("unit_cost");
                    Double quantity = resultSet.getDouble("quantity");
                    Double transportCost = resultSet.getDouble("transport_cost");
                    Double qualityCoefficient = resultSet.getDouble("quality_coefficient");
                    return Optional.of(new Material(id, name, componentType, VATRate, project, unitCost, quantity, transportCost, qualityCoefficient));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Boolean delete(int id) {
        String query = "DELETE FROM component WHERE id = ?";
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
    public List<Material> projectMaterials(int projectId) {
        String query = "SELECT * FROM material WHERE project_id = ?";
        List<Material> materials = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, projectId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int componentId = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String componentType = resultSet.getString("component_type");
                Double VATRate = resultSet.getDouble("vat_rate");
                int project_id = resultSet.getInt("project_id");
                Project project = this.projectService.findById(project_id).get();
                Double unitPrice = resultSet.getDouble("unit_cost");
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
    public List<Labor> projectLabors(int projectId) {
        String query = "SELECT * FROM labor WHERE project_id = ?";
        List<Labor> labors = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, projectId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int componentId = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String componentType = resultSet.getString("component_type");
                Double VATRate = resultSet.getDouble("vat_rate");
                int project_id = resultSet.getInt("project_id");
                Project project = this.projectService.findById(project_id).get();
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
