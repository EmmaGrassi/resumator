package io.sytac.resumator.security;

import com.google.common.collect.Sets;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.SecurityContext;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;

/**
 * Sets up a security context
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
@Priority(Priorities.AUTHENTICATION)
public class Oauth2AuthenticationFilter implements ContainerRequestFilter {

    private static final String AUTHENTICATION_COOKIE = "resumatorJWT";

    final Oauth2SecurityService security;

    @Inject
    public Oauth2AuthenticationFilter(Oauth2SecurityService security) {
        this.security = security;
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        final SecurityContext sc = defineSecurityContext(requestContext.getCookies().get(AUTHENTICATION_COOKIE));
        requestContext.setSecurityContext(sc);
    }

    private SecurityContext defineSecurityContext(final Cookie cookie) {
        final User user = security.authenticateUser(cookie.getValue())
                                  .orElse(new User("", Collections.unmodifiableSet(Sets.newHashSet("anonymous"))));
        return new Oauth2SecurityContext(user.getRoles(), user.getName());
    }

}
