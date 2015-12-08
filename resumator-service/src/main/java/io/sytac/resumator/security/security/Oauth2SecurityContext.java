package io.sytac.resumator.security.security;

import javax.ws.rs.core.SecurityContext;
import java.security.Principal;
import java.util.Set;

/**
 * Security context for an Oauth2 scheme
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
class Oauth2SecurityContext implements SecurityContext {

    private final User principal;

    Oauth2SecurityContext(Set<String> roles, String principal) {
        this.principal = principal == null ? null : new User(principal, roles);
    }

    @Override
    public Principal getUserPrincipal() {
        return principal;
    }

    @Override
    public boolean isUserInRole(String role) {
        return principal.hasRole(role);
    }

    @Override
    public boolean isSecure() {
        return true;
    }

    @Override
    public String getAuthenticationScheme() {
        return null;
    }
}
