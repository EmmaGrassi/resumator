package io.sytac.resumator.security;

import java.security.Principal;
import java.util.Collections;
import java.util.Set;

/**
 * Model a Resumator user
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
class User implements Principal {

    private final String name;
    private final Set<String> roles;

    public User(final String name, final Set<String> roles) {
        this.name = name;
        this.roles = Collections.unmodifiableSet(roles);
    }

    @Override
    public String getName() {
        return name;
    }

    public boolean hasRole(String role) {
        return roles.contains(role);
    }
}
