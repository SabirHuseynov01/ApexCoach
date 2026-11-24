package az.apexcoach.api.identity;

/**
 * Public API of the identity module for resolving users by email.
 */
public interface UserLookupPort {

    /**
     * @throws shared.error.NotFoundException if no user is registered with the given email
     */
    UserEntity requireUser(String email);
}
