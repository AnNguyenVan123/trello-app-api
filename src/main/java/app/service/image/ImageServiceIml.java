package app.service.image;

import app.model.sql_models.entities.House;
import app.model.sql_models.entities.Image;
import app.repository.ImageRepository;
import com.google.cloud.storage.Bucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageServiceIml implements ImageService {
    @Autowired
    private ImageRepository houseImageRepository;
    @Autowired
    private Bucket bucket;
    @Value("${firebase.bucket.name}")
    private String bucketName;

    @Override
    public Image addHouseImage(MultipartFile file, House house) throws IOException {
        String fileName = file.getOriginalFilename();
        bucket.create(fileName, file.getInputStream(), file.getContentType());
        String imageUrl = String.format("https://firebasestorage.googleapis.com/v0/b/%s/o/%s?alt=media", bucketName, fileName);
        return houseImageRepository.save(new Image(null, imageUrl, house));
    }

    @Override
    public void delete(Long id) {
        houseImageRepository.deleteById(id);
    }
}
