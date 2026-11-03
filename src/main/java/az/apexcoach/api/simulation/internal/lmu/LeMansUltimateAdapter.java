package az.apexcoach.api.simulation.internal.lmu;

import az.apexcoach.api.simulation.NormalizedTelemetry;
import az.apexcoach.api.simulation.SimulationTelemetryAdapter;
import az.apexcoach.api.simulation.SimulationType;
import az.apexcoach.api.simulation.TelemetrySource;
import org.springframework.stereotype.Component;

@Component
public class LeMansUltimateAdapter implements SimulationTelemetryAdapter {

    private final LmuSharedMemoryReader reader;
    private final LmuPacketMapper mapper;
    private final LmuLapBuilder lapBuilder;

    public LeMansUltimateAdapter(
            LmuSharedMemoryReader reader,
            LmuPacketMapper mapper,
            LmuLapBuilder lapBuilder
    ) {
        this.reader = reader;
        this.mapper = mapper;
        this.lapBuilder = lapBuilder;
    }

    @Override
    public SimulationType supports() {
        return SimulationType.LE_MANS_ULTIMATE;
    }

    @Override
    public TelemetrySource source() {
        return TelemetrySource.LOCAL_SHARED_MEMORY;
    }

    @Override
    public boolean isAvailable() {
        return false; // live bridge hazır olanda true
    }

    @Override
    public NormalizedTelemetry parse(byte[] rawTelemetry) {
        var snapshot = mapper.map(rawTelemetry);
        return lapBuilder.build(snapshot);
    }
}