package az.apexcoach.api.simulation.internal.acc;

import az.apexcoach.api.simulation.NormalizedTelemetry;
import az.apexcoach.api.simulation.SimulationTelemetryAdapter;
import az.apexcoach.api.simulation.SimulationType;
import az.apexcoach.api.simulation.TelemetrySource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccAdapter implements SimulationTelemetryAdapter {

    private final AccPacketMapper packetMapper;
    private final AccLapBuilder lapBuilder;
    private final AccUdpReceiver udpReceiver;
    private final AccSharedMemoryReader sharedMemoryReader;

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