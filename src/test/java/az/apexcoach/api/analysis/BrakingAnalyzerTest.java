package az.apexcoach.api.analysis;

import az.apexcoach.api.analysis.internal.BrakingAnalyzer;
import az.apexcoach.api.analysis.internal.DrivingMetric;
import az.apexcoach.api.telemetry.TelemetryPointSnapshot;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BrakingAnalyzerTest {

    private final BrakingAnalyzer analyzer = new BrakingAnalyzer();

    @Test
    void reportsMaximumPressureAndFullBrakingSamples() {
        List<DrivingMetric> metrics = analyzer.analyze(List.of(
                point(0.2),
                point(1.0),
                point(0.5)
        ));

        assertAll(
                () -> assertEquals(2, metrics.size()),
                () -> assertEquals(DrivingMetricType.BRAKING, metrics.getFirst().type()),
                () -> assertEquals("Maximum brake pressure", metrics.getFirst().name()),
                () -> assertEquals(1.0, metrics.getFirst().value()),
                () -> assertEquals("INFO", metrics.getFirst().severity()),
                () -> assertEquals("Full braking samples", metrics.get(1).name()),
                () -> assertEquals(1.0, metrics.get(1).value()),
                () -> assertEquals("INFO", metrics.get(1).severity())
        );
    }

    @Test
    void warnsWhenFullBrakingCapacityIsNotUsed() {
        List<DrivingMetric> metrics = analyzer.analyze(List.of(
                point(0.3),
                point(0.4)
        ));

        assertAll(
                () -> assertEquals(0.4, metrics.getFirst().value()),
                () -> assertEquals("WARN", metrics.getFirst().severity()),
                () -> assertEquals(0.0, metrics.get(1).value()),
                () -> assertEquals("WARN", metrics.get(1).severity())
        );
    }

    @Test
    void handlesLapWithoutBrakingData() {
        List<DrivingMetric> metrics = analyzer.analyze(List.of());

        assertAll(
                () -> assertEquals(2, metrics.size()),
                () -> assertEquals(0.0, metrics.getFirst().value()),
                () -> assertEquals("WARN", metrics.getFirst().severity()),
                () -> assertEquals(0.0, metrics.get(1).value())
        );
    }

    private TelemetryPointSnapshot point(double brake) {
        return new TelemetryPointSnapshot(0.0, 100.0, 0.5, brake, 0.0);
    }
}