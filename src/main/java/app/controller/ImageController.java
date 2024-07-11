package app.controller;

import app.model.sql_models.entities.House;
import app.service.house.HouseService;
import app.service.image.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("images")
@CrossOrigin(origins = "http://localhost:3000")
public class ImageController {
    @Autowired
    ImageService imageService;
    @Autowired
    HouseService houseService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> add(@RequestPart("file") MultipartFile file,
                                 @RequestPart("house_id") String id) {
        House house = houseService.getById(Long.parseLong(id));
        if (house == null) {
            return ResponseEntity.status(404).body("House not found");
        }
        try {
            return ResponseEntity.created(null).body(imageService.addHouseImage(file, house));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error uploading image");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        imageService.delete(id);
        return ResponseEntity.noContent().build();

    }


}
