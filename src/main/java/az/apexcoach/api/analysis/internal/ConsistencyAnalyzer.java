package az.apexcoach.api.analysis.internal;

import az.apexcoach.api.analysis.DrivingMetricType;
import az.apexcoach.api.telemetry.internal.TelemetryPointEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ConsistencyAnalyzer {

    public List<DrivingMetric> analyze(List<TelemetryPointEntity> points) {
        List<DrivingMetric> metrics = new ArrayList<>();

        double avgSpeed = points.stream()
                .mapToDouble(TelemetryPointEntity::getSpeed)
                .average()
                .orElse(0);

        double speedVariance = points.stream()
                .mapToDouble(point -> Math.pow(point.getSpeed() - avgSpeed, 2))
                .average()
                .orElse(0);

        double speedStdDev = Math.sqrt(speedVariance);

        metrics.add(new DrivingMetric(
                DrivingMetricType.CONSISTENCY,
                "Speed standard deviation",
                speedStdDev,
                "speed units",
                speedStdDev > 30 ? "WARN" : "GOOD",
                speedStdDev > 30
                        ? "Speed variation is high; braking and throttle consistency can improve."
                        : "Speed management looks consistent."
        ));

        return metrics;
    }
}