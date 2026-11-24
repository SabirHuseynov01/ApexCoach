package az.apexcoach.api.simulation;

import shared.error.UnsupportedSimulationException;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface SimulationTelemetryAdapter {

    SimulationType supports();

    TelemetrySource source();

    boolean isAvailable();

    NormalizedTelemetry parse(byte[] rawTelemetry);

    default byte[] capture() {
        throw new UnsupportedSimulationException(
                "Live telemetry capture is not available for: " + supports());
    }


    default NormalizedTelemetry toNormalizedTelemetry(List<NormalizedLap> laps) {
        return new NormalizedTelemetry(
                UUID.randomUUID(),
                supports(),
                source(),
                Instant.now(),
                new NormalizedSession(
                        "Live session",
                        "PRACTICE",
                        null,
                        null,
                        "Unknown track",
                        "Unknown car",
                        "Unknown"
                ),
                new TrackInfo("Unknown track", null, null),
                new CarInfo("Unknown car", null, null),
                new DriverInfo("Unknown driver", null),
                new WeatherInfo("Unknown", null, null),
                new TelemetryQuality(true, true, false, false, false, false, 0, 0),
                laps
        );
    }
}