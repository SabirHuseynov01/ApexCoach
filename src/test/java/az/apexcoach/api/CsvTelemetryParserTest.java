package az.apexcoach.api;

import az.apexcoach.api.simulation.NormalizedLap;
import az.apexcoach.api.simulation.NormalizedTelemetry;
import az.apexcoach.api.simulation.NormalizedTelemetryPoint;
import az.apexcoach.api.simulation.SimulationTelemetryService;
import az.apexcoach.api.simulation.SimulationType;
import az.apexcoach.api.simulation.TelemetrySource;
import az.apexcoach.api.simulation.internal.generic.GenericCsvAdapter;
import az.apexcoach.api.simulation.internal.generic.GenericCsvLapBuilder;
import az.apexcoach.api.simulation.internal.generic.GenericCsvParser;
import az.apexcoach.api.simulation.TelemetryAdapterRegistry;
import az.apexcoach.api.telemetry.internal.CsvTelemetryParser;
import org.junit.jupiter.api.Test;
import shared.error.TelemetryParseException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CsvTelemetryParserTest {

    private static final String CSV_SAMPLE = """
            time,lap,lapTime,distance,speed,rpm,gear,throttle,brake,clutch,steering,latG,longG,vertG,x,y,z,yaw,pitch,roll
            0.0,1,0.0,0.0,210.5,7200,4,1.0,0.0,0.0,-3.5,0.42,0.11,0.02,10.0,20.0,30.0,0.5,0.01,0.02
            0.5,1,0.5,12.4,245.8,8100,5,0.95,0.0,0.0,-1.2,0.51,0.08,0.03,12.5,22.0,31.5,0.6,0.01,0.02
            0.9,1,0.9,23.7,248.1,8250,5,0.92,0.0,0.0,0.4,0.38,0.05,0.02,14.8,23.7,32.9,0.7,0.01,0.02
            1.4,2,0.0,0.0,180.3,6400,3,0.7,0.1,0.0,-6.1,0.22,0.31,0.01,20.1,25.4,35.2,0.9,0.02,0.01
            1.9,2,0.5,45.2,199.9,7000,4,0.8,0.0,0.0,-2.8,0.33,0.14,0.02,22.6,27.1,36.8,1.0,0.02,0.01
            """;

    private final CsvTelemetryParser parser = new CsvTelemetryParser(
            new SimulationTelemetryService(new TelemetryAdapterRegistry(
                    List.of(new GenericCsvAdapter(new GenericCsvParser(), new GenericCsvLapBuilder()))
            ))
    );

    @Test
    void parsesCsvIntoLapsThroughTheGenericCsvAdapter() {
        NormalizedTelemetry telemetry = parser.parse(CSV_SAMPLE);

        assertAll(
                () -> assertEquals(SimulationType.GENERIC_CSV, telemetry.simulation()),
                () -> assertEquals(TelemetrySource.FILE_UPLOAD, telemetry.source()),
                () -> assertEquals(2, telemetry.laps().size()),
                () -> assertLap(telemetry.laps().getFirst(), 1, 3, 0.9),
                () -> assertLap(telemetry.laps().get(1), 2, 2, 0.5)
        );
    }

    @Test
    void parsesFirstPointValues() {
        NormalizedTelemetryPoint firstPoint = parser.parse(CSV_SAMPLE).laps().getFirst().points().getFirst();

        assertAll(
                () -> assertEquals(0.0, firstPoint.sessionTime()),
                () -> assertEquals(210.5, firstPoint.speed()),
                () -> assertEquals(7200.0, firstPoint.rpm()),
                () -> assertEquals(4, firstPoint.gear()),
                () -> assertEquals(1.0, firstPoint.throttle()),
                () -> assertEquals(-3.5, firstPoint.steeringAngle()),
                () -> assertEquals(0.42, firstPoint.lateralG()),
                () -> assertEquals(10.0, firstPoint.posX()),
                () -> assertEquals(0.02, firstPoint.roll())
        );
    }

    @Test
    void rejectsMissingContent() {
        assertAll(
                () -> assertThrows(TelemetryParseException.class, () -> parser.parse((byte[]) null)),
                () -> assertThrows(TelemetryParseException.class, () -> parser.parse(new byte[0])),
                () -> assertThrows(TelemetryParseException.class, () -> parser.parse((String) null))
        );
    }

    private void assertLap(NormalizedLap lap, int lapNumber, int points, double lastPointLapTime) {
        assertAll(
                () -> assertEquals(lapNumber, lap.lapNumber()),
                () -> assertEquals(points, lap.points().size()),
                () -> assertEquals(lastPointLapTime, lap.lapTime()),
                () -> assertEquals(lastPointLapTime, lap.points().getLast().lapTime()),
                () -> assertFalse(lap.isOutLap()),
                () -> assertFalse(lap.isInLap())
        );
    }
}
