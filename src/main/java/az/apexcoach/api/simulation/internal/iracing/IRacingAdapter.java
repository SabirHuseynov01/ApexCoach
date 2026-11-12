package az.apexcoach.api.simulation.internal.iracing;

import az.apexcoach.api.simulation.NormalizedTelemetry;
import az.apexcoach.api.simulation.SimulationTelemetryAdapter;
import az.apexcoach.api.simulation.SimulationType;
import az.apexcoach.api.simulation.TelemetrySource;
import org.springframework.stereotype.Component;
import shared.error.UnsupportedSimulationException;

@Component
public class IRacingAdapter implements SimulationTelemetryAdapter {

    private final IRacingPacketMapper packetMapper;
    private final IRacingLapBuilder lapBuilder;

    public IRacingAdapter(IRacingPacketMapper packetMapper, IRacingLapBuilder lapBuilder) {
        this.packetMapper = packetMapper;
        this.lapBuilder = lapBuilder;
    }

    @Override
    public SimulationType supports() {
        return SimulationType.IRACING;
    }

    @Override
    public TelemetrySource source() {
        return TelemetrySource.LOCAL_SHARED_MEMORY;
    }

    @Override
    public boolean isAvailable() {
        return false;
    }

    @Override
    public NormalizedTelemetry parse(byte[] rawTelemetry) {
        throw new UnsupportedSimulationException("iRacing telemetry parsing is not implemented yet");
    }
}
