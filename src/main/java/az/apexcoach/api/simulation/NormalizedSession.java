package az.apexcoach.api.simulation;

public record NormalizedSession(
        String sessionName,
        String sessionType,
        Integer totalLaps,
        Double sessionTimeLeft,
        String trackName,
        String carName,
        String weatherCondition
) {}