package az.apexcoach.api.telemetry;

/**
 * Read-only snapshot of a telemetry point, exposed to other modules so they do not
 * have to depend on the persistence entity inside {@code telemetry.internal}.
 */
public record TelemetryPointSnapshot(
        double lapDistance,
        double speed,
        double throttle,
        double brake,
        double steeringAngle
) {
}
