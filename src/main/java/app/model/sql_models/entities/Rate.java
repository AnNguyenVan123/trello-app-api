package app.model.sql_models.entities;

import app.model.sql_models.composit_key.RatingKey;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

@Entity
public class Rate {
    @EmbeddedId
    private RatingKey id;
    private int rating ;
}
