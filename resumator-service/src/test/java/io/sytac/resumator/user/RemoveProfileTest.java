package io.sytac.resumator.user;

import io.sytac.resumator.security.Roles;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.naming.NoPermissionException;
import javax.ws.rs.core.Response;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Tests the RemoveProfile resource
 */
@RunWith(MockitoJUnitRunner.class)
public class RemoveProfileTest extends CommonProfileTest {

    @Mock
    private RemoveProfileCommand removeProfileCommandMock;

    @InjectMocks
    private RemoveProfile removeProfile;


    @Before
    public void before() throws URISyntaxException {
        super.before();
        when(descriptorsMock.removeProfileCommand(eq(UUID))).thenReturn(removeProfileCommandMock);
        doNothing().when(profileRepositoryMock).remove(eq(removeProfileCommandMock));
    }

    @Test
    public void testRemoveProfileOk() throws NoPermissionException {
        final Response response = removeProfile.doRemove(EMAIL, identityMock);
        assertNotNull(response);
        assertEquals(response.getStatus(), HttpStatus.SC_NO_CONTENT);
    }

    @Test
    public void testRemoveProfileNotFound() throws NoPermissionException {
        when(profileRepositoryMock.getProfileByEmail(eq(EMAIL))).thenReturn(null);
        final Response response = removeProfile.doRemove(EMAIL, identityMock);
        assertNotNull(response);
        assertEquals(response.getStatus(), HttpStatus.SC_NOT_FOUND);
    }

    @Test(expected = NoPermissionException.class)
    public void testRemoveEmployeesDifferentEmails() throws NoPermissionException {
        when(identityMock.hasRole(eq(Roles.ADMIN))).thenReturn(false);
        removeProfile.doRemove(WRONG_EMAIL, identityMock);
    }
}