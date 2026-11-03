package az.apexcoach.api.simulation.internal.generic;

import java.util.Map;

public final class GenericCsvColumnMapper {

    public static final Map<String, String> DEFAULT_COLUMNS = Map.ofEntries(
            Map.entry("time", "sessionTime"),
            Map.entry("lap", "lapNumber"),
            Map.entry("lapTime", "lapTime"),
            Map.entry("distance", "lapDistance"),
            Map.entry("speed", "speed"),
            Map.entry("rpm", "rpm"),
            Map.entry("gear", "gear"),
            Map.entry("throttle", "throttle"),
            Map.entry("brake", "brake"),
            Map.entry("clutch", "clutch"),
            Map.entry("steering", "steeringAngle"),
            Map.entry("latG", "lateralG"),
            Map.entry("longG", "longitudinalG"),
            Map.entry("vertG", "verticalG"),
            Map.entry("x", "posX"),
            Map.entry("y", "posY"),
            Map.entry("z", "posZ"),
            Map.entry("yaw", "yaw"),
            Map.entry("pitch", "pitch"),
            Map.entry("roll", "roll")
    );

    private GenericCsvColumnMapper() {
    }
}
