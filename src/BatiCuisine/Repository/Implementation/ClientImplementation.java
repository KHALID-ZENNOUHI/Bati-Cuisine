package BatiCuisine.Repository.Implementation;

import BatiCuisine.Config.DataBaseConnection;
import BatiCuisine.Repository.Interface.InterfaceDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class ImplementationDAO implements InterfaceDAO {
    private Connection connection;
    private String table;

    public ImplementationDAO(String table) throws SQLException {
        this.connection = DataBaseConnection.getInstance().getConnection();
        this.table = table;
    }

    @Override
    public Object save(HashMap<String, Object> data) {
    return  null;
    }

    @Override
    public Object update(HashMap<String, Object> data) {
        return null;
    }

    @Override
    public Optional<Object> delete(int id) {
        return null;
    }

    @Override
    public Optional<Object> findById(int id) {
        return null;
    }

    @Override
    public List<Object> findAll() {
        return null;
    }
}
