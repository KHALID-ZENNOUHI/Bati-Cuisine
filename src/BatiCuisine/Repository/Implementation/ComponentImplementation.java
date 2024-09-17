package BatiCuisine.Repository.Implementation;

import BatiCuisine.Config.DataBaseConnection;
import BatiCuisine.Domain.Entity.Component;
import BatiCuisine.Domain.Entity.Labor;
import BatiCuisine.Domain.Entity.Material;
import BatiCuisine.Domain.Enum.ComponentType;
import BatiCuisine.Repository.Interface.ComponentInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ComponentImplementation implements ComponentInterface {
    private Connection connection;

    public ComponentImplementation() throws SQLException {
        this.connection = DataBaseConnection.getInstance().getConnection();
    }

    @Override
    public int save(Component component) {
        String query = "INSERT INTO component (name, component_type, vat_rate, project_id) VALUES (?, ?::component_type, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, component.getName());
            preparedStatement.setObject(2, component.getComponentType().name());
            preparedStatement.setDouble(3, component.getVATRate());
            preparedStatement.setInt(4, component.getProjectId());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                component.setId(resultSet.getInt(1));
            }
            return component.getId();

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public Labor saveLabor (Labor labor) {
        int id = save(labor);
        if (id != -1) {
            String query = "INSERT INTO labor (id, hourly_rate, hours_worked, worker_productivity) VALUES (?, ?, ?, ?)";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, id);
                preparedStatement.setDouble(2, labor.getHourlyRate());
                preparedStatement.setDouble(3, labor.getHoursWorked());
                preparedStatement.setDouble(4, labor.getWorkerProductivity());
                preparedStatement.executeUpdate();
                return labor;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    @Override
    public Material saveMaterial (Material material) {
        int id = save(material);
        if (id != -1) {
            String query = "INSERT INTO material (id, unit_price, quantity) VALUES (?, ?, ?)";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, id);
                preparedStatement.setDouble(2, material.getUnitCost());
                preparedStatement.setDouble(3, material.getQuantity());
                preparedStatement.executeUpdate();
                return material;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    @Override
    public int update (Component component) {
        String query = "UPDATE component SET name = ?, component_type = ?::component_type, vat_rate = ?, project_id = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, component.getName());
            preparedStatement.setObject(2, component.getComponentType().name());
            preparedStatement.setDouble(3, component.getVATRate());
            preparedStatement.setInt(4, component.getProjectId());
            preparedStatement.setInt(5, component.getId());
            preparedStatement.executeUpdate();
            return component.getId();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public Labor updateLabor (Labor labor) {
        int id = update(labor);
        if (id != -1) {
            String query = "UPDATE labor SET hourly_rate = ?, hours_worked = ?, worker_productivity = ? WHERE id = ?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setDouble(1, labor.getHourlyRate());
                preparedStatement.setDouble(2, labor.getHoursWorked());
                preparedStatement.setDouble(3, labor.getWorkerProductivity());
                preparedStatement.setInt(4, id);
                preparedStatement.executeUpdate();
                return labor;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    @Override
    public Material updateMaterial (Material material) {
        int id = update(material);
        if (id != -1) {
            String query = "UPDATE material SET unit_price = ?, quantity = ? WHERE id = ?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setDouble(1, material.getUnitCost());
                preparedStatement.setDouble(2, material.getQuantity());
                preparedStatement.setInt(3, id);
                preparedStatement.executeUpdate();
                return material;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
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
