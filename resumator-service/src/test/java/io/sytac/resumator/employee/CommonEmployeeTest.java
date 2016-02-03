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
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.naming.NoPermissionException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

/**
 * Tests the NewEmployees resource
 */
@Ignore
public class CommonEmployeeTest {

    protected static final String ORG_ID = "org";
    protected static final String DOMAIN = "domain";
    protected static final String EMAIL = "email@dot.com";
    protected static final String WRONG_EMAIL = "wrong.email@dot.com";
    protected static final String UUID = "6743e653-f3cc-4580-84e8-f44ee8531128";
    protected static final String URI_BASE = "http://base.uri";
    protected static final String URI_ABSOLUTE_PATH = URI_BASE + "/employees";

    @Mock
    protected OrganizationRepository organizationRepositoryMock;

    @Mock
    protected Organization organizationMock;

    @Mock
    protected CommandFactory descriptorsMock;

    @Mock
    protected EventPublisher eventsMock;

    @Mock
    protected User userMock;

    @Mock
    protected UriInfo uriInfoMock;

    @Mock
    protected Employee employeeMock;


    @Before
    public void before() throws URISyntaxException {
        when(organizationRepositoryMock.get(eq(ORG_ID))).thenReturn(Optional.of(organizationMock));

        when(organizationMock.getDomain()).thenReturn(DOMAIN);
        when(organizationMock.getEmployeeByEmail(eq(EMAIL))).thenReturn(employeeMock);

        when(userMock.getName()).thenReturn(EMAIL);
        when(userMock.hasRole(eq(Roles.ADMIN))).thenReturn(true);
        when(userMock.getOrganizationId()).thenReturn(ORG_ID);

        when(employeeMock.getId()).thenReturn(UUID);
        when(employeeMock.getEmail()).thenReturn(EMAIL);

        when(uriInfoMock.getAbsolutePath()).thenReturn(new URI(URI_ABSOLUTE_PATH));
        when(uriInfoMock.getBaseUri()).thenReturn(new URI(URI_BASE));
    }

    protected EmployeeCommandPayload getEmployeeCommandPayload() {
        return getEmployeeCommandPayload(false);
    }

    protected EmployeeCommandPayload getEmployeeCommandPayload(boolean isAdmin) {
        return new EmployeeCommandPayload("title", "name", "surname", EMAIL, "phoneNumber", null, null, null, null,
                null, null, null, null, null, null, isAdmin);
    }
}