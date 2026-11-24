package az.apexcoach.api.simulation.internal.rennsport;

import az.apexcoach.api.simulation.NormalizedTelemetry;
import az.apexcoach.api.simulation.SimulationTelemetryAdapter;
import az.apexcoach.api.simulation.SimulationType;
import az.apexcoach.api.simulation.TelemetrySource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RennsportAdapter implements SimulationTelemetryAdapter {

    private final RennsportPacketMapper packetMapper;
    private final RennsportLapBuilder lapBuilder;
    private final RennsportTelemetryClient telemetryClient;

    @Override
    public SimulationType supports() {
        return SimulationType.RENNSPORT;
    }

    @Override
    public TelemetrySource source() {
        return TelemetrySource.REST_API;
    }

    @Override
    public boolean isAvailable() {
        return false;
    }

    @Override
    public byte[] capture() {
        return telemetryClient.fetch();
    }

    @Override
    public NormalizedTelemetry parse(byte[] rawTelemetry) {
        Object packets = packetMapper.map(rawTelemetry);

        return toNormalizedTelemetry(lapBuilder.build(packets));
    }
}