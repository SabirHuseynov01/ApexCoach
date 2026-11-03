package shared.utils;

import java.time.Duration;

public final class TimeUtils {

    private TimeUtils() {
    }

    public static String formatLapTime(Double seconds) {
        if (seconds == null || seconds < 0) {
            return "--:--.---";
        }

        Duration duration = Duration.ofNanos((long) (seconds * 1_000_000_000));
        long minutes = duration.toMinutes();
        long secondsPart = duration.toSecondsPart();
        long millis = duration.toMillisPart();

        return "%02d:%02d.%03d".formatted(minutes, secondsPart, millis);
    }
}