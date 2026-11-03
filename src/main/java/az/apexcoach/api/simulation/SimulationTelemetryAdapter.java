package az.apexcoach.api.simulation;

public interface SimulationTelemetryAdapter {

    SimulationType supports();

    TelemetrySource source();

    boolean isAvailable();

    NormalizedTelemetry parse(byte[] rawTelemetry);
}
