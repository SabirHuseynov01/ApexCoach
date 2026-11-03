package az.apexcoach.api.simulation;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record NormalizedTelemetry(
        UUID sessionId,
        SimulationType simulation,
        TelemetrySource source,
        Instant capturedAt,
        NormalizedSession session,
        TrackInfo track,
        CarInfo car,
        DriverInfo driver,
        WeatherInfo weather,
        TelemetryQuality quality,
        List<NormalizedLap> laps
) {}
