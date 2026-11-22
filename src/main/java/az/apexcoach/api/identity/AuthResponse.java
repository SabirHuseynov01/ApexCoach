package az.apexcoach.api.identity;

public record AuthResponse(
        String accessToken,
        UserDTO user
) {}
