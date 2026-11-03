package az.apexcoach.api.simulation.internal.raceroom;

import az.apexcoach.api.simulation.NormalizedTelemetry;
import az.apexcoach.api.simulation.SimulationTelemetryAdapter;
import az.apexcoach.api.simulation.SimulationType;
import az.apexcoach.api.simulation.TelemetrySource;
import org.springframework.stereotype.Component;

@Component
public class RaceRoomAdapter implements SimulationTelemetryAdapter {

    private final RaceRoomSharedMemoryReader reader;
    private final RaceRoomPacketMapper mapper;
    private final RaceRoomLapBuilder lapBuilder;

    public RaceRoomAdapter(
            RaceRoomSharedMemoryReader reader,
            RaceRoomPacketMapper mapper,
            RaceRoomLapBuilder lapBuilder
    ) {
        this.reader = reader;
        this.mapper = mapper;
        this.lapBuilder = lapBuilder;
    }

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
    public NormalizedTelemetry parse(byte[] rawTelemetry) {
        var snapshot = mapper.map(rawTelemetry);
        return lapBuilder.build(snapshot);
    }
}