package io.sytac.resumator.security;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.common.collect.Sets;
import com.google.common.eventbus.EventBus;
import io.sytac.resumator.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.function.Supplier;

import static io.sytac.resumator.ConfigurationEntries.GOOGLE_APPS_DOMAIN_NAME;
import static io.sytac.resumator.ConfigurationEntries.GOOGLE_CLIENT_ID;

/**
 * Authentication service that validates Google JWT tokens
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
public class Oauth2SecurityService {

    private static final Logger LOGGER = LoggerFactory.getLogger(Oauth2SecurityService.class);

    private final Configuration config;
    private final EventBus eventBus;

    @Inject
    public Oauth2SecurityService(Configuration config, EventBus eventBus) {
        this.config = config;
        this.eventBus = eventBus;
    }

    public Optional<User> authenticateUser(final String idtoken) {
        final GoogleIdTokenVerifier verifier = buildVerifier();
        final Optional<GoogleIdToken> idToken = verify(verifier, idtoken);
        final Optional<User> user = toUser(idToken);
        return user;
    }

    private Optional<User> toUser(Optional<GoogleIdToken> idToken) {
        return idToken.map(token -> new User(token.getPayload().getAuthorizedParty(), Sets.newHashSet("user")));
    }

    private Optional<GoogleIdToken> verify(GoogleIdTokenVerifier verifier, String idtoken) {
        final GoogleIdToken token;
        try {
            token = verifier.verify(idtoken);
            return validate(token);
        } catch (GeneralSecurityException | IOException e) {
            LOGGER.warn("Couldn't validate the Google JWT", e);
        }

        return Optional.empty();
    }

    private Optional<GoogleIdToken> validate(GoogleIdToken token) {
        if (token != null) {
            GoogleIdToken.Payload payload = token.getPayload();
            if (assertCondition("Wrong Google domain", () -> payload.getHostedDomain().equals(GOOGLE_APPS_DOMAIN_NAME))
                    && assertCondition("Wrong client application ID", () -> Collections.singletonList(GOOGLE_CLIENT_ID).contains(payload.getAuthorizedParty()))) {
                return Optional.of(token);
            }
        }
        return Optional.empty();
    }

    private Boolean assertCondition(final String errorMessage, final Supplier<Boolean> assertion) {
        final Boolean result = assertion.get();
        if(!result) {
            LOGGER.info(errorMessage);
        }

        return result;
    }

    private GoogleIdTokenVerifier buildVerifier() {
        final JsonFactory jsonFactory = new JacksonFactory();
        final HttpTransport httpTransport = new NetHttpTransport();
        return new GoogleIdTokenVerifier.Builder(httpTransport, jsonFactory)
                .setAudience(Collections.singletonList(config.getProperty(GOOGLE_CLIENT_ID).get()))
                .build();
    }

}
