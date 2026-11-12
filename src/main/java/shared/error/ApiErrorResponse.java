package shared.error;

import java.net.URI;
import java.time.Instant;

public record ApiErrorResponse(
        int status,
        String title,
        String detail,
        URI type,
        URI instance,
        Instant timestamp
) {

    public static ApiErrorResponse of(
            int status,
            String title,
            String detail,
            String requestUri
    ) {
        return new ApiErrorResponse(
                status,
                title,
                detail,
                URI.create("https://api.apexcoach.az/errors/" + title.toLowerCase().replace(' ', '-')),
                URI.create(requestUri),
                Instant.now()
        );
    }
}
