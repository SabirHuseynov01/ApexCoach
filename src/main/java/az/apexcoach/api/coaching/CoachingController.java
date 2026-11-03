package az.apexcoach.api.coaching;

import az.apexcoach.api.security.CurrentUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/coaching")
@RequiredArgsConstructor
public class CoachingController {

    private final CoachingService coachingService;

    @PostMapping("/laps")
    public ResponseEntity<CoachingResponse> coach(
            @CurrentUser String email,
            @Valid @RequestBody CoachingRequest request
    ) {
        return ResponseEntity.ok(coachingService.coach(request.lapId()));
    }
}