package az.apexcoach.api.coaching;

import java.util.List;
import java.util.UUID;

public record CoachingResponse(
        UUID lapId,
        String summary,
        List<String> actionItems
) {}