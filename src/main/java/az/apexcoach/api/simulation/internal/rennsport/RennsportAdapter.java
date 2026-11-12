package az.apexcoach.api.simulation.internal.rennsport;

import az.apexcoach.api.simulation.NormalizedTelemetry;
import az.apexcoach.api.simulation.SimulationTelemetryAdapter;
import az.apexcoach.api.simulation.SimulationType;
import az.apexcoach.api.simulation.TelemetrySource;
import org.springframework.stereotype.Component;
import shared.error.UnsupportedSimulationException;

@Component
public class RennsportAdapter implements SimulationTelemetryAdapter {

    private final RennsportPacketMapper packetMapper;
    private final RennsportLapBuilder lapBuilder;

    public RennsportAdapter(RennsportPacketMapper packetMapper, RennsportLapBuilder lapBuilder) {
        this.packetMapper = packetMapper;
        this.lapBuilder = lapBuilder;
    }

    @Override
    public SimulationType supports() {
        return SimulationType.RENNSPORT;
    }

    @Override
    public TelemetrySource source() {
        return TelemetrySource.REST_API;
    }

    @Override
    public boolean isAvailable() {
        return false;
    }

    @Override
    public NormalizedTelemetry parse(byte[] rawTelemetry) {
        throw new UnsupportedSimulationException("Rennsport telemetry parsing is not implemented yet");
    }
}
