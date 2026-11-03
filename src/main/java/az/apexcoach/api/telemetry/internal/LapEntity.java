package az.apexcoach.api.telemetry.internal;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "laps")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LapEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "telemetry_file_id", nullable = false)
    private TelemetryFileEntity telemetryFile;

    @Column(nullable = false)
    private int lapNumber;

    private Double lapTime;

    private Double sector1Time;
    private Double sector2Time;
    private Double sector3Time;

    @Column(nullable = false)
    private boolean valid;

    @OneToMany(
            mappedBy = "lap",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Builder.Default
    private List<TelemetryPointEntity> points = new ArrayList<>();
}