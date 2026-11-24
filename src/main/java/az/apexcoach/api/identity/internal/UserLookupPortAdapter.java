package az.apexcoach.api.identity.internal;

import az.apexcoach.api.identity.UserEntity;
import az.apexcoach.api.identity.UserLookupPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shared.error.NotFoundException;

@Component
@RequiredArgsConstructor
public class UserLookupPortAdapter implements UserLookupPort {

    private final UserRepository userRepository;

    @Override
    public UserEntity requireUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }
}
