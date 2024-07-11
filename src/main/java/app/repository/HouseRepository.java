package app.repository;

import app.model.sql_models.entities.House;
import app.model.sql_models.enums.HouseState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HouseRepository extends JpaRepository<House,Long> {
    List<House> findByOwnerId(Long id);


    List<House> findByState(HouseState state);



    List<House> findByAddressContainsIgnoreCaseAndState(String address,HouseState state);

    List<House> findByPriceBetweenAndState( int minPrice, int maxPrice,HouseState state);

    List<House> findByAddressContainsIgnoreCaseAndPriceBetweenAndState( String address, int minPrice, int maxPrice,HouseState state);
}
