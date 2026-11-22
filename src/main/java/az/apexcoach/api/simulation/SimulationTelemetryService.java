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
        return adapterRegistry.get(simulationType).parse(content);
    }
}