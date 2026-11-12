package az.apexcoach.api.telemetry;

import java.util.UUID;

public record TelemetryPointDTO(
        UUID id,
        double sessionTime,
        double lapTime,
        double lapDistance,
        double speed,
        double rpm,
        int gear,
        double throttle,
        double brake,
        double clutch,
        double steeringAngle,
        double lateralG,
        double longitudinalG,
        double verticalG,
        double posX,
        double posY,
        double posZ,
        double yaw,
        double pitch,
        double roll
) {}