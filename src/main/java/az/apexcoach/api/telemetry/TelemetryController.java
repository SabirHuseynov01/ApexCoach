package az.apexcoach.api.telemetry;

import az.apexcoach.api.security.CurrentUser;
import az.apexcoach.api.simulation.SimulationType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/telemetry")
@RequiredArgsConstructor
public class TelemetryController {

    private final TelemetryService telemetryService;

    @PostMapping("/upload")
    public ResponseEntity<TelemetryUploadResponse> upload(
            @CurrentUser String email,
            @RequestParam SimulationType simulationType,
            @RequestParam MultipartFile file
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(telemetryService.upload(email, simulationType, file));
    }

    @GetMapping("/{telemetryFileId}/laps")
    public ResponseEntity<List<LapDTO>> getLaps(
            @CurrentUser String email,
            @PathVariable UUID telemetryFileId
    ) {
        return ResponseEntity.ok(telemetryService.getLaps(email, telemetryFileId));
    }
}