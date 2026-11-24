package az.apexcoach.api.simulation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Public API of the simulation module for parsing raw telemetry content.
 */
@Service
@RequiredArgsConstructor
public class SimulationTelemetryService {

    private final TelemetryAdapterRegistry adapterRegistry;

    /**
     * @throws shared.error.NotFoundException              if no adapter is registered for the type
     * @throws shared.error.UnsupportedSimulationException if the adapter is not implemented yet
     */
    public NormalizedTelemetry parse(SimulationType simulationType, byte[] content) {
        SimulationTelemetryAdapter adapter = adapterRegistry.get(simulationType);

        byte[] rawTelemetry = content;

        if (rawTelemetry == null || rawTelemetry.length == 0) {
            rawTelemetry = adapter.capture();
        }

        return adapter.parse(rawTelemetry);
    }

    /**
     * Tells whether the given simulation can be handled right now, so callers can
     * reject an upload before reading the file.
     */
    public boolean supports(SimulationType simulationType) {
        return adapterRegistry.supports(simulationType);
    }
}