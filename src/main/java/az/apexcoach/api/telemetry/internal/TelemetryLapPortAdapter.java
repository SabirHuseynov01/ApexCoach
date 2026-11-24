package az.apexcoach.api.telemetry.internal;

import az.apexcoach.api.telemetry.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import shared.error.NotFoundException;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TelemetryLapPortAdapter implements TelemetryLapPort {

    private final LapRepository lapRepository;
    private final TelemetryPointRepository telemetryPointRepository;

    @Override
    @Transactional(readOnly = true)
    public void requireOwnedLap(UUID lapId, String email) {
        LapEntity lap = lapRepository.findById(lapId)
                .orElseThrow(() -> new NotFoundException("Lap not found"));

        if (!lap.getTelemetryFile().getUser().getEmail().equals(email)) {
            throw new NotFoundException("Lap not found");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<TelemetryPointSnapshot> getPoints(UUID lapId) {
        return telemetryPointRepository.findByLapIdOrderBySessionTimeAsc(lapId)
                .stream()
                .map(point -> new TelemetryPointSnapshot(
                        point.getLapDistance(),
                        point.getSpeed(),
                        point.getThrottle(),
                        point.getBrake(),
                        point.getSteeringAngle()
                ))
                .toList();
    }
}