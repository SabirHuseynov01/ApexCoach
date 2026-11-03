package az.apexcoach.api.simulation;

public record NormalizedTelemetryPoint(
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
        double roll,
        TyreInfo tyres
) {}