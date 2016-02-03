package io.sytac.resumator.employee;

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
 * Tests the NewEmployees resource
 */
@RunWith(MockitoJUnitRunner.class)
public class RemoveEmployeeTest extends CommonEmployeeTest {


    @Mock
    private RemoveEmployeeCommand removeEmployeeCommandMock;

    @InjectMocks
    private RemoveEmployee removeEmployee;


    @Before
    public void before() throws URISyntaxException {
        super.before();
        when(descriptorsMock.removeEmployeeCommand(eq(UUID), eq(DOMAIN))).thenReturn(removeEmployeeCommandMock);
        doNothing().when(organizationMock).removeEmployee(eq(removeEmployeeCommandMock));
    }

    @Test
    public void testRemoveEmployeesOk() throws NoPermissionException {
        final Response response = removeEmployee.removeEmployee(EMAIL, userMock);
        assertNotNull(response);
        assertEquals(response.getStatus(), HttpStatus.SC_NO_CONTENT);
    }

    @Test
    public void testRemoveEmployeesNotFound() throws NoPermissionException {
        when(organizationMock.getEmployeeByEmail(eq(EMAIL))).thenReturn(null);
        final Response response = removeEmployee.removeEmployee(EMAIL, userMock);
        assertNotNull(response);
        assertEquals(response.getStatus(), HttpStatus.SC_NOT_FOUND);
    }

    @Test(expected = NoPermissionException.class)
    public void testRemoveEmployeesDifferentEmails() throws NoPermissionException {
        when(userMock.hasRole(eq(Roles.ADMIN))).thenReturn(false);
        removeEmployee.removeEmployee(WRONG_EMAIL, userMock);
    }
}