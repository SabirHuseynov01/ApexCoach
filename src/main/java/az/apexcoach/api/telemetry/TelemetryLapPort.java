package az.apexcoach.api.telemetry;

import java.util.List;
import java.util.UUID;

/**
 * Public API of the telemetry module for lap-level access.
 */
public interface TelemetryLapPort {

    /**
     * Ensures the lap exists and belongs to the given user.
     *
     * @throws shared.error.NotFoundException if the lap does not exist or belongs to another user
     */
    void requireOwnedLap(UUID lapId, String email);

    List<TelemetryPointSnapshot> getPoints(UUID lapId);
}
