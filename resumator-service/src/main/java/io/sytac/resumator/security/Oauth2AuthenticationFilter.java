package io.sytac.resumator.security;

import com.google.common.collect.Sets;

import javax.annotation.Priority;
import javax.security.auth.Subject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.SecurityContext;
import java.io.IOException;
import java.security.Principal;
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

    private class Oauth2SecurityContext implements SecurityContext {

        private final Set<String> roles;
        private final User principal;

        private Oauth2SecurityContext(Set<String> roles, String principal) {
            this.roles = roles;
            this.principal = principal == null ? null : new User(principal);
        }

        @Override
        public Principal getUserPrincipal() {
            return principal;
        }

        @Override
        public boolean isUserInRole(String role) {
            return roles.contains(role);
        }

        @Override
        public boolean isSecure() {
            return false;
        }

        @Override
        public String getAuthenticationScheme() {
            return null;
        }
    }

    private class User implements Principal {

        private final String name;

        private User(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public boolean implies(Subject subject) {
            return false;
        }
    }
}
