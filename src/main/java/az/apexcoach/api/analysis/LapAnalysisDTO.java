package az.apexcoach.api.analysis;

import java.util.List;
import java.util.UUID;

public record LapAnalysisDTO(
        UUID lapId,
        AnalysisStatus status,
        List<DrivingMetricDTO> metrics
) {}