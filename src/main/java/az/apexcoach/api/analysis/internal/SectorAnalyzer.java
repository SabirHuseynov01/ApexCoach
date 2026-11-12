package az.apexcoach.api.analysis.internal;

import az.apexcoach.api.analysis.DrivingMetricType;
import az.apexcoach.api.telemetry.internal.TelemetryPointEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SectorAnalyzer {

    public List<DrivingMetric> analyze(List<TelemetryPointEntity> points) {
        List<DrivingMetric> metrics = new ArrayList<>();

        if (points.isEmpty()) {
            return metrics;
        }

        double maxDistance = points.stream()
                .mapToDouble(TelemetryPointEntity::getLapDistance)
                .max()
                .orElse(0);

        if (maxDistance <= 0) {
            return metrics;
        }

        double sectorLength = maxDistance / 3;

        double sector1AvgSpeed = averageSpeed(points, 0, sectorLength);
        double sector2AvgSpeed = averageSpeed(points, sectorLength, sectorLength * 2);
        double sector3AvgSpeed = averageSpeed(points, sectorLength * 2, maxDistance);

        metrics.add(new DrivingMetric(
                DrivingMetricType.SECTOR,
                "Sector 1 average speed",
                sector1AvgSpeed,
                "speed units",
                severity(sector1AvgSpeed, sector2AvgSpeed, sector3AvgSpeed),
                "Average speed in sector 1."
        ));

        metrics.add(new DrivingMetric(
                DrivingMetricType.SECTOR,
                "Sector 2 average speed",
                sector2AvgSpeed,
                "speed units",
                severity(sector2AvgSpeed, sector1AvgSpeed, sector3AvgSpeed),
                "Average speed in sector 2."
        ));

        metrics.add(new DrivingMetric(
                DrivingMetricType.SECTOR,
                "Sector 3 average speed",
                sector3AvgSpeed,
                "speed units",
                severity(sector3AvgSpeed, sector1AvgSpeed, sector2AvgSpeed),
                "Average speed in sector 3."
        ));

        return metrics;
    }

    private double averageSpeed(List<TelemetryPointEntity> points, double from, double to) {
        return points.stream()
                .filter(p -> p.getLapDistance() >= from && p.getLapDistance() < to)
                .mapToDouble(TelemetryPointEntity::getSpeed)
                .average()
                .orElse(0);
    }

    private String severity(double value, double other1, double other2) {
        if (value < other1 && value < other2) {
            return "WARN";
        }
        return "INFO";
    }
}
