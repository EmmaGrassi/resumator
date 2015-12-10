package io.sytac.resumator.security;

/**
 * Labels for application roles
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
public interface Roles {

    /**
     * Super user, admin of all communities. Currently unused as we only support just one community
     */
    String SYSADMIN = "root";

    /**
     * Community administrator
     */
    String ADMIN = "admin";

    /**
     * Regular user within the community
     */
    String USER = "user";

    /**
     * A roaming anonymous
     */
    String ANON = "anonymous";

}
