package io.sytac.resumator.employee;

import com.theoryinpractise.halbuilder.api.Representation;
import io.sytac.resumator.organization.Organization;
import io.sytac.resumator.organization.OrganizationRepository;
import io.sytac.resumator.security.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

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
import static org.mockito.Mockito.when;

/**
 * Tests the Employees resource
 */
@RunWith(MockitoJUnitRunner.class)
public class EmployeesQueryTest {

    private static final String URI_BASE = "http://base.uri";

    private static final String URI_ABSOLUTE = URI_BASE + "/employees";

    private static final String URI_REQUEST = "http://request.uri";

    @Mock
    private OrganizationRepository organizations;

    @Mock
    private Organization organization;

    @Mock
    private User user;

    @Mock
    private UriInfo uriInfo;

    @InjectMocks
    private EmployeesQuery employeesQuery;

    @Before
    public void before() throws URISyntaxException {
        when(organizations.get(anyString())).thenReturn(Optional.of(organization));
        when(user.getOrganizationId()).thenReturn("dummy");

        when(uriInfo.getAbsolutePath()).thenReturn(new URI(URI_ABSOLUTE));
        when(uriInfo.getBaseUri()).thenReturn(new URI(URI_BASE));
        when(uriInfo.getRequestUri()).thenReturn(new URI(URI_REQUEST));
    }

    @Test
    public void getEmployeesPageLowerThan1ReturnsPage1() {
        List<Employee> employees = getNumberOfEmployees(EmployeesQuery.DEFAULT_PAGE_SIZE + 1);

        when(organization.getEmployees()).thenReturn(employees);

        Representation actual = employeesQuery.getEmployees(0, user, uriInfo);
        assertThat(actual.getResourcesByRel("employees").size(), equalTo(EmployeesQuery.DEFAULT_PAGE_SIZE));
    }

    @Test
    public void getEmployeesPage2ReturnsPage2() {
        List<Employee> employees = getNumberOfEmployees(EmployeesQuery.DEFAULT_PAGE_SIZE + 1);

        when(organization.getEmployees()).thenReturn(employees);

        Representation actual = employeesQuery.getEmployees(2, user, uriInfo);
        assertThat(actual.getResourcesByRel("employees").size(), equalTo(1));
    }

    @Test
    public void getEmployeesPageHigherThanNumberPagesReturnsNoEmployees() {
        List<Employee> employees = getNumberOfEmployees(5);

        when(organization.getEmployees()).thenReturn(employees);

        Representation actual = employeesQuery.getEmployees(2, user, uriInfo);
        assertThat(actual.getResourcesByRel("employees").size(), equalTo(0));
    }

    @Test
    public void getEmployeesReturnsExpectedLinks() {
        List<Employee> employees = getNumberOfEmployees(EmployeesQuery.DEFAULT_PAGE_SIZE + 1);

        when(organization.getEmployees()).thenReturn(employees);

        Representation actual = employeesQuery.getEmployees(1, user, uriInfo);
        assertThat(actual.getLinkByRel("self").getHref(), equalTo(URI_REQUEST));
        assertThat(actual.getLinkByRel("employees").getHref(), equalTo(URI_ABSOLUTE));
        assertThat(actual.getLinkByRel("next").getHref(), equalTo(URI_ABSOLUTE + "?page=2"));
    }

    @Test
    public void eachEmployeeInGetEmployeesHasSelfLink() {
        List<Employee> employees = getNumberOfEmployees(5);

        when(organization.getEmployees()).thenReturn(employees);

        Representation actual = employeesQuery.getEmployees(1, user, uriInfo);
        actual.getResourcesByRel("employees")
                .forEach(resource -> assertThat(resource.getLinkByRel("self").getHref(),
                        equalTo(URI_ABSOLUTE + "/" + resource.getProperties().get("id"))));
    }

    private List<Employee> getNumberOfEmployees(int numberOfEmployees) {
        return Stream.generate(() -> getDummyEmployee(UUID.randomUUID().toString()))
                .limit(numberOfEmployees)
                .collect(toList());
    }

    private Employee getDummyEmployee(final String id) {
        return new Employee(id, "title", "name", "surname", "email", "phoneNumber", null, null, null, null,
                null, null, null, null, null, null);
    }
}