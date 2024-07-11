package app.service.house;

import app.model.sql_models.entities.House;

import java.util.List;
import java.util.Map;

public interface HouseService {
    public House getById(Long id);

    public House add(House newHouse);

    public House edit(Long id, House newHouse);

    public void delete(Long id);

    public List<House> getAll();

    public House rented(Long id);

    public House empty(Long id);

    public List<House> findByOwnerId(Long id) ;
    public Boolean IsOwner(House house, String ownerAccount);
    public List<House> search(Map<String,String> params);
}
