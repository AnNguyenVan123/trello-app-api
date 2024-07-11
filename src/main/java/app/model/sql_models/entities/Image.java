package app.model.sql_models.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;
    @ManyToOne()
    private House house;
    public void setHouse(House house) {
        this.house = house;
    }
    public Image(Long id, String url,House house) {
        this.id = id;

        this.url = url;
        this.house =house;
    }
    public Image() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
