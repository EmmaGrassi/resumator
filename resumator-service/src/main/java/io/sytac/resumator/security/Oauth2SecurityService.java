package io.sytac.resumator.security;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.common.collect.Sets;
import io.sytac.resumator.Configuration;
import io.sytac.resumator.organization.Organization;
import io.sytac.resumator.organization.OrganizationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

import static io.sytac.resumator.ConfigurationEntries.*;
import static io.sytac.resumator.security.Roles.ADMIN;
import static io.sytac.resumator.security.Roles.USER;

/**
 * Authentication service that validates Google JWT tokens
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
public class Oauth2SecurityService {

    private static final Logger LOGGER = LoggerFactory.getLogger(Oauth2SecurityService.class);

    private final Configuration config;
    private final OrganizationRepository organizations;

    @Inject
    public Oauth2SecurityService(final Configuration config, final OrganizationRepository organizations) {
        this.config = config;
        this.organizations = organizations;
    }

    /**
     * Authenticate the token Id against the GoogleId verifier, and return the associated user if authenticated.
     * If security is disabled, return an explicitly fake user.
     * @param idtoken the Id token provided by the Google Authentication on Front End
     * @return the associated user
     */
    public User authenticateUser(final String idtoken) {
        boolean securityDisabled = config.getProperty(SECURITY_DISABLED)
                                         .map(Boolean::parseBoolean)
                                         .orElse(false);
        if (!securityDisabled) {
            final GoogleIdTokenVerifier verifier = buildVerifier();
            final Optional<GoogleIdToken> idToken = verify(verifier, idtoken);
            return toUser(idToken);
        } else {
            return new User("Fake Organization Id", "Fake User", Sets.newHashSet("user"));
        }

    }

    private User toUser(final Optional<GoogleIdToken> idToken) {
        return idToken.map(token -> {
            final Optional<Organization> organization = organizations.fromDomain(token.getPayload().getHostedDomain());
            return organization.map(org -> new User(org.getId(),
                                                    token.getPayload().getEmail(),
                                                    getRoles(token.getPayload().getEmail())))
                        .orElse(User.ANONYMOUS);
        }).orElse(User.ANONYMOUS);
    }

    private Set<String> getRoles(final String user) {
        final Set<String> admins = new HashSet<>(config.getListProperty(ADMIN_ACCOUNT_LIST));
        return admins.contains(user) ? Sets.newHashSet(ADMIN) : Sets.newHashSet(USER);
    }

    private Optional<GoogleIdToken> verify(final GoogleIdTokenVerifier verifier, final String idtoken) {
        final GoogleIdToken token;
        try {
            token = verifier.verify(idtoken);
            return validate(token);
        } catch (GeneralSecurityException | IOException e) {
            LOGGER.warn("Couldn't validate the Google JWT", e);
        }

        return Optional.empty();
    }

    private Optional<GoogleIdToken> validate(final GoogleIdToken token) {
        if (token != null) {
            GoogleIdToken.Payload payload = token.getPayload();
            if (assertCondition("Wrong Google domain", () ->
                    config.getProperty(GOOGLE_APPS_DOMAIN_NAME)
                          .map(domain -> domain.equals(payload.getHostedDomain()))
                          .orElse(false)
                    && assertCondition("Wrong client application ID", () ->
                            config.getProperty(GOOGLE_CLIENT_ID)
                                  .map(id -> id.equals(payload.getAuthorizedParty()))
                                  .orElse(false)))) {
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
