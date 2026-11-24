package az.apexcoach.api.simulation.internal.raceroom;

import az.apexcoach.api.simulation.NormalizedTelemetry;
import az.apexcoach.api.simulation.SimulationTelemetryAdapter;
import az.apexcoach.api.simulation.SimulationType;
import az.apexcoach.api.simulation.TelemetrySource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RaceRoomAdapter implements SimulationTelemetryAdapter {

    private final RaceRoomPacketMapper packetMapper;
    private final RaceRoomLapBuilder lapBuilder;
    private final RaceRoomSharedMemoryReader sharedMemoryReader;

    @Override
    public SimulationType supports() {
        return SimulationType.RACE_ROOM;
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