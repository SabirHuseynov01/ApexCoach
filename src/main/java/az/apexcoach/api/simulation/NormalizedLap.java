package az.apexcoach.api.simulation;

import java.util.List;

public record NormalizedLap(
        int lapNumber,
        Double lapTime,
        Double sector1Time,
        Double sector2Time,
        Double sector3Time,
        boolean isValid,
        boolean isOutLap,
        boolean isInLap,
        List<NormalizedTelemetryPoint> points
) {}