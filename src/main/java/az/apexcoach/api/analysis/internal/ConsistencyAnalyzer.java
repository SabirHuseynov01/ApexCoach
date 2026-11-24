package az.apexcoach.api.analysis.internal;

import az.apexcoach.api.analysis.DrivingMetricType;
import az.apexcoach.api.telemetry.TelemetryPointSnapshot;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ConsistencyAnalyzer {

    public List<DrivingMetric> analyze(List<TelemetryPointSnapshot> points) {
        List<DrivingMetric> metrics = new ArrayList<>();

        double avgSpeed = points.stream()
                .mapToDouble(TelemetryPointSnapshot::speed)
                .average()
                .orElse(0);

        double speedVariance = points.stream()
                .mapToDouble(point -> Math.pow(point.speed() - avgSpeed, 2))
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