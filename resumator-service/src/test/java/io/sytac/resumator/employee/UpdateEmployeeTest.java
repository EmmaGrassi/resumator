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
import javax.naming.OperationNotSupportedException;
import javax.ws.rs.core.Response;
import java.net.URISyntaxException;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

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

    @Test
    public void updateEmployee_nonAdminNotChangingEmployeeType_responseStatusIsOk() throws NoPermissionException, OperationNotSupportedException {
        when(userMock.hasRole(eq(Roles.ADMIN))).thenReturn(false);
        Response response = updateEmployee.updateEmployee(EMAIL, getEmployeeCommandPayload(), userMock, uriInfoMock);

        assertThat(response.getStatus(), equalTo(org.eclipse.jetty.http.HttpStatus.OK_200));
    }

    @Test(expected = NoPermissionException.class)
    public void updateEmployee_nonAdminChangingEmployeeType_permissionExceptionIsThrown() throws NoPermissionException, OperationNotSupportedException {
        when(userMock.hasRole(eq(Roles.ADMIN))).thenReturn(false);
        updateEmployee.updateEmployee(EMAIL, getEmployeeCommandPayload(false, EmployeeType.FREELANCER), userMock, uriInfoMock);
    }

    @Test
    public void updateEmployee_adminChangingEmployeeType_responseStatusIsOk() throws NoPermissionException, OperationNotSupportedException {
        when(userMock.hasRole(eq(Roles.ADMIN))).thenReturn(true);
        Response response = updateEmployee.updateEmployee(EMAIL,
                getEmployeeCommandPayload(true, EmployeeType.FREELANCER), userMock, uriInfoMock);

        assertThat(response.getStatus(), equalTo(org.eclipse.jetty.http.HttpStatus.OK_200));
    }
}