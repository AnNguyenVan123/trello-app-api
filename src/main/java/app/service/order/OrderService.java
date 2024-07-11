package app.service.order;

import app.model.sql_models.entities.Orders;
import org.aspectj.weaver.ast.Or;


import java.util.List;

public interface OrderService {
     public Orders getById(Long id);
     public List<Orders> getAll();
     public List<Orders> getOrdersByOwner(Long id);
     public void  delete(Long id);
     public Orders edit(Long id , Orders newOrders);
     public Orders add(Orders newOrders);
     public Orders done(Long id);
     public List<Orders> getByTenant(Long id);
     public Orders cancel(Long id);
}
