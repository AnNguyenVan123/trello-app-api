package app.service.order;

import app.model.sql_models.entities.Orders;
import app.model.sql_models.enums.OrderState;
import app.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepository orderRepository;


    @Override
    public Orders getById(Long id) {
        return orderRepository.findById(id).get();
    }

    @Override
    public List<Orders> getAll() {
        return orderRepository.findAll();
    }

    @Override
    public List<Orders> getOrdersByOwner(Long id) {
        return orderRepository.findOrdersByHouse_Owner_Id(id);
    }

    @Override
    public void delete(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public Orders edit(Long id, Orders newOrders) {
        return orderRepository.findById(id).map(orders ->
                {
                    orders.setHouse(newOrders.getHouse());
                    orders.setState(newOrders.getState());
                    orders.setTenant(newOrders.getTenant());
                    return orderRepository.save(orders);
                }
        ).orElseGet(() -> {
            return orderRepository.save(newOrders);
        });
    }

    @Override
    public Orders add(Orders newOrders) {
        return orderRepository.save(newOrders);
    }

    @Override
    public Orders done(Long id) {
        return orderRepository.findById(id).map(orders ->
                {
                    orders.setState(OrderState.DONE);
                    return orderRepository.save(orders);
                }
        ).orElseGet(() -> {
            return null;
        });
    }

    @Override
    public List<Orders> getByTenant(Long id) {
        return orderRepository.findOrdersByTenantId(id);
    }

    @Override
    public Orders cancel(Long id) {
        return orderRepository.findById(id).map(orders ->
                {
                    orders.setState(OrderState.CANCELED);
                    return orderRepository.save(orders);
                }
        ).orElseGet(() -> {
            return null;
        });
    }
}
