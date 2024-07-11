package app.model.sql_models.entities;


import app.model.sql_models.enums.OrderState;
import jakarta.persistence.*;

@Table(name = "orders")
@Entity
public class Orders {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id ;
    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Users tenant;
    @ManyToOne
    @JoinColumn(name = "house_id")
    private House house;
    private OrderState state;

    public Users getTenant() {
        return tenant;
    }

    public void setTenant(Users tenant) {
        this.tenant = tenant;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public Orders() {
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Orders(Long id, Users tenant,  House house, OrderState state) {
        this.id = id;
        this.tenant = tenant;

        this.house = house;
        this.state = state;
    }

    public OrderState getState() {
        return state;
    }
    public void setState(OrderState state) {
        this.state = state;
    }
}
