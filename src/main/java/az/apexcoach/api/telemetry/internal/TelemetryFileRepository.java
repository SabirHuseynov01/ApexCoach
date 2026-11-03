package az.apexcoach.api.telemetry.internal;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TelemetryFileRepository extends JpaRepository<TelemetryFileEntity, UUID> {

    List<TelemetryFileEntity> findByUserIdOrderByUploadedAtDesc(UUID userId);
}