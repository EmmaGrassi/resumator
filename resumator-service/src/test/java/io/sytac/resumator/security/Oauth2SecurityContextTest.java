package io.sytac.resumator.security;

import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Test role access levels
 */
public class Oauth2SecurityContextTest {

    private Oauth2SecurityContext ctx = new Oauth2SecurityContext(Optional.of(User.ANONYMOUS));

    @Test
    public void sysAdminIsInAllRoles() throws Exception {
        final User root = new User("Sytac", "root", Sets.newHashSet(Roles.SYSADMIN));
        ctx = new Oauth2SecurityContext(Optional.of(root));

        assertTrue("Sysadmins must be able to access user-restricted features", ctx.isUserInRole(Roles.USER));
        assertTrue("Sysadmins must be able to access anon-restricted features", ctx.isUserInRole(Roles.ANON));
        assertTrue("Sysadmins must be able to access admin-restricted features", ctx.isUserInRole(Roles.ADMIN));
        assertTrue("Sysadmins must be able to access superuser-restricted features", ctx.isUserInRole(Roles.SYSADMIN));
    }

    @Test
    public void noUserMeansAnonymousUser() {
        ctx = new Oauth2SecurityContext(Optional.empty());
        assertEquals("When there's no user it means we're dealing with an anonymous", User.ANONYMOUS, ctx.getUserPrincipal());
    }

    @Test
    public void weDontTrustAnyChannelAtTheMoment() {
        // trusted / secure connections are not supported at this stage
        assertFalse("Secure connections are not supported, we don't trust anything for the time being", ctx.isSecure());
    }
}