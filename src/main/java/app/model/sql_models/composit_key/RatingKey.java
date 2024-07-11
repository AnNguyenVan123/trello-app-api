package app.model.sql_models.composit_key;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
@Embeddable
public class RatingKey implements Serializable {
    @Column(name = "user_id")
    private Long user_id ;
    @Column(name = "house_id")
    private Long house_id ;

    public RatingKey(Long user_id, Long house_id) {
        this.user_id = user_id;
        this.house_id = house_id;
    }

    public RatingKey() {
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getHouse_id() {
        return house_id;
    }

    public void setHouse_id(Long house_id) {
        this.house_id = house_id;
    }
}
