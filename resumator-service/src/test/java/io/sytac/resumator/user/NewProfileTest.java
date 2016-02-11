package io.sytac.resumator.user;

import io.sytac.resumator.security.Roles;
import org.apache.http.HttpStatus;
import org.eclipse.jetty.http.HttpHeader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.naming.NoPermissionException;
import javax.ws.rs.core.Response;
import java.net.URISyntaxException;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * Tests the NewProfile resource
 */
@RunWith(MockitoJUnitRunner.class)
public class NewProfileTest extends CommonProfileTest {

    @Mock
    private NewProfileCommand newProfileCommandMock;

    @InjectMocks
    private NewProfile newProfile;


    @Before
    public void before() throws URISyntaxException {
        super.before();
        when(descriptorsMock.newProfileCommand(any(ProfileCommandPayload.class))).thenReturn(newProfileCommandMock);
        when(profileRepositoryMock.register(eq(newProfileCommandMock))).thenReturn(profileMock);
    }

    @Test
    public void testNewProfileOk() throws NoPermissionException {
        final Response response = newProfile.newProfile(getProfileCommandPayload(), identityMock, uriInfoMock);

        assertNotNull(response);
        assertEquals(HttpStatus.SC_CREATED, response.getStatus());
        assertEquals(URI_ABSOLUTE_PATH + "/" + EMAIL, response.getHeaderString(HttpHeader.LOCATION.asString()));

        verify(identityMock).hasRole(eq(Roles.ADMIN));
        verify(descriptorsMock).newProfileCommand(any(ProfileCommandPayload.class));
        verify(profileRepositoryMock).register(eq(newProfileCommandMock));
        verifyNoMoreInteractions(newProfileCommandMock, identityMock, descriptorsMock, profileRepositoryMock);
    }

    @Test
    public void testNewProfileAsAdminOk() throws NoPermissionException {
        final Response response = newProfile.newProfile(getProfileCommandPayload(), identityMock, uriInfoMock);

        assertNotNull(response);
        assertEquals(HttpStatus.SC_CREATED, response.getStatus());
        assertEquals(URI_ABSOLUTE_PATH + "/" + EMAIL, response.getHeaderString(HttpHeader.LOCATION.asString()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNewProfileEmailInRequestIsDifferentFromEmailInPayload() throws NoPermissionException {
        when(identityMock.hasRole(eq(Roles.ADMIN))).thenReturn(false);
        when(identityMock.getName()).thenReturn(WRONG_EMAIL);
        newProfile.newProfile(getProfileCommandPayload(false), identityMock, uriInfoMock);
    }

    @Test(expected = NoPermissionException.class)
    public void testNewProfileAddIsAdminFlagFail() throws NoPermissionException {
        when(identityMock.hasRole(eq(Roles.ADMIN))).thenReturn(false);
        newProfile.newProfile(getProfileCommandPayload(true), identityMock, uriInfoMock);
    }

    @Test
    public void testNewEmployeesValidation() throws NoPermissionException {
        final Response response = newProfile.newProfile(getProfileValidatableCommandPayload(), identityMock, uriInfoMock);
        final Map<String, String> fields = getValidationErrors(response);

        assertNotNull("email validation is not done.", fields.get("email"));
        assertNotNull("phonenumber validation is not done.", fields.get("phonenumber"));
        assertNotNull("surname validation is not done.", fields.get("surname"));
    }

    @Test
    public void testNewEmployeesDetailedValidation() throws NoPermissionException {
        final Response response = newProfile.newProfile(getProfileDetailedValidatableCommandPayload(), identityMock, uriInfoMock);
        final Map<String, String> fields = getValidationErrors(response);

        assertNotNull("dateOfBirth validation is not done.", fields.get("dateOfBirth"));
        assertNotNull("nationality validation is not done.", fields.get("nationality"));
    }
}