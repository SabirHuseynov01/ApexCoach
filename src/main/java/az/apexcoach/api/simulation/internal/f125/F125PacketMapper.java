package az.apexcoach.api.simulation.internal.f125;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class F125PacketMapper {

    private final F125SessionMapper sessionMapper;
    private final F125LapDataMapper lapDataMapper;
    private final F125CarTelemetryMapper carTelemetryMapper;
    private final F125TyreSetMapper tyreSetMapper;

    public F125PacketHeader header(byte[] raw) {
        throw new UnsupportedOperationException("F1 25 packet header parsing not implemented yet");
    }

    public Object map(byte[] raw) {
        F125PacketHeader header = header(raw);
        F125PacketType packetType = F125PacketType.fromPacketId(header.packetId());

        return switch (packetType) {
            case SESSION -> sessionMapper.map(raw);
            case LAP_DATA -> lapDataMapper.map(raw);
            case CAR_TELEMETRY, CAR_TELEMETRY_2 -> carTelemetryMapper.map(raw);
            case TYRE_SETS -> tyreSetMapper.map(raw);
            default -> throw new UnsupportedOperationException(
                    "F1 25 packet mapping not implemented yet: " + packetType);
        };
    }
}