package az.apexcoach.api.analysis;

import az.apexcoach.api.analysis.internal.*;


import az.apexcoach.api.telemetry.LapEntity;
import az.apexcoach.api.telemetry.LapRepository;
import az.apexcoach.api.telemetry.TelemetryPointEntity;
import az.apexcoach.api.telemetry.TelemetryPointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shared.error.NotFoundException;
import tools.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AnalysisService {

    private final LapRepository lapRepository;
    private final TelemetryPointRepository telemetryPointRepository;
    private final LapAnalysisRepository lapAnalysisRepository;
    private final BrakingAnalyzer brakingAnalyzer;
    private final ThrottleAnalyzer throttleAnalyzer;
    private final SteeringAnalyzer steeringAnalyzer;
    private final ConsistencyAnalyzer consistencyAnalyzer;
    private final ObjectMapper objectMapper;
    private final SectorAnalyzer sectorAnalyzer;

    @Transactional
    public LapAnalysisDTO analyzeLap(String email, UUID lapId) {
        LapEntity lap = requireOwnedLap(lapId, email);

        List<TelemetryPointEntity> points =
                telemetryPointRepository.findByLapIdOrderBySessionTimeAsc(lapId);

        List<DrivingMetric> metrics = new ArrayList<>();

        metrics.addAll(brakingAnalyzer.analyze(points));
        metrics.addAll(throttleAnalyzer.analyze(points));
        metrics.addAll(steeringAnalyzer.analyze(points));
        metrics.addAll(consistencyAnalyzer.analyze(points));
        metrics.addAll(sectorAnalyzer.analyze(points));

        LapAnalysisEntity analysis = lapAnalysisRepository.findByLapId(lapId)
                .orElseGet(() -> LapAnalysisEntity.builder()
                        .lap(lap)
                        .build());

        analysis.setStatus(AnalysisStatus.COMPLETED);
        analysis.setAnalyzedAt(Instant.now());
        analysis.setResultJson(toJson(metrics));

        lapAnalysisRepository.save(analysis);

        return new LapAnalysisDTO(
                lapId,
                AnalysisStatus.COMPLETED,
                metrics.stream().map(DrivingMetric::toDto).toList()
        );
    }

    @Transactional(readOnly = true)
    public LapAnalysisDTO getAnalysis(String email, UUID lapId) {
        requireOwnedLap(lapId, email);

        LapAnalysisEntity analysis = lapAnalysisRepository.findByLapId(lapId)
                .orElseThrow(() -> new NotFoundException("Analysis not found"));

        List<DrivingMetricDTO> metrics = parseMetrics(analysis.getResultJson());

        return new LapAnalysisDTO(lapId, analysis.getStatus(), metrics);
    }

    private LapEntity requireOwnedLap(UUID lapId, String email) {
        LapEntity lap = lapRepository.findById(lapId)
                .orElseThrow(() -> new NotFoundException("Lap not found"));

        if (!email.equals(lap.getTelemetryFile().getUser().getEmail())) {
            throw new NotFoundException("Lap not found");
        }

        return lap;
    }

    private String toJson(List<DrivingMetric> metrics) {
        try {
            return objectMapper.writeValueAsString(metrics);
        } catch (Exception e) {
            throw new IllegalStateException("Unable to serialize analysis result", e);
        }
    }

    private List<DrivingMetricDTO> parseMetrics(String json) {
        try {
            return List.of(objectMapper.readValue(json, DrivingMetricDTO[].class));
        } catch (Exception e) {
            throw new IllegalStateException("Unable to read analysis result", e);
        }
    }
}