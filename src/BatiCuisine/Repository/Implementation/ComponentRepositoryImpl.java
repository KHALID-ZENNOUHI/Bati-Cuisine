package BatiCuisine.Repository.Implementation;

import BatiCuisine.Config.DataBaseConnection;
import BatiCuisine.Domain.Entity.Component;
import BatiCuisine.Domain.Entity.Labor;
import BatiCuisine.Domain.Entity.Material;
import BatiCuisine.Domain.Enum.ComponentType;
import BatiCuisine.Repository.Interface.ComponentRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ComponentRepositoryImpl implements ComponentRepository {
    private final Connection connection;

    public ComponentRepositoryImpl() throws SQLException {
        this.connection = DataBaseConnection.getInstance().getConnection();
    }

    @Override
    public Component save(Component component) {
        if (component instanceof Labor) saveLabor((Labor) component);
        else saveMaterial((Material) component);
        return component;
    }

    @Override
    public Labor saveLabor (Labor labor) {
            String query = "INSERT INTO labor (name, component_type, vat_rate, project_id, hourly_rate, hours_worked, worker_productivity) VALUES (?, ?::component_type, ?, ?, ?, ?, ?)";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, labor.getName());
                preparedStatement.setObject(2, labor.getComponentType().name());
                preparedStatement.setDouble(3, labor.getVATRate());
                preparedStatement.setInt(4, labor.getProjectId());
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

            String query = "INSERT INTO material (name, component_type, vat_rate, project_id, unit_price, quantity) VALUES (?, ?::component_type, ?, ?, ?, ?)";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, material.getName());
                preparedStatement.setObject(2, material.getComponentType().name());
                preparedStatement.setDouble(3, material.getVATRate());
                preparedStatement.setInt(4, material.getProjectId());
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
    public Component update (Component component) {
        if (component instanceof Labor) updateLabor((Labor) component);
        else updateMaterial((Material) component);
        return component;
    }

    @Override
    public Labor updateLabor (Labor labor) {
            String query = "UPDATE labor SET name = ?, component_type = ?::component_type, vat_rate = ?, project_id = ?, hourly_rate = ?, hours_worked = ?, worker_productivity = ? WHERE id = ?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, labor.getName());
                preparedStatement.setObject(2, labor.getComponentType().name());
                preparedStatement.setDouble(3, labor.getVATRate());
                preparedStatement.setInt(4, labor.getProjectId());
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
            String query = "UPDATE material SET name = ?, component_type = ?::component_type, vat_rate = ?, project_id = ?, unit_price = ?, quantity = ? WHERE id = ?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, material.getName());
                preparedStatement.setObject(2, material.getComponentType().name());
                preparedStatement.setDouble(3, material.getVATRate());
                preparedStatement.setInt(4, material.getProjectId());
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
                    ComponentType componentType = ComponentType.valueOf(resultSet.getString("component_type"));
                    Double VATRate = resultSet.getDouble("vat_rate");
                    int projectId = resultSet.getInt("project_id");
                    if (componentType == ComponentType.LABOR) {
                        Double hourlyRate = resultSet.getDouble("hourly_rate");
                        Double hoursWorked = resultSet.getDouble("hours_worked");
                        Double workerProductivity = resultSet.getDouble("worker_productivity");
                        components.add(new Labor(id, name, componentType, VATRate, projectId, hourlyRate, hoursWorked, workerProductivity));
                    } else {
                        Double unitCost = resultSet.getDouble("unit_price");
                        Double quantity = resultSet.getDouble("quantity");
                        Double transportCost = resultSet.getDouble("transport_cost");
                        Double qualityCoefficient = resultSet.getDouble("quality_coefficient");
                        components.add(new Material(id, name, componentType, VATRate, projectId, unitCost, quantity, transportCost, qualityCoefficient));
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
                ComponentType componentType = ComponentType.valueOf(resultSet.getString("component_type"));
                Double VATRate = resultSet.getDouble("vat_rate");
                int projectId = resultSet.getInt("project_id");
                if (componentType == ComponentType.LABOR) {
                    Double hourlyRate = resultSet.getDouble("hourly_rate");
                    Double hoursWorked = resultSet.getDouble("hours_worked");
                    Double workerProductivity = resultSet.getDouble("worker_productivity");
                    return Optional.of(new Labor(id, name, componentType, VATRate, projectId, hourlyRate, hoursWorked, workerProductivity));
                } else {
                    Double unitCost = resultSet.getDouble("unit_price");
                    Double quantity = resultSet.getDouble("quantity");
                    Double transportCost = resultSet.getDouble("transport_cost");
                    Double qualityCoefficient = resultSet.getDouble("quality_coefficient");
                    return Optional.of(new Material(id, name, componentType, VATRate, projectId, unitCost, quantity, transportCost, qualityCoefficient));
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
}
