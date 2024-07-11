package app.repository;

import app.model.sql_models.entities.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OrderRepository extends JpaRepository<Orders,Long> {

    List<Orders> findOrdersByHouse_Owner_Id(Long id);
    List<Orders> findOrdersByTenantId(Long id);
}
