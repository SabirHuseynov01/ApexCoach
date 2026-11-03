package az.apexcoach.api.simulation.internal.registry;

import az.apexcoach.api.simulation.SimulationTelemetryAdapter;
import az.apexcoach.api.simulation.SimulationType;
import org.springframework.stereotype.Component;
import shared.error.NotFoundException;
import shared.error.UnsupportedSimulationException;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
public class TelemetryAdapterRegistry {

    private final Map<SimulationType, SimulationTelemetryAdapter> adapters =
            new EnumMap<>(SimulationType.class);

    public TelemetryAdapterRegistry(List<SimulationTelemetryAdapter> adapterList) {
        adapterList.forEach(adapter -> adapters.put(adapter.supports(), adapter));
    }

    public SimulationTelemetryAdapter get(SimulationType type) {
        SimulationTelemetryAdapter adapter = adapters.get(type);

        if (adapter == null) {
            throw new NotFoundException("No telemetry adapter for: " + type);
        }

        if (!adapter.isAvailable()) {
            throw new UnsupportedSimulationException("Adapter not available yet: " + type);
        }

        return adapter;
    }

    public boolean supports(SimulationType type) {
        SimulationTelemetryAdapter adapter = adapters.get(type);
        return adapter != null && adapter.isAvailable();
    }
}
