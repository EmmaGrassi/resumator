package io.sytac.resumator.employee;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.io.Reader;
import java.io.StringReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.naming.NoPermissionException;
import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.core.IsEqual.equalTo;

import io.sytac.resumator.model.exceptions.InvalidOrganizationException;
import io.sytac.resumator.security.Roles;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

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
        assertEquals(response.getStatus(), HttpStatus.SC_CREATED);
        assertEquals(response.getHeaderString("Location"), URI_ABSOLUTE_PATH + "/" + EMAIL);
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
    public void newEmployee_nonAdminNotSettingEmployeeType_responseStatusIsCreated() throws NoPermissionException {
        when(identityMock.hasRole(eq(Roles.ADMIN))).thenReturn(false);
        newEmployee.newEmployee(getEmployeeCommandPayload(), identityMock, uriInfoMock);
    }

    @Test(expected = NoPermissionException.class)
    public void newEmployee_nonAdminSettingEmployeeType_permissionExceptionIsThrown() throws NoPermissionException {
        when(identityMock.hasRole(eq(Roles.ADMIN))).thenReturn(false);
        newEmployee.newEmployee(getEmployeeCommandPayload(false, EmployeeType.EMPLOYEE), identityMock, uriInfoMock);
    }

    @Test
    public void newEmployee_adminSettingEmployeeType_responseStatusIsCreated() throws NoPermissionException {
        when(identityMock.hasRole(eq(Roles.ADMIN))).thenReturn(true);
        Response response = newEmployee.newEmployee(getEmployeeCommandPayload(false, EmployeeType.EMPLOYEE), identityMock, uriInfoMock);

        assertThat(response.getStatus(), equalTo(HttpStatus.SC_CREATED));

    }
    
    @SuppressWarnings("unchecked")
 	@Test
     public void testNewEmployeesValidation() throws NoPermissionException {
         final Response response = newEmployee.newEmployee(getEmployeeValidatableCommandPayload(), identityMock, uriInfoMock);
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
         assertNotNull("Email validation is not done.", fields.get("email"));
         assertNotNull("phonenumber validation is not done.", fields.get("phonenumber"));
         assertNotNull("surname validation is not done.", fields.get("surname"));

     }
     
     @SuppressWarnings("unchecked")
 	@Test
     public void testNewEmployeesDetailedValidation() throws NoPermissionException {
         final Response response = newEmployee.newEmployee(getEmployeeDetailedValidatableCommandPayload(), identityMock, uriInfoMock);
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

     }

}