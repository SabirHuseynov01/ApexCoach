package az.apexcoach.api.simulation.internal.f125;

import az.apexcoach.api.simulation.NormalizedTelemetry;
import az.apexcoach.api.simulation.SimulationTelemetryAdapter;
import az.apexcoach.api.simulation.SimulationType;
import az.apexcoach.api.simulation.TelemetrySource;
import org.springframework.stereotype.Component;

@Component
public class F125Adapter implements SimulationTelemetryAdapter {

    private final F125PacketMapper packetMapper;
    private final F125LapBuilder lapBuilder;

    public F125Adapter(
            F125PacketMapper packetMapper,
            F125LapBuilder lapBuilder
    ) {
        this.packetMapper = packetMapper;
        this.lapBuilder = lapBuilder;
    }

    @Override
    public SimulationType supports() {
        return SimulationType.F1_25;
    }

    @Override
    public TelemetrySource source() {
        return TelemetrySource.UDP;
    }

    @Override
    public boolean isAvailable() {
        return false; // ilk buraxılışda planned
    }

    @Override
    public NormalizedTelemetry parse(byte[] rawTelemetry) {
        var packets = packetMapper.map(rawTelemetry);
        return lapBuilder.build(packets);
    }
}