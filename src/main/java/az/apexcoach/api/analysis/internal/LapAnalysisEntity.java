package az.apexcoach.api.analysis.internal;

import az.apexcoach.api.analysis.AnalysisStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "lap_analyses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LapAnalysisEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "lap_id", nullable = false, unique = true)
    private UUID lapId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AnalysisStatus status;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String resultJson;

    @Column(nullable = false)
    private Instant analyzedAt;
}