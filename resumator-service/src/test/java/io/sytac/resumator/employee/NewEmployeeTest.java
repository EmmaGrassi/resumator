package io.sytac.resumator.employee;

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
import javax.ws.rs.core.Response;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
}