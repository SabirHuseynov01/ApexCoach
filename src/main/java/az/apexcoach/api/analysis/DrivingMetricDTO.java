package az.apexcoach.api.analysis;

public record DrivingMetricDTO(
        DrivingMetricType type,
        String name,
        Double value,
        String unit,
        String severity,
        String message
) {}