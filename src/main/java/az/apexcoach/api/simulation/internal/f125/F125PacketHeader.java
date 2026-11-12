package az.apexcoach.api.simulation.internal.f125;

public record F125PacketHeader(
        int packetFormat,
        int gameMajorVersion,
        int gameMinorVersion,
        int packetVersion,
        int packetId,
        long sessionUid,
        float sessionTime,
        int frameIdentifier,
        int playerCarIndex,
        int secondaryPlayerCarIndex
) {}
