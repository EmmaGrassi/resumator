package io.sytac.resumator.employee;

import io.sytac.resumator.command.CommandFactory;
import io.sytac.resumator.events.EventPublisher;
import io.sytac.resumator.model.exceptions.InvalidOrganizationException;
import io.sytac.resumator.organization.Organization;
import io.sytac.resumator.organization.OrganizationRepository;
import io.sytac.resumator.security.Roles;
import io.sytac.resumator.security.User;
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
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

/**
 * Tests the Employees resource
 */
@RunWith(MockitoJUnitRunner.class)
public class UpdateEmployeeTest {

    public static final String ORG_ID = "org";
    private static final String URI_BASE = "http://base.uri";
    private static final String URI_ABSOLUTE_PATH = URI_BASE + "/employees";
    public static final String UUID = "6743e653-f3cc-4580-84e8-f44ee8531128";
    public static final String DOMAIN = "domain";
    public static final String EMAIL = "email@dot.com";

    @Mock
    private OrganizationRepository organizationRepositoryMock;

    @Mock
    private Organization organizationMock;

    @Mock
    private CommandFactory descriptorsMock;

    @Mock
    private EventPublisher eventsMock;

    @Mock
    private User userMock;

    @Mock
    private UriInfo uriInfoMock;

    @Mock
    private Employee employeeMock;

    @Mock
    private UpdateEmployeeCommand updateEmployeeCommandMock;

    @InjectMocks
    private UpdateEmployee updateEmployee;


    @Before
    public void before() throws URISyntaxException {
        when(organizationRepositoryMock.get(eq(ORG_ID))).thenReturn(Optional.of(organizationMock));
        when(descriptorsMock.updateEmployeeCommand(eq(UUID), any(EmployeeCommandPayload.class), eq(DOMAIN))).thenReturn(updateEmployeeCommandMock);
        when(organizationMock.getDomain()).thenReturn(DOMAIN);
        when(organizationMock.getEmployeeByEmail(eq(EMAIL))).thenReturn(employeeMock);
        when(organizationMock.updateEmployee(eq(updateEmployeeCommandMock))).thenReturn(employeeMock);
        when(userMock.getOrganizationId()).thenReturn(ORG_ID);
        when(userMock.hasRole(eq(Roles.ADMIN))).thenReturn(true);
        when(employeeMock.getId()).thenReturn(UUID);
        when(employeeMock.getEmail()).thenReturn(EMAIL);

        when(uriInfoMock.getAbsolutePath()).thenReturn(new URI(URI_ABSOLUTE_PATH));
        when(uriInfoMock.getBaseUri()).thenReturn(new URI(URI_BASE));
    }

    @Test
    public void testNewEmployeesOk() throws NoPermissionException, OperationNotSupportedException {
        final Response response = updateEmployee.updateEmployee(EMAIL, getEmployeeCommandPayload(), userMock, uriInfoMock);
        assertNotNull(response);
        assertEquals(response.getStatus(), HttpStatus.SC_OK);
        assertEquals(response.getHeaderString("Location"), URI_ABSOLUTE_PATH + "/" + EMAIL);
    }

    @Test(expected = InvalidOrganizationException.class)
    public void testNewEmployeesWrongOrganisation() throws NoPermissionException, OperationNotSupportedException {
        when(organizationRepositoryMock.get(eq(ORG_ID))).thenThrow(InvalidOrganizationException.class);
        updateEmployee.updateEmployee(EMAIL, getEmployeeCommandPayload(), userMock, uriInfoMock);
    }

    private EmployeeCommandPayload getEmployeeCommandPayload() {
        return new EmployeeCommandPayload("title", "name", "surname", EMAIL, "phoneNumber", null, null, null, null,
                null, null, null, null, null, null, false);
    }
}