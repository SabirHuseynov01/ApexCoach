package az.apexcoach.api.simulation;

public record TelemetryQuality(
        boolean hasPosition,
        boolean hasGForce,
        boolean hasTyreData,
        boolean hasWeather,
        boolean hasSectorTimes,
        boolean hasLapValidity,
        int sampleRateHz,
        int missingFieldCount
) {}