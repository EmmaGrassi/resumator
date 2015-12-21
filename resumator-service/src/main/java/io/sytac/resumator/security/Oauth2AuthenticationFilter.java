package io.sytac.resumator.security;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.SecurityContext;
import java.io.IOException;
import java.util.Optional;

/**
 * Sets up a security context
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
@Priority(Priorities.AUTHENTICATION)
public class Oauth2AuthenticationFilter implements ContainerRequestFilter {

    public static final String AUTHENTICATION_COOKIE = "resumatorJWT";

    final Oauth2SecurityService security;

    @Inject
    public Oauth2AuthenticationFilter(Oauth2SecurityService security) {
        this.security = security;
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        defineSecurityContext(Optional.ofNullable(requestContext.getCookies()
                .get(AUTHENTICATION_COOKIE)))
                .ifPresent(requestContext::setSecurityContext);
    }

    private Optional<SecurityContext> defineSecurityContext(final Optional<Cookie> maybeCookie) {
        return maybeCookie.map(cookie -> {
            final Optional<User> maybeUser = Optional.ofNullable(security.authenticateUser(cookie.getValue()));
            return new Oauth2SecurityContext(maybeUser);
        });
    }

}
