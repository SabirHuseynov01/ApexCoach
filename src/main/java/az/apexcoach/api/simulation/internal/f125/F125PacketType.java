package az.apexcoach.api.simulation.internal.f125;

public enum F125PacketType {
    MOTION,
    SESSION,
    LAP_DATA,
    EVENT,
    PARTICIPANTS,
    CAR_SETUPS,
    CAR_TELEMETRY,
    CAR_STATUS,
    FINAL_CLASSIFICATION,
    LOBBY_INFO,
    CAR_DAMAGE,
    SESSION_HISTORY,
    TYRE_SETS,
    MOTION_EX,
    TIME_TRIAL,
    LAP_POSITIONS,
    CAR_TELEMETRY_2;

    private static final F125PacketType[] BY_PACKET_ID = values();

    public static F125PacketType fromPacketId(int packetId) {
        if (packetId < 0 || packetId >= BY_PACKET_ID.length) {
            throw new IllegalArgumentException("Unknown F1 25 packet id: " + packetId);
        }

        return BY_PACKET_ID[packetId];
    }
}