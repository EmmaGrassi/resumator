package io.sytac.resumator.employee;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.io.Reader;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.naming.NoPermissionException;
import javax.naming.OperationNotSupportedException;
import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.sytac.resumator.model.exceptions.InvalidOrganizationException;
import io.sytac.resumator.security.Roles;

/**
 * Tests the UpdateEmployees resource
 */
@RunWith(MockitoJUnitRunner.class)
public class UpdateEmployeeTest extends CommonEmployeeTest {

    @Mock
    private UpdateEmployeeCommand updateEmployeeCommandMock;

    @InjectMocks
    private UpdateEmployee updateEmployee;


    @Before
    public void before() throws URISyntaxException {
        super.before();
        when(descriptorsMock.updateEmployeeCommand(eq(UUID), any(EmployeeCommandPayload.class), eq(DOMAIN))).thenReturn(updateEmployeeCommandMock);
        when(organizationMock.updateEmployee(eq(updateEmployeeCommandMock))).thenReturn(employeeMock);
    }

    @Test
    public void testUpdateEmployeesOk() throws NoPermissionException, OperationNotSupportedException {
        final Response response = updateEmployee.updateEmployee(EMAIL, getEmployeeCommandPayload(), userMock, uriInfoMock);
        assertNotNull(response);
        assertEquals(response.getStatus(), HttpStatus.SC_OK);
        assertEquals(response.getHeaderString("Location"), URI_ABSOLUTE_PATH + "/" + EMAIL);
    }

    @Test
    public void testUpdateEmployeesNotFound() throws NoPermissionException, OperationNotSupportedException {
        when(organizationMock.getEmployeeByEmail(eq(EMAIL))).thenReturn(null);
        final Response response = updateEmployee.updateEmployee(EMAIL, getEmployeeCommandPayload(), userMock, uriInfoMock);
        assertNotNull(response);
        assertEquals(response.getStatus(), HttpStatus.SC_NOT_FOUND);
    }

    @Test(expected = InvalidOrganizationException.class)
    public void testUpdateEmployeesWrongOrganisation() throws NoPermissionException, OperationNotSupportedException {
        when(organizationRepositoryMock.get(eq(ORG_ID))).thenThrow(InvalidOrganizationException.class);
        updateEmployee.updateEmployee(EMAIL, getEmployeeCommandPayload(), userMock, uriInfoMock);
    }

    @Test(expected = NoPermissionException.class)
    public void testUpdateEmployeesWithNoPermissions() throws NoPermissionException, OperationNotSupportedException {
        when(userMock.getName()).thenReturn(WRONG_EMAIL);
        when(userMock.hasRole(eq(Roles.ADMIN))).thenReturn(false);
        updateEmployee.updateEmployee(EMAIL, getEmployeeCommandPayload(), userMock, uriInfoMock);
    }

    @Test(expected = OperationNotSupportedException.class)
    public void testUpdateEmployeesDifferentEmailInPayload() throws NoPermissionException, OperationNotSupportedException {
        updateEmployee.updateEmployee(WRONG_EMAIL, getEmployeeCommandPayload(), userMock, uriInfoMock);
    }

    @Test(expected = NoPermissionException.class)
    public void testUpdateEmployeesNotAdminChangeAdminFlag() throws NoPermissionException, OperationNotSupportedException {
        when(userMock.hasRole(eq(Roles.ADMIN))).thenReturn(false);
        updateEmployee.updateEmployee(EMAIL, getEmployeeCommandPayload(true), userMock, uriInfoMock);
    }
    
    @SuppressWarnings("unchecked")
	@Test
    public void testNewEmployeesDetailedValidation() throws NoPermissionException, OperationNotSupportedException {
        final Response response = updateEmployee.updateEmployee(EMAIL,getEmployeeDetailedValidatableCommandPayload(), userMock, uriInfoMock);
        assertNotNull(response);
        assertEquals(response.getStatus(), HttpStatus.SC_BAD_REQUEST);
        Map<String,Object> validationErrors= new HashMap<>();

        ObjectMapper objectMapper = new ObjectMapper();
        Reader reader = new StringReader(response.getEntity().toString());
        
        try {
			validationErrors = objectMapper.readValue(reader, HashMap.class);
		} catch (Exception e) {
			throw new IllegalArgumentException("An error occured with http response");
		}
        Map<String,String> fields=(Map<String, String>) validationErrors.get("fields");
        assertNotNull("Education startyear validation is not done.", fields.get("Education.startyear"));
        assertNotNull("dateOfBirth validation is not done.", fields.get("dateOfBirth"));
        assertNotNull("nationality validation is not done.", fields.get("nationality"));
        assertNotNull("course year validation is not done.", fields.get("course.year"));

    }


}