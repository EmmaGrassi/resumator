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

import io.sytac.resumator.model.exceptions.InvalidOrganizationException;
import io.sytac.resumator.security.Roles;

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
        final Response response = newEmployee.newEmployee(getEmployeeCommandPayload(), userMock, uriInfoMock);
        assertNotNull(response);
        assertEquals(response.getStatus(), HttpStatus.SC_CREATED);
        assertEquals(response.getHeaderString("Location"), URI_ABSOLUTE_PATH + "/" + EMAIL);
    }

    @Test(expected = InvalidOrganizationException.class)
    public void testNewEmployeesWrongOrganisation() throws NoPermissionException {
        when(organizationRepositoryMock.get(eq(ORG_ID))).thenThrow(InvalidOrganizationException.class);
        newEmployee.newEmployee(getEmployeeCommandPayload(), userMock, uriInfoMock);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNewEmployeesNotAnAdmin() throws NoPermissionException {
        when(userMock.hasRole(eq(Roles.ADMIN))).thenReturn(false);
        when(userMock.getName()).thenReturn(WRONG_EMAIL);
        newEmployee.newEmployee(getEmployeeCommandPayload(), userMock, uriInfoMock);
    }

    @Test(expected = NoPermissionException.class)
    public void testNewEmployeesWithAdminFlag() throws NoPermissionException {
        when(userMock.hasRole(eq(Roles.ADMIN))).thenReturn(false);
        newEmployee.newEmployee(getEmployeeCommandPayload(true), userMock, uriInfoMock);
    }
    
    @SuppressWarnings("unchecked")
 	@Test
     public void testNewEmployeesValidation() throws NoPermissionException {
         final Response response = newEmployee.newEmployee(getEmployeeValidatableCommandPayload(), userMock, uriInfoMock);
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
         final Response response = newEmployee.newEmployee(getEmployeeDetailedValidatableCommandPayload(), userMock, uriInfoMock);
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


 	private EmployeeCommandPayload getEmployeeValidatableCommandPayload() {
 		
     	return new EmployeeCommandPayload("title", "name", null, "email", "0212238sa32", null,null,"2020-01-01","ANDORRAN", "Netherlands", "about", new ArrayList<>(),new ArrayList<>(),
         		new ArrayList<>(), new ArrayList<>(),true);
 	}

}