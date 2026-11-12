package az.apexcoach.api.telemetry.internal;

import az.apexcoach.api.simulation.NormalizedTelemetry;
import az.apexcoach.api.simulation.SimulationType;

public interface TelemetryParser {

    SimulationType supports();

    NormalizedTelemetry parse(byte[] content);
}
