package az.apexcoach.api.telemetry;

import java.util.UUID;

public record LapDTO(
        UUID id,
        int lapNumber,
        Double lapTime,
        Double sector1Time,
        Double sector2Time,
        Double sector3Time,
        boolean valid
) {}