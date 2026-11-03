package az.apexcoach.api.analysis.internal;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface LapAnalysisRepository extends JpaRepository<LapAnalysisEntity, UUID> {

    Optional<LapAnalysisEntity> findByLapId(UUID lapId);
}