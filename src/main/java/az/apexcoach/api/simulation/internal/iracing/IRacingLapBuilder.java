package az.apexcoach.api.simulation.internal.iracing;

import az.apexcoach.api.simulation.NormalizedLap;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IRacingLapBuilder {

    public List<NormalizedLap> build(Object packets) {
        throw new UnsupportedOperationException("iRacing lap building not implemented yet");
    }
}