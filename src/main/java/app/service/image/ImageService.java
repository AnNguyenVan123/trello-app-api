package app.service.image;

import app.model.sql_models.entities.House;
import app.model.sql_models.entities.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface ImageService {

    Image addHouseImage(MultipartFile file , House house) throws IOException;

    void delete(Long id);
}
