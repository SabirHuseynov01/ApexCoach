package az.apexcoach.api.telemetry.internal;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "telemetry_points")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TelemetryPointEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lap_id", nullable = false)
    private LapEntity lap;

    private double sessionTime;
    private double lapTime;
    private double lapDistance;

    private double speed;
    private double rpm;
    private int gear;

    private double throttle;
    private double brake;
    private double clutch;
    private double steeringAngle;

    private double lateralG;
    private double longitudinalG;
    private double verticalG;

    private double posX;
    private double posY;
    private double posZ;

    private double yaw;
    private double pitch;
    private double roll;
}