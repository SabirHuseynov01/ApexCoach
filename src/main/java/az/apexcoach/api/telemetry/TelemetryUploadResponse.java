package az.apexcoach.api.telemetry;

import az.apexcoach.api.simulation.SimulationType;

import java.time.Instant;
import java.util.UUID;

public record TelemetryUploadResponse(
        UUID telemetryFileId,
        SimulationType simulationType,
        String fileName,
        int lapCount,
        Instant uploadedAt
) {}