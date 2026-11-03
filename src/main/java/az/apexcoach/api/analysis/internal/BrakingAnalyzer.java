package az.apexcoach.api.analysis.internal;

import az.apexcoach.api.analysis.DrivingMetricType;
import az.apexcoach.api.telemetry.internal.TelemetryPointEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BrakingAnalyzer {

    public List<DrivingMetric> analyze(List<TelemetryPointEntity> points) {
        List<DrivingMetric> metrics = new ArrayList<>();

        double maxBrake = points.stream()
                .mapToDouble(TelemetryPointEntity::getBrake)
                .max()
                .orElse(0);

        double fullBrakePoints = points.stream()
                .filter(point -> point.getBrake() > 0.9)
                .count();

        metrics.add(new DrivingMetric(
                DrivingMetricType.BRAKING,
                "Maximum brake pressure",
                maxBrake,
                "ratio",
                maxBrake > 0.95 ? "INFO" : "WARN",
                maxBrake > 0.95
                        ? "You are reaching full braking pressure."
                        : "You may not be using full braking capacity."
        ));

        metrics.add(new DrivingMetric(
                DrivingMetricType.BRAKING,
                "Full braking samples",
                (double) fullBrakePoints,
                "samples",
                fullBrakePoints > 0 ? "INFO" : "WARN",
                fullBrakePoints > 0
                        ? "Full braking zones detected."
                        : "No clear full-braking zone detected."
        ));

        return metrics;
    }
}