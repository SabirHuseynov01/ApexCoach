package az.apexcoach.api.simulation.internal.lmu;

import az.apexcoach.api.simulation.NormalizedTelemetry;
import az.apexcoach.api.simulation.SimulationTelemetryAdapter;
import az.apexcoach.api.simulation.SimulationType;
import az.apexcoach.api.simulation.TelemetrySource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LeMansUltimateAdapter implements SimulationTelemetryAdapter {

    private final LmuPacketMapper packetMapper;
    private final LmuLapBuilder lapBuilder;
    private final LmuSharedMemoryReader sharedMemoryReader;

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
        return false;
    }

    @Override
    public byte[] capture() {
        return sharedMemoryReader.read();
    }

    @Override
    public NormalizedTelemetry parse(byte[] rawTelemetry) {
        Object packets = packetMapper.map(rawTelemetry);

        return toNormalizedTelemetry(lapBuilder.build(packets));
    }
}