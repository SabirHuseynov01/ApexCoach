package az.apexcoach.api.telemetry.internal;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TelemetryPointRepository extends JpaRepository<TelemetryPointEntity, UUID> {

    List<TelemetryPointEntity> findByLapIdOrderBySessionTimeAsc(UUID lapId);
}