package az.apexcoach.api.telemetry.internal;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LapRepository extends JpaRepository<LapEntity, UUID> {

    List<LapEntity> findByTelemetryFileIdOrderByLapNumberAsc(UUID telemetryFileId);
}