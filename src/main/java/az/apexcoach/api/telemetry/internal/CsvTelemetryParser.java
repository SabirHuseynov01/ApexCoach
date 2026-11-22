package az.apexcoach.api.telemetry.internal;

import az.apexcoach.api.simulation.NormalizedTelemetry;
import az.apexcoach.api.simulation.SimulationTelemetryService;
import az.apexcoach.api.simulation.SimulationType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shared.error.TelemetryParseException;

import java.nio.charset.StandardCharsets;

/**
 * Entry point for CSV telemetry uploads. The actual CSV work is delegated to the
 * {@code GENERIC_CSV} {@link az.apexcoach.api.simulation.SimulationTelemetryAdapter}
 * that the simulation module has registered, so CSV parsing stays in one place.
 */
@Component
@RequiredArgsConstructor
public class CsvTelemetryParser {

    private final SimulationTelemetryService simulationTelemetryService;

    public NormalizedTelemetry parse(String csvContent) {
        if (csvContent == null) {
            throw new TelemetryParseException("CSV content is missing");
        }

        return parse(csvContent.getBytes(StandardCharsets.UTF_8));
    }

    public NormalizedTelemetry parse(byte[] csvContent) {
        if (csvContent == null || csvContent.length == 0) {
            throw new TelemetryParseException("CSV content is missing");
        }

        return simulationTelemetryService.parse(SimulationType.GENERIC_CSV, csvContent);
    }
}