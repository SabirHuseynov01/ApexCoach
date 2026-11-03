package az.apexcoach.api.analysis.internal;

import az.apexcoach.api.analysis.DrivingMetricDTO;
import az.apexcoach.api.analysis.DrivingMetricType;

public record DrivingMetric(
        DrivingMetricType type,
        String name,
        Double value,
        String unit,
        String severity,
        String message
) {

    public DrivingMetricDTO toDto() {
        return new DrivingMetricDTO(type, name, value, unit, severity, message);
    }
}