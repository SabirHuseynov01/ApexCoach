package az.apexcoach.api.identity;

import az.apexcoach.api.identity.internal.PasswordHasher;
import az.apexcoach.api.identity.internal.UserRepository;
import az.apexcoach.api.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shared.error.NotFoundException;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class IdentityService {

    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email is already registered");
        }

        UserEntity user = UserEntity.builder()
                .email(request.email().toLowerCase())
                .passwordHash(passwordHasher.hash(request.password()))
                .displayName(request.displayName())
                .role(UserRole.USER)
                .enabled(true)
                .createdAt(Instant.now())
                .build();

        userRepository.save(user);

        return new AuthResponse(
                jwtService.generateToken(user),
                toDto(user)
        );
    }

    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        UserEntity user = userRepository.findByEmail(request.email().toLowerCase())
                .orElseThrow(() -> new NotFoundException("Invalid email or password"));

        if (!passwordHasher.matches(request.password(), user.getPasswordHash())) {
            throw new NotFoundException("Invalid email or password");
        }

        return new AuthResponse(
                jwtService.generateToken(user),
                toDto(user)
        );
    }

    @Transactional(readOnly = true)
    public UserDTO getCurrentUser(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));

        return toDto(user);
    }

    private UserDTO toDto(UserEntity user) {
        return new UserDTO(
                user.getId(),
                user.getEmail(),
                user.getDisplayName(),
                user.getRole()
        );
    }
}