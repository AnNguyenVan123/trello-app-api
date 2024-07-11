package app.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class FirebaseConfig {
    @Value("${firebase.config.path}")
    private String firebaseConfigPath;
    @Value("${firebase.bucket.name}")
    private String bucketName;

    @Bean
    public FirebaseApp initializeFirebaseApp() throws IOException {
        System.out.println("Firebase Config Path: " + firebaseConfigPath);
        System.out.println("Firebase Bucket Name: " + bucketName);

        if (FirebaseApp.getApps().isEmpty()) {
            FileInputStream serviceAccount = new FileInputStream(firebaseConfigPath);
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setStorageBucket(bucketName)
                    .build();

            FirebaseApp app = FirebaseApp.initializeApp(options);
            System.out.println("FirebaseApp initialized successfully");
            return app;
        } else {
            System.out.println("FirebaseApp already initialized");
            return FirebaseApp.getInstance();
        }
    }
    @Bean
    public Bucket storageClient() throws IOException {
        return StorageClient.getInstance(this.initializeFirebaseApp()).bucket();
    }
}
