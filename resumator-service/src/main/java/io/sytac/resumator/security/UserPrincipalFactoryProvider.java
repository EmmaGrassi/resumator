package io.sytac.resumator.security;

import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.server.internal.inject.AbstractContainerRequestValueFactory;
import org.glassfish.jersey.server.internal.inject.AbstractValueFactoryProvider;
import org.glassfish.jersey.server.internal.inject.MultivaluedParameterExtractorProvider;
import org.glassfish.jersey.server.model.Parameter;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

/**
 * Factory provider for the {@link UserPrincipal} annotation.
 */
@Singleton
public class UserPrincipalFactoryProvider extends AbstractValueFactoryProvider {

    @Context
    private SecurityContext securityContext;

    @Inject
    protected UserPrincipalFactoryProvider(MultivaluedParameterExtractorProvider mpep, ServiceLocator locator) {
        super(mpep, locator, Parameter.Source.UNKNOWN);
    }

    @Override
    protected Factory<Identity> createValueFactory(Parameter parameter) {
        Class<?> classType = parameter.getRawType();

        if (classType == null || !classType.equals(Identity.class)) {
            return null;
        }

        return new AbstractContainerRequestValueFactory<Identity>() {
            @Override
            public Identity provide() {
                final Principal user = securityContext.getUserPrincipal();
                if (user instanceof Identity) {
                    return (Identity) user;
                }

                throw new IllegalStateException("Identity is missing, this should never happen.");
            }
        };
    }
}
