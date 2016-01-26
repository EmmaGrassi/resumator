package io.sytac.resumator.employee;

import com.theoryinpractise.halbuilder.api.Representation;
import io.sytac.resumator.command.CommandFactory;
import io.sytac.resumator.events.EventPublisher;
import io.sytac.resumator.model.exceptions.InvalidOrganizationException;
import io.sytac.resumator.organization.Organization;
import io.sytac.resumator.organization.OrganizationRepository;
import io.sytac.resumator.security.User;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

/**
 * Tests the Employees resource
 */
@RunWith(MockitoJUnitRunner.class)
public class NewEmployeeTest {

    public static final String ORG_ID = "org";
    private static final String URI_BASE = "http://base.uri";
    private static final String URI_ABSOLUTE_PATH = URI_BASE + "/employees";
    public static final String UUID = "6743e653-f3cc-4580-84e8-f44ee8531128";
    public static final String DOMAIN = "domain";

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
    private NewEmployeeCommand newEmployeeCommandMock;

    @InjectMocks
    private NewEmployee newEmployee;


    @Before
    public void before() throws URISyntaxException {
        when(organizationRepositoryMock.get(eq(ORG_ID))).thenReturn(Optional.of(organizationMock));
        when(descriptorsMock.newEmployeeCommand(any(EmployeeCommandPayload.class), eq(DOMAIN))).thenReturn(newEmployeeCommandMock);
        when(organizationMock.getDomain()).thenReturn(DOMAIN);
        when(organizationMock.addEmployee(eq(newEmployeeCommandMock))).thenReturn(employeeMock);
        when(userMock.getOrganizationId()).thenReturn(ORG_ID);
        when(employeeMock.getId()).thenReturn(UUID);

        when(uriInfoMock.getAbsolutePath()).thenReturn(new URI(URI_ABSOLUTE_PATH));
        when(uriInfoMock.getBaseUri()).thenReturn(new URI(URI_BASE));
    }

    @Test
    public void testNewEmployeesOk() {
        final Response response = newEmployee.newEmployee(getEmployeeCommandPayload(), userMock, uriInfoMock);
        assertNotNull(response);
        assertEquals(response.getStatus(), HttpStatus.SC_CREATED);
        assertEquals(response.getHeaderString("Location"), URI_ABSOLUTE_PATH + "/" + UUID);
    }

    @Test(expected = InvalidOrganizationException.class)
    public void testNewEmployeesWrongOrganisation() {
        when(organizationRepositoryMock.get(eq(ORG_ID))).thenThrow(InvalidOrganizationException.class);
        newEmployee.newEmployee(getEmployeeCommandPayload(), userMock, uriInfoMock);
    }

    private EmployeeCommandPayload getEmployeeCommandPayload() {
        return new EmployeeCommandPayload("title", "name", "surname", "email", "phoneNumber", null, null, null, null,
                null, null, null, null, null, null);
    }
}