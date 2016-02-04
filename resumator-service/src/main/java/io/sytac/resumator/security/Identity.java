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
public class Identity implements Principal {

    public static final Identity ANONYMOUS = new Identity("", "___anonymous", Collections.emptySet());

    private final String organizationId;
    private final String name;
    private final Set<String> roles;

    public Identity(final String organizationId, final String name, final Set<String> roles) {
        this.organizationId = organizationId;
        this.name = name;
        this.roles = Collections.unmodifiableSet(roles);
    }

    @Override
    public String getName() {
        return name;
    }

    public boolean hasRole(String role) {
        return isSuperUser() || roles.contains(role);
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public boolean isSuperUser() {
        return roles.contains(Roles.SYSADMIN);
    }
}
