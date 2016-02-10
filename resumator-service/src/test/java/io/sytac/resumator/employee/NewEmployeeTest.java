package io.sytac.resumator.employee;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.sytac.resumator.model.exceptions.InvalidOrganizationException;
import io.sytac.resumator.security.Roles;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.naming.NoPermissionException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.io.Reader;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

/**
 * Tests the NewEmployees resource
 */
@RunWith(MockitoJUnitRunner.class)
public class NewEmployeeTest extends CommonEmployeeTest {

    @Mock
    private NewEmployeeCommand newEmployeeCommandMock;

    @InjectMocks
    private NewEmployee newEmployee;


    @Before
    public void before() throws URISyntaxException {
        super.before();
        when(descriptorsMock.newEmployeeCommand(any(EmployeeCommandPayload.class), eq(DOMAIN))).thenReturn(newEmployeeCommandMock);
        when(organizationMock.addEmployee(eq(newEmployeeCommandMock))).thenReturn(employeeMock);
    }

    @Test
    public void testNewEmployeesOk() throws NoPermissionException {
        final Response response = newEmployee.newEmployee(getEmployeeCommandPayload(), identityMock, uriInfoMock);
        assertNotNull(response);
        assertEquals(HttpStatus.SC_CREATED, response.getStatus());
        assertEquals(URI_ABSOLUTE_PATH + "/" + EMAIL, response.getHeaderString(HttpHeaders.LOCATION.toString()));
    }

    @Test(expected = InvalidOrganizationException.class)
    public void testNewEmployeesWrongOrganisation() throws NoPermissionException {
        when(organizationRepositoryMock.get(eq(ORG_ID))).thenThrow(InvalidOrganizationException.class);
        newEmployee.newEmployee(getEmployeeCommandPayload(), identityMock, uriInfoMock);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNewEmployeesNotAnAdmin() throws NoPermissionException {
        when(identityMock.hasRole(eq(Roles.ADMIN))).thenReturn(false);
        when(identityMock.getName()).thenReturn(WRONG_EMAIL);
        newEmployee.newEmployee(getEmployeeCommandPayload(), identityMock, uriInfoMock);
    }

    @Test(expected = NoPermissionException.class)
    public void testNewEmployeesWithAdminFlag() throws NoPermissionException {
        when(identityMock.hasRole(eq(Roles.ADMIN))).thenReturn(false);
        newEmployee.newEmployee(getEmployeeCommandPayload(true), identityMock, uriInfoMock);
    }

    @Test
    public void newEmployeeNonAdminNotSettingEmployeeTypeResponseStatusIsCreated() throws NoPermissionException {
        when(identityMock.hasRole(eq(Roles.ADMIN))).thenReturn(false);
        newEmployee.newEmployee(getEmployeeCommandPayload(), identityMock, uriInfoMock);
    }

    @Test(expected = NoPermissionException.class)
    public void newEmployeeNonAdminSettingEmployeeTypePermissionExceptionIsThrown() throws NoPermissionException {
        when(identityMock.hasRole(eq(Roles.ADMIN))).thenReturn(false);
        newEmployee.newEmployee(getEmployeeCommandPayload(false, EmployeeType.EMPLOYEE), identityMock, uriInfoMock);
    }

    @Test
    public void newEmployeeAdminSettingEmployeeTypeResponseStatusIsCreated() throws NoPermissionException {
        when(identityMock.hasRole(eq(Roles.ADMIN))).thenReturn(true);
        Response response = newEmployee.newEmployee(getEmployeeCommandPayload(false, EmployeeType.EMPLOYEE), identityMock, uriInfoMock);

        assertThat(response.getStatus(), equalTo(HttpStatus.SC_CREATED));

    }
    
 	@Test
    public void testNewEmployeesValidation() throws NoPermissionException {
        final Map<String, String> fields = getValidationErrors(getEmployeeValidatableCommandPayload());
        assertNotNull("email validation is not done.", fields.get("email"));
        assertNotNull("phonenumber validation is not done.", fields.get("phonenumber"));
        assertNotNull("surname validation is not done.", fields.get("surname"));
    }

    @Test
    public void testNewEmployeesDetailedValidation() throws NoPermissionException {
        final Map<String, String> fields = getValidationErrors(getEmployeeDetailedValidatableCommandPayload());
        assertNotNull("education startyear validation is not done.", fields.get("education.startyear"));
        assertNotNull("dateOfBirth validation is not done.", fields.get("dateOfBirth"));
        assertNotNull("nationality validation is not done.", fields.get("nationality"));
    }

    @SuppressWarnings("unchecked")
    private Map<String, String> getValidationErrors(EmployeeCommandPayload employeeCommandPayload) throws NoPermissionException {
        final Map<String, Object> validationErrors;
        final Response response = newEmployee.newEmployee(employeeCommandPayload, identityMock, uriInfoMock);

        assertNotNull(response);
        assertEquals(response.getStatus(), HttpStatus.SC_BAD_REQUEST);

        try {
            final Reader reader = new StringReader(response.getEntity().toString());
            validationErrors = new ObjectMapper().readValue(reader, HashMap.class);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("An error occurred with http response");
        } catch (Exception e) {
            throw new RuntimeException("Test failed: ", e);
        }

        return (Map<String, String>) validationErrors.get("fields");
    }
}