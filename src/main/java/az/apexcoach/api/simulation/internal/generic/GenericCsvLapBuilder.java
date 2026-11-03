package az.apexcoach.api.simulation.internal.generic;

import az.apexcoach.api.simulation.NormalizedLap;
import az.apexcoach.api.simulation.NormalizedTelemetryPoint;
import az.apexcoach.api.simulation.TyreInfo;
import org.springframework.stereotype.Component;
import shared.error.TelemetryParseException;

import java.util.*;

@Component
public class GenericCsvLapBuilder {

    public List<NormalizedLap> build(List<Map<String, String>> rows) {
        Map<Integer, List<NormalizedTelemetryPoint>> laps = new TreeMap<>();

        for (Map<String, String> row : rows) {
            int lapNumber = parseInt(row.get("lap"), 1);
            laps.computeIfAbsent(lapNumber, k -> new ArrayList<>())
                    .add(toPoint(row));
        }

        return laps.entrySet().stream()
                .map(entry -> new NormalizedLap(
                        entry.getKey(),
                        parseDouble(entry.getValue().getLast().get("lapTime")),
                        null,
                        null,
                        null,
                        true,
                        false,
                        false,
                        entry.getValue()
                ))
                .toList();
    }

    private NormalizedTelemetryPoint toPoint(Map<String, String> row) {
        return new NormalizedTelemetryPoint(
                parseDouble(row.get("sessionTime")),
                parseDouble(row.get("lapTime")),
                parseDouble(row.get("lapDistance")),
                parseDouble(row.get("speed")),
                parseDouble(row.get("rpm")),
                parseInt(row.get("gear"), 0),
                parseDouble(row.get("throttle")),
                parseDouble(row.get("brake")),
                parseDouble(row.get("clutch")),
                parseDouble(row.get("steeringAngle")),
                parseDouble(row.get("lateralG")),
                parseDouble(row.get("longitudinalG")),
                parseDouble(row.get("verticalG")),
                parseDouble(row.get("posX")),
                parseDouble(row.get("posY")),
                parseDouble(row.get("posZ")),
                parseDouble(row.get("yaw")),
                parseDouble(row.get("pitch")),
                parseDouble(row.get("roll")),
                TyreInfo.empty()
        );
    }

    private Double parseDouble(String value) {
        try {
            return value == null || value.isBlank() ? 0.0 : Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new TelemetryParseException("Invalid numeric telemetry value: " + value);
        }
    }

    private int parseInt(String value, int defaultValue) {
        try {
            return value == null || value.isBlank() ? defaultValue : Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
