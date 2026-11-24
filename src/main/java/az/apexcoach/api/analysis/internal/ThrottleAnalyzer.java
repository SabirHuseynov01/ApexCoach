package az.apexcoach.api.analysis.internal;

import az.apexcoach.api.analysis.DrivingMetricType;
import az.apexcoach.api.telemetry.TelemetryPointSnapshot;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ThrottleAnalyzer {

    public List<DrivingMetric> analyze(List<TelemetryPointSnapshot> points) {
        List<DrivingMetric> metrics = new ArrayList<>();

        double avgThrottle = points.stream()
                .mapToDouble(TelemetryPointSnapshot::throttle)
                .average()
                .orElse(0);

        double fullThrottlePoints = points.stream()
                .filter(point -> point.throttle() > 0.95)
                .count();

        metrics.add(new DrivingMetric(
                DrivingMetricType.THROTTLE,
                "Average throttle",
                avgThrottle,
                "ratio",
                avgThrottle > 0.6 ? "GOOD" : "INFO",
                "Average throttle application across the lap."
        ));

        metrics.add(new DrivingMetric(
                DrivingMetricType.THROTTLE,
                "Full throttle samples",
                (double) fullThrottlePoints,
                "samples",
                fullThrottlePoints > 0 ? "GOOD" : "WARN",
                fullThrottlePoints > 0
                        ? "Full throttle zones detected."
                        : "Very limited full throttle usage detected."
        ));

        return metrics;
    }
}