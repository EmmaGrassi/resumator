package io.sytac.resumator.security;

import com.google.common.eventbus.EventBus;
import org.glassfish.hk2.api.Factory;
import io.sytac.resumator.Configuration;

import javax.inject.Inject;

/**
 * Instantiates the security service
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
public class Oauth2SecurityServiceFactory implements Factory<Oauth2SecurityService> {

    private final Configuration configuration;
    private final EventBus eventBus;

    @Inject
    public Oauth2SecurityServiceFactory(final Configuration configuration, final EventBus eventBus) {
        this.configuration = configuration;
        this.eventBus = eventBus;
    }

    @Override
    public Oauth2SecurityService provide() {
        return new Oauth2SecurityService(configuration, eventBus);
    }

    @Override
    public void dispose(Oauth2SecurityService instance) {
        // NOOP
    }
}
