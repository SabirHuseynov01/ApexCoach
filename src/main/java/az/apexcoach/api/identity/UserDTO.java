package az.apexcoach.api.identity;

import java.util.UUID;

public record UserDTO(
        UUID id,
        String email,
        String displayName,
        UserRole role
) {}