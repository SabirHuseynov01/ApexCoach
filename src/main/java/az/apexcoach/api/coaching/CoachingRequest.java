package az.apexcoach.api.coaching;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CoachingRequest(
        @NotNull UUID lapId
) {}