package az.apexcoach.api.analysis.internal;

import az.apexcoach.api.analysis.DrivingMetricType;
import az.apexcoach.api.telemetry.internal.TelemetryPointEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SteeringAnalyzer {

    public List<DrivingMetric> analyze(List<TelemetryPointEntity> points) {
        List<DrivingMetric> metrics = new ArrayList<>();

        double steeringChanges = 0;

        for (int i = 1; i < points.size(); i++) {
            double previous = points.get(i - 1).getSteeringAngle();
            double current = points.get(i).getSteeringAngle();

            if (Math.abs(current - previous) > 0.15) {
                steeringChanges++;
            }
        }

        metrics.add(new DrivingMetric(
                DrivingMetricType.STEERING,
                "Steering corrections",
                steeringChanges,
                "corrections",
                steeringChanges > 50 ? "WARN" : "GOOD",
                steeringChanges > 50
                        ? "Frequent steering corrections detected; try smoother inputs."
                        : "Steering inputs look relatively smooth."
        ));

        return metrics;
    }
}