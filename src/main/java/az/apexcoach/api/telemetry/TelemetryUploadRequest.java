package az.apexcoach.api.telemetry;

import az.apexcoach.api.simulation.SimulationType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class TelemetryUploadRequest {

    @NotNull
    private SimulationType simulationType;

    @NotNull
    private MultipartFile file;
}
