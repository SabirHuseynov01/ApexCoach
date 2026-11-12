package az.apexcoach.api.telemetry.internal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class TelemetryStorageService {

    private final Path storageRoot;

    public TelemetryStorageService(
            @Value("${apexcoach.telemetry.storage-path:./uploads/telemetry}") String storagePath
    ) {
        this.storageRoot = Paths.get(storagePath);
    }

    public Path store(MultipartFile file) {
        try {
            Files.createDirectories(storageRoot);

            String originalName = file.getOriginalFilename() == null
                    ? "telemetry.csv"
                    : file.getOriginalFilename();

            String safeName = UUID.randomUUID() + "-" + originalName.replaceAll("[^a-zA-Z0-9._-]", "_");
            Path target = storageRoot.resolve(safeName).normalize();

            if (!target.startsWith(storageRoot)) {
                throw new SecurityException("Invalid file path");
            }

            file.transferTo(target);
            return target;
        } catch (IOException e) {
            throw new IllegalStateException("Unable to store telemetry file", e);
        }
    }
}
