package az.apexcoach.api.analysis;

import az.apexcoach.api.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/analysis")
@RequiredArgsConstructor
public class AnalysisController {

    private final AnalysisService analysisService;

    @PostMapping("/laps/{lapId}")
    public ResponseEntity<LapAnalysisDTO> analyze(
            @CurrentUser String email,
            @PathVariable UUID lapId
    ) {
        return ResponseEntity.ok(analysisService.analyzeLap(lapId));
    }

    @GetMapping("/laps/{lapId}")
    public ResponseEntity<LapAnalysisDTO> getAnalysis(
            @CurrentUser String email,
            @PathVariable UUID lapId
    ) {
        return ResponseEntity.ok(analysisService.getAnalysis(lapId));
    }
}