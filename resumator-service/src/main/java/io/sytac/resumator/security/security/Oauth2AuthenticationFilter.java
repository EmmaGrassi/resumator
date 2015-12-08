package io.sytac.resumator.security.security;

import com.google.common.collect.Sets;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
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

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        final Set<String> roles = Collections.unmodifiableSet(Sets.newHashSet("anonymous"));
        final SecurityContext sc = new Oauth2SecurityContext(roles, null);

        requestContext.setSecurityContext(sc);
    }

}
