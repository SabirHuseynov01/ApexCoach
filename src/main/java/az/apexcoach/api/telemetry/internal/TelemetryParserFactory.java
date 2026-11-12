package az.apexcoach.api.telemetry.internal;

import az.apexcoach.api.simulation.SimulationType;
import az.apexcoach.api.simulation.internal.registry.TelemetryAdapterRegistry;
import org.springframework.stereotype.Component;
import shared.error.NotFoundException;

@Component
public class TelemetryParserFactory {

    private final TelemetryAdapterRegistry registry;

    public TelemetryParserFactory(TelemetryAdapterRegistry registry) {
        this.registry = registry;
    }

    public TelemetryParser getParser(SimulationType type) {
        var adapter = registry.get(type);

        if (adapter instanceof TelemetryParser parser) {
            return parser;
        }

        throw new NotFoundException("No telemetry parser for: " + type);
    }
}
