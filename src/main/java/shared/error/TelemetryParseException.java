package shared.error;

public class TelemetryParseException extends RuntimeException {

    public TelemetryParseException(String message) {
        super(message);
    }

    public TelemetryParseException(String message, Throwable cause) {
        super(message, cause);
    }
}