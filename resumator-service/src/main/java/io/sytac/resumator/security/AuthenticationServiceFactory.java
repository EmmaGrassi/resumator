package io.sytac.resumator.security;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.sytac.resumator.Configuration;
import io.sytac.resumator.organization.OrganizationRepository;
import org.glassfish.hk2.api.Factory;

import javax.inject.Inject;

/**
 * Instantiates the authentication service
 *
 * @author Selman Tayyar
 * @since 0.1
 */
public class AuthenticationServiceFactory implements Factory<AuthenticationService> {

    private final Configuration config;
    private final Oauth2SecurityService securityService;
    private final ObjectMapper objectMapper;

    @Inject
    public AuthenticationServiceFactory(final Configuration config, final ObjectMapper objectMapper,
            final Oauth2SecurityService securityService) {
        this.config = config;
        this.securityService = securityService;
        this.objectMapper = objectMapper;
    }

    @Override
    public AuthenticationService provide() {
        return new AuthenticationService(config, objectMapper,securityService);
    }

    @Override
    public void dispose(AuthenticationService instance) {
        // NOOP
    }
}
