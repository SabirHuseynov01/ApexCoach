package az.apexcoach.api.analysis.internal;

import az.apexcoach.api.analysis.DrivingMetricType;
import az.apexcoach.api.telemetry.internal.TelemetryPointEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import List;
import java.util.List;

import static jdk.internal.org.jline.reader.impl.LineReaderImpl.CompletionType.List;

@Component
public class ThrottleAnalyzer {

    public List<DrivingMetric> analyze(List<TelemetryPointEntity> points) {
        List<DrivingMetric> metrics = new ArrayList<>();

        double avgThrottle = points.stream()
                .mapToDouble(TelemetryPointEntity::getThrottle)
                .average()
                .orElse(0);

        double fullThrottlePoints = points.stream()
                .filter(point -> point.getThrottle() > 0.95)
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