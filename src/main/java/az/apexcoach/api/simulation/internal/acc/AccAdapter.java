package az.apexcoach.api.simulation.internal.acc;

import az.apexcoach.api.simulation.NormalizedTelemetry;
import az.apexcoach.api.simulation.SimulationTelemetryAdapter;
import az.apexcoach.api.simulation.SimulationType;
import az.apexcoach.api.simulation.TelemetrySource;
import org.springframework.stereotype.Component;
import shared.error.UnsupportedSimulationException;

@Component
public class AccAdapter implements SimulationTelemetryAdapter {

    private final AccPacketMapper packetMapper;
    private final AccLapBuilder lapBuilder;

    public AccAdapter(AccPacketMapper packetMapper, AccLapBuilder lapBuilder) {
        this.packetMapper = packetMapper;
        this.lapBuilder = lapBuilder;
    }

    @Override
    public SimulationType supports() {
        return SimulationType.ASSETTO_CORSA_COMPETIZIONE;
    }

    @Override
    public TelemetrySource source() {
        return TelemetrySource.UDP;
    }

    @Override
    public boolean isAvailable() {
        return false;
    }

    @Override
    public NormalizedTelemetry parse(byte[] rawTelemetry) {
        throw new UnsupportedSimulationException("ACC telemetry parsing is not implemented yet");
    }
}
