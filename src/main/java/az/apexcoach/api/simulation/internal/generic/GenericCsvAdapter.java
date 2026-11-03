package az.apexcoach.api.simulation.internal.generic;

import az.apexcoach.api.simulation.NormalizedTelemetry;
import az.apexcoach.api.simulation.SimulationTelemetryAdapter;
import az.apexcoach.api.simulation.SimulationType;
import az.apexcoach.api.simulation.TelemetrySource;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class GenericCsvAdapter implements SimulationTelemetryAdapter {

    private final GenericCsvParser csvParser;
    private final GenericCsvLapBuilder lapBuilder;

    public GenericCsvAdapter(GenericCsvParser csvParser, GenericCsvLapBuilder lapBuilder) {
        this.csvParser = csvParser;
        this.lapBuilder = lapBuilder;
    }

    @Override
    public SimulationType supports() {
        return SimulationType.GENERIC_CSV;
    }

    @Override
    public TelemetrySource source() {
        return TelemetrySource.FILE_UPLOAD;
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public NormalizedTelemetry parse(byte[] rawTelemetry) {
        List<Map<String, String>> rows = csvParser.parse(rawTelemetry);

        return new NormalizedTelemetry(
                UUID.randomUUID(),
                SimulationType.GENERIC_CSV,
                TelemetrySource.FILE_UPLOAD,
                Instant.now(),
                new az.apexcoach.api.simulation.NormalizedSession(
                        "Uploaded session",
                        "PRACTICE",
                        null,
                        null,
                        "Unknown track",
                        "Unknown car",
                        "Unknown"
                ),
                new az.apexcoach.api.simulation.TrackInfo("Unknown track", null, null),
                new az.apexcoach.api.simulation.CarInfo("Unknown car", null, null),
                new az.apexcoach.api.simulation.DriverInfo("Unknown driver", null),
                new az.apexcoach.api.simulation.WeatherInfo("Unknown", null, null),
                new az.apexcoach.api.simulation.TelemetryQuality(
                        true, true, false, false, false, false, 0, 0
                ),
                lapBuilder.build(rows)
        );
    }
}