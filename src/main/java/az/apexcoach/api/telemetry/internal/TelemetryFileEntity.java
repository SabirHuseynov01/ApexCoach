package az.apexcoach.api.telemetry.internal;

import az.apexcoach.api.identity.UserEntity;
import az.apexcoach.api.simulation.SimulationType;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "telemetry_files")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TelemetryFileEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SimulationType simulationType;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private long fileSizeBytes;

    @Column(nullable = false)
    private Instant uploadedAt;
}