package io.sytac.resumator.security;

import javax.ws.rs.core.SecurityContext;
import java.security.Principal;
import java.util.Set;

/**
 * Created by skuro on 04/12/15.
 */
class Oauth2SecurityContext implements SecurityContext {

    private final Set<String> roles;
    private final User principal;

    Oauth2SecurityContext(Set<String> roles, String principal) {
        this.roles = roles;
        this.principal = principal == null ? null : new User(principal, roles);
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
