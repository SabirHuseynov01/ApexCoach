package az.apexcoach.api.simulation.internal.raceroom;

import az.apexcoach.api.simulation.NormalizedTelemetry;
import az.apexcoach.api.simulation.SimulationTelemetryAdapter;
import az.apexcoach.api.simulation.SimulationType;
import az.apexcoach.api.simulation.TelemetrySource;
import org.springframework.stereotype.Component;
import shared.error.UnsupportedSimulationException;

@Component
public class RaceRoomAdapter implements SimulationTelemetryAdapter {

    private final RaceRoomPacketMapper packetMapper;
    private final RaceRoomLapBuilder lapBuilder;

    public RaceRoomAdapter(RaceRoomPacketMapper packetMapper, RaceRoomLapBuilder lapBuilder) {
        this.packetMapper = packetMapper;
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
        throw new UnsupportedSimulationException("RaceRoom telemetry parsing is not implemented yet");
    }
}