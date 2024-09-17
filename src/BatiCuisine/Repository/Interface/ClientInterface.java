package BatiCuisine.Repository.Interface;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface InterfaceDAO {
    public  Object save(HashMap<String, Object> data);
    public  Object update(HashMap<String, Object> data);
    public Optional<Object> delete(int id);
    public Optional<Object> findById(int id);
    public List<Object> findAll();
}
