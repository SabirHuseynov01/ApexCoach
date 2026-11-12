package az.apexcoach.api.simulation.internal.ams2;

import az.apexcoach.api.simulation.NormalizedTelemetry;
import az.apexcoach.api.simulation.SimulationTelemetryAdapter;
import az.apexcoach.api.simulation.SimulationType;
import az.apexcoach.api.simulation.TelemetrySource;
import org.springframework.stereotype.Component;
import shared.error.UnsupportedSimulationException;

@Component
public class Automobilista2Adapter implements SimulationTelemetryAdapter {

    private final Ams2PacketMapper packetMapper;
    private final Ams2LapBuilder lapBuilder;

    public Automobilista2Adapter(Ams2PacketMapper packetMapper, Ams2LapBuilder lapBuilder) {
        this.packetMapper = packetMapper;
        this.lapBuilder = lapBuilder;
    }

    @Override
    public SimulationType supports() {
        return SimulationType.AUTOMOBILISTA_2;
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
        throw new UnsupportedSimulationException("AMS2 telemetry parsing is not implemented yet");
    }
}
