package az.apexcoach.api.identity;

public interface UserLookupPort {
    UserEntity requireUser(String email);
}
