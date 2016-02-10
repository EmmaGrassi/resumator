package io.sytac.resumator.employee;

import com.theoryinpractise.halbuilder.api.Representation;
import io.sytac.resumator.organization.Organization;
import io.sytac.resumator.organization.OrganizationRepository;
import io.sytac.resumator.security.Identity;
import io.sytac.resumator.security.Roles;
import io.sytac.resumator.user.Profile;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.naming.NoPermissionException;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

/**
 * Tests the EmployeesQuery resource
 */
@RunWith(MockitoJUnitRunner.class)
public class EmployeesQueryTest {

    private static final String URI_BASE = "http://base.uri";

    private static final String URI_ABSOLUTE_PATH = URI_BASE + "/employees";

    private static final String URI_REQUEST = URI_ABSOLUTE_PATH + "?page=1";

    @Mock
    private OrganizationRepository organizations;

    @Mock
    private Organization organization;

    @Mock
    private Identity identity;

    @Mock
    private UriInfo uriInfo;

    @InjectMocks
    private EmployeesQuery employeesQuery;

    @Before
    public void before() throws URISyntaxException {
        when(organizations.get(anyString())).thenReturn(Optional.of(organization));
        when(identity.getOrganizationId()).thenReturn("dummy");
        when(identity.hasRole(eq(Roles.ADMIN))).thenReturn(true);

        when(uriInfo.getAbsolutePath()).thenReturn(new URI(URI_ABSOLUTE_PATH));
        when(uriInfo.getBaseUri()).thenReturn(new URI(URI_BASE));
        when(uriInfo.getRequestUri()).thenReturn(new URI(URI_REQUEST));
    }

    @Test
    public void getEmployeesPageLowerThan1ReturnsPage1() throws NoPermissionException {
        List<Employee> employees = getNumberOfEmployees(EmployeesQuery.DEFAULT_PAGE_SIZE + 1);

        when(organization.getEmployees()).thenReturn(employees);

        Representation actual = employeesQuery.getEmployees(0, null, identity, uriInfo);
        assertThat(actual.getResourcesByRel("employees").size(), equalTo(EmployeesQuery.DEFAULT_PAGE_SIZE));
    }

    @Test
    public void getEmployeesPage2ReturnsPage2() throws NoPermissionException {
        List<Employee> employees = getNumberOfEmployees(EmployeesQuery.DEFAULT_PAGE_SIZE + 1);

        when(organization.getEmployees()).thenReturn(employees);

        Representation actual = employeesQuery.getEmployees(2, null, identity, uriInfo);
        assertThat(actual.getResourcesByRel("employees").size(), equalTo(1));
    }

    @Test
    public void getEmployeesPageHigherThanNumberPagesReturnsNoEmployees() throws NoPermissionException {
        List<Employee> employees = getNumberOfEmployees(5);

        when(organization.getEmployees()).thenReturn(employees);

        Representation actual = employeesQuery.getEmployees(2, null, identity, uriInfo);
        assertThat(actual.getResourcesByRel("employees").size(), equalTo(0));
    }

    @Test
    public void getEmployeesReturnsExpectedLinks() throws NoPermissionException {
        List<Employee> employees = getNumberOfEmployees(EmployeesQuery.DEFAULT_PAGE_SIZE + 1);

        when(organization.getEmployees()).thenReturn(employees);

        Representation actual = employeesQuery.getEmployees(1, null, identity, uriInfo);
        assertThat(actual.getLinkByRel("self").getHref(), equalTo(URI_REQUEST));
        assertThat(actual.getLinkByRel("employees").getHref(), equalTo(URI_ABSOLUTE_PATH));
        assertThat(actual.getLinkByRel("next").getHref(), equalTo(URI_ABSOLUTE_PATH + "?page=2"));
    }

    @Test
    public void eachEmployeeInGetEmployeesHasSelfLink() throws NoPermissionException {
        List<Employee> employees = getNumberOfEmployees(5);

        when(organization.getEmployees()).thenReturn(employees);

        Representation actual = employeesQuery.getEmployees(1, null, identity, uriInfo);
        actual.getResourcesByRel("employees")
                .forEach(resource -> assertThat(resource.getLinkByRel("self").getHref(),
                        equalTo(URI_ABSOLUTE_PATH + "/" + resource.getProperties().get("email"))));
    }

    @Test
    public void getEmployeesWithTypeFilterReturnsFilteredEmployees() throws NoPermissionException {
        final int nrFreelancers = 3;

        List<Employee> employees = getNumberOfEmployees(5, EmployeeType.EMPLOYEE);
        employees.addAll(getNumberOfEmployees(nrFreelancers, EmployeeType.FREELANCER));

        when(organization.getEmployees()).thenReturn(employees);

        Representation actual = employeesQuery.getEmployees(1, EmployeeType.FREELANCER, identity, uriInfo);
        assertThat(actual.getResourcesByRel("employees").size(), equalTo(nrFreelancers));
    }

    private List<Employee> getNumberOfEmployees(int numberOfEmployees) {
        return Stream.generate(() -> getDummyEmployee(UUID.randomUUID().toString(), EmployeeType.EMPLOYEE))
                .limit(numberOfEmployees)
                .collect(toList());
    }

    private List<Employee> getNumberOfEmployees(int numberOfEmployees, EmployeeType employeeType) {
        return Stream.generate(() -> getDummyEmployee(UUID.randomUUID().toString(), employeeType))
                .limit(numberOfEmployees)
                .collect(toList());
    }

    private Employee getDummyEmployee(final String id, EmployeeType employeeType) {
        final Profile profile = new Profile("", "title", "name", "surname", null, "email", "phoneNumber", null, null,
                null, null, null, null, false);
        return new Employee(id, employeeType, profile, null, null, null, null);
    }
}