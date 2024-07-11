package app.model.sql_models.entities;

import app.model.sql_models.enums.HouseState;
import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Users owner;
    private String address;
    private int price;
    private HouseState state;
    @OneToMany(mappedBy = "house")
    private Set<Image> images;
    private String title;


    private String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public House(Long id, Users owner, String address, int price, HouseState state, Set<Image> images, String title, String description) {
        this.id = id;
        this.owner = owner;
        this.address = address;
        this.price = price;
        this.state = state;
        this.images = images;
        this.title = title;
        this.description = description;
    }

    public House() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Users getOwner() {
        return owner;
    }

    public void setOwner(Users owner) {
        this.owner = owner;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public HouseState getState() {
        return state;
    }

    public void setState(HouseState state) {
        this.state = state;
    }

    public Set<Image> getImages() {
        return images;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
    }
}
