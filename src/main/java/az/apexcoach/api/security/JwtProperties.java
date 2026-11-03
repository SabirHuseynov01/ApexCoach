package az.apexcoach.api.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "apexcoach.security")
public class JwtProperties {

    private String jwtSecret;
    private long jwtExpiration = 3600;
}