package io.sytac.resumator.security;

import io.sytac.resumator.Configuration;
import io.sytac.resumator.organization.OrganizationRepository;
import org.glassfish.hk2.api.Factory;

import javax.inject.Inject;

/**
 * Instantiates the security service
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
public class Oauth2SecurityServiceFactory implements Factory<Oauth2SecurityService> {

    private final Configuration configuration;
    private final OrganizationRepository organizations;

    @Inject
    public Oauth2SecurityServiceFactory(final Configuration configuration, final OrganizationRepository repository) {
        this.configuration = configuration;
        this.organizations = repository;
    }

    @Override
    public Oauth2SecurityService provide() {
        return new Oauth2SecurityService(configuration, organizations);
    }

    @Override
    public void dispose(Oauth2SecurityService instance) {
        // NOOP
    }
}
