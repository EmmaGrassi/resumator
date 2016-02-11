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
import javax.naming.OperationNotSupportedException;
import javax.ws.rs.core.Response;
import java.net.URISyntaxException;
import java.util.Map;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * Tests the UpdateProfile resource
 */
@RunWith(MockitoJUnitRunner.class)
public class UpdateProfileTest extends CommonProfileTest {

    @Mock
    private UpdateProfileCommand doUpdateCommandMock;

    @InjectMocks
    private UpdateProfile updateProfile;


    @Before
    public void before() throws URISyntaxException {
        super.before();
        when(descriptorsMock.updateProfileCommand(eq(UUID), any(ProfileCommandPayload.class))).thenReturn(doUpdateCommandMock);
        when(profileRepositoryMock.update(eq(doUpdateCommandMock))).thenReturn(profileMock);
    }

    @Test
    public void testUpdateProfileOk() throws NoPermissionException, OperationNotSupportedException {
        final Response response = updateProfile.doUpdate(EMAIL, getProfileCommandPayload(), identityMock, uriInfoMock);

        assertNotNull(response);
        assertEquals(HttpStatus.SC_OK, response.getStatus());
        assertEquals(URI_ABSOLUTE_PATH + "/" + EMAIL, response.getHeaderString(HttpHeader.LOCATION.asString()));

        verify(identityMock).hasRole(eq(Roles.ADMIN));
        verify(descriptorsMock).updateProfileCommand(eq(UUID), any(ProfileCommandPayload.class));
        verify(profileRepositoryMock).getProfileByEmail(eq(EMAIL));
        verify(profileRepositoryMock).update(eq(doUpdateCommandMock));
        verifyNoMoreInteractions(doUpdateCommandMock, identityMock, descriptorsMock, profileRepositoryMock);
    }

    @Test
    public void testUpdateProfilesNotFound() throws NoPermissionException, OperationNotSupportedException {
        when(profileRepositoryMock.getProfileByEmail(eq(EMAIL))).thenReturn(null);
        final Response response = updateProfile.doUpdate(EMAIL, getProfileCommandPayload(), identityMock, uriInfoMock);
        assertNotNull(response);
        assertEquals(response.getStatus(), HttpStatus.SC_NOT_FOUND);
    }

    @Test(expected = NoPermissionException.class)
    public void testUpdateProfilesWithNoPermissions() throws NoPermissionException, OperationNotSupportedException {
        when(identityMock.getName()).thenReturn(WRONG_EMAIL);
        when(identityMock.hasRole(eq(Roles.ADMIN))).thenReturn(false);
        updateProfile.doUpdate(EMAIL, getProfileCommandPayload(), identityMock, uriInfoMock);
    }

    @Test(expected = OperationNotSupportedException.class)
    public void testUpdateProfilesDifferentEmailInPayload() throws NoPermissionException, OperationNotSupportedException {
        updateProfile.doUpdate(WRONG_EMAIL, getProfileCommandPayload(), identityMock, uriInfoMock);
    }

    @Test(expected = NoPermissionException.class)
    public void testUpdateProfilesNotAdminChangeAdminFlag() throws NoPermissionException, OperationNotSupportedException {
        when(identityMock.hasRole(eq(Roles.ADMIN))).thenReturn(false);
        updateProfile.doUpdate(EMAIL, getProfileCommandPayload(true), identityMock, uriInfoMock);
    }

    @Test
    public void updateProfileNonAdminNotChangingProfileTypeResponseStatusIsOk() throws NoPermissionException, OperationNotSupportedException {
        when(identityMock.hasRole(eq(Roles.ADMIN))).thenReturn(false);
        Response response = updateProfile.doUpdate(EMAIL, getProfileCommandPayload(), identityMock, uriInfoMock);

        assertThat(response.getStatus(), equalTo(org.eclipse.jetty.http.HttpStatus.OK_200));
    }

    @Test(expected = NoPermissionException.class)
    public void updateProfileNonAdminChangingProfileTypePermissionExceptionIsThrown() throws NoPermissionException, OperationNotSupportedException {
        when(identityMock.hasRole(eq(Roles.ADMIN))).thenReturn(false);
        updateProfile.doUpdate(EMAIL, getProfileCommandPayload(true), identityMock, uriInfoMock);
    }

    @Test
    public void updateProfileAdminChangingProfileTypeResponseStatusIsOk() throws NoPermissionException, OperationNotSupportedException {
        when(identityMock.hasRole(eq(Roles.ADMIN))).thenReturn(true);
        Response response = updateProfile.doUpdate(EMAIL, getProfileCommandPayload(true), identityMock, uriInfoMock);

        assertThat(response.getStatus(), equalTo(org.eclipse.jetty.http.HttpStatus.OK_200));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testNewProfilesDetailedValidation() throws NoPermissionException, OperationNotSupportedException {
        final Response response = updateProfile.doUpdate(EMAIL, getProfileDetailedValidatableCommandPayload(), identityMock, uriInfoMock);
        final Map<String, String> fields = getValidationErrors(response);

        assertNotNull("dateOfBirth validation is not done.", fields.get("dateOfBirth"));
        assertNotNull("nationality validation is not done.", fields.get("nationality"));
    }
}