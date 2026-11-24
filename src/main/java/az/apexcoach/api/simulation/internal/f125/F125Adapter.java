package az.apexcoach.api.simulation.internal.f125;

import az.apexcoach.api.simulation.NormalizedTelemetry;
import az.apexcoach.api.simulation.SimulationTelemetryAdapter;
import az.apexcoach.api.simulation.SimulationType;
import az.apexcoach.api.simulation.TelemetrySource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class F125Adapter implements SimulationTelemetryAdapter {

    private final F125PacketMapper packetMapper;
    private final F125LapBuilder lapBuilder;
    private final F125UdpReceiver udpReceiver;

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
        return false;
    }

    @Override
    public byte[] capture() {
        return udpReceiver.receive();
    }

    @Override
    public NormalizedTelemetry parse(byte[] rawTelemetry) {
        Object packets = packetMapper.map(rawTelemetry);

        return toNormalizedTelemetry(lapBuilder.build(packets));
    }
}