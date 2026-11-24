package az.apexcoach.api.simulation.internal.ams2;

import az.apexcoach.api.simulation.NormalizedTelemetry;
import az.apexcoach.api.simulation.SimulationTelemetryAdapter;
import az.apexcoach.api.simulation.SimulationType;
import az.apexcoach.api.simulation.TelemetrySource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Automobilista2Adapter implements SimulationTelemetryAdapter {

    private final Ams2PacketMapper packetMapper;
    private final Ams2LapBuilder lapBuilder;
    private final Ams2UdpReceiver udpReceiver;
    private final Ams2SharedMemoryReader sharedMemoryReader;

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
    public byte[] capture() {
        byte[] udpTelemetry = udpReceiver.receive();

        if (udpTelemetry != null && udpTelemetry.length > 0) {
            return udpTelemetry;
        }

        return sharedMemoryReader.read();
    }

    @Override
    public NormalizedTelemetry parse(byte[] rawTelemetry) {
        Object packets = packetMapper.map(rawTelemetry);

        return toNormalizedTelemetry(lapBuilder.build(packets));
    }
}